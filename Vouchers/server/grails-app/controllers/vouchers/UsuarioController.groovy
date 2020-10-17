package vouchers


import grails.rest.*

class UsuarioController extends RestfulController {
    static responseFormats = ['json', 'xml']

    UsuarioController() {
        super(Cliente)
    }
}
