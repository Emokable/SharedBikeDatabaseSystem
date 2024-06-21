/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 14:31:12
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-21 12:10:27
 */
import axios from 'axios' // 使用前要先安装依赖：npm install axios 

// 创建axios实例
const service = axios.create({ 
	// 这里可以放一下公用属性等。
	baseURL: 'http://127.0.0.1:8088', // 用于配置请求接口公用部分，请求时会自动拼接在你定义的url前面。
	withCredentials: false, // 跨域请求时是否需要访问凭证
	timeout: 3 * 1000, // 请求超时时间
	headers: { // 请求头
		
	}
})

// 请求拦截器
service.interceptors.request.use(config => {
	// 这里可以进行请求加密等操作。如添加token,cookie，修改数据传输格式等。
	config.headers['token'] = '';

	return config;
})

service.interceptors.response.use(response => {
    // 请求成功进行的操作
	return response;
}, error => {
	// 请求失败进行的操作
	return error;
})

export default service;
