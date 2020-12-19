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
    * URL/negocios/{id}
    */
    def show(Negocio negocio){
        println("Request for a business by id")
        if (!negocio){
            response.sendError(404)
        } else {
            respond negocio
        }
    }

    /*
    * Returns counterfoils requested by Id
    * URL/negocios/obtenerTarifarios/{id}
    */
    def obtenerTarifarios(Long id){
        Negocio negocio = Negocio.get(id)
        println("Request: tarifarios de negocio")
        if (!negocio){
            return response.sendError(400, "Negocio inexistente")
        }
        respond negocio.tarifarios
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

    /*
    * Dada una cadena de largo mayor a 2
    * URL/negocios/search?q={busqueda}
    * URL/negocios/search?q={busqueda}&max={maximas respuestas deseadas}
    * Devuelve listado de los negocios que poseen esa cadena en su nombre
    */
    def search(String q, Integer max){
        def map = [:]
        map.max = Math.min( max ?: 10, 100)
        if (q &&  q.length() > 2){
            respond negocioService.findSimilar(q, map)
        } else {
            respond([])
        }
    }
}
