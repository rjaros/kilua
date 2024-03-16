config.resolve.modules.push("../../processedResources/js/main");
config.resolve.modules.push("../../processedResources/wasmJs/main");
config.resolve.alias = {
    "zzz-kilua-assets/style.css": false,
    "@fortawesome/fontawesome-free/css/all.min.css": false,
    "bootstrap/dist/css/bootstrap.min.css": false,
    "bootstrap-icons/font/bootstrap-icons.css": false,
    "@eonasdan/tempus-dominus/dist/css/tempus-dominus.min.css": false,
    "tabulator-tables/dist/css/tabulator.min.css": false,
    "tabulator-tables/dist/css/tabulator_bootstrap5.min.css": false,
    "tabulator-tables/dist/css/tabulator_bulma.min.css": false,
    "tabulator-tables/dist/css/tabulator_materialize.min.css": false,
    "tabulator-tables/dist/css/tabulator_midnight.min.css": false,
    "tabulator-tables/dist/css/tabulator_modern.min.css": false,
    "tabulator-tables/dist/css/tabulator_simple.min.css": false,
    "tabulator-tables/dist/css/tabulator_semanticui.min.css": false,
    "toastify-js/src/toastify.css": false,
    "tom-select/dist/css/tom-select.bootstrap5.min.css": false,
    "tom-select/dist/css/tom-select.default.min.css": false,
    "tom-select/dist/css/tom-select.min.css": false,
    "trix/dist/trix.css": false
}
if (config.devServer) {
    config.devServer.hot = true;
    config.devServer.open = false;
    config.devServer.port = 3000;
    config.devServer.historyApiFallback = true;
    config.devtool = 'eval-cheap-source-map';
    config.devServer.proxy = {
        "/rpc/*": {
            target: 'http://localhost:8080',
        },
        "/rpcws/*": {
            target: 'http://localhost:8080',
            ws: true,
        },
    }
} else {
    config.target = 'node';
    config.devtool = undefined;
}

// disable bundle size warning
config.performance = {
    assetFilter: function (assetFilename) {
        return !assetFilename.endsWith('.js');
    },
};
