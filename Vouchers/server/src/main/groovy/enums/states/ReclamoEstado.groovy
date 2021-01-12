package enums.states

enum ReclamoEstado {
    Abierto('Abierto'),
    Cerrado('Cerrado'),
    Respondido('Respondido')

    String id

    ReclamoEstado(String id) {
        this.id = id
    }
}
