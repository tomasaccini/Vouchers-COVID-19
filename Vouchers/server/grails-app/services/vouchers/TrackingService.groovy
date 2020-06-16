package vouchers

import commands.TrackingCommand

import javax.transaction.Transactional

@Transactional
class TrackingService {

    void save(TrackingCommand trackingCommand) {

    }

    List<Tracking> list(Map args) {

    }

    // !!!!
    /*
    Tracking get(Serializable id)

    List<Tracking> list(Map args)

    Long count()

    void delete(Serializable id)

    Tracking save(Tracking tracking)
     */
}