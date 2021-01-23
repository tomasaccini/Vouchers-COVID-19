import assemblers.ClienteAssembler
import assemblers.TalonarioAssembler
import assemblers.ItemAssembler
import assemblers.NegocioAssembler
import assemblers.ProductAssembler
import assemblers.VoucherAssembler
import assemblers.InformacionVoucherAssembler
import services.RecommendationService

// Place your Spring DSL code here
beans = {
    productAssembler(ProductAssembler) {}

    itemAssembler(ItemAssembler) {
        productAssembler = ref("productAssembler")
    }

    informacionVoucherAssembler(InformacionVoucherAssembler) {
        itemAssembler = ref("itemAssembler")
    }

    talonarioAssembler(TalonarioAssembler) {
        informacionVoucherAssembler = ref("informacionVoucherAssembler")
        voucherAssembler = ref("voucherAssembler")
    }

    voucherAssembler(VoucherAssembler) {
        informacionVoucherAssembler = ref("informacionVoucherAssembler")
        negocioAssembler = ref("negocioAssembler")
    }

    clienteAssembler(ClienteAssembler) {
        voucherAssembler = ref("voucherAssembler")
    }

    negocioAssembler(NegocioAssembler) {
        talonarioAssembler = ref("talonarioAssembler")
    }

    recommendationService(RecommendationService) {
        talonarioService = ref("talonarioService")
    }
}
