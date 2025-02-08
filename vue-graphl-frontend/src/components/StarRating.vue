<script setup>
import { computed } from "vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faStar, faStarHalfAlt, faStar as faStarEmpty } from "@fortawesome/free-solid-svg-icons";

const props = defineProps({
  rating: Number,
});

const stars = computed(() => {
  const fullStars = Math.floor(props.rating);
  const halfStar = props.rating % 1 >= 0.5;
  const emptyStars = 5 - fullStars - (halfStar ? 1 : 0);
  return { fullStars, halfStar, emptyStars };
});
</script>

<template>
  <div class="stars">
    <FontAwesomeIcon v-for="i in stars.fullStars" :key="'full-' + i" :icon="faStar" class="star full-star"/>
    <FontAwesomeIcon v-if="stars.halfStar" :icon="faStarHalfAlt" class="star half-star"/>
    <FontAwesomeIcon v-for="i in stars.emptyStars" :key="'empty-' + i" :icon="faStarEmpty" class="star empty-star"/>
    <p>{{rating}}</p>
  </div>
</template>

<style scoped>
.stars {
  display: flex;
  gap: 4px;
}

.star {
  color: #FFD700;
  font-size: 18px;
}
</style>