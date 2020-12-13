package vouchers

import assemblers.CounterfoilAssembler
import commands.TarifarioCommand
import grails.converters.*
import services.RecommendationService

class RecommendationController {

    RecommendationService recommendationService
    TarifarioService tarifarioService
    CounterfoilAssembler counterfoilAssembler

	static responseFormats = ['json']
	
    def index() { }

    def getRecommendationsForUser(Long userId) {
        // TODO validate param !!!!
        println("Asking for recommendations for: ${userId}")

        List<Tarifario> tarifarios = tarifarioService.getAll()

        respond tarifarios
    }
}
