<template>
  <div class="login">
    <h1>Login2</h1>
    <form @submit.prevent="submitLogin">
      <div>
        <label for="username">email:</label>
        <input type="text" v-model="email" />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="password" />
      </div>
      <div>
        <button type="submit">Login</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
export default {
  name: "LoginView",
  data() {
    return {
      // 초기값 설정
      email: "",
      password: "",
    };
  },
  methods: {
    ...mapActions(["login"]),
    submitLogin() {
      this.login({ email: this.email, password: this.password })
        .then(() => {
          this.$router.push("/");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
