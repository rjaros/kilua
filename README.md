# Kilua

Composable web framework for Kotlin/Wasm and Kotlin/JS.

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
- Rendering to String with JS/Node or WasmJS/Node target (for use with SSR).
- Ready to use components for all typical web application use cases. 
- Component based API (familiar to KVision users).
- Integration with [Kilua RPC](https://github.com/rjaros/kilua-rpc) library (fullstack support ported from KVision)
- Modular architecture.

## Project status

All main concepts have been tested and all planned frontend and fullstack components are ready.
Base SSR (server-side-rendering) is implemented for Ktor server, support for other servers will follow.
A Gradle plugin has also been implemented to automate SSR configuration and tasks.

I'm investigating possible solutions for I18n (internationalization).

The project can be built and tested with single gradle tasks. Contributions and PRs are welcomed.
Still no artifacts are published, but we are close to the first alpha release.

## TODO

- [X] Compose runtime integration
- [X] Basic project architecture
- [X] HMR support (JS target only)
- [X] Testing environment
- [X] Typesafe CSS style properties
- [X] A few basic HTML tags
- [X] A few basic form input components with `StateFlow` integration
- [X] A component based on external NPM library (`SplitPanel`)
- [X] Implement all standard HTML tags
- [X] Implement all standard form input components
- [X] Bootstrap module with basic components (tabs, toasts, dropdowns, modals etc.)
- [X] Implement RichText form component based on Trix editor NPM library
- [X] Implement RichDateTime form components based on Tempus Dominus NPM library
- [X] Implement TomSelect and TomTypeahead form components based on TomSelect NPM library
- [X] Implement support for masked inputs based on Imask NPM library
- [X] CSS styles declarations
- [X] Typesafe forms support with built-in validation 
- [X] Implement Tabulator component
- [X] Routing support
- [X] Web client
- [ ] I18n support (currently blocked by https://github.com/JetBrains/compose-multiplatform/issues/4171)
- [X] Gradle plugin ~~and KSP compiler plugin~~
- [X] Fullstack `Select`
- [X] Fullstack `TomSelect`
- [X] Fullstack `TomTypeahead`
- [X] Fullstack `Tabulator`
- [X] SSR support
- [X] Code documentation
- [X] Unit tests
- [ ] More examples of typical applications (crud, chat, todo, etc.) 

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
## SSR (Server-Side Rendering)

Kilua is the first Kotlin/JS web framework supporting true Server-Side Rendering. 
SSR is a crucial concept in modern web development that enhances user experience 
and boosts SEO performance. Kilua SSR support is based on the possibility to run exactly the same 
application code both in the browser and in NodeJs environment. What's more, you can easily use 
WASM compilation target for much better performance.  

### Current limitations:
- Only Ktor server is supported out of the box.
- The URL address needs to be the only source of application state.
- Using browser APIs directly is not recommended.
- Advanced JS components (like RichText, Tabulator etc.) are rendered on the server
as simple HTML placeholders.
- Running fullstack RPC services on the server is possible, but needs further testing. 
- Rendering authenticated content is not supported at the moment.
- The "hydration" is implemented in a very primitive way (by replacing the rendered content).

## Leave a star

If you like this project, please give it a star on GitHub. Thank you!
