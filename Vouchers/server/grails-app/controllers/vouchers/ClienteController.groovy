package vouchers


import grails.rest.RestfulController
import commands.ClienteCommand
import assemblers.ClienteAssembler

import javax.transaction.Transactional

class ClienteController extends RestfulController {

    static responseFormats = ['json', 'xml']
    ClienteService clienteService
    ClienteAssembler clienteAssembler = new ClienteAssembler()

    ClienteController() {
        super(Cliente)
    }

    /*
    * Returns client requested by Id
    * URL/clients/{id}

    def show(Cliente client) {
        println("Request for a client by id")
        if (!client) {
            response.sendError(404)
        } else {
            respond client
        }
    }
    */

    ClienteCommand obtenerCliente(Long clienteId) {
        Cliente cliente = clienteService.obtener(clienteId)
        ClienteCommand clienteCommand = clienteAssembler.toBean(cliente)
        respond clienteCommand
    }

    List<ClienteCommand> obtenerTodos() {
        List<ClienteCommand> clientesCommand = []
        List<Cliente> clientes = clienteService.obtenerTodos()

        for (Cliente cliente : clientes) {
            ClienteCommand clienteCommand = clienteAssembler.toBean(cliente)
            clientesCommand.add(clienteCommand)

        }

        respond clientesCommand
    }

    @Transactional
    def save(Cliente client) {
        println("Creating client")
        if (!client || client.hasErrors()) {
            respond client.errors
            return
        }
        try {
            client.save(failOnError: true, flush: true)
            respond client
        } catch (Exception e) {
            response.sendError(404)
        }

    }

    @Transactional
    def update(Cliente client) {
        println("Updating client")
        if (client?.hasErrors()) {
            respond client.errors
            return
        }
        //TODO: Check version for update crash
        try {
            client.save(failOnError: true, flush: true)
            respond client
        } catch (Exception e) {
            response.sendError(404)
        }
    }

}
