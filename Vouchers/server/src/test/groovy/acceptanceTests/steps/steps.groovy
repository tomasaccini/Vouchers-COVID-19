package acceptanceTests.steps

import enums.ProductoTipo
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
        Voucher v = talonarioService.comprarVoucher(this.talonario.id, this.cliente.id)
        v != null && this.cliente.getVoucher(v.id) == v && this.talonario.cantidadVendida() == 1 && v.getCliente() == this.cliente && v.getTalonario().getNegocio() == this.negocio
    }

    static boolean "Los clientes no pueden comprarlo"(){
        Voucher v = null
        try {
            v = talonarioService.comprarVoucher(this.talonario.id, this.cliente.id)
        } catch (RuntimeException e) {
            v == null && this.cliente.getVouchers().size() == 0 && this.talonario.cantidadVendida() == 0
        }
    }
}
