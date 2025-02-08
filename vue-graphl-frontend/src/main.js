import { ApolloClient, createHttpLink, InMemoryCache } from '@apollo/client/core'
import { createApp, provide, h } from 'vue'
import { DefaultApolloClient } from "@vue/apollo-composable";
import './style.css'
import App from './App.vue'
import router from "./router.js";
import 'bootstrap/dist/css/bootstrap.min.css';
import "bootstrap-icons/font/bootstrap-icons.css";
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
// HTTP connection to the API
const httpLink = createHttpLink({
    // You should use an absolute URL here
    uri: 'http://localhost:8080/graphql',
})

// Cache implementation
const cache = new InMemoryCache()

// Create the apollo client
const apolloClient = new ApolloClient({
    link: httpLink,
    cache,
})

const app = createApp({
    setup() {
        provide(DefaultApolloClient, apolloClient);
    },
    render: () => h(App),
});
app.use(router);
app.mount("#app");
