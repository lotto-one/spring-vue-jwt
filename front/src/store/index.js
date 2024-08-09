import { createStore } from "vuex";
import axios from "axios";

export default createStore({
  state: {
    //초기화 로컬스토리지 선언
    email: localStorage.getItem("email") || "",
    memno: localStorage.getItem("memno") || "",
    rolecd: localStorage.getItem("rolecd") || "",
    token: localStorage.getItem("token") || "",
  },
  getters: {
    // 인증했는지 판단
    isAuthenticated: (state) => !!state.token,
    email: (state) => state.email,
    rolecd: (state) => state.rolecd,
    accessToken: (state) => state.token, // 'accessToken'을 사용하는 경우
    memno: (state) => state.memno, // 'id' 또는 'memno'를 사용하는 경우
  },
  mutations: {
    setAuth(state, { email, token, memno, rolecd }) {
      state.email = email;
      state.memno = memno;
      state.token = token;
      state.rolecd = rolecd;
      localStorage.setItem("email", email);
      localStorage.setItem("token", token);
      localStorage.setItem("memno", memno);
      localStorage.setItem("rolecd", rolecd);
    },
    clearAuth(state) {
      state.email = "";
      state.token = "";
      state.memno = "";
      state.rolecd = "";
      localStorage.removeItem("email");
      localStorage.removeItem("token");
      localStorage.removeItem("memno");
      localStorage.removeItem("rolecd");
    },
  },
  actions: {
    login({ commit }, { email, password }) {
      return axios
        .post("http://localhost/jwtdemo/membership/login", {
          email,
          password,
        })
        .then((response) => {
          console.log(response.data);

          const { accessToken, memno, rolecd } = response.data; // Postman 결과와 동일하게 맞춤

          commit("setAuth", {
            email,
            token: accessToken,
            memno: memno,
            rolecd: rolecd,
          });
        })
        .catch((error) => {
          console.error(
            "Login error:",
            error.response ? error.response.data : error.message
          );
          throw new Error("Invalid credentials");
        });
    },
    logout({ commit, state }) {
      return axios
        .get("http://localhost/jwtdemo/membership/logout")
        .then(() => {
          commit("clearAuth");
        })
        .catch((error) => {
          console.error(
            "Logout error:",
            error.response ? error.response.data : error.message
          );
        });
    },
    setAuth({ commit }, { email, token, memno, rolecd }) {
      commit("setAuth", { email, token, memno, rolecd });
    },
  },
});
