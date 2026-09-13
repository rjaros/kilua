# AGENTS.md

Kilua: Kotlin Multiplatform web framework (Compose runtime) targeting **both Kotlin/JS and Kotlin/Wasm**. Gradle 9.7.1 wrapper, Kotlin 2.4.20, JDK 25 (SSR examples/buildSrc default; Gradle plugin targets 21).

## Build / test / lint

- Everything is a Gradle subproject: `./gradlew :kilua:build`, `./gradlew :modules:kilua-routing:allTests`.
- Root `./gradlew build` builds every module + example — slow, and examples fail unless the snapshot Gradle plugin is published locally (see below). Prefer module-scoped commands.
- Tests: module tests live in `src/commonTest`. `allTests` runs node AND browser (Karma/ChromeHeadless) suites for both `js` and `wasmJs` — needs Chrome installed. Node-only: `jsNodeTest` / `wasmJsNodeTest`.
- Lint: detekt per-module (`./gradlew :modules:kilua-routing:detekt`). Config `detekt-config.yml` is shared; `maxIssues: 0`. Root has no detekt task.
- `gradle.properties`: `org.gradle.jvmargs=-Xmx10g`, config cache off, `kotlin.js.yarn=false` (npm, not yarn; lockfiles under `kotlin-js-store/` and `kotlin-js-store/wasm/`).

## Repo layout

- `kilua/` — main `dev.kilua:kilua` facade (Application, startApplication, HTML components).
- `modules/` — optional feature modules; `kilua-core-modules` (`CoreModule` et al.) is dependency injection glue, not a runtime module.
- `plugins/` — `kilua-gradle-plugin` (`dev.kilua` Gradle plugin, applied by examples/templates) and `kilua-ksp-processor`.
- `kilua-assets/` — npm-published package (`aaa-kilua-assets`, `zzz-kilua-assets`); its declared version must match `npm-kilua-assets` in the version catalog.
- `examples/` — runnable apps (JS+Wasm; SSR ones add `jvm`). Built against the `:kilua` project **plus** the published `dev.kilua` plugin.
- `templates/template/` — standalone starter project users copy; upgraded at each release (must stay buildable on its own).

## Conventions every module follows

- Module `build.gradle.kts` files use shared helpers from `buildSrc/src/main/kotlin/Shared.kt`: `kotlinJsTargets()` + `kotlinWasmTargets()` (adds browser+node), `compilerOptions()` (adds `-Xexpect-actual-classes` + opt-ins), `setupDokka()`, `setupPublishing()`, `setupKsp()`. Match the pattern of a sibling module — do not hand-write targets.
- `explicitApi()` is set on every module: all public declarations need explicit visibility/modifier.
- All library code must compile for BOTH `js` and `wasmJs` targets; JS-only interop goes in `jsMain`/`wasmJsMain` with a `commonMain` interface.
- HTML element builders are codegened: to add an element, declare a `dev.kilua.html.I*` interface annotated with `@SimpleHtmlComponent(tagName = ...)`; the KSP processor (`plugins/kilua-ksp-processor`) generates the builder into `build/generated/ksp/metadata/commonMain/kotlin`. Editing an annotated interface requires re-running KSP (`compileKotlinJs`/`compileKotlinWasmJs` depend on `kspCommonMainKotlinMetadata` already).

## Examples need the snapshot plugin in mavenLocal

`settings.gradle.kts` resolves from `mavenLocal()`; examples/template apply the `dev.kilua` plugin at `kilua-published` version (currently `0.0.36-SNAPSHOT`), which only exists locally. Before building/running examples:

```
./gradlew publishToMavenLocal -PSNAPSHOT=true   # build-mvnlocal.sh
```

Then run an example dev server: `./gradlew -t :examples:bootstrap-form:jsBrowserDevelopmentRun` (or `:wasmJsBrowserDevelopmentRun`), port 3000. SSR/fullstack examples: `:examples:X:jvmRun` (port 8080), `jarWithJs`/`jarWithWasmJs`, `exportWithJs`/`exportWithWasmJs`. SSR examples share frontend code via a `webMain` source set (js+wasmJs) with JVM backend in `jvmMain`.

## Version bumps touch three files

`kilua` (release) and `kilua-published` (plugin version, snapshot during dev) in `gradle/libs.versions.toml`; `templates/template/gradle/libs.versions.toml`; and `versions.json` (consumed by the Kilua Project Wizard website, includes per-server dependency overrides). All three must stay in sync; recent commits confirm the pattern (`0.0.35` → `0.0.36-RC` → `0.0.36`).