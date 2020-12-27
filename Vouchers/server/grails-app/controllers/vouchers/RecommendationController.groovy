package vouchers

import assemblers.TalonarioAssembler
import services.RecommendationService

class RecommendationController {

    RecommendationService recommendationService
    TalonarioService talonarioService
    TalonarioAssembler talonarioAssembler

	static responseFormats = ['json']
	
    def index() { }

    def getRecommendationsForUser(Long userId) {
        // TODO validate param !!!!
        println("Asking for recommendations for: ${userId}")

        List<Talonario> talonarios = talonarioService.getAll()

        respond talonarios
    }
}
