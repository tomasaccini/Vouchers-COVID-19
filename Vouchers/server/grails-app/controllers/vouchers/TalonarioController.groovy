package vouchers

import assemblers.TalonarioAssembler
import assemblers.VoucherAssembler
import commands.TalonarioCommand
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

    /*
    * Crea un voucher del talonario indicado
    * Se lo asigna al cliente indicado
    * URL/talonarios/comprar
    * body: { talonarioId={id}, clienteId={id} }
    */

    def obtenerRecomendaciones() {
        println("Obtener Recomendaciones")

        List<Talonario> talonarios = talonarioService.generarOrdenDeRecomendacion()

        List<TalonarioCommand> talonariosCommands = []

        for (def talonario : talonarios) {
            talonariosCommands.add(talonarioAssembler.toBean(talonario))
        }

        respond talonariosCommands
    }

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
            if (productos.size() == 0){
                throw new RuntimeException('No se puede crear un talonario sin productos')
            }
            Talonario talonario = talonarioService.crear(negocioId, stock, precio, descripcion, validoDesdeStr, validoHastaStr)
            talonarioService.agregarProductos(talonario, productos)
            respond talonarioAssembler.toBean(talonario)
        } catch (RuntimeException re) {
            println("Error")
            response.sendError(400, re.message)
        }
    }

    @Transactional
    def pausar() {
        println('Pausando Talonario')

        Object requestBody = request.JSON

        try {
            Long negocioId = requestBody['negocioId']?.toLong()
            Long talonarioId = requestBody['talonarioId']?.toLong()
            Talonario talonario = talonarioService.pausar(negocioId, talonarioId)
            respond talonarioAssembler.toBean(talonario)
        } catch (RuntimeException e) {
            println(e)
            return response.sendError(400, "Error al pausar talonario:" + e.message)
        }
    }

    @Transactional
    def activar() {
        println('Activando Talonario')

        Object requestBody = request.JSON

        try {
            Long negocioId = requestBody['negocioId']?.toLong()
            Long talonarioId = requestBody['talonarioId']?.toLong()
            Talonario talonario = talonarioService.activar(negocioId, talonarioId)
            respond talonarioAssembler.toBean(talonario)
        } catch (RuntimeException e) {
            println(e)
            return response.sendError(400, "Error al activar talonario:" + e.message)
        }
    }

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
