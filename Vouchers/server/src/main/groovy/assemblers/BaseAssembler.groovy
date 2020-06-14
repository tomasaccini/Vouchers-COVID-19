package assemblers

abstract class BaseAssembler {

    def copyProperties(source, target) {
        source.properties.each { key, value ->
            if (target.hasProperty(key) && !(key in ['class', 'metaClass']) && value) {
                target[key] = value
            }
        }
    }
}
