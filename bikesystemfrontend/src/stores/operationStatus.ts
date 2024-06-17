/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 17:28:11
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 06:00:50
 */
import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'

export const useStatusStore = defineStore('status', () => {
    const isEditFinish = ref(false)
    const isCreateFinish = ref(false)
    const isSelectRecordByTime = ref(false)
    const isSelectBikeByLocation = ref(false)
    const isGetingSchedule = ref(false)
    const isShowHotTimes = ref(false)
    const isShowHotBikes = ref(false)

    const setEditFinish = (status: boolean) => {
        isEditFinish.value = status
    }
    const setCreateFinish = (status: boolean) => {
        isCreateFinish.value = status
    }
    const setSelectRecordByTime = (status: boolean) => {
        isSelectRecordByTime.value = status
    }
    const setSelectBikeByLocation = (status: boolean) => {
        isSelectBikeByLocation.value = status
    }
    const setGetingSchedule = (status: boolean) => {
        isGetingSchedule.value = status
    }
    const setShowHotTimes = (status: boolean) => {
        isShowHotTimes.value = status
    }
    const setShowHotBikes = (status: boolean) => {
        isShowHotBikes.value = status
    }
    return {
        isEditFinish,
        isCreateFinish,
        setEditFinish,
        setCreateFinish,
        isSelectRecordByTime,
        setSelectRecordByTime,
        isSelectBikeByLocation,
        setSelectBikeByLocation,
        isGetingSchedule,
        setGetingSchedule,
        isShowHotTimes, 
        setShowHotTimes,
        isShowHotBikes,
        setShowHotBikes
    }
})