package vouchers

import grails.rest.*
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class TarifarioController extends RestfulController{

    static responseFormats = ['json', 'xml']
    static allowedMethods = [getAll: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    TarifarioService tarifarioService

    TarifarioController() {
        super(Tarifario)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tarifarioService.list(params), model:[counterfoilCount: tarifarioService.count()]
    }

    def show(Long id) {
        respond tarifarioService.get(id)
    }

    List<Tarifario> getAll() {
        respond tarifarioService.getAll()
    }

    /*
    * Crea un voucher del tarifario indicado
    * Se lo asigna al cliente indicado
    * URL/tarifarios/comprar
    * body: { tarifarioId={id}, clienteId={id} }
    */
    @Transactional
    def comprar(){
        Object requestBody = request.JSON
        Cliente cliente = Cliente.get(requestBody['clienteId']?.toLong())
        Tarifario tarifario = Tarifario.get(requestBody['tarifarioId']?.toLong())
        if (!cliente || !tarifario){
            return response.sendError(400, "Cliente o tarifario erroneo")
        }
        try {
            Voucher voucher = tarifario.crearVoucher(cliente)
            respond voucher
        } catch (Exception e) {
            println(e)
            return response.sendError(400, "El Voucher no pudo ser creado")
        }
    }

    def crear() {
        Object requestBody = request.JSON

        String negocioId
        Integer stock
        Double precio
        String descripcion
        String validoDesdeStr
        String validoHastaStr

        try {
            negocioId = requestBody['negocioId']
            stock = requestBody['stock']
            Object informacionVoucher = requestBody['informacionVoucher']
            precio = informacionVoucher['precio']
            descripcion = informacionVoucher['descripcion']
            validoDesdeStr = informacionVoucher['validoDesde']
            validoHastaStr = informacionVoucher['validoHasta']
        } catch (Exception e) {
            println(e)
            return response.sendError(400, "!!!! format")
        }

        try {
            respond tarifarioService.createMock(negocioId, stock, precio, descripcion, validoDesdeStr, validoHastaStr), [status: CREATED]
        } catch (RuntimeException re) {
            response.sendError(400, re.message)
        }
    }



    @Transactional
    def save(Tarifario counterfoil) {
        respond tarifarioService.createMock(), [status: CREATED]

        /*
        if (counterfoil == null) {
            render status: NOT_FOUND
            return
        }
        if (counterfoil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond counterfoil.errors
            return
        }

        try {
            counterfoilService.save(counterfoil)
        } catch (ValidationException e) {
            respond counterfoil.errors
            return
        }

        respond counterfoil, [status: CREATED, view:"show"]
         */
    }

    @Transactional
    def update(Tarifario counterfoil) {
        if (counterfoil == null) {
            render status: NOT_FOUND
            return
        }
        if (counterfoil.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond counterfoil.errors
            return
        }

        try {
            tarifarioService.save(counterfoil)
        } catch (ValidationException e) {
            respond counterfoil.errors
            return
        }

        respond counterfoil, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        tarifarioService.delete(id)

        render status: NO_CONTENT
    }

    /*
    * Dada una cadena de largo mayor a 2
    * URL/tarifario/search?q={busqueda}
    * URL/tarifario/search?q={busqueda}&max={maximas respuestas deseadas}
    * Devuelve listado de los tarifarios que poseen esa cadena en su descripcion
    */
    def search(String q, Integer max){
        def map = [:]
        map.max = Math.min( max ?: 10, 100)
        if (q &&  q.length() > 2){
            respond tarifarioService.findSimilar(q, map)
        } else {
            respond([])
        }
    }
}
