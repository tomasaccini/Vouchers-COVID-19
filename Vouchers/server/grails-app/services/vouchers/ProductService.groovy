package vouchers

import commands.ProductCommand
import grails.gorm.transactions.Transactional

@Transactional
class ProductService {

    def productAssembler

    def save(ProductCommand productCommand, Long businessId) {
        Business business = Business.findById(businessId)
        Product product = productAssembler.toDomain(productCommand)
        business.addToProducts(product)
        business.save(flush: true, failOnError: true)
    }

    def delete(ProductCommand productCommand, Long businessId) {
        Business business = Business.findById(businessId)
        Product product = productAssembler.toDomain(productCommand)
        business.removeFromProducts(product)
        business.save(flush: true, failOnError: true)
    }

    def update(ProductCommand productCommand) {
        Product product = productAssembler.toDomain(productCommand)
        product.save(flush: true, failOnError: true)
    }
}
