package vouchers


import grails.rest.*

class UserController extends RestfulController {
    static responseFormats = ['json', 'xml']
    UserController() {
        super(Client)
    }
}
