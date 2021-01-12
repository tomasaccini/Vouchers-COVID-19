package vouchers

abstract class Usuario {

    String email
    String contrasenia
    Boolean cuentaVerificada

    static constraints = {
        email email: true, blank: false, nullable: false
        contrasenia password: true, blank: false, nullable: false
        cuentaVerificada blank: false, nullable: false, default: false
    }

    void verificarCuenta() {
        cuentaVerificada = true
    }
}
