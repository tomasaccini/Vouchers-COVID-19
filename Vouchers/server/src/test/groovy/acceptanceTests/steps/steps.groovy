package acceptanceTests.steps

import enums.ProductoTipo
import enums.states.VoucherEstado
import vouchers.Cliente
import vouchers.Direccion
import vouchers.InformacionVoucher
import vouchers.Item
import vouchers.Negocio
import vouchers.NegocioService
import vouchers.Producto
import vouchers.Talonario
import vouchers.TalonarioService
import vouchers.Voucher

class Steps {
    static Negocio negocio
    static Talonario talonario
    static TalonarioService talonarioService = new TalonarioService()
    static NegocioService negocioService = new NegocioService()
    static Cliente cliente
    static Voucher voucher
    static boolean errorDuranteSolicitudCanje = false
    static boolean errorDuranteConfirmacionCanje = false


    private static InformacionVoucher crearInformacionVoucher(validoHasta = new Date('2030/12/12')) {
        Producto p1 = new Producto(nombre: "Hamburguesa", descripcion: "Doble cheddar", tipo: ProductoTipo.COMIDA_RAPIDA)
        Producto p2 = new Producto(nombre: "Pinta cerveza", descripcion: "Cerveza artesanal de la casa", tipo: ProductoTipo.COMIDA_RAPIDA)
        negocioService.agregarProducto(this.negocio.id, p1)
        negocioService.agregarProducto(this.negocio.id, p2)
        Item i1 = new Item(producto: p1, cantidad: 1)
        Item i2 = new Item(producto: p2, cantidad: 2)

        InformacionVoucher iv = new InformacionVoucher(precio: 400, descripcion: "Promo verano", validoDesde: new Date('2020/08/01'), validoHasta: validoHasta, items: [i1, i2])
        iv.save(flush: true)
        iv
    }

    static void "Un negocio existe"(){
        this.negocio = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234", cuentaVerificada: true)
        this.negocio.save(flush: true)
    }

    static void "Un cliente existe"(){
        this.cliente = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234", cuentaVerificada: true)
        this.cliente.save(flush: true)
    }

    static void "Existe un talonario asociado a dicho negocio"(){
        InformacionVoucher iv = this.crearInformacionVoucher()
        this.talonario = new Talonario(stock: 6, informacionVoucher: iv, negocio: negocio, activo: false)
        this.talonario.save(flush: true)
    }

    static void "El talonario esta activo"(){
        this.talonario.activo = true
        this.talonario.save(flush: true)
    }

    static void "El negocio activa dicho talonario"(){
        talonarioService.activar(this.talonario.id)
        this.talonario.save(flush: true)
    }

    static void "El negocio desactiva dicho talonario"(){
        talonarioService.desactivar(this.talonario.id)
        this.talonario.save(flush: true)
    }

    static boolean "El talonario es público"(){
        talonarioService.get(this.talonario.id) != null
    }

    static boolean "El talonario es privado"(){
        talonarioService.get(this.talonario.id) != null
    }

    static boolean "Los clientes pueden buscarlo"(){
        talonarioService.get(this.talonario.id) != null && this.talonario.getActivo() == true && !this.talonario.estaExpirado() && this.talonario.getStock() > 0
    }

    static boolean "Los clientes no pueden buscarlo"(){
        talonarioService.get(this.talonario.id) != null && this.talonario.getActivo() == false && !this.talonario.estaExpirado() && this.talonario.getStock() > 0
    }

    static boolean "Los clientes pueden comprarlo"(){
        this.talonario.esComprable()
    }

    static boolean "Los clientes no pueden comprarlo"(){
        !this.talonario.esComprable()
    }

    static void "Un cliente compra un voucher del talonario"(){
        this.voucher = talonarioService.comprarVoucher(this.talonario.id, this.cliente.id)
    }

    static void "El cliente compra un voucher del talonario"(){
        this.voucher = talonarioService.comprarVoucher(this.talonario.id, this.cliente.id)
    }

    static void "El cliente solicita canjear el voucher"(){
        try {
            this.voucher.solicitarCanje(this.cliente)
            this.errorDuranteSolicitudCanje = false
        } catch (RuntimeException e) {
            this.errorDuranteSolicitudCanje = true
        }
    }

    static void "El negocio confirma el canje"(){
        try {
            this.voucher.confirmarCanje(this.negocio)
            this.errorDuranteConfirmacionCanje = false
        } catch (RuntimeException e) {
            this.errorDuranteConfirmacionCanje = true
        }
    }

    static boolean "La transaccion es exitosa"(){
        this.voucher != null && this.talonario.cantidadVendida() == 1 && this.voucher.getCliente() == this.cliente
    }

    static boolean "El voucher se guarda en la sección Mis vouchers del cliente"(){
        this.cliente.getVoucher(this.voucher.id) == this.voucher && this.cliente.getVouchers().size() == 1
    }

    static boolean "El cliente recibe el servicio o producto"(){
        this.cliente.getVoucher(this.voucher.id) == this.voucher && this.cliente.getVouchers().size() == 1
    }

    static boolean "El voucher se marca como canjeado"(){
        this.voucher.estado == VoucherEstado.Canjeado
    }

    static boolean "El voucher se marca como pendiente de confirmacion"(){
        this.voucher.estado == VoucherEstado.ConfirmacionPendiente
    }

    static boolean "El cliente no recibe el producto ni servicio porque ya fue canjeado"(){
        this.errorDuranteSolicitudCanje && this.cliente.getVoucher(this.voucher.id) == this.voucher && this.cliente.getVouchers().size() == 1
    }

    static boolean "El cliente no puede volver a solicitar el canje porque ya esta pendiente de confirmacion"(){
        this.errorDuranteSolicitudCanje && this.cliente.getVoucher(this.voucher.id) == this.voucher && this.cliente.getVouchers().size() == 1 && this.voucher.estado == VoucherEstado.ConfirmacionPendiente
    }

    static boolean "El cliente no puede volver a solicitar el canje porque ya esta canjeado"(){
        this.errorDuranteSolicitudCanje
    }

    static boolean "El negocio no puede confirmar el canje porque nunca el canje nunca fue solicitado"(){
        this.errorDuranteConfirmacionCanje
    }
}
