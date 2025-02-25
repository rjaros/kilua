# Kilua

Composable web framework for Kotlin/Wasm and Kotlin/JS.

[![Maven Central](https://img.shields.io/maven-central/v/dev.kilua/kilua.svg?label=Maven%20Central)](https://central.sonatype.com/search?namespace=dev.kilua&name=kilua)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Kilua allows you to build modern web applications with the [Kotlin](https://kotlinlang.org) language. 

It is powered by the Compose Runtime and is similar to the [compose-html](https://github.com/JetBrains/compose-multiplatform#compose-html)
library. It gives you clean, modular API to create declarative UI components and manage their state. 
Unlike compose-html, Kilua supports both Kotlin/Wasm and Kotlin/JS targets. It also provides a lot
of ready to use components for many typical web application use cases.

Kilua is a kind of successor to my [KVision](https://kvision.io) framework. Writing Kilua applications should be 
familiar to both Compose users (`@Composable` functions, state management, coroutines/flow integration) and 
KVision users (component based API, allowing some imperative, direct ways to interact with the UI components).

## Features

- Use powerful Compose programming model and state management to develop web applications.
- Choose from the wide range of ready to use components and form inputs.
- Compile the same application code for Kotlin/Wasm and Kotlin/JS targets.
- Create fullstack applications with [Kilua RPC](https://github.com/rjaros/kilua-rpc) library.
- Translate your application to other languages with [Gettext](https://github.com/rjaros/kilua-gettext) - 
one of the most widely used tool for i18n.
- Deploy your application with full SSR (Server Side Rendering) for better 
SEO performance and user experience.
- Export your application as a set of static HTML files for more affordable hosting solutions.

## Project status

Kilua is being actively developed. Please create an issue for any bugs or feature requests. 
Contributions and PRs are welcomed. All artifacts are published to Maven Central.

## Documentation and examples

The official guide is published at [https://kilua.gitbook.io/kilua-guide](https://kilua.gitbook.io/kilua-guide). 
It's still a work in progress and may be incomplete.

Current API documentation is published at [https://rjaros.github.io/kilua/api/](https://rjaros.github.io/kilua/api/).

Different example applications can be found in the [examples directory](https://github.com/rjaros/kilua/tree/main/examples), 
including fully compatible TodoMVC and Realworld.io (with SSR) implementations.

More documentation, including tutorials and guides, is planned for the future.

## Building and running the examples

To build the examples, you need to have JDK 21 or later installed.

The following tasks are available from the root project level:

- `./gradlew -t :examples:[exampleName]:jsBrowserDevelopmentRun` - run the webpack dev server in continuous build mode for JS target on `http://localhost:3000`
- `./gradlew -t :examples:[exampleName]:wasmJsBrowserDevelopmentRun` - run the webpack dev server in continuous build mode for Wasm target on `http://localhost:3000`
- `./gradlew :examples:[exampleName]:jsBrowserDistribution` - build production application for JS target to `examples/[exampleName]/build/dist/js/productionExecutable` directory
- `./gradlew :examples:[exampleName]:wasmJsBrowserDistribution` - build production application for Wasm target to `examples/[exampleName]/build/dist/wasmJs/productionExecutable` directory

For fullstack and SSR examples additional tasks are available:

- `./gradlew :examples:[exampleName]:jvmRun` - run the backend application for development on `http://localhost:8080` 
- `./gradlew :examples:[exampleName]:jarWithJs` - build and package the production application with JS frontend to `examples/[exampleName]/build/libs` directory
- `./gradlew :examples:[exampleName]:jarWithWasmJs` - build and package the production application with Wasm frontend to `examples/[exampleName]/build/libs` directory
- `./gradlew :examples:[exampleName]:exportWithJs` - export static site with JS frontend to `examples/[exampleName]/build/site` directory
- `./gradlew :examples:[exampleName]:exportWithWasmJs` - export static site with Wasm frontend to `examples/[exampleName]/build/site` directory

To run packaged fullstack or SSR example just use `java -jar [exampleName]-[version].jar` command.

Note: use `gradlew.bat` instead of `./gradlew` on Windows operating system.

If you want to start your own project, just copy the [Kilua template](https://github.com/rjaros/kilua/tree/main/templates/template) project.

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

Kilua is the first Kotlin/Wasm and Kotlin/JS web framework supporting true Server-Side Rendering. 
SSR is a crucial concept in modern web development that enhances user experience 
and boosts SEO performance. Kilua SSR support is based on the possibility to run exactly the same 
application code both in the browser and in NodeJs environment. What's more, you can easily use 
WASM compilation target for much better performance.  

### SSR Features

- Preparing application for SSR is as easy as changing the router class.
- Ability to use external API calls and fullstack RPC services.
- Automatically extracting CSS styles from JS bundle and injecting them into the HTML document before sending to the browser.
- Serialization of the application state from the server to the client side.

### Current limitations

- The URL address and the browser preferred locale need to be the only source of the application state.
- Using browser APIs directly is not recommended.
- Advanced JS components (like RichText, Tabulator etc.) are rendered on the server
as simple HTML placeholders.
- Rendering authenticated content is not supported at the moment.
- The "hydration" is implemented in a very primitive way (by replacing the rendered content).

## Leave a star

If you like this project, please give it a star on GitHub. Thank you!
