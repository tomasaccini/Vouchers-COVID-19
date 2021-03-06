package vouchers

import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

class Talonario {

    InformacionVoucher informacionVoucher
    int stock
    Set vouchers = []
    boolean activo = false
    Negocio negocio

    static belongsTo = [negocio: Negocio]

    static hasMany = [vouchers: Voucher]

    static mapping = {
        vouchers cascade: 'save-update'
    }
    static constraints = {
        informacionVoucher blank: false, nullable: false
        stock blank: false, nullable: false, default: 0
        activo blank: false, nullable: false, default: false
        negocio nullable: false
    }

    Integer cantidadVendida() {
        return vouchers ? vouchers.size() : 0
    }

    boolean estaExpirado() {
        informacionVoucher.validoHasta <= new Date()
    }

    boolean esComprable() {
        stock > 0 && activo && !estaExpirado()
    }

    void lanzarErrorCompra() {
        if (stock <= 0) {
            throw new RuntimeException("Talonario no tiene suficiente stock")
        }

        if (!activo) {
            throw new RuntimeException("Talonario no esta activo")
        }

        if (estaExpirado()) {
            throw new RuntimeException("No se puede comprar un voucher de un talonario expirado")
        }
    }

    /*
    * Creates voucher from talonario
    * it associates voucher to client
    * decrease the quantity of stock
    */

    Voucher comprarVoucher(Cliente cliente) {
        if (!esComprable()) {
            lanzarErrorCompra()
        }

        Voucher voucher = new Voucher(
                informacionVoucher: informacionVoucher.duplicar(),
                cliente: cliente,
                talonario: this
        )

        //TODO: This must be propably an atomic attribute
        stock -= 1

        this.addToVouchers(voucher)
        cliente.addToVouchers(voucher)

        try {
            voucher.informacionVoucher.save(flush: true, failOnError: true)
            voucher.save(flush: true, failOnError: true)
            this.save(flush: true, failOnError: true)
            cliente.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }

        return voucher
    }

    Short obtenerRating(){
        Integer puntajes = 0
        Integer cantVotos = 0
        for (v in vouchers){
            if (v.rating != 0) {
                puntajes += v.rating
                cantVotos += 1
            }
        }
        return puntajes == 0 ? 0 : (puntajes / cantVotos).round(0) as Short
    }
}
