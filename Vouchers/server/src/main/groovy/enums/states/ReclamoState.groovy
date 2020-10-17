package enums.states

enum ReclamoState {
    Abierto('Abierto'),
    Respondido('Respondido'),
    Cerrado('Cerrado')
    String id

    ReclamoState(String id) {
        this.id = id
    }
}