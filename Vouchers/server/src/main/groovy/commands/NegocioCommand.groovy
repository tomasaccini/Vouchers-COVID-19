package commands

import vouchers.Direccion

class NegocioCommand {
    Long id
    Long version
    String nombre
    String numeroTelefonico
    String website
    String categoria
    List<TalonarioCommand> talonariosCommands
    Direccion direccion


    @Override
    public String toString() {
        return "NegocioCommand{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
