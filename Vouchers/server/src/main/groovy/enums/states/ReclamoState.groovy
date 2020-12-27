package enums.states

enum ReclamoState {
    Abierto('Abierto'),
    Cerrado('Cerrado'),
    Respondido('Respondido')

    String id

    ReclamoState(String id) {
        this.id = id
    }
}
