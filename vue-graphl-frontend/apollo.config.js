// apollo.config.js
module.exports = {
    client: {
        service: {
            name: 'vue-graphql-frontend',
            // URL to the GraphQL API
            url: 'http://backend:8080/graphql',
        },
        // Files processed by the extension
        includes: [
            'src/**/*.vue',
            'src/**/*.js',
        ],
    },
}