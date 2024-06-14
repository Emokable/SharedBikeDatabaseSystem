/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 17:28:11
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 17:33:44
 */
import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'

export const useStatusStore = defineStore('status', () => {
    const isEditFinish = ref(false)

    const setEditFinish = (status: boolean) => {
        isEditFinish.value = status
    }
    
    return {
        isEditFinish,
        setEditFinish
    }
})