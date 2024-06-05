import { createRouter, createWebHistory } from 'vue-router'
// 导入刚才编写的组件
import AppIndex from '@/components/home/AppIndex'
import AdminLogin from '@/components/AdminLogin'


const routerHistory = createWebHistory()

const router = createRouter({
    history: routerHistory,
    routes: [
        {
            path: '/login',
            name: 'AdminLogin',
            component: AdminLogin
        },
        {
            path: '/index',
            name: 'AppIndex',
            component: AppIndex
        }
    ]
})

