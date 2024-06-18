import { ref, onMounted } from 'vue'
import { defineStore } from 'pinia'
import { useStorage } from '@vueuse/core'
import router from '../router'
import { http } from '../utils/http'

export const useUserStore = defineStore('user', () => {
    const isLogged = ref(false)
    const editAble = ref(false)
    const isSuperuser = ref(false)
    const token = useStorage<string | undefined>('token', undefined)
    const username = ref('')
    const authorityLevel = ref('')
    const avatar = ref('')
    const adminid = ref(0)

    const setUser = (userInfoObj) => {
        isLogged.value = userInfoObj.isLogged
        editAble.value = userInfoObj.editAble
        isSuperuser.value = userInfoObj.isSuperuser
        token.value = userInfoObj.token
        username.value = userInfoObj.username
        authorityLevel.value = userInfoObj.authorityLevel
        avatar.value = userInfoObj.avatar
        adminid.value = userInfoObj.adminid
    }

    onMounted(() => {
        let userInfo = localStorage.getItem('userInfo')
        if (userInfo) {
            let userInfoObj = JSON.parse(userInfo)
            setUser(userInfoObj)
        }
    })

    const register = async (username: string, password: string) => {
        // Registration logic here
    }

    const login = async (usernameParam: string, password: string) => {
        let data = {
            username: usernameParam,
            password
        }
        http.post('/login', data)
            .then(successResponse => {
                if (successResponse.data.errCode == 0) {
                    window.alert('登录成功')
                    token.value = successResponse.headers['x-authorization-with']
                    authorityLevel.value = successResponse.data.data.privileges
                    avatar.value = '/' + successResponse.data.data.avatar
                    adminid.value = successResponse.data.data.adminid
                    editAble.value = authorityLevel.value === 'superuser' || authorityLevel.value === 'data_modification'
                    isSuperuser.value = authorityLevel.value === 'superuser'
                    isLogged.value = true
                    username.value = data.username

                    let userInfo = {
                        username: username.value,
                        avatar: avatar.value,
                        authorityLevel: authorityLevel.value,
                        adminid: adminid.value,
                        isLogged: isLogged.value,
                        editAble: editAble.value,
                        isSuperuser: isSuperuser.value,
                        token: token.value
                    }

                    localStorage.setItem('userInfo', JSON.stringify(userInfo))
                } else {
                    window.alert('登录失败：' + successResponse.data.message)
                }
            })
            .catch(failResponse => {
                console.log(failResponse)
                window.alert('登录失败：无法连接到服务器')
            })
    }

    const logout = () => {
        token.value = undefined
        username.value = ''
        avatar.value = ''
        isLogged.value = false
        authorityLevel.value = ''
        editAble.value = false
        isSuperuser.value = false
        localStorage.clear()
        router.push('/')
    }

    return {
        isLogged,
        editAble,
        isSuperuser,
        token,
        username,
        authorityLevel,
        avatar,
        adminid,
        register,
        logout,
        login,
        setUser // Expose setUser for external use if necessary
    }
})