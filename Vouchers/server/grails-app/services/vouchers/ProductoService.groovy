package vouchers

import commands.ProductoCommand
import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException
import javax.xml.bind.ValidationException

@Transactional
class ProductoService {

    def productAssembler
    NegocioService negocioService

    Producto save(ProductoCommand productoCommand, Long negocioId) {
        Producto producto = productAssembler.fromBean(productoCommand)
        negocioService.agregarProducto(negocioId, producto)
        try {
            producto.save(flush: true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    def update(ProductoCommand productoCommand) {
        Producto producto = productAssembler.fromBean(productoCommand)
        producto.save(flush: true, failOnError: true)
    }

    def delete(Long id) {
        try {
            Producto producto = Producto.get(id)
            negocioService.eliminarProducto(producto.negocio.id, producto)
            producto.delete(failOnError: true)
        }
        catch (ServiceException se) {
            throw se
        }
    }
}
