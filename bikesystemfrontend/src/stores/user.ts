/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 13:59:19
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 21:27:44
 */
import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'
import router from '../router'
import { log } from 'console'
import { http } from '../utils/http'


export const useUserStore = defineStore('user', () => {
    const isLogged = ref(false)
    const token = useStorage<string | undefined>('token', undefined)
    const username = ref('')
    const authorityLevel = ref('')
    const avatar = ref('')
    const email = ref('')


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
        }
      }


    const logout = () => {
        token.value = undefined
        username.value = ''
        avatar.value = ''
        isLogged.value = false
        router.push('/login')
    }

    return {
        isLogged,
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