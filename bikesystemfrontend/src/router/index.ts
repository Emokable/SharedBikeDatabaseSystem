/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 09:50:41
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-12 19:29:56
 */
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../components/HelloWorld.vue')
    },
    {
      path: '/hello',
      name: 'hello',
      component: () => import('../components/HelloWorld.vue')
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('../components/AdminLogin.vue')
    },
    {
      path: '/profile',
      name: 'Profile',
      component: () => import('../views/PersonHome.vue')
    },
    {
      path: '/search/user',
      name: 'SearchUser',
      component: () => import('../components/search/UserSearch.vue')
    },
    {
      path: '/search/bike',
      name: 'SearchBike',
      component: () => import('../components/search/BikeSearch.vue')
    },
    {
      path: '/search/record',
      name: 'SearchRecord',
      component: () => import('../components/search/RecordSearch.vue')
    },
    {
      path: '/search/noparking',
      name: 'SearchNoParking',
      component: () => import('../components/search/NoParkingSearch.vue')
    },
    {
      path: '/mapPanel',
      name: 'MapPanel',
      component: () => import('../components/MapPanel.vue')
    }
  ]
})

export default router
