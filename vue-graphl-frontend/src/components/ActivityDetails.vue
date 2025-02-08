<script setup>
import { defineProps } from "vue";

const props = defineProps({
  activityResponse: Object
});
</script>

<template>
  <div class="container mt-4">
    <hr />

    <!-- Activity Title -->
    <div class="text-center">
      <h3 class="mb-4">{{ activityResponse.title }}</h3>
    </div>

    <!-- Activity Images -->
    <div class="row justify-content-center">
      <div class="col-lg-5 mb-3">
        <div class="card shadow-sm">
          <img src="../assets/dummy-image.webp" class="card-img-top rounded" alt="Activity Image" />
        </div>
      </div>
      <div class="col-lg-5 mb-3">
        <div class="card shadow-sm">
          <img src="../assets/dummy-image2.jpg" class="card-img-top rounded" alt="Activity Image" />
        </div>
      </div>
    </div>

    <!-- Activity Details -->
    <div class="row justify-content-center mt-4">
      <div class="col-lg-8">
        <div class="card shadow-sm p-4">
          <h4 class="mb-3">About this Tour</h4>

          <p class="text-muted">{{ activityResponse.title }}</p>

          <ul class="list-group list-group-flush">
            <li class="list-group-item">
              <strong>Price per person:</strong> {{ activityResponse.currency }} {{ activityResponse.price }}
            </li>
            <li class="list-group-item">
              <strong>Rating:</strong> ⭐ {{ activityResponse.rating }}
            </li>
            <li class="list-group-item">
              <strong>Tour Provider:</strong>
              <span v-if="activityResponse.supplierId">{{ activityResponse.supplierId.name ?? "No Supplier Name" }}</span>
            </li>
            <li class="list-group-item">
              <strong>Provider Address:</strong>
              <span v-if="activityResponse.supplierId">{{ activityResponse.supplierId.address ?? "No Address Available" }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>