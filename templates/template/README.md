# Building and running

To build the application, you need to have JDK 17 or later installed.

The following tasks are available:

- `./gradlew -t jsRun` - run the webpack dev server in continuous build mode for JS target on `http://localhost:3000`
- `./gradlew -t wasmJsRun` - run the webpack dev server in continuous build mode for Wasm target on `http://localhost:3000`
- `./gradlew jsBrowserDistribution` - build production application for JS target to `build/dist/js/productionExecutable` directory
- `./gradlew wasmJsBrowserDistribution` - build production application for Wasm target to `build/dist/wasmJs/productionExecutable` directory

Note: use `gradlew.bat` instead of `./gradlew` on Windows operating system.
