config.resolve.fallback = {
    "http": false,
};
config.resolve.modules.push("kotlin");
config.watchOptions = config.watchOptions || {
    ignored: ["**/*.kt", "**/node_modules"]
};
if (config.devServer) {
    config.devServer.client = {
        overlay: false
    };
    config.devServer.hot = true;
    config.devServer.open = false;
    config.devServer.port = 3000;
    config.devServer.historyApiFallback = true;
    config.devtool = 'eval-cheap-source-map';
    config.devServer.static = config.devServer.static.map(file => {
        if (typeof file === "string") {
            return {
                directory: file,
                watch: false,
            }
        } else {
            return file
        }
    });
} else {
    config.devtool = undefined;
    config.resolve.alias = {
        "zzz-kilua-assets/k-style.css": false,
        "zzz-kilua-assets/k-animation.css": false,
        "zzz-kilua-assets/k-bootstrap.css": false,
        "zzz-kilua-assets/k-jetpack.css": false,
        "zzz-kilua-assets/k-splitjs.css": false,
        "zzz-kilua-assets/k-tabulator.css": false,
        "zzz-kilua-assets/k-tempus-dominus.css": false,
        "zzz-kilua-assets/k-toastify.css": false,
        "zzz-kilua-assets/k-tom-select.css": false,
        "zzz-kilua-assets/k-trix.css": false,
        "bootstrap/dist/css/bootstrap.min.css": false,
        "@eonasdan/tempus-dominus/dist/css/tempus-dominus.min.css": false,
        "leaflet/dist/leaflet.css": false,
        "tabulator-tables/dist/css/tabulator.min.css": false,
        "tabulator-tables/dist/css/tabulator_bootstrap5.min.css": false,
        "tabulator-tables/dist/css/tabulator_bulma.min.css": false,
        "tabulator-tables/dist/css/tabulator_materialize.min.css": false,
        "tabulator-tables/dist/css/tabulator_midnight.min.css": false,
        "tabulator-tables/dist/css/tabulator_modern.min.css": false,
        "tabulator-tables/dist/css/tabulator_simple.min.css": false,
        "tabulator-tables/dist/css/tabulator_semanticui.min.css": false,
        "tabulator-tables/dist/css/tabulator_site_dark.min.css": false,
        "toastify-js/src/toastify.css": false,
        "tom-select/dist/css/tom-select.bootstrap5.min.css": false,
        "tom-select/dist/css/tom-select.default.min.css": false,
        "tom-select/dist/css/tom-select.min.css": false,
        "trix/dist/trix.css": false,
        "./tailwind/tailwind.css": false,
    };
}

// disable bundle size warning
config.performance = {
    assetFilter: function (assetFilename) {
        return !assetFilename.endsWith('.js');
    },
};
