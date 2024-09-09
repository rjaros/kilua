plugins {
    kotlin("jvm")
    alias(libs.plugins.spring.dependency.management)
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":examples:ssr-spring-boot"))
    implementation("org.springframework.boot:spring-boot-devtools")
}

springBoot {
    mainClass.value(project.parent?.extra?.get("mainClassName")?.toString())
}
