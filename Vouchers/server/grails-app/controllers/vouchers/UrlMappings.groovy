package vouchers

class UrlMappings {

    static mappings = {

        //Country
        "/countries"(resources:"country")

        //Business
        "/businesses"(resources:"business")
        "/negocios"(controller: 'negocio', action: 'obtenerTodos')
        post "/negocios"(controller: 'negocio', action: 'crear')
        "/businesses/getCounterfoils/$id"(controller: 'negocio', action: 'getCounterfoils')

        //Voucher
        // "/vouchers"(resources:"voucher")
        "/vouchers"(controller: 'voucher', action: 'getAll')
        "/vouchers/getByUser/$userId"(controller: 'voucher', action: 'getByUser')

        //Client
        "/clients"(resources:"client")

        //Products
        "/products"(resources:"product")
        "/products/getByBusiness/$businessId"(controller: 'producto', action: 'getByBusiness')

        // Reclamos
        // "/reclamos"(resources:"complaint")
        "/reclamos"(controller: 'reclamo', action: 'obtenerTodos')
        "/reclamos/$reclamoId"(controller: 'reclamo', action: 'obtenerReclamo')
        post "/reclamos"(controller: 'reclamo', action: 'crearReclamo')
        post "/reclamos/$reclamoId/nuevoMensaje"(controller: 'reclamo', action: 'nuevoMensaje')
        "/reclamos/negocios/$negocioId"(controller: 'reclamo', action: 'getPorNegocio')
        "/reclamos/clientes/$clienteId"(controller: 'reclamo', action: 'getPorCliente')
        post "/reclamos/cerrar/$reclamoId"(controller: 'reclamo', action: 'cerrarReclamo')

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

        "/tarifarios"(controller: 'tarifario', action: 'getAll')
        // post "/tarifarios"(controller: 'tarifario', action: 'create')
        post "/tarifarios"(controller: 'tarifario', action: 'crear')

        get "/recommendations"(controller: 'recommendation', action: 'getRecommendationsForUser')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
