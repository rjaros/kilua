plugins {
    kotlin("jvm")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":examples:ssr-spring-boot"))
    implementation(project.dependencies.platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-devtools")
}

springBoot {
    mainClass.value(project.parent?.extra?.get("mainClassName")?.toString())
}
