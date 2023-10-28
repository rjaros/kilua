plugins {
    alias(libs.plugins.npm.publish)
}

npmPublish {
    dry.set(System.getenv("NPM_AUTH_TOKEN") == null)
    readme.set(file("README.md"))
    packages {
        register("kilua-assets") {
            files {
                from("$projectDir/src")
            }
            packageJson {
                main.set("index.js")
                version.set("0.0.4")
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
        }
    }
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            authToken.set(System.getenv("NPM_AUTH_TOKEN"))
        }
    }
}
