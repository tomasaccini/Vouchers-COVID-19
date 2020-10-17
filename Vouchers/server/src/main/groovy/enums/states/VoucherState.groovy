package enums.states

enum VoucherState {
    Comprado('Comprado'),
    ConfirmacionPendiente('ConfirmacionPendiente'),
    Retirado('Retirado'),
    Expirado('Expirado')
    String id

    VoucherState(String id){
        this.id = id
    }
}