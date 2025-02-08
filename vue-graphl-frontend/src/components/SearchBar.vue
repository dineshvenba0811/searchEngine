<script setup>
import { ref, watch } from "vue";
import { useQuery } from "@vue/apollo-composable";
import { useRouter } from "vue-router";
import gql from "graphql-tag";

const router = useRouter();
const showDropdown = ref(false);
const query = ref(""); // User input title
const suggestions = ref([]);
const minPrice = ref("");
const maxPrice = ref("");
const specialOffer = ref(false);

const SEARCH_SUPPLIER_SUGGESTIONS = gql(`query searchAutoSuggest($query: String!) {
    searchAutoSuggest(keyword: $query){
    id
    title
    }
  }`);


const { result, refetch } = useQuery(SEARCH_SUPPLIER_SUGGESTIONS, { query: query.value });

watch(query, async () => {
  if (query.value.length > 2) {
    console.log("triggering auto suggest query")// Trigger API call after 3 characters
    await refetch({ query: query.value });
    suggestions.value = result.value?.searchAutoSuggest || [];
  } else {
    suggestions.value = []; // Clear suggestions if query is too short
  }
});

const goToActivityDetails = (activityId) => {
  router.push({path: `/activityDetailById/${activityId}`});
};

const getActivitiesByTitle = (activityTitle) => {
  router.push({
    path: "/allActivities",
    query: {
      title: query.value || undefined,
      minPrice: minPrice.value || undefined,
      maxPrice: maxPrice.value || undefined,
      specialOffer: specialOffer.value ? "true" : undefined
    }
  });
};

const hideDropdownWithDelay = () => {
  setTimeout(() => {
    showDropdown.value = false;
  }, 200);
};

// Price Filter
const applyPriceFilter = () => {
  router.push({
    path: "/allActivities",
    query: {
      title: query.value || undefined,
      minPrice: minPrice.value || undefined,
      maxPrice: maxPrice.value || undefined,
      specialOffer: specialOffer.value ? "true" : undefined
    }
  });
};

// Checkbox Filter
const yesChecked = ref(false);
const noChecked = ref(false);

const applyCheckboxFilter = () => {
  let specialOffer = undefined;

  if (yesChecked.value && !noChecked.value) {
    specialOffer = "true"; // Only "Yes" checked
  } else if (!yesChecked.value && noChecked.value) {
    specialOffer = "false"; // Only "No" checked
  } else if (yesChecked.value && noChecked.value) {
    specialOffer = undefined; // Both checked → No filtering (optional behavior)
  }

  router.push({
    path: "/allActivities",
    query: {
      title: query.value || undefined,
      minPrice: minPrice.value || undefined,
      maxPrice: maxPrice.value || undefined,
      specialOffer: specialOffer
    }
  });
};


</script>

<template>
  <div class="container">
    <div class="row align-items-center">
      <!-- Logo Section -->
      <router-link to="/" class="col-lg-2">
        <img src="../assets/logo.png" alt="Logo" id="logo" class="img-fluid" />
      </router-link>

      <!-- Search Bar Section -->
      <div class="col-lg-8">
        <nav class="navbar navbar-light bg-light p-2">
          <form class="container-fluid position-relative">
            <div class="input-group">
              <span class="input-group-text"><i class="bi bi-search"></i></span>

              <input
                  type="text"
                  class="form-control"
                  v-model="query"
                  placeholder="Search for activities"
                  aria-label="Search"
                  @focus="showDropdown = true"
                  @blur="hideDropdownWithDelay"
              />

              <button type="button" class="btn btn-primary" @click="getActivitiesByTitle(query)">Search</button>

              <ul
                  v-if="showDropdown && suggestions.length"
                  class="dropdown-menu show position-absolute mt-1 w-100 shadow"
                  ref="dropdown"
              >
                <li
                    v-for="suggestion in suggestions"
                    :key="suggestion.id"
                    class="dropdown-item"
                    @click="goToActivityDetails(suggestion.id)"
                >
                  {{ suggestion.title }}
                </li>
              </ul>
            </div>
          </form>
        </nav>
      </div>

      <!-- Price Filter Dropdown -->

    <div class="row align-items-center">
      <div class="col-lg-2">
        <div class="dropdown mx-2">
          <button
              class="btn btn-primary dropdown-toggle"
              type="button"
              id="priceDropdown"
              data-bs-toggle="dropdown"
              aria-expanded="false">
            Filter by Price
          </button>

          <div class="dropdown-menu p-3">
            <label for="minPrice" class="form-label">Min Price</label>
            <input v-model="minPrice" type="number" class="form-control mb-2" placeholder="Min Price">

            <label for="maxPrice" class="form-label">Max Price</label>
            <input v-model="maxPrice" type="number" class="form-control mb-2" placeholder="Max Price">

            <button class="btn btn-success w-100" @click="applyPriceFilter">Apply</button>
          </div>
        </div>
      </div>

      <div class="col-lg-2">
        <div class="dropdown">
          <button
              class="btn btn-primary dropdown-toggle"
              type="button"
              id="checkboxDropdown"
              data-bs-toggle="dropdown"
              aria-expanded="false">
            Special Offer
          </button>

          <div class="dropdown-menu p-3">
            <div class="form-check">
              <input v-model="yesChecked" class="form-check-input" type="checkbox" id="checkYes">
              <label class="form-check-label" for="checkYes">Yes</label>
            </div>
            <div class="form-check">
              <input v-model="noChecked" class="form-check-input" type="checkbox" id="checkNo">
              <label class="form-check-label" for="checkNo">No</label>
            </div>

            <button class="btn btn-success w-100 mt-2" @click="applyCheckboxFilter">Apply</button>
          </div>
        </div>
      </div>
    </div>

    </div>
  </div>
</template>

<style scoped>
#logo {
  max-width: 120px; /* Ensures logo remains appropriately sized */
  height: auto;
}

.dropdown-menu {
  z-index: 1050;
  max-height: 200px;
  overflow-y: auto;
}
.position-relative {
  display: block;
}
.input-group-text {
  background: white;
}
.position-absolute {
  top: 100%; /* ✅ Positions dropdown just below the input field */
  left: 0;
  width: 100%;
}
</style>