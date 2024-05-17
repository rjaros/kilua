;(function() {
    config.module.rules.push({
        test: /\.twcss$/,
        use: [
            "style-loader",
            {
                loader: "css-loader",
                options: {sourceMap: false}
            },
            {
                loader: "postcss-loader",
                options: {
                    postcssOptions: {
                        plugins: [
                            ["tailwindcss", {} ],
                            ["autoprefixer", {} ],
                            (config.devServer ? undefined : [ "cssnano", {} ])
                        ]
                    }
                }
            }
        ]
    });
})();
