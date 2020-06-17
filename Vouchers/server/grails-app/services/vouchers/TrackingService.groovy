package vouchers

import assemblers.TrackingAssembler
import commands.TrackingCommand
import enums.ProductType
import enums.TrackingType
import org.hibernate.service.spi.ServiceException

import javax.transaction.Transactional
import javax.xml.bind.ValidationException

@Transactional
class TrackingService {

    TrackingAssembler trackingAssembler

    Tracking save(TrackingCommand trackingCommand) {
        Tracking tracking = TrackingAssembler.toDomain(trackingCommand)
        try {
            tracking.save(flush:true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
        return tracking
    }

    // TODO Add filter by client's trackings !!!!
    Map<Tuple2<TrackingType, ProductType>, List<Tracking>> countyByProductTypeAndTrackingType(Client client) {
        try {
            Map<Tuple2<TrackingType, ProductType>, List<Tracking>> count = Tracking.list()
                    .groupBy { tracking -> new Tuple2(tracking.type, tracking.voucherInformation.product.type) }
            return count
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