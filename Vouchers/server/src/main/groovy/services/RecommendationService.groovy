package services

import enums.ProductoTipo
import enums.TrackingType
import vouchers.Cliente
import vouchers.Talonario
import vouchers.TalonarioService
import vouchers.Tracking
import vouchers.TrackingService

class RecommendationService {

    static final Long DETAIL_POINTS_MULTIPLIER = 1
    static final Long THANKS_POINTS_MULTIPLIER = 20

    TrackingService trackingService
    TalonarioService counterfoilService

    private static Long getPointsMultiplierForTrackingType(TrackingType trackingType) {
        switch (trackingType) {
            case TrackingType.DETAIL:
                DETAIL_POINTS_MULTIPLIER
                break
            case TrackingType.THANKS:
                THANKS_POINTS_MULTIPLIER
                break
            default:
                throw new RuntimeException("Trying to calculate points of an invalid TrackingType.")
        }
    }

    /**
     * If the client does not have purchases, it will return the counterfoils sorted by amount sold.
     * If it does have purchases, it will return the counterfoils after grouping them by the client
     * product type preferences and concatenating each block of counterfoils (by product type).
    */
    List<Talonario> recommendCounterfoils(Cliente client) {
        Map<Tuple2<TrackingType, ProductoTipo>, List<Tracking>> count = trackingService.countyByProductTypeAndTrackingType(client)

        List<Talonario> sortedCounterfoils = defaultCounterfoils()
        List<ProductoTipo> sortedProductTypes = calculateProductTypesWithDecreasingPriority(count)

        if (sortedProductTypes.indexOf()) {
            sortedCounterfoils
        } else {
            personalizeCounterfoils(sortedCounterfoils, sortedProductTypes)
        }
    }

    def defaultCounterfoils() {
        counterfoilService.list().findAll { it.isActive }
                .sort { -it.cantidadVendida }
    }

    List<Talonario> personalizeCounterfoils(List<Talonario> sortedCounterfoils, List<ProductoTipo> sortedProductTypes) {
        Map<ProductoTipo, Integer> priorityPerProductType = sortedProductTypes.withIndex().collectEntries { ProductoTipo entry, int i -> [(entry): i] }
        Map<ProductoTipo, List<Talonario>> counterfoilsPerProductType = sortedCounterfoils.groupBy { it.informacionVoucher.product.tipo }

        List<Tuple2<Long, List<Talonario>>> prioritiesAndCounterFoils = counterfoilsPerProductType.keySet().collect { productType ->
            Long productPriority = priorityPerProductType[productType]
            List<Talonario> counterfoils = counterfoilsPerProductType[productType]

            new Tuple2(productPriority, counterfoils)
        }

        List<Talonario> personalizedCounterfoils =  (List<Talonario>) prioritiesAndCounterFoils.sort { it.first }
                .collect { it.second }
                .flatten()

        personalizedCounterfoils
    }

    private List<ProductoTipo> calculateProductTypesWithDecreasingPriority(Map<Tuple2<TrackingType, ProductoTipo>, List<Tracking>> count) {

        List<Tuple2<ProductoTipo, Long>> productTypesAndPoints = count.keySet().toList().collect { tuple ->
            TrackingType trackingType = (TrackingType) tuple[0]
            ProductoTipo productType = (ProductoTipo) tuple[1]
            List<Tracking> trackings = count[tuple]

            Long points = trackings.size() * getPointsMultiplierForTrackingType(trackingType)
            new Tuple2(productType, points)
        }

        Map<ProductoTipo, List<Tuple2<ProductoTipo, Long>>> grouped = productTypesAndPoints.groupBy { t -> t.first }

        List<Tuple2<ProductoTipo, Long>> pointsPerProduct = grouped.keySet().toList().collect() { productType  ->
            List<Tuple2<ProductoTipo, Long>> pointsList = grouped[productType]
            Long finalPointsForProductType = (Long) pointsList.collect { it.second }
                    .sum()
            new Tuple2(productType, finalPointsForProductType)
        }

        List<Tuple2<ProductoTipo, Long>> sortedPointsPerProduct = pointsPerProduct.sort { tuple -> - tuple.second }

        sortedPointsPerProduct.collect { it.first }
    }
}
