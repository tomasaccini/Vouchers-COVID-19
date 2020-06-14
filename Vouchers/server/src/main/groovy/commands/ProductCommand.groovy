package commands

import grails.validation.Validateable

class ProductCommand implements Validateable {

    Long id
    Long version
    String name
    String description
}
