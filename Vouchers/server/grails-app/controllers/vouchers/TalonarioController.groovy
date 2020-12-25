package vouchers

import grails.rest.*
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class TalonarioController extends RestfulController{

    static responseFormats = ['json', 'xml']
    static allowedMethods = [getAll: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    TalonarioService talonarioService

    TalonarioController() {
        super(Talonario)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond talonarioService.list(params), model:[counterfoilCount: talonarioService.count()]
    }

    def show(Long id) {
        respond talonarioService.get(id)
    }

    List<Talonario> getAll() {
        respond talonarioService.getAll()
    }

    /*
    * Crea un voucher del talonario indicado
    * Se lo asigna al cliente indicado
    * URL/talonarios/comprar
    * body: { talonarioId={id}, clienteId={id} }
    */
    @Transactional
    def comprar(){
        Object requestBody = request.JSON
        Cliente cliente = Cliente.get(requestBody['clienteId']?.toLong())
        Talonario talonario = Talonario.get(requestBody['talonarioId']?.toLong())
        if (!cliente || !talonario){
            return response.sendError(400, "Cliente o talonario erroneo")
        }
        try {
            Voucher voucher = talonario.crearVoucher(cliente)
            respond voucher
        } catch (Exception e) {
            println(e)
            return response.sendError(400, "El Voucher no pudo ser creado: " + e.message)
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
            respond talonarioService.createMock(negocioId, stock, precio, descripcion, validoDesdeStr, validoHastaStr), [status: CREATED]
        } catch (RuntimeException re) {
            response.sendError(400, re.message)
        }
    }



    @Transactional
    def save(Talonario counterfoil) {
        respond talonarioService.createMock(), [status: CREATED]

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
    def update(Talonario counterfoil) {
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
            talonarioService.save(counterfoil)
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

        talonarioService.delete(id)

        render status: NO_CONTENT
    }

    /*
    * Dada una cadena de largo mayor a 2
    * URL/talonario/search?q={busqueda}
    * URL/talonario/search?q={busqueda}&max={maximas respuestas deseadas}
    * Devuelve listado de los talonarios que poseen esa cadena en su descripcion
    */
    def search(String q, Integer max){
        def map = [:]
        map.max = Math.min( max ?: 10, 100)
        if (q &&  q.length() > 2){
            respond talonarioService.findSimilar(q, map)
        } else {
            respond([])
        }
    }
}
