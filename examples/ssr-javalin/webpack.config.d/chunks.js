;(function() {
    const webpack = require('webpack')
    config.plugins.push(new webpack.optimize.LimitChunkCountPlugin({
        maxChunks: 1,
    }));
})();
