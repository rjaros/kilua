if (config.devServer) {
    config.devServer.hot = true;
    config.devServer.open = false;
    config.devServer.port = 3000;
    config.devServer.historyApiFallback = true;
    config.devtool = 'eval-cheap-source-map';
    config.devServer.proxy = [
        {
            context: ["/rpc/*", "/rpcsse/*"],
            target: 'http://localhost:8080'
        },
        {
            context: ["/rpcws/*"],
            target: 'http://localhost:8080',
            ws: true
        }
    ]
} else {
    config.devtool = undefined;
}

// disable bundle size warning
config.performance = {
    assetFilter: function (assetFilename) {
        return !assetFilename.endsWith('.js');
    },
};
