# Kilua

Experimental web framework for Kotlin/Wasm and Kotlin/JS.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Kilua allows you to build web applications with the [Kotlin](https://kotlinlang.org) language. 

You can think Kilua re-implements the [compose-html](https://github.com/JetBrains/compose-multiplatform#compose-html) 
library with a different API based on my [KVision](https://kvision.io) experiences and some new, ambitious goals in mind. 
Writing Kilua applications should be familiar to both Compose users (`@Composable` functions, state management) and 
KVision users (component based API, allowing some imperative, direct ways to interact with the UI components).

## Goals

- Modern web framework powered by Compose Runtime.
- Compile the same application code for Kotlin/Wasm and Kotlin/JS targets.
- Direct DOM manipulation without virtual DOM.
- Rendering to String with JS/Node target (ready for SSR).
- Ready to use components for all typical web application use cases. 
- Component based API (familiar to KVision users).
- Fullstack support ported from KVision.
- Modular architecture.

## Project status

Early in development. Main concepts have been tested, but everything is a subject to change.

## TODO

- [X] Compose runtime integration
- [X] Basic project architecture
- [X] HMR support (JS target only)
- [X] Testing environment
- [X] Typesafe CSS style properties
- [X] A few basic HTML tags
- [X] A few basic form input components with `StateFlow` integration
- [X] A component based on external NPM library (`SplitPanel`)
- [ ] Implement all standard HTML tags
- [ ] Implement all standard form input components
- [ ] Implement advanced form input components (based on external NPM libraries - TempusDominus, TomSelect, Imask, Trix)
- [ ] Typesafe forms support with buit-in validation 
- [ ] Implement Tabulator component
- [ ] Bootstrap module with basic components (tabs, toasts, dropdowns, modals etc.)
- [ ] Routing support
- [ ] I18n support
- [ ] Gradle plugin and KSP compiler plugin
- [ ] Fullstack support with SSR
- [ ] Code documentation
- [ ] Unit tests

## Code sample

This is a simple "Hello, world!" application written in Kilua:

```kotlin
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.compose.root
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.unaryPlus
import dev.kilua.startApplication

class App : Application() {

    override fun start() {
        root("root") {
            var state by remember { mutableStateOf("Hello, world!") }

            div {
                +state
            }
            button("Add an exclamation mark") {
                onClick {
                    state += "!"
                }
            }
        }
    }
}

fun main() {
    startApplication(::App, CoreModule)
}
```

## Leave a star

If you like this project, please give it a star on GitHub. Thank you!
