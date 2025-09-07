;(function() {
    config.module.rules.push({
        test: /tailwind\.css$/,
        use: [
            {
                loader: "postcss-loader",
                options: {
                    postcssOptions: {
                        plugins: [
                            ["@tailwindcss/postcss", {} ],
                            (config.devServer ? undefined : [ "cssnano", {} ])
                        ]
                    }
                }
            }
        ]
    });
})();
