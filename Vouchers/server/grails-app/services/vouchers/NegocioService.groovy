package vouchers

import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class NegocioService {

    VoucherService voucherService

    /*
    * Gets a product and adds it to business
    */

    void agregarProducto(Long id, Producto p) {
        Negocio negocio = Negocio.get(id)
        negocio.addToProductos(p)
        try {
            negocio.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }
    }

    void eliminarProducto(Long id, Producto p) {
        Negocio negocio = Negocio.get(id)
        negocio.removeFromProductos(p)
        try {
            negocio.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }
    }

    def confirmarCanjeVoucher(Long id, Voucher voucher) {
        Negocio negocio = Negocio.get(id)
        if (!negocio.esDuenioDeVoucher(voucher)) {
            throw new RuntimeException("El negocio no es el duenio del Voucher")
        }
        if (!voucher.esConfirmable()) {
            throw new RuntimeException("No es posible confirmar el voucher")
        }
        voucherService.confirmarCanje(voucher.id, id)
    }

    Negocio obtener(Long negocioId) {
        def negocio = Negocio.findById(negocioId)
        return negocio
    }

    List<Negocio> obtenerTodos() {
        def a = Negocio.findAll()
        println("\n\n\n\n\n !!!! ${a}")
        a
    }
}
