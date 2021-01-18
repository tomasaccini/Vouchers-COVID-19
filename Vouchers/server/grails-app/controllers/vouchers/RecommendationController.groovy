package vouchers

import assemblers.TalonarioAssembler
import commands.TalonarioCommand
import services.RecommendationService

class RecommendationController {

    RecommendationService recommendationService
    TalonarioService talonarioService
    TalonarioAssembler talonarioAssembler

    static responseFormats = ['json']

    def index() {}

    def getRecommendationsForUser(Long userId) {
        println("Asking for recommendations for: ${userId}")

        List<Talonario> talonarios = Talonario.findAllByActivo(true)

        List<TalonarioCommand> talonariosCommands = []

        for (def talonario : talonarios) {
            talonariosCommands.add(talonarioAssembler.toBean(talonario))
        }

        respond talonariosCommands
    }
}
