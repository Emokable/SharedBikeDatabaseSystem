/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 14:35:25
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-12 19:51:30
 */
import request from './request';

export const http = {
    get(url, params, headers) {
        const config = {
            method: 'GET',
            url: url,
            params: params ? params : {},
            headers: headers ? headers : {}
        }
        return request(config);	
    },
    
    post(url, data) {
        const config = {
            method: 'POST',
            url: url,
            data: data ? data : {}
        }
        return request(config);	
    }
}
 
