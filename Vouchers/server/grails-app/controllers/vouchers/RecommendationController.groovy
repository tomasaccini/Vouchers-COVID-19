package vouchers

import assemblers.CounterfoilAssembler
import commands.CounterfoilCommand
import grails.converters.*
import services.RecommendationService

class RecommendationController {

    RecommendationService recommendationService
    TarifarioService tarifarioService
    CounterfoilAssembler counterfoilAssembler

	static responseFormats = ['json']
	
    def index() { }

    def getRecommendationsForUser() {
        // TODO validate param !!!!
        String userIdParam = params.userId

        List<Tarifario> counterfoils = tarifarioService.getAll()

        List<CounterfoilCommand> counterfoilCommands = []
        for (def c : counterfoils) {
            counterfoilCommands.add(counterfoilAssembler.toBean(c))
        }

        // TODO throws exception but works
        JSON.use("deep") {
            render counterfoilCommands as JSON
        }
    }
}
