// Workaround for https://youtrack.jetbrains.com/issue/KT-88434
(function () {
    if (typeof config !== "undefined" && config.files && config.basePath && config.basePath.indexOf("build/wasm") !== -1) {
        var runnerIndex = config.files.findIndex(function (f) {
            return String(f).indexOf('kotlin-test-karma-runner.js') !== -1;
        });
        var insertAt = runnerIndex === -1 ? 0 : runnerIndex + 1;
        config.files.splice(insertAt, 0, "../../../../kilua/karma.config.d/karma.conf.js");
    }
    if (typeof config === "undefined" && window && window.kotlinTest && window.kotlinTest.adapterTransformer) {
        delete window.kotlinTest.adapterTransformer;
    }
})();
