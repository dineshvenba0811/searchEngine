<script>
import StarRating from "./StarRating.vue";

export default {
  name: 'ActivityBox',
  components: {StarRating},
  props:["activity"]
}
</script>

<template>
  <div class="card activity-card shadow-sm rounded overflow-hidden" style="width: 24rem;">

    <router-link :to="`/activityDetailById/${activity.id}`">
      <img src="../assets/dummy-image.webp" class="card-img-top" alt="Activity Image" style="cursor: pointer;">
    </router-link>

    <!-- Card Body -->
    <div class="card-body text-center">
      <h5 class="card-title fw-bold">{{ activity.title }}</h5>
    </div>

    <!-- Card Footer -->
    <div class="card-footer bg-light text-center">
      <p class="mb-1">
        <StarRating :rating="activity.rating" />
      </p>
      <p class="fw-semibold text-primary">
        From {{ activity.currency }} {{ activity.price }} per person
      </p>
    </div>

  </div>
</template>
<style scoped>

</style>