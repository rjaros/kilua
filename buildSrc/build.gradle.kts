plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/kotlin/p/dokka/dev")
    }}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    implementation(gradleApi())
    implementation("com.vanniktech:gradle-maven-publish-plugin:${libs.versions.vannik.maven.publish.get()}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${libs.versions.dokka.get()}")
}
