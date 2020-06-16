package enums.states

enum VoucherState {
    BOUGHT('Bought'),
    PENDING_CONFIRMATION('Pending Confirmation'),
    RETIRED('Retired'),
    EXPIRED('Expired')
    String id

    VoucherState(String id){
        this.id = id
    }
}