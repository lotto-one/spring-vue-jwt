// router/index.js
import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import AboutView from "../views/AboutView.vue";
import Login2 from "../views/LoginView.vue";
import Admin from "../views/Admin.vue";
import Consul from "../views/Consul.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
    meta: { requiresAuth: true }, // 모든 사용자에게 필요한 권한
  },
  {
    path: "/about",
    name: "about",
    component: AboutView,
    meta: { requiresAuth: true }, // 모든 사용자에게 필요한 권한
  },
  {
    path: "/login",
    name: "login",
    component: Login2,
  },
  {
    path: "/Admin",
    name: "Admin",
    component: Admin,
    meta: { requiresAuth: true, role: "A" }, // 관리자만 접근 가능
  },
  {
    path: "/Consul",
    name: "Consul",
    component: Consul,
    meta: { requiresAuth: true, role: "C" }, // 컨설턴트만 접근 가능
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
