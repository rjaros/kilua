;(function() {
    const MiniCssExtractPlugin = require("mini-css-extract-plugin");
    config.module.rules.push({
        test: /tailwind\.css$/,
        use: [
            MiniCssExtractPlugin.loader,
            {
                loader: "css-loader",
                options: {sourceMap: false}
            },
            {
                loader: "postcss-loader",
                options: {
                    postcssOptions: {
                        plugins: [
                            ["@tailwindcss/postcss", {} ],
                            [ "cssnano", {} ]
                        ]
                    }
                }
            }
        ]
    });
    config.plugins.push(new MiniCssExtractPlugin({
        filename: "tailwindcss.css"
    }));
})();
