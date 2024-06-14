import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'
import router from '../router'
import { log } from 'console'
import { http } from '../utils/http'
import {bikeData} from '../types/bike'

export const useBikeStore = defineStore('user', () => {
    // bike数组
    const bike = ref<bikeData[]>([])

    // 


    const userStore = useBikeStore()

    return { bike, userStore}
})