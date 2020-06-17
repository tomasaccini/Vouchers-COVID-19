package vouchers

import commands.ProductCommand
import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException
import javax.xml.bind.ValidationException

@Transactional
class ProductService {

    def productAssembler

    Product save(ProductCommand productCommand) {
        Product product = productAssembler.fromBean(productCommand)
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

    def delete(ProductCommand productCommand) {
        Product product = productAssembler.fromBean(productCommand)
        product.delete(flush: true, failOnError: true)
    }
}
