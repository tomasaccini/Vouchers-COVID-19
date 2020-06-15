package states

enum ComplaintState {
    OPENED('Opened'),
    ANSWERED('Answered'),
    CLOSED('Closed')
    String id

    ComplaintState(String id) {
        this.id = id
    }
}