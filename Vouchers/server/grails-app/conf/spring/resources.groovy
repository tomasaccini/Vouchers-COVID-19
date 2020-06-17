import assemblers.CounterfoilAssembler
import assemblers.ItemAssembler
import assemblers.ProductAssembler
import assemblers.VoucherAssembler
import assemblers.VoucherInformationAssembler

// Place your Spring DSL code here
beans = {
    productAssembler(ProductAssembler){}

    itemAssembler(ItemAssembler) {
        productAssembler = ref("productAssembler")
    }

    voucherInformationAssembler(VoucherInformationAssembler){
        itemAssembler = ref("itemAssembler")
    }

    counterfoilAssembler(CounterfoilAssembler) {
        voucherInformationAssembler = ref("voucherInformationAssembler")
    }

    voucherAssembler(VoucherAssembler) {
        voucherInformationAssembler = ref("voucherInformationAssembler")
    }
}
