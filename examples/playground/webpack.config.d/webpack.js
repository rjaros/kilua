config.resolve.modules.push("../../processedResources/js/main");
config.resolve.modules.push("../../processedResources/wasmJs/main");

if (config.devServer) {
    config.devServer.hot = true;
    config.devServer.open = false;
    config.devServer.port = 3000;
    config.devtool = 'eval-cheap-source-map';
} else {
    config.devtool = undefined;
}

// disable bundle size warning
config.performance = {
    assetFilter: function (assetFilename) {
        return !assetFilename.endsWith('.js');
    },
};
