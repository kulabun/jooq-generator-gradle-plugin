package org.labun.jooq.codegenng.gradle

import org.labun.jooq.codegen.config.NameConfig

/**
 * @author Konstantin Labun
 */
class JooqCodeGenerationExtension {
    Closure database
    List<Closure> generators

    def methodMissing(String configName, args) {
        if ('nameConfig'.equals(configName)) return new NameConfig()
    }

    def nameConfig(Closure closure) {
        def config = new NameConfig()
        config.with(closure)
        return config
    }
}
