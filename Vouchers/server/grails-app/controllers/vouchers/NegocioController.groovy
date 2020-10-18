package vouchers

import assemblers.NegocioAssembler
import commands.NegocioCommand
import grails.rest.*

class NegocioController extends RestfulController {

    static responseFormats = ['json', 'xml']
    NegocioService negocioService

    NegocioAssembler negocioAssembler = new NegocioAssembler()

    NegocioController() {
        super(Negocio)
    }

    /*
    * Returns business requested by Id
    * URL/businesses/{id}
    */
    def show(Negocio business){
        println("Request for a business by id")
        if (!business){
            response.sendError(404)
        } else {
            respond business
        }
    }

    /*
    * Returns counterfoils requested by Id
    * URL/businesses/getCounterfoils/{id}
    */
    def getCounterfoils(Long id){
        Negocio negocio = Negocio.get(id)
        println("Request counterfoils of business")
        if (!negocio){
            response.sendError(404)
        } else {
            respond negocio.tarifarios
        }
    }

    List<NegocioCommand> obtenerTodos() {
        List<NegocioCommand> negociosCommand = []
        List<Negocio> negocios = negocioService.obtenerTodos()

        for (Negocio negocio : negocios) {
            NegocioCommand negocioCommand = negocioAssembler.toBean(negocio)
            negociosCommand.add(negocioCommand)

        }

        respond negociosCommand
    }

    Negocio crear() {
        Object requestBody = request.JSON

        String nombre = requestBody['nombre']
        String numeroTelefonico = requestBody['numeroTelefonico']
        String categoria = requestBody['categoria']
        String email = requestBody['email']
        String contrasenia = requestBody['contrasenia']
        Boolean cuentaVerificada = requestBody['cuentaVerificada']

        Object direccion = requestBody['direccion']
        String numero = requestBody['numero']
        String departamento = requestBody['departamento']
        String provincia = requestBody['provincia']
        String paisId = requestBody['paisId']

        negocioService.crear()
    }

    /*
    * Returns list of businesses. If specified, it returns a max amount of them.
    * URL/businesses -> When max is not specified
    * URL/businesses?max=n" -> When max is specified
    */
    def index(Integer max){
        println("Request for business list, max size: ${params.max}")
        params.max = Math.min(max ?: 10, 100)
        respond Negocio.list(params)
    }
}
