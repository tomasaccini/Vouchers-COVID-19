package templates

abstract class ConcreteObjectAssembler<Domain, Bean> extends GenericAssembler<Domain, Bean> {

    protected abstract getEntity(Long id)

    protected abstract createBean()

    Bean toBean(Domain domain) {
        Bean bean = createBean()
        for (attr in bean.properties.keySet()) {
            if (domain.properties.containsKey(attr) && !(attr in ['class', 'metaClass'])) {
                bean[attr] = domain[attr]
            }
        }
        bean.id = domain.id
        bean.version = domain.version
        return bean
    }

    Domain fromBean(Bean bean) {
        Domain domain = getEntity(bean.id)
        for (attr in domain.properties.keySet()) {
            if (bean.properties.containsKey(attr) && !(attr in ['class', 'metaClass', 'dateCreated'])) {
                domain[attr] = bean[attr]
            }
        }
        domain.id = bean.id
        domain.version = bean.version
        return domain
    }
}
