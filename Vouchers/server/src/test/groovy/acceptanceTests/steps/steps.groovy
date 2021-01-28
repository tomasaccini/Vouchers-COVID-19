package acceptanceTests.steps

import enums.ProductoTipo
import enums.states.ReclamoEstado
import enums.states.VoucherEstado
import vouchers.*

class Steps {
    static Negocio negocio
    static Talonario talonario
    static Talonario talonario_A
    static Talonario talonario_B
    static List<Talonario> talonariosRecomendados
    static TalonarioService talonarioService = new TalonarioService()
    static VoucherService voucherService = new VoucherService()
    static NegocioService negocioService = new NegocioService()
    static ReclamoService reclamoService = new ReclamoService()
    static RecomendadorTalonarios recomendadorTalonarios = new RecomendadorTalonarios()
    static Cliente cliente
    static Voucher voucher
    static Voucher voucher_A1
    static Voucher voucher_A2
    static Voucher voucher_B1
    static Reclamo reclamo
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

    static void "Un negocio existe"() {
        this.negocio = new Negocio(nombre: "Burger", numeroTelefonico: "123412341234", direccion: new Direccion(calle: "Libertador", numero: "1234", pais: "Argentina"), categoria: "Restaurant", email: "burger@gmail.com", contrasenia: "burger1234", cuentaVerificada: true)
        this.negocio.save(flush: true)
    }

    static void "Un cliente existe"() {
        this.cliente = new Cliente(nombreCompleto: "Ricardo Fort", email: "ricki@gmail.com", contrasenia: "ricki1234", cuentaVerificada: true)
        this.cliente.save(flush: true)
    }

    static void "Existe un talonario asociado a dicho negocio"() {
        InformacionVoucher iv = this.crearInformacionVoucher()
        this.talonario = new Talonario(stock: 6, informacionVoucher: iv, negocio: negocio, activo: false)
        this.negocio.addToTalonarios(this.talonario)
        this.talonario.save(flush: true)
        this.negocio.save(flush: true)
    }

    static void "Existe un talonario talonario_A asociado a dicho negocio"() {
        InformacionVoucher iv = this.crearInformacionVoucher()
        this.talonario_A = new Talonario(stock: 6, informacionVoucher: iv, negocio: negocio, activo: false)
        this.negocio.addToTalonarios(this.talonario_A)
        this.talonario_A.save(flush: true)
        this.negocio.save(flush: true)
    }

    static void "Existe un talonario talonario_B asociado a dicho negocio"() {
        InformacionVoucher iv = this.crearInformacionVoucher()
        this.talonario_B = new Talonario(stock: 6, informacionVoucher: iv, negocio: negocio, activo: false)
        this.negocio.addToTalonarios(this.talonario_B)
        this.talonario_B.save(flush: true)
        this.negocio.save(flush: true)
    }

    static void "El talonario esta activo"() {
        this.talonario.activo = true
        this.talonario.save(flush: true)
    }

    static void "El talonario talonario_A esta activo"() {
        this.talonario_A.activo = true
        this.talonario_A.save(flush: true)
    }

    static void "El talonario talonario_B esta activo"() {
        this.talonario_B.activo = true
        this.talonario_B.save(flush: true)
    }

    static void "El negocio activa dicho talonario"() {
        talonarioService.activar(this.negocio.id, this.talonario.id)
    }

    static void "El negocio pausa dicho talonario"() {
        talonarioService.pausar(this.negocio.id, this.talonario.id)
        this.talonario.save(flush: true)
    }

    static boolean "El talonario es público"() {
        talonarioService.get(this.talonario.id) != null
    }

    static boolean "El talonario es privado"() {
        talonarioService.get(this.talonario.id) != null
    }

    static boolean "Los clientes pueden buscarlo"() {
        talonarioService.get(this.talonario.id) != null && this.talonario.getActivo() == true && !this.talonario.estaExpirado() && this.talonario.getStock() > 0
    }

    static boolean "Los clientes no pueden buscarlo"() {
        talonarioService.get(this.talonario.id) != null && this.talonario.getActivo() == false && !this.talonario.estaExpirado() && this.talonario.getStock() > 0
    }

    static boolean "Los clientes pueden comprarlo"() {
        this.talonario.esComprable()
    }

    static boolean "Los clientes no pueden comprarlo"() {
        !this.talonario.esComprable()
    }

    static void "Un cliente compra un voucher del talonario"() {
        this.voucher = talonarioService.comprarVoucher(this.talonario.id, this.cliente.id)
    }

    static void "El cliente compra un voucher del talonario"() {
        this.voucher = talonarioService.comprarVoucher(this.talonario.id, this.cliente.id)
    }

    static void "El talonario talonario_A tiene 1 voucher vendido"() {
        this.voucher_A1 = talonarioService.comprarVoucher(this.talonario_A.id, this.cliente.id)
    }

    static void "El talonario talonario_A tiene 2 voucher vendido"() {
        this.voucher_A1 = talonarioService.comprarVoucher(this.talonario_A.id, this.cliente.id)
        this.voucher_A2 = talonarioService.comprarVoucher(this.talonario_A.id, this.cliente.id)
    }

    static void "El talonario talonario_B tiene 1 voucher vendido"() {
        this.voucher_B1 = talonarioService.comprarVoucher(this.talonario_B.id, this.cliente.id)
    }

    static void "El talonario talonario_A tiene un rating promedio de 5 estrellas"() {
        this.voucher_A1 = voucherService.cambiarRating(this.voucher_A1.id, 5 as Short)
    }

    static void "El talonario talonario_B tiene un rating promedio de 5 estrellas"() {
        this.voucher_B1 = voucherService.cambiarRating(this.voucher_B1.id, 5 as Short)
    }

    static void "El talonario talonario_B tiene un rating promedio de 1 estrellas"() {
        this.voucher_A1 = voucherService.cambiarRating(this.voucher_B1.id, 1 as Short)
    }

    static void "El cliente busca los talonarios activos"() {
        this.talonariosRecomendados = recomendadorTalonarios.generarOrdenDeRecomendacion()
    }

    static void "El cliente solicita canjear el voucher"() {
        try {
            this.voucher.solicitarCanje(this.cliente)
            this.errorDuranteSolicitudCanje = false
        } catch (RuntimeException e) {
            this.errorDuranteSolicitudCanje = true
        }
    }

    static void "El cliente inicia un reclamo"() {
        this.reclamo = this.reclamoService.abrirReclamo(this.voucher.id, "Pregunta")
        this.reclamo.save(flush: true)
    }

    static void "El negocio confirma el canje"() {
        try {
            this.voucher.confirmarCanje(this.negocio)
            this.errorDuranteConfirmacionCanje = false
        } catch (RuntimeException e) {
            this.errorDuranteConfirmacionCanje = true
        }
    }

    static boolean "La transaccion es exitosa"() {
        this.voucher != null && this.talonario.cantidadVendida() == 1 && this.voucher.getCliente() == this.cliente
    }

    static boolean "El voucher se guarda en la sección Mis vouchers del cliente"() {
        this.cliente.getVoucher(this.voucher.id) == this.voucher && this.cliente.getVouchers().size() == 1
    }

    static boolean "El cliente recibe el servicio o producto"() {
        this.cliente.getVoucher(this.voucher.id) == this.voucher && this.cliente.getVouchers().size() == 1
    }

    static boolean "El voucher se marca como canjeado"() {
        this.voucher.estado == VoucherEstado.Canjeado
    }

    static boolean "El voucher se marca como pendiente de confirmacion"() {
        this.voucher.estado == VoucherEstado.ConfirmacionPendiente
    }

    static boolean "El cliente no recibe el producto ni servicio porque ya fue canjeado"() {
        this.errorDuranteSolicitudCanje && this.cliente.getVoucher(this.voucher.id) == this.voucher && this.cliente.getVouchers().size() == 1
    }

    static boolean "El cliente no puede volver a solicitar el canje porque ya esta pendiente de confirmacion"() {
        this.errorDuranteSolicitudCanje && this.cliente.getVoucher(this.voucher.id) == this.voucher && this.cliente.getVouchers().size() == 1 && this.voucher.estado == VoucherEstado.ConfirmacionPendiente
    }

    static boolean "El cliente no puede volver a solicitar el canje porque ya esta canjeado"() {
        this.errorDuranteSolicitudCanje
    }

    static boolean "El negocio no puede confirmar el canje porque nunca el canje nunca fue solicitado"() {
        this.errorDuranteConfirmacionCanje
    }

    static boolean "El negocio puede ver el reclamo"() {
        this.negocio.obtenerReclamosAbiertos().size() == 1 && this.negocio.obtenerReclamosAbiertos()[0] == this.reclamo && this.reclamo.estado == ReclamoEstado.Abierto && this.reclamo.mensajes.size() == 1
    }

    static boolean "El negocio puede contestar el reclamo"() {
        this.reclamo = this.reclamoService.nuevoMensaje(this.reclamo.id, this.negocio.id, "respuesta")
        this.reclamo.estado == ReclamoEstado.Respondido && this.reclamo.mensajes.size() == 2
    }

    static boolean "El cliente puede cerrar el reclamo"() {
        this.reclamo = this.reclamoService.cerrarReclamo(this.reclamo.id, this.cliente.id)
        this.reclamo.estado == ReclamoEstado.Cerrado && this.reclamo.mensajes.size() == 2
    }

    static void "El cliente cierra el reclamo"() {
        this.reclamo = this.reclamoService.cerrarReclamo(this.reclamo.id, this.cliente.id)
    }

    static void "El cliente reabre el reclamo"() {
        this.reclamo.reabrir()
    }

    static boolean "El cliente puede agregar nuevos mensajes"() {
        try {
            this.reclamo = this.reclamoService.nuevoMensaje(this.reclamo.id, this.cliente.id, "pregunta")
            return true
        } catch (RuntimeException e) {
            return false
        }
    }

    static boolean "El negocio puede agregar nuevos mensajes"() {
        try {
            this.reclamo = this.reclamoService.nuevoMensaje(this.reclamo.id, this.negocio.id, "respuesta")
            return true
        } catch (RuntimeException e) {
            return false
        }
    }

    static boolean "El talonario talonario_A aparece en la posición 1"() {
        println(this.talonariosRecomendados)
        this.talonariosRecomendados[0].id == this.talonario_A.id
    }

    static boolean "El talonario talonario_B aparece en la posición 2"() {
        this.talonariosRecomendados[1].id == this.talonario_B.id
    }
}
