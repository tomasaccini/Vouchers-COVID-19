package services

import enums.ProductType
import enums.TrackingType
import vouchers.Cliente
import vouchers.Tarifario
import vouchers.TarifarioService
import vouchers.Tracking
import vouchers.TrackingService

class RecommendationService {

    static final Long DETAIL_POINTS_MULTIPLIER = 1
    static final Long THANKS_POINTS_MULTIPLIER = 20

    TrackingService trackingService
    TarifarioService counterfoilService

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
    List<Tarifario> recommendCounterfoils(Cliente client) {
        Map<Tuple2<TrackingType, ProductType>, List<Tracking>> count = trackingService.countyByProductTypeAndTrackingType(client)

        List<Tarifario> sortedCounterfoils = defaultCounterfoils()
        List<ProductType> sortedProductTypes = calculateProductTypesWithDecreasingPriority(count)

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

    List<Tarifario> personalizeCounterfoils(List<Tarifario> sortedCounterfoils, List<ProductType> sortedProductTypes) {
        Map<ProductType, Integer> priorityPerProductType = sortedProductTypes.withIndex().collectEntries { ProductType entry, int i -> [(entry): i] }
        Map<ProductType, List<Tarifario>> counterfoilsPerProductType = sortedCounterfoils.groupBy { it.informacionVoucher.product.type }

        List<Tuple2<Long, List<Tarifario>>> prioritiesAndCounterFoils = counterfoilsPerProductType.keySet().collect { productType ->
            Long productPriority = priorityPerProductType[productType]
            List<Tarifario> counterfoils = counterfoilsPerProductType[productType]

            new Tuple2(productPriority, counterfoils)
        }

        List<Tarifario> personalizedCounterfoils =  (List<Tarifario>) prioritiesAndCounterFoils.sort { it.first }
                .collect { it.second }
                .flatten()

        personalizedCounterfoils
    }

    private List<ProductType> calculateProductTypesWithDecreasingPriority(Map<Tuple2<TrackingType, ProductType>, List<Tracking>> count) {

        List<Tuple2<ProductType, Long>> productTypesAndPoints = count.keySet().toList().collect { tuple ->
            TrackingType trackingType = (TrackingType) tuple[0]
            ProductType productType = (ProductType) tuple[1]
            List<Tracking> trackings = count[tuple]

            Long points = trackings.size() * getPointsMultiplierForTrackingType(trackingType)
            new Tuple2(productType, points)
        }

        Map<ProductType, List<Tuple2<ProductType, Long>>> grouped = productTypesAndPoints.groupBy { t -> t.first }

        List<Tuple2<ProductType, Long>> pointsPerProduct = grouped.keySet().toList().collect() { productType  ->
            List<Tuple2<ProductType, Long>> pointsList = grouped[productType]
            Long finalPointsForProductType = (Long) pointsList.collect { it.second }
                    .sum()
            new Tuple2(productType, finalPointsForProductType)
        }

        List<Tuple2<ProductType, Long>> sortedPointsPerProduct = pointsPerProduct.sort { tuple -> - tuple.second }

        sortedPointsPerProduct.collect { it.first }
    }
}
