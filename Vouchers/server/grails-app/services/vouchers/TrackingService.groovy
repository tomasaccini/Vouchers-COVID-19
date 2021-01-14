package vouchers

import assemblers.TrackingAssembler
import commands.TrackingCommand
import enums.ProductoTipo
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
            tracking.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }
        return tracking
    }

    // TODO Add filter by client's trackings !!!!
    Map<Tuple2<TrackingType, ProductoTipo>, List<Tracking>> countyByProductTypeAndTrackingType(Cliente client) {
        try {
            Map<Tuple2<TrackingType, ProductoTipo>, List<Tracking>> count = Tracking.list()
                    .groupBy { tracking -> new Tuple2(tracking.tipo, tracking.informacionVoucher.product.tipo) }
            count
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