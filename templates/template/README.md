# Building and running

To build the application, you need to have JDK 21 or later installed.

The following tasks are available:
- 
- `./gradlew jsViteRun` - run the Vite dev server for JS target on `http://localhost:3000`
- `./gradlew -t jsViteCompileDev` - run the development compilation in continuous mode for JS target
- `./gradlew jsViteBuild` - build production application for JS target to `build/vite/js/dist` directory

- `./gradlew wasmJsViteRun` - run the Vite dev server for Wasm target on `http://localhost:3000`
- `./gradlew -t wasmJsViteCompileDev` - run the development compilation in continuous mode for Wasm target
- `./gradlew wasmJsViteBuild` - build production application for Wasm target to `build/vite/wasmJs/dist` directory

- `./gradlew -t jsBrowserDevelopmentRun` - run the webpack dev server in continuous build mode for JS target on `http://localhost:3000`
- `./gradlew -t wasmJsBrowserDevelopmentRun` - run the webpack dev server in continuous build mode for Wasm target on `http://localhost:3000`
- `./gradlew jsBrowserDistribution` - build production application for JS target to `build/dist/js/productionExecutable` directory
- `./gradlew wasmJsBrowserDistribution` - build production application for Wasm target to `build/dist/wasmJs/productionExecutable` directory

Note: use `gradlew.bat` instead of `./gradlew` on Windows operating system.
