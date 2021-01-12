package enums.states

enum VoucherEstado {
    Comprado('Comprado'),
    ConfirmacionPendiente('ConfirmacionPendiente'),
    Canjeado('Canjeado'),
    Expirado('Expirado')
    String id

    VoucherEstado(String id){
        this.id = id
    }
}
