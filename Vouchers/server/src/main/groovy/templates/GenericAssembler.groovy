package templates

abstract class GenericAssembler<Domain, Bean> {


    abstract Bean toBean(Domain domain)

    abstract Domain fromBean(Bean bean)

    List<Bean> toBeans(List<Domain> lista) {
        return convertList(lista, this.&toBean)
    }

    List<Domain> fromBeans(List<Bean> lista) {
        return convertList(lista, this.&fromBean)
    }

    private List convertList(List original, conversor) {
        if (original == null)
            return null
        def result = []
        for (elem in original) {
            result.add(conversor(elem))
        }
        return result
    }
}