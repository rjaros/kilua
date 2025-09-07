# Building and running

To build the application, you need to have JDK 21 or later installed.

The following tasks are available:

- `./gradlew viteRun` - run the Vite dev server for JS target on `http://localhost:3000`
- `./gradlew -t viteCompileDev` - run the development compilation in continuous mode for JS target
- `./gradlew viteBuild` - build production application for JS target to `build/vite/dist` directory

WasmJs target is currently not supported.

Note: use `gradlew.bat` instead of `./gradlew` on Windows operating system.
