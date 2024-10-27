import dev.petuska.npm.publish.extension.domain.json.PackageJson

plugins {
    alias(libs.plugins.npm.publish)
}

val packageJsonFun: PackageJson.() -> Unit = {
    main.set("index.js")
    version.set("0.0.15")
    description.set("The assets for the Kilua framework")
    keywords.set(listOf("kilua", "kotlin", "wasm"))
    homepage.set("https://kilua.dev")
    license.set("MIT")
    repository {
        type.set("git")
        url.set("git+https://github.com/rjaros/kilua.git")
    }
    author {
        name.set("Robert Jaros")
    }
    bugs {
        url.set("https://github.com/rjaros/kilua/issues")
    }
}

npmPublish {
    dry.set(System.getenv("NPM_AUTH_TOKEN") == null)
    readme.set(file("README.md"))
    packages {
        register("aaa-kilua-assets") {
            packageName.set("aaa-kilua-assets")
            files {
                from("$projectDir/src/js")
                from("$projectDir/src/index.js")
            }
            packageJson(packageJsonFun)
        }
        register("zzz-kilua-assets") {
            packageName.set("zzz-kilua-assets")
            files {
                from("$projectDir/src/css")
                from("$projectDir/src/index.js")
            }
            packageJson(packageJsonFun)
        }
    }
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            authToken.set(System.getenv("NPM_AUTH_TOKEN"))
        }
    }
}
