/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 11:56:19
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 13:54:05
 */
import {reactive, ref } from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'
import { http } from '../utils/http'
import { riderData } from '../types/rider'
import { useUserStore } from './user'

export const useRiderStore = defineStore('rider', () => {
    const rider = reactive<riderData>({
        userid: 0,
        username: '',
        gender: '',
        password: '',
        phoneNumber: '',
        avatar: '',
        birthday: '',
    })

    const isEdit = ref(false)

    const userStore = useUserStore()


    return { rider, isEdit}
})