package vouchers


import grails.rest.*
import grails.converters.*
import services.RecommendationService

class RecommendationController {

    RecommendationService recommendationService
    CounterfoilService counterfoilService

	static responseFormats = ['json']
	
    def index() { }

    List<Counterfoil> getRecommendationsForUser(Long userId) {
        def counterfoils = counterfoilService.getAll()
        // respond counterfoils
        // TODO throws exception but works
        JSON.use("deep") {
            render counterfoils as JSON
        }
    }
}
