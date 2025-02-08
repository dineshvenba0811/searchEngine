<script setup>
import { useRoute } from "vue-router";
import { useQuery } from "@vue/apollo-composable";
import ActivityBox from "./ActivityBox.vue";
import {computed, ref, watch, watchEffect} from "vue";
import gql from "graphql-tag";

const route = useRoute();
// Define GraphQL ALL_ACTIVITY_QUERY to fetch getActivities
// based on the user input title, minprice, maxprice, special offer
const ALL_ACTIVITY_QUERY = gql(`query getActivities(
    $title: String
    $minPrice: Int
    $maxPrice: Int
    $specialOffer: Boolean
  ) {
    getActivities(
      title: $title
      minPrice: $minPrice
      maxPrice: $maxPrice
      specialOffer: $specialOffer
    ) {
      id
      title
      price
      currency
      rating
      specialOffer
      supplierId {
        id
        name
      }
    }
  }`);

const filters = ref ({
  title: route.query.title || null,
  minPrice: route.query.minPrice ? parseInt(route.query.minPrice) : null,
  maxPrice: route.query.maxPrice ? parseInt(route.query.maxPrice) : null,
  specialOffer: route.query.specialOffer ? route.query.specialOffer === "true" : null,
});

const { result, loading, error,refetch } = useQuery(ALL_ACTIVITY_QUERY ,filters.value);
const activityResponse = computed(() => result.value?.getActivities ?? []);

watchEffect(() => {
  filters.value = {
    title: route.query.title || null,
    minPrice: route.query.minPrice ? parseInt(route.query.minPrice) : null,
    maxPrice: route.query.maxPrice ? parseInt(route.query.maxPrice) : null,
    specialOffer: route.query.specialOffer ? route.query.specialOffer === "true" : null,
  };
  refetch(filters.value);
});
</script>

<template>
  <div class="container">
  <div v-if="loading">Loading...</div>

    <div class="row" v-else>
      <div v-for="activity in activityResponse" :key="activity.id" class="col-md-6 col-xl-3 col-12 pt-3  justify-content-around d-flex">
        <ActivityBox :activity="activity">
        </ActivityBox>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>
