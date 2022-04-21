import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  { path: '/', component: () => import('@/views/Home/home.vue') },
  { path: '/login', component: () => import('@/views/login.vue') }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
