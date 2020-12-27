package enums.states

enum VoucherState {
    Comprado('Comprado'),
    ConfirmacionPendiente('ConfirmacionPendiente'),
    Canjeado('Canjeado'),
    Expirado('Expirado')
    String id

    VoucherState(String id){
        this.id = id
    }
}
