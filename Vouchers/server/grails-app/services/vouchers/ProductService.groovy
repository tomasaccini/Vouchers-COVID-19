package vouchers

import commands.ProductCommand
import grails.gorm.transactions.Transactional

@Transactional
class ProductService {

    def productAssembler

    def save(ProductCommand productCommand, Long businessId) {
        Business business = Business.findById(businessId)
        Product product = productAssembler.fromBean(productCommand)
        business.addToProducts(product)
        business.save(flush: true, failOnError: true)
    }

    def delete(ProductCommand productCommand, Long businessId) {
        Business business = Business.findById(businessId)
        Product product = productAssembler.fromBean(productCommand)
        business.removeFromProducts(product)
        product.delete()
        business.save(flush: true, failOnError: true)
    }

    def update(ProductCommand productCommand) {
        Product product = productAssembler.fromBean(productCommand)
        product.save(flush: true, failOnError: true)
    }
}
