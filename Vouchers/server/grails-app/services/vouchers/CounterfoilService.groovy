package vouchers

import grails.gorm.transactions.Transactional

@Transactional
class CounterfoilService {

    def serviceMethod() {

    }

    List<Counterfoil> list(Map args) {
        Counterfoil.list(args)
    }
}
