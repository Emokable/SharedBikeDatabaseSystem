/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-15 02:50:26
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-15 03:49:27
 */
import { ref } from 'vue'
import { defineStore } from 'pinia'
import { TableConfig } from '../types/table'
import { locationData } from '../types/location'

export const useMapStore = defineStore('map', () => {
    // 定义ponit数组，为多个经纬度点的数组
    const point = ref<locationData[]>([])

    // 定义area数组，为多个经纬度数组组成的数组
    const area = ref<locationData[]>([])

    // 清空point数组
    const clearPoint = () => {
        point.value = []
    }

    // 清空area数组
    const clearArea = () => {
        area.value = []
    }

    // 添加一个点
    const addPoint = (location: locationData) => {
        point.value.push(location)
    }
    // 添加一个区域
    const addArea = (location: locationData) => {
        area.value.push(location)
    }


    return { point, area, clearPoint, clearArea, addPoint, addArea}
})