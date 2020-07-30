package vouchers

class UrlMappings {

    static mappings = {

        //Country
        "/api/countries"(resources:"country")

        //Business
        "/api/businesses"(resources:"business")

        //Voucher
        "/api/vouchers"(resources:"voucher")
        "/api/vouchers/getByUser/$userId"(controller: 'voucher', action: 'getByUser')

        //Client
        "/api/clients"(resources:"client")

        delete "/api/$controller/$id(.$format)?"(action:"delete")
        get "/api/$controller(.$format)?"(action:"index")
        get "/api/$controller/$id(.$format)?"(action:"show")
        post "/api/$controller(.$format)?"(action:"save")
        put "/api/$controller/$id(.$format)?"(action:"update")
        patch "/api/$controller/$id(.$format)?"(action:"patch")
        // !!!!
        get "/api/vouchers"(controller: 'voucher', action: 'getByUserId')
        post "/api/vouchers"(controller: 'voucher', action: 'create')
        put "/api/vouchers"(controller: 'voucher', action: 'asd')

        get "/api/counterfoils"(controller: 'counterfoil', action: 'getAll')
        post "/api/counterfoils"(controller: 'counterfoil', action: 'save')

        get "/api/recommendations"(controller: 'recommendation', action: 'getRecommendationsForUser')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
