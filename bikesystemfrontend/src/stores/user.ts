import {ref} from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'
import router from '../router'
import { log } from 'console'


export const useUserStore = defineStore('user', () => {
    const isLogged = ref(false)
    const token = useStorage<string | undefined>('token', undefined)
    const username = ref('')
    const email = ref('')
    const roles = ref<string[]>([])
    const avatar = ref('')

    const register = async (username: string, password: string) => {
    }

    const logout = () => {
        token.value = undefined
        username.value = ''
        email.value = ''
        roles.value = []
        avatar.value = ''
        isLogged.value = false
        router.push('/login')
    }    

    return {
        isLogged,
        token,
        username,
        email,
        roles,
        avatar,
        register,
        logout
    }
})