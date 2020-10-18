package vouchers

class Direccion {

    String calle
    String numero
    String departamento
    String provincia
    String pais


    static constraints = {
        calle      nullable: false, blank: false
        numero      nullable: false, blank: false
        departamento   nullable: true, blank: false
        provincia    nullable: true, blank: true
        pais     nullable: false
    }

    @Override
    String toString() {
        return "${calle}-${numero}-${pais}"
    }
}
