import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

fun KotlinMultiplatformExtension.compilerOptions() {
    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

fun KotlinMultiplatformExtension.kotlinJsTargets(buildTarget: String, isInIdea: Boolean) {
    if (buildTarget == "js" || !isInIdea) {
        js(IR) {
            useEsModules()
            browser {
                testTask {
                    useKarma {
                        useChromeHeadless()
                    }
                }
            }
            nodejs {
                testTask {
                    useMocha()
                }
            }
        }
    }
}

@OptIn(ExperimentalWasmDsl::class)
fun KotlinMultiplatformExtension.kotlinWasmTargets(buildTarget: String, isInIdea: Boolean) {
    if (buildTarget == "wasm" || !isInIdea) {
        wasmJs {
            useEsModules()
            browser {
                testTask {
                    useKarma {
                        useChromeHeadlessWasmGc()
                    }
                }
            }
            nodejs {
                testTask {
                    useMocha()
                }
            }
        }
    }
}
