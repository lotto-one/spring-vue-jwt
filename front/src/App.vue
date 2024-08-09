<!-- App.vue -->
<template>
  <div>
    <nav>
      <div><router-link to="/">Home</router-link> |</div>
      <div><router-link to="/about">내 정보</router-link> |</div>
      <div>
        <router-link v-if="!isAuthenticated || rolecd !== 'M'" to="/Admin"
          >관리자</router-link
        >
        |
        <router-link v-if="!isAuthenticated || rolecd !== 'M'" to="/Consul"
          >컨설턴트</router-link
        >
        |
      </div>
      <div v-if="isAuthenticated" style="display: inline">
        <span>
          <b>{{ email }} 님 반가워요</b></span
        >
        <button @click="logout">Logout</button> |
      </div>
      <div v-else><router-link to="/login">Login</router-link> |</div>
    </nav>
    <router-view />
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

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
  methods: {
    ...mapActions(["logout"]),
  },
};
</script>

<style>
nav {
  padding: 30px;
}
nav a {
  font-weight: bold;
  color: #2c3e50;
}
nav a.router-link-exact-active {
  color: #42b983;
}
nav div {
  float: left;
}
</style>
