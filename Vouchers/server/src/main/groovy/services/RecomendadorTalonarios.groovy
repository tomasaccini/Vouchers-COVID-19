package services

import vouchers.*

class RecomendadorTalonarios {

    private final Double FACTOR_CANTIDAD_VENDIDA = 0.6
    private final Double FACTOR_RATING = 0.4
    private final Double MAX_RATING = 5

    TalonarioService talonarioService

    List<Talonario> generarOrdenDeRecomendacion() {
        List<Talonario> talonarios = Talonario.findAllByActivo(true)
        return ordenarPorPuntajeEnBaseAlasDimensionesPonderadas(talonarios)
    }

    private List<Talonario> ordenarPorPuntajeEnBaseAlasDimensionesPonderadas(List<Talonario> talonarios) {
        Long maxVendidos = talonarios.max { it.cantidadVendida() }.cantidadVendida()
        println("Max vendidos: " + maxVendidos)
        List<Tuple2<Talonario, Double>> talonariosConPuntaje = talonarios
                .collect { generarTalonarioConPuntajeEnBaseAlasDimensionesPonderadas(it, maxVendidos) }
        return talonariosConPuntaje.sort { -it.second }
                .collect { it.first }
    }

    private Tuple2<Talonario, Double> generarTalonarioConPuntajeEnBaseAlasDimensionesPonderadas(Talonario talonario, Long maxVendidos) {
        println("!!!! " + talonario.negocio.nombre  + ": " + calcularPuntajeEnBaseAlasDimensionesPonderadas(talonario, maxVendidos))
        return new Tuple2(talonario, calcularPuntajeEnBaseAlasDimensionesPonderadas(talonario, maxVendidos))
    }

    private Double calcularPuntajeEnBaseAlasDimensionesPonderadas(Talonario talonario, Long maxVendidos) {
        return calcularPuntajeDimensionCantidadVendida(talonario.cantidadVendida(), maxVendidos) +
                calcularPuntajeDimensionRating(talonario.informacionVoucher.obtenerRating())
    }

    private Double calcularPuntajeDimensionCantidadVendida(Long cantidadVendida, Long maxVendidos) {
        return  FACTOR_CANTIDAD_VENDIDA * cantidadVendida / maxVendidos
    }

    private Double calcularPuntajeDimensionRating(Long rating) {
        return FACTOR_RATING * rating / MAX_RATING
    }
}
