package org.labun.jooq.generator.gradle
import org.labun.jooq.codegen.DefaultGenerator
import org.labun.jooq.codegen.GenerationTool
import org.labun.jooq.codegen.config.Configuration
import org.labun.jooq.codegen.config.DatabaseConfig
import org.labun.jooq.codegen.config.CodeGenerationConfig
import org.labun.jooq.codegen.config.Defaults
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author Konstantin Labun
 */
class JooqCodeGenerationPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def configuration = new Configuration()
        configuration.database = new DatabaseConfig()
        configuration.database.schemas(["PUBLIC"])

        JooqCodeGenerationExtension extension = project.extensions.create("jooqCodeGeneration", JooqCodeGenerationExtension.class)

        project.task('generateJOOQ') {
            doLast {
                configuration.database.with(extension.database)

                List<CodeGenerationConfig> defaultConfigs = Defaults.CodeGenerationConfigs.all()

                extension.generators.each({ cfg ->
                    def generator = new CodeGenerationConfig()
                    generator.with(cfg)

                    def defaultGenerator = defaultConfigs.find({ it.generatorName == generator.generatorName })
                    if (defaultGenerator != null) {
                        generator = defaultGenerator
                        generator.with(cfg)
                    }

                    configuration.codeGeneration.add(generator)
                })

                GenerationTool.generate(new DefaultGenerator(configuration))
            }
        }
    }
}