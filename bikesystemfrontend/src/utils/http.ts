/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 14:35:25
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 00:51:58
 */
import { el } from 'element-plus/es/locales.mjs';
import request from './request';

export const http = {
    get(url, token?: string) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'GET',
            url: url,
            headers: headers
        };

        return request(config);
    },

    getCount(url, token?: string) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'GET',
            url: url + '/count',
            headers: headers
        };
        return request(config);
    },

    getList(url, token?: string, page?: number, size?: number, sortBy?: string, sortOrder?: string) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        let params = new URLSearchParams();

        if (page) {
            params.append('page', page.toString());
        }

        if (size) {
            params.append('size', size.toString());
        }

        if (sortBy) {
            console.log(sortBy)
            // Ensure sortBy is a string
            if (typeof sortBy === 'object') {
                sortBy = JSON.stringify(sortBy);
            }
            params.append('sortBy', sortBy);
        }

        if (sortOrder) {
            params.append('sortOrder', sortOrder);
        }

        if (params.toString()) {
            url += '?' + params.toString();
        }
        const config = {
            method: 'GET',
            url: url,
            headers: headers
        };
        console.log(config)
        return request(config);
    },

    post(url, data, token?: string) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'POST',
            url: url,
            data: data ? data : {},
            headers: headers
        }
        return request(config);
    },

    delete(url, token?: string, id?: number) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'DELETE',
            url: url + '/' + id?.toString(),
            headers: headers
        }
        return request(config);
    },

    search(url, token?: string, page?: number, size?: number, searchColumn?: string ,keyword?: string ) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        let params = new URLSearchParams();

        if (searchColumn) {
            params.append('searchColumn', searchColumn);
        }
        
        if (keyword) {
            params.append('keyword', keyword);
        }

        if (page) {
            params.append('page', page.toString());
        }

        if (size) {
            params.append('size', size.toString());
        }

        if (params.toString()) {
            url += '?' + params.toString();
        }
        const config = {
            method: 'GET',
            url: url,
            headers: headers
        }
        return request(config);
        
    }

}