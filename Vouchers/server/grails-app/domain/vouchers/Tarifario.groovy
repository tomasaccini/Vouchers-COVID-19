package vouchers

import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

class Tarifario {

    InformacionVoucher informacionVoucher
    int stock
    Set vouchers = []
    boolean activo = false

    static belongsTo = [negocio: Negocio]

    static hasMany = [vouchers: Voucher]

    static mapping = {
        vouchers cascade: 'save-update'
    }
    static constraints = {
        informacionVoucher      blank: false, nullable: false
        stock                   blank: false, nullable: false, default: 0
        activo blank:false, nullable: false, default: false
    }

    Integer cantidadVendida() {
        return vouchers ? vouchers.size() : 0
    }

    /*
    * Creates voucher from counterfoil
    * it associates voucher to client
    * decrease the quantity of stock
    */
    Voucher crearVoucher(Cliente cliente) {

        if (stock <= 0) {
            throw new RuntimeException("Voucher does not have stock")
        }

        Voucher voucher = new Voucher(informacionVoucher: informacionVoucher.duplicar(), cliente: cliente, tarifario: this
        )

        //TODO: This must be propably an atomic attribute
        stock -= 1

        this.addToVouchers(voucher)
        cliente.addToVouchers(voucher)

        try {
            voucher.informacionVoucher.save(flush: true, failOnError: true)
            voucher.save(flush: true, failOnError: true)
            this.save(flush:true, failOnError:true)
            cliente.save(flush:true, failOnError:true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }

        return voucher
    }
}
