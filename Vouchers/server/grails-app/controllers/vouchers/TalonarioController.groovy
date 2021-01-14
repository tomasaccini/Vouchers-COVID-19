package vouchers

import assemblers.TalonarioAssembler
import assemblers.VoucherAssembler
import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

class TalonarioController extends RestfulController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [getAll: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    TalonarioService talonarioService
    TalonarioAssembler talonarioAssembler = new TalonarioAssembler()
    VoucherAssembler voucherAssembler = new VoucherAssembler()

    TalonarioController() {
        super(Talonario)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond talonarioService.list(params), model: [counterfoilCount: talonarioService.count()]
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
    def comprarVoucher() {
        println('Comprar nuevo voucher')

        Object requestBody = request.JSON

        Long clienteId = requestBody['clienteId']?.toLong()
        Long talonarioId = requestBody['talonarioId']?.toLong()

        try {
            Voucher voucher = talonarioService.comprarVoucher(talonarioId, clienteId)
            respond voucherAssembler.toBean(voucher)
        } catch (Exception e) {
            println(e)
            return response.sendError(400, "El Voucher no pudo ser creado: " + e.message)
        }
    }

    def crear() {
        println('Creando nuevo Talonario')

        Object requestBody = request.JSON

        String negocioId
        Integer stock
        Double precio
        String descripcion
        String validoDesdeStr
        String validoHastaStr
        def productos

        try {
            negocioId = requestBody['negocioId']
            stock = requestBody['stock']
            Object informacionVoucher = requestBody['informacionVoucher']
            precio = informacionVoucher['precio']
            descripcion = informacionVoucher['descripcion']
            validoDesdeStr = informacionVoucher['validoDesde']
            validoHastaStr = informacionVoucher['validoHasta']
            productos = requestBody['productos']
        } catch (Exception e) {
            println(e)
            return response.sendError(400, "Error en formato al crear talonario")
        }

        try {
            Talonario talonario = talonarioService.crear(negocioId, stock, precio, descripcion, validoDesdeStr, validoHastaStr)
            talonarioService.agregarProductos(talonario, productos)
            respond talonarioAssembler.toBean(talonario)
        } catch (RuntimeException re) {
            println("Error")
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

        respond counterfoil, [status: OK, view: "show"]
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

    def search(String q, Integer max) {
        def map = [:]
        map.max = Math.min(max ?: 10, 100)
        if (q && q.length() > 2) {
            respond talonarioService.findSimilar(q, map)
        } else {
            respond([])
        }
    }
}
