package vouchers

import assemblers.CounterfoilAssembler
import services.RecommendationService

class RecommendationController {

    RecommendationService recommendationService
    TalonarioService talonarioService
    CounterfoilAssembler counterfoilAssembler

	static responseFormats = ['json']
	
    def index() { }

    def getRecommendationsForUser(Long userId) {
        // TODO validate param !!!!
        println("Asking for recommendations for: ${userId}")

        List<Talonario> talonarios = talonarioService.getAll()

        respond talonarios
    }
}
