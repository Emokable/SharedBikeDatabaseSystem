/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 14:35:25
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-16 15:36:49
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

    getByID(url, token?: string, id?: number) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'GET',
            url: url + '/' + id?.toString(),
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

    getSelectCounts(url, token?: string, searchColumn?: string, keyword?: string) {
        const headers = {};
        let params = new URLSearchParams();
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        
        if (searchColumn) {
            params.append('searchBy', searchColumn);
        }
        
        if (keyword) {
            params.append('keyword', keyword);
        }
        url += '/counts';
        if (params.toString()) {
            url += '?' + params.toString();
        }
        const config = {
            method: 'GET',
            url: url ,
            headers: headers
        };
        return request(config);
    },


    getList(url, token?: string, page?: number, size?: number, sortBy?: string, sortOrder?: string, searchColumn?: string ,keyword?: string) {
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
            params.append('sortBy', sortBy);
        }

        if (sortOrder) {
            params.append('sortOrder', sortOrder);
        }

        if (searchColumn) {
            params.append('searchBy', searchColumn);
        }
        
        if (keyword) {
            params.append('keyword', keyword);
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

    editData(url,token?: string, data?: any) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }   
        const config = {
            method: 'PUT',
            url: url+ '/update',
            data: data,
            headers: headers
        }
        return request(config);
    },

    editUserData(url,token?: string, data?: any) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }   
        data.password = null;
        const config = {
            method: 'PUT',
            url: url+ '/update',
            data: data,
            headers: headers
        }
        return request(config);
    },


    insertData(url,token?: string, data?: any) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }   
        const config = {
            method: 'PUT',
            url: url,
            data: data,
            headers: headers
        }
        return request(config);
    },

}