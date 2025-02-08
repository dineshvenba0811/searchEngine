<script setup>
import { ref } from "vue";
import { useMutation } from "@vue/apollo-composable";
import gql from "graphql-tag";

// Define GraphQL Mutation
const INSERT_ACTIVITY = gql(` mutation insertActivity($activity: ActivityInput!) {
    insertActivity(activity: $activity)
  }`);

// Form Data (Reactive State)
const title = ref("");
const price = ref(null);
const currency = ref("");
const rating = ref(null);
const specialOffer = ref(false);
const supplierId = ref(null);

// Use Mutation Hook
const { mutate, loading, error } = useMutation(INSERT_ACTIVITY);

// Submit Function
const addActivity = async () => {
  try {
    const response = await mutate({
      activity: {
        title: title.value,
        price: price.value,
        currency: currency.value,
        rating: rating.value,
        specialOffer: specialOffer.value,
        supplierId: supplierId.value,
      }
    });

    alert("Activity added successfully!");
    console.log("Response:", response.data);

    // Reset form after submission
    title.value = "";
    price.value = null;
    currency.value = "";
    rating.value = null;
    specialOffer.value = false;
    supplierId.value = null;
  } catch (err) {
    console.error("Error inserting activity:", err);
    alert("Failed to add activity.");
  }
};
</script>
<template>
  <div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="col-lg-6">

      <div class="card shadow-sm rounded p-4">
        <h3 class="text-center mb-4">Add Activity</h3>

        <form >
          <!-- Title Input -->
          <div class="mb-3">
            <label class="form-label fw-bold">Title</label>
            <input type="text" class="form-control" v-model="title" required>
          </div>

          <!-- Price Input -->
          <div class="mb-3">
            <label class="form-label fw-bold">Price</label>
            <input type="number" class="form-control" v-model="price" required>
          </div>

          <!-- Currency Input -->
          <div class="mb-3">
            <label class="form-label fw-bold">Currency</label>
            <input type="text" class="form-control" v-model="currency" required>
          </div>

          <!-- Rating Input -->
          <div class="mb-3">
            <label class="form-label fw-bold">Rating</label>
            <input type="number" class="form-control" v-model="rating" required>
          </div>

          <!-- Special Offer Checkbox -->
          <div class="mb-3 form-check">
            <input class="form-check-input" type="checkbox" v-model="specialOffer">
            <label class="form-check-label fw-bold ms-2">Special Offer</label>
          </div>

          <!-- Supplier ID Input -->
          <div class="mb-3">
            <label class="form-label fw-bold">Supplier</label>
            <input class="form-control" type="number" v-model="supplierId" required>
          </div>

          <!-- Submit Button -->
          <div class="text-center">
            <button type="button" class="btn btn-primary" @click="addActivity">Search</button>
          </div>

          <!-- Error Message -->
          <div v-if="error" class="alert alert-danger mt-3">
            Failed to submit activity. Please try again.
          </div>

        </form>
      </div>

    </div>
  </div>
</template>

<style scoped>

</style>
