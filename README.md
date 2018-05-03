# JOOQ-Generator-Gradle-Plugin
Generate JOOQ-related database metadata as easy this gradle config:

```groovy
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath 'org.labun.jooq:code-generator-gradle-plugin:2.0-SNAPSHOT'
        classpath 'com.h2database:h2:1.4.196'
    }
}

apply plugin: 'java'
apply plugin: 'org.labun.jooq.codegenerator'

jooqCodeGeneration {
    database = {
        username = 'sa'
        password = ''
        driverClass = 'org.h2.Driver'
        dbMetaClass = 'org.jooq.util.h2.H2Database'
        jdbcUrl = 'jdbc:h2:mem:test';'
        schemas = ['PUBLIC']
    }
}

```

Also you can make easily make it generate classes based on you own velocity templates:
```groovy
jooqCodeGeneration {
...
    generators = [
            {
                generatorName = 'CustomGenerator'
                subGenerator = 'org.labun.jooq.generator.task.TableSubGenerator'
                packageName = 'com.example'
                className = nameConfig { postfix = 'Table' }
                template = 'templates/my-custom-template.vm'
                javaTimeDates = true
            }
    ]
}
```

Look an example project for more information: https://github.com/LabunORG/jooq-generator-gradle-plugin-samples
