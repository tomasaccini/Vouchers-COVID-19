package vouchers

class UrlMappings {

    static mappings = {

        //Country
        "/countries"(resources:"country")

        //Business
        "/businesses"(resources:"business")
        "/businesses/getCounterfoils/$id"(controller: 'negocio', action: 'getCounterfoils')

        //Voucher
        "/vouchers"(resources:"voucher")
        "/vouchers/getByUser/$userId"(controller: 'voucher', action: 'getByUser')

        //Client
        "/clients"(resources:"client")

        //Products
        "/products"(resources:"product")
        "/products/getByBusiness/$businessId"(controller: 'producto', action: 'getByBusiness')

        // Reclamos
        "/reclamos"(resources:"complaint")
        "/reclamos/getPorNegocio/$negocioId"(controller: 'reclamo', action: 'getPorNegocio')
        "/reclamos/getPorCliente/$clienteId"(controller: 'reclamo', action: 'getPorCliente')
        "/reclamos/cerrarReclamo/$reclamoId"(controller: 'reclamo', action: 'cerrarReclamo')

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

        get "/counterfoils"(controller: 'counterfoil', action: 'getAll')
        post "/counterfoils"(controller: 'counterfoil', action: 'save')

        get "/recommendations"(controller: 'recommendation', action: 'getRecommendationsForUser')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
