;(function() {
    const webpack = require('webpack')
    config.plugins.push(new webpack.DefinePlugin({
        Temporal: {},
    }));
})();
