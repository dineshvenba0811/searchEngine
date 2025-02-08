<script setup>
import { useRoute } from "vue-router";
import {computed, ref, watch, watchEffect} from "vue";
import { useQuery } from "@vue/apollo-composable";
import gql from "graphql-tag";
import ActivityDetails from "./ActivityDetails.vue";


const route = useRoute();
const activityId = ref(route.params.id || route.query.id || "");
const ACTIVITY_DETAILS = gql(`query getActivityById($id: Int!){
  getActivityById(id:$id){
    id
    title
    rating
    price
    currency
    supplierId{
      id
      name
      city
      country
      address
    }
  }
}`);

const { result, loading, error ,refetch} = useQuery(ACTIVITY_DETAILS,{ id: parseInt(activityId.value) } );
const activityResponse = computed(() => result.value?.getActivityById ?? []);

watchEffect(() => {
  activityId.value = route.params.id || route.query.id || "";
  if (activityId.value) {
    refetch({ id: parseInt(activityId.value) });
  }
});
</script>

<template>
  <ActivityDetails :activityResponse="activityResponse" />
</template>

<style scoped>

</style>