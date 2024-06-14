/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 09:50:41
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 20:56:56
 */
import { createRouter, createWebHistory } from 'vue-router'

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
      path: '/profile',
      name: 'Profile',
      component: () => import('../views/PersonHome.vue')
    },
    {
      path: '/table',
      name: 'Table',
      component: () => import('../components/Table.vue')
    },
    {
      path: '/editForm',
      name: 'EditForm',
      component: () => import('../components/EditForm.vue')
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
      path: '/search/noParking',
      name: 'SearchNoParking',
      component: () => import('../components/search/NoParkingSearch.vue')
    },
    {
      path: '/mapPanel',
      name: 'MapPanel',
      component: () => import('../views/MapPanel.vue')
    },
    {
      path: '/adminView',
      name: 'AdminView',
      component: () => import('../views/AdminView.vue')
    },
    {
      path: '/bikeView',
      name: 'BikeView',
      component: () => import('../views/BikeView.vue')
    },
    {
      path: '/recordView',
      name: 'RecordView',
      component: () => import('../views/RecordView.vue')
    },
    {
      path: '/noParkingView',
      name: 'NoParkingView',
      component: () => import('../views/NoParkingView.vue')
    },
    {
      path: '/riderView',
      name: 'RiderView',
      component: () => import('../views/RiderView.vue')
    }
  ]
})

export default router
