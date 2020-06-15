import assemblers.CounterfoilAssembler
import assemblers.ItemAssembler
import assemblers.ProductAssembler
import assemblers.VoucherInformationAssembler

// Place your Spring DSL code here
beans = {
    productAssembler(ProductAssembler){}

    itemAssembler(ItemAssembler) {
        productAssembler = ref("productAssembler")
    }

    voucherInformationAssembler(VoucherInformationAssembler){}

    counterfoilAssembler(CounterfoilAssembler) {
        voucherInformationAssembler = ref("voucherInformationAssembler")
    }
}
