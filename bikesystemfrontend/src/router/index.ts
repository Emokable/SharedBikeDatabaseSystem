/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-11 09:50:41
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 23:50:46
 */
import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/user' 


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
      path: '/insertForm',
      name: 'InsertForm',
      component: () => import('../components/InsertForm.vue')
    },
    {
      path: '/timeSelect',
      name: 'TimeSelect',
      component: () => import('../components/TimeSelect.vue')
    },
    {
      path: '/locationSelect',
      name: 'LocationSelect',
      component: () => import('../components/LocationSelect.vue')
    },
    {
      path: '/mapForNoParking',
      name: 'MapForNoParking',
      component: () => import('../components/MapContainerForNoParkingZone.vue')
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

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  if (to.name !== 'hello' && !userStore.isLogged) {
    next({ name: 'hello' })
  } else {
    next()
  }
})

export default router
