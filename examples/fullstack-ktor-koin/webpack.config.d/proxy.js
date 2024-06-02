if (config.devServer) {
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
}
