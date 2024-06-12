/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 14:35:25
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-12 18:50:14
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
    
    post(url, data, headers) {
        const config = {
            method: 'POST',
            url: url,
            data: data ? data : {},
            headers: headers ? headers : {}
        }
        return request(config);	
    }
}
 
