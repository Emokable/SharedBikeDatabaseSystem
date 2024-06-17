/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 13:59:19
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 04:55:40
 */
import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'
import router from '../router'
import { log } from 'console'
import { http } from '../utils/http'


export const useUserStore = defineStore('user', () => {
    const isLogged = ref(false)
    const editAble = ref(false)
    const isSuperuser = ref(false)
    const isInPersonHome = ref(false)
    const token = useStorage<string | undefined>('token', undefined)
    const username = ref('')
    const authorityLevel = ref('')
    const avatar = ref('')
    const adminid = ref(0)


    const register = async (username: string, password: string) => {
    }


    const login = async (username: string, password: string) => {
        let data = {
            username,
            password
        }
        http.post('/login', data)
            .then(successResponse => {
                if (successResponse.data.errCode == 0) {
                    window.alert('登录成功')
                    token.value = successResponse.headers['x-authorization-with']
                    authorityLevel.value = successResponse.data.data.privileges
                    avatar.value = '/'+successResponse.data.data.avatar
                    adminid.value = successResponse.data.data.adminid
                    if (authorityLevel.value === 'superuser' || authorityLevel.value === 'data_modification') {
                        editAble.value = true
                    }
                    if (authorityLevel.value === 'superuser') {
                        console.log(authorityLevel.value)
                        isSuperuser.value = true
                    }
                    isLogged.value = true
                    username = data.username
                    hydrate()
                } else {
                    window.alert('登录失败：' + successResponse.data.massage)
                }
            })
            .catch(failResponse => {
                console.log(failResponse)
                window.alert('登录失败：无法连接到服务器')
            })
    }

    const hydrate = async () => {
        if (!token.value) {
          return
        }else{
            // 通过
        }
      }


    const logout = () => {
        token.value = undefined
        username.value = ''
        avatar.value = ''
        isLogged.value = false
        authorityLevel.value = ''
        editAble.value = false
        router.push('/')
    }

    return {
        isLogged,
        editAble,
        isSuperuser,
        isInPersonHome,
        token,
        username,
        authorityLevel,
        avatar,
        register,
        logout,
        login,
        hydrate
    }
})