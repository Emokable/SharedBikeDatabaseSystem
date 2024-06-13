import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'
import router from '../router'
import { log } from 'console'
import { http } from '../utils/http'

export const useNoParkingZoneStore = defineStore('noParkingZone', () => {
    const noParkingZones = ref([])

    const getNoParkingZones = async () => {
        http.get('/noParkingZone')
            .then(successResponse => {
                if (successResponse.data.errCode == 0) {
                    noParkingZones.value = successResponse.data.data
                } else {
                    window.alert('获取禁停区失败：' + successResponse.data.massage)
                }
            })
            .catch(failResponse => {
                console.log(failResponse)
                window.alert('获取禁停区失败：无法连接到服务器')
            })
    }

    const addNoParkingZone = async (name: string, location: string) => {
        let data = {
            name,
            location
        }
        http.post('/noParkingZone', data)
            .then(successResponse => {
                if (successResponse.data.errCode == 0) {
                    window.alert('添加禁停区成功')
                    getNoParkingZones()
                } else {
                    window.alert('添加禁停区失败：' + successResponse.data.massage)
                }
            })
            .catch(failResponse => {
                console.log(failResponse)
                window.alert('添加禁停区失败：无法连接到服务器')
            })
    }

    const deleteNoParkingZone = async (id: number) => {
        http.delete('/noParkingZone/' + id)
            .then(successResponse => {
                if (successResponse.data.errCode == 0) {
                    window.alert('删除禁停区成功')
                    getNoParkingZones()
                } else {
                    window.alert('删除禁停区失败：' + successResponse.data.massage)
                }
            })
            .catch(failResponse => {
                console.log(failResponse)
                window.alert('删除禁停区失败：无法连接到服务器')
            })
    }

    return {
        noParkingZones,
        getNoParkingZones,
        addNoParkingZone,
        deleteNoParkingZone
    }
})