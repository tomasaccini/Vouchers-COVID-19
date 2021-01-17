package vouchers


import grails.rest.RestfulController

class UsuarioController extends RestfulController {
    static responseFormats = ['json', 'xml']

    UsuarioController() {
        super(Cliente)
    }
}
