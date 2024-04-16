config.module.rules.push(
    {
        test: /\.(jpe?g|png|gif|svg)$/i,
        type: 'asset/resource'
    }
);
config.module.rules.push(
    {
        test: /\.(po)$/i,
        type: 'asset/source'
    }
);
