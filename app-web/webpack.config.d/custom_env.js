const webpack = require("webpack")

module.exports = env => {
    let environmentPlugin

    if (env.debug !== undefined) {
        environmentPlugin = new webpack.DefinePlugin({
            CUSTOM_ENV: {
                "debug": JSON.stringify(env.debug),
            },
        })
    } else {
        environmentPlugin = new webpack.DefinePlugin({
            CUSTOM_ENV: {},
        })
    }

    config.plugins.push(environmentPlugin)

    return config
}
