package vouchers

import commands.ProductCommand
import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException
import javax.xml.bind.ValidationException

@Transactional
class ProductService {

    def productAssembler
    BusinessService businessService

    Product save(ProductCommand productCommand, Long businessId) {
        Product product = productAssembler.fromBean(productCommand)
        businessService.addProduct(businessId, product)
        try {
            product.save(flush: true, failOnError: true)
        } catch (ValidationException e){
            throw new ServiceException(e.message)
        }
    }

    def update(ProductCommand productCommand) {
        Product product = productAssembler.fromBean(productCommand)
        product.save(flush: true, failOnError: true)
    }

    def delete(Long id) {
        try {
            Product product = Product.get(id)
            businessService.removeProduct(product.business.id, product)
            product.delete(failOnError: true)
        }
        catch (ServiceException se) {
            throw se
        }
    }
}
