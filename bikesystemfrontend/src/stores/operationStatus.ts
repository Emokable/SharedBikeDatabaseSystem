/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 17:28:11
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 15:42:12
 */
import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'

export const useStatusStore = defineStore('status', () => {
    const isEditFinish = ref(false)
    const isCreateFinish = ref(false)

    const setEditFinish = (status: boolean) => {
        isEditFinish.value = status
    }
    const setCreateFinish = (status: boolean) => {
        isCreateFinish.value = status
    }
    
    return {
        isEditFinish,
        isCreateFinish,
        setEditFinish,
        setCreateFinish
    }
})