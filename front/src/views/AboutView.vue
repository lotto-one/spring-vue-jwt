<template>
  <div class="about" v-if="isAuthenticated">
    <h1>이메일: {{ email }}</h1>
    <h2>memno: {{ memno }}</h2>
    <p>토큰: {{ accessToken }}</p>
    <p>토큰 일치여부: {{ isAuthenticated }}</p>

    <p>현재 나의권한 : {{ rolecd }}</p>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters([
      "isAuthenticated",
      "email",
      "accessToken",
      "memno",
      "rolecd",
    ]),
  },

  watch: {
    isAuthenticated(newVal) {
      if (!newVal) {
        this.$router.push("/login");
      }
    },
  },
  mounted() {
    if (!this.isAuthenticated) {
      this.$router.push("/login");
      console.log("token");
    }
  },
};
</script>

<style scoped>
.about {
  padding: 20px;
}
</style>
