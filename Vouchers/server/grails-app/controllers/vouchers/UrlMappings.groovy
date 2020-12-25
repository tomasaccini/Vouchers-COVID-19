package vouchers

class UrlMappings {

    static mappings = {

        //Country
        "/countries"(resources:"country")

        //Negocio
        "/negocios"(resources:"negocio")
        "/negocios"(controller: 'negocio', action: 'obtenerTodos')
        "/negocios/search"(controller: 'negocio', action: 'search')
        post "/negocios"(controller: 'negocio', action: 'crear')
        "/negocios/obtenerTalonarios/$id"(controller: 'negocio', action: 'obtenerTalonarios')

        //Voucher
        "/vouchers"(resources:"voucher")
        "/vouchers/getByUser/$userId"(controller: 'voucher', action: 'getByUser')
        "/vouchers/search"(controller: 'voucher', action: 'search')
        post "/vouchers/canjear"(controller: 'voucher', action: 'canjear')
        post "/vouchers/confirmar"(controller: 'voucher', action: 'confirmar')

        //Client
        "/clientes"(resources:"cliente")

        //Products
        "/productos"(resources:"producto")
        "/productos/crear"(controller: 'producto', action: 'crear')
        "/productos/obtenerPorNegocio/$negocioId"(controller: 'producto', action: 'obtenerPorNegocio')

        // Reclamos
        // "/reclamos"(resources:"complaint")
        "/reclamos"(controller: 'reclamo', action: 'obtenerTodos')
        "/reclamos/$reclamoId"(controller: 'reclamo', action: 'obtenerReclamo')
        post "/reclamos"(controller: 'reclamo', action: 'crearReclamo')
        post "/reclamos/$reclamoId/nuevoMensaje"(controller: 'reclamo', action: 'nuevoMensaje')
        "/reclamos/usuarios/$usuarioId"(controller: 'reclamo', action: 'getPorUsuario')
        "/reclamos/negocios/$negocioId"(controller: 'reclamo', action: 'getPorNegocio')
        "/reclamos/clientes/$clienteId"(controller: 'reclamo', action: 'getPorCliente')
        post "/reclamos/cerrar/$reclamoId"(controller: 'reclamo', action: 'cerrarReclamo')

        // Recomendaciones
        "/recommendations"(controller: 'recommendation', action: 'getRecommendationsForUser')

        // Talonarios
        "/talonarios"(controller: 'talonario', action: 'getAll')
        "/talonarios/search"(controller: 'talonario', action: 'search')
        post "/talonarios/comprar"(controller: 'talonario', action: 'comprar')
        post "/talonarios"(controller: 'talonario', action: 'crear')


        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
        // !!!!
        get "/vouchers"(controller: 'voucher', action: 'getByUserId')
        post "/vouchers"(controller: 'voucher', action: 'create')
        put "/vouchers"(controller: 'voucher', action: 'asd')


        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
