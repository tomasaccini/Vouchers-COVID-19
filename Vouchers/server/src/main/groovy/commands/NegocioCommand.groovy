package commands

class NegocioCommand {
    Long id
    Long version
    String nombre

    @Override
    public String toString() {
        return "NegocioCommand{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
