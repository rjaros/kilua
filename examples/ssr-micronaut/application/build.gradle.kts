plugins {
    kotlin("jvm")
    alias(libs.plugins.micronaut.application)
    alias(libs.plugins.micronaut.aot)
}

dependencies {
    implementation(project(":examples:ssr-micronaut"))
}

application {
    mainClass.set(project.parent?.extra?.get("mainClassName")?.toString())
}

micronaut {
    runtime("netty")
    processing {
        incremental(true)
        annotations("example.*")
    }
    aot {
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
    }
}
