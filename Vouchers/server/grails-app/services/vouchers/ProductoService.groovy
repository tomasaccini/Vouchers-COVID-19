package vouchers

import commands.ProductoCommand
import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException
import javax.xml.bind.ValidationException

@Transactional
class ProductoService {

    def productAssembler
    NegocioService negocioService

    Producto save(ProductoCommand productCommand, Long businessId) {
        Producto product = productAssembler.fromBean(productCommand)
        negocioService.addProduct(businessId, product)
        try {
            product.save(flush: true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    def update(ProductoCommand productCommand) {
        Producto product = productAssembler.fromBean(productCommand)
        product.save(flush: true, failOnError: true)
    }

    def delete(Long id) {
        try {
            Producto product = Producto.get(id)
            negocioService.removeProduct(product.business.id, product)
            product.delete(failOnError: true)
        }
        catch (ServiceException se) {
            throw se
        }
    }
}
