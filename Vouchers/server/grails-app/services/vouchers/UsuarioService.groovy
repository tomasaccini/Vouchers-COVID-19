package vouchers

import grails.gorm.transactions.Transactional

@Transactional
class UsuarioService {

    Usuario obtener(Long usuarioId) {
        Cliente cliente = Cliente.findById(usuarioId)
        if (cliente != null) {
            return cliente
        }

        Negocio negocio = Negocio.findById(usuarioId)
        if (negocio != null) {
            return negocio
        }

        throw new RuntimeException("El usuario no existe. UsuarioId: ${usuarioId}")
    }
}
