package vouchers

import assemblers.VoucherAssembler
import commands.VoucherCommand
import enums.states.VoucherState
import grails.gorm.transactions.Transactional
import org.hibernate.service.spi.ServiceException

import javax.xml.bind.ValidationException

@Transactional
class VoucherService {

    VoucherAssembler voucherAssembler

    def save(VoucherCommand voucherCommand) {
        Voucher voucher = voucherAssembler.fromBean(voucherCommand)
        try {
            voucher.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }
        return voucher
    }

    def update(VoucherCommand voucherCommand) {
        Voucher voucher = voucherAssembler.fromBean(voucherCommand)
        try {
            voucher.save(flush: true, failOnError: true)
        } catch (ValidationException e) {
            throw new ServiceException(e.message)
        }
        return voucher
    }

    def delete(Long id) {
        try {
            Voucher voucher = Voucher.get(id)
            voucher.delete(flush: true, failOneEror: true)
        } catch (ServiceException e) {
            log.error(e)
            throw e
        }
    }

    def confirmarCanje(Long voucherId, Long negocioId) {

        Negocio negocio = Negocio.get(negocioId)
        if (negocio == null) {
            throw new RuntimeException("El negocio " + negocioId + " no existe")
        }

        Voucher voucher = Voucher.get(voucherId)
        if (voucher == null) {
            throw new RuntimeException("El voucher " + voucherId + " no existe")
        }

        // El codigo esta aca en vez de en voucher.confirmarCanje() porque no se actualizaba la DB cuando se pone ahi. Cosa rara de grails.
        // voucher.confirmarCanje(negocio)

        def negocioConfirmador = negocio
        def negocioVoucher = voucher.talonario.negocio.id
        if (negocioVoucher != negocioConfirmador.id) {
            throw new RuntimeException("El negocio " + negocioVoucher + " no puede confirmar el canje")
        }

        if (!voucher.esConfirmable()) {
            throw new RuntimeException("El voucher " + voucher.id + " no puede ser confirmado")
        }

        voucher.state = VoucherState.Canjeado
        voucher.lastStateChange = new Date()
        voucher.save(flush: true, failOnError: true)
    }

    Voucher solicitarCanje(Long voucherId, Long clienteId) {

        Cliente cliente = Cliente.get(clienteId)
        if (cliente == null) {
            throw new RuntimeException("El cliente " + clienteId + " no existe")
        }

        Voucher voucher = Voucher.get(voucherId)
        if (voucher == null) {
            throw new RuntimeException("El voucher " + voucherId + " no existe")
        }

        // El codigo esta aca en vez de en voucher.solicitarCanje() porque no se actualizaba la DB cuando se pone ahi. Cosa rara de grails.
        // voucher.solicitarCanje(cliente)

        Cliente clienteSolicitante = cliente

        if (voucher.cliente.id != clienteSolicitante.id) {
            throw new RuntimeException("El cliente " + clienteId + " no puede solicitar el canje. Solo el cliente que creo el voucher puede hacerlo")
        }

        if (!voucher.esCanjeable()) {
            throw new RuntimeException("El voucher " + voucher.id + " no puede ser canjeado")
        }

        voucher.state = VoucherState.ConfirmacionPendiente
        voucher.lastStateChange = new Date()
        voucher.save(flush: true, failOnError: true)

        return voucher
    }

    Voucher cancelarSolicitudDeCanje(Long voucherId, Long clienteId) {

        Cliente cliente = Cliente.get(clienteId)
        if (cliente == null) {
            throw new RuntimeException("El cliente " + clienteId + " no existe")
        }

        Voucher voucher = Voucher.get(voucherId)
        if (voucher == null) {
            throw new RuntimeException("El voucher " + voucherId + " no existe")
        }

        // El codigo esta aca en vez de en voucher.cancelarSolicitudDeCanje() porque no se actualizaba la DB cuando se pone ahi. Cosa rara de grails.
        // voucher.cancelarSolicitudDeCanje(cliente)

        Cliente clienteSolicitante = cliente
        if (voucher.cliente.id != clienteSolicitante.id) {
            throw new RuntimeException("El cliente " + clienteId + " no puede cancelar la solicitud el canje. Solo el cliente que creo el voucher puede hacerlo")
        }

        if (!voucher.esConfirmable()) {
            throw new RuntimeException("La solicitud del canje del voucher " + voucher.id + " no puede ser cancelada")
        }

        voucher.state = VoucherState.Comprado
        voucher.lastStateChange = new Date()
        voucher.save(flush: true, failOnError: true)

        return voucher
    }

    List<Voucher> list() {
        return mockVoucherList()
    }

    List<Voucher> listByUserId(String userId) {
        return mockVoucherList()
    }

    List<Voucher> findSimilar(String q, Map map) {
        String query = "select distinct(v) from Voucher as v "
        query += " where lower(v.informacionVoucher.descripcion) like :search "
        Voucher.executeQuery(query, [search: "%${q}%".toLowerCase()], map)
    }

    private List<Voucher> mockVoucherList() {
        InformacionVoucher vi = createVoucherInformation()
        Voucher v = new Voucher(informacionVoucher: vi)
        return [v]
    }

    private InformacionVoucher createVoucherInformation(valid_until = new Date('2020/12/31')) {
        Producto p1 = new Producto(nombre: "Hamburguesa", descripcion: "Doble cheddar")
        Producto p2 = new Producto(nombre: "Pinta cerveza", descripcion: "Cerveza artesanal de la casa")
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher vi = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: valid_until, items: [i1, i2])
        vi
    }

    List<Voucher> obtenerConfirmables(Long negocioId) {
        Negocio negocio = Negocio.get(negocioId)
        if (negocio == null) {
            throw new RuntimeException("El negocio " + negocioId + " no existe")
        }

        return negocio.obtenerVouchersConfirmables()
    }

    List<Voucher> buscarPorUsuarioNoCanjeados(Cliente cliente, Map params){
        String query = "select distinct(v) from Voucher as v "
        query += " where v.cliente.id like :clienteId "
        query += " and v.state.id like 1 or v.state.id like 2 " // Comprado o ConfirmacionPendiente
        Voucher.executeQuery(query, [clienteId: cliente.id], params)
    }

    List<Voucher> buscarPorUsuarioYEstado(Cliente cliente, String estado, Map params){
        def estadoEnum = VoucherState.valueOf(estado)
        String query = "select distinct(v) from Voucher as v "
        query += " where v.cliente.id like :clienteId "
        query += " and v.state = :estado "
        Voucher.executeQuery(query, [clienteId: cliente.id, estado: estadoEnum], params)
    }
}
