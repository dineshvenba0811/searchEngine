import { createRouter, createWebHistory } from "vue-router";
import AllActivities from "./components/AllActivities.vue";
import ActivityDetailsById from "./components/ActivityDetailsById.vue";
import CreateActivities from "./components/CreateActivities.vue";

const routes = [
    { path: "/", name:AllActivities,component: AllActivities },
   // { path: "/create", name:CreateActivities,component: CreateActivities },
    { path: "/allActivities", name:AllActivities,component: AllActivities },
    { path: "/activityDetailById/:id", name:ActivityDetailsById,component: ActivityDetailsById,props:true }
];

const router = createRouter({
    history: createWebHistory(), // Uses HTML5 history mode
    routes,
});

export default router;