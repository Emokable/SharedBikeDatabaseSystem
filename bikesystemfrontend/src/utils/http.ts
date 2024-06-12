/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 14:35:25
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-12 22:11:22
 */
import request from './request';

export const http = {
    get(url: string, token?: string) {
        const config = {
            method: 'GET',
            url: url,
            if(token) {
                Headers['x-authorization'] = token
            }
        }
        return request(config);	
    },
    
    post(url, data, token?: string) {
        const config = {
            method: 'POST',
            url: url,
            data: data ? data : {},
            if(token) {
                Headers['x-authorization'] = token
            }
        }
        return request(config);	
    }
}
 
