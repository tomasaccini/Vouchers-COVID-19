package vouchers

import assemblers.TrackingAssembler
import commands.TrackingCommand
import enums.TrackingType
import org.hibernate.service.spi.ServiceException

import javax.transaction.Transactional
import javax.xml.bind.ValidationException

@Transactional
class TrackingService {

    TrackingAssembler trackingAssembler

    Tracking save(TrackingCommand trackingCommand) {
        Tracking tracking = trackingAssembler.fromBean(trackingCommand)
        try {
            tracking.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
        return tracking
    }

    List<Tracking> list(Map args) {
        try {
            return Tracking.list(args)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    Map<TrackingType, Long> countByType() {
        try {
            return Tracking.list()
                    .groupBy { tracking -> tracking.type }
                    .collectEntries { type, list -> [(type): list.size()] }
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }
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