/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-12 14:35:25
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 00:58:10
 */
import { el } from 'element-plus/es/locales.mjs';
import request from './request';
import { Coordinate } from '@element-plus/icons-vue';

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

    getMaxID(url, token?: string) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'GET',
            url: url + '/maxid',
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

    getBikeInZone(url, token?: string, edges?: string) {
        const headers = {};
        let params = new URLSearchParams();
        if (edges) {
            params.append('coordinates', edges);
        }
        if (params.toString()) {
            url += '/locked-in-zone?' + params.toString();
        }
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'GET',
            url: url,
            headers: headers,
        }
        return request(config);
    },

    getRecordByTime(url, token?: string, startTime?: string, endTime?: string) {
        const headers = {};
        let params = new URLSearchParams();
        if (startTime) {
            params.append('startDate', startTime);
        }
        if (endTime) {
            params.append('endDate', endTime);
        }
        if (params.toString()) {
            url += '?' + params.toString();
        }
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'GET',
            url: url,
            headers: headers,
        }
        return request(config);
    },

    getBikeByLocation(url, token?: string, startX?: number, startY?: number, endX?: number, endY?: number) {
        const headers = {};
        let params = new URLSearchParams();
        if (startX){
            params.append('startX', startX.toString());
        }
        if (startY){
            params.append('startY', startY.toString());
        }
        if (endX){
            params.append('endX', endX.toString());
        }
        if (endY){
            params.append('endY', endY.toString());
        }
        if (params.toString()) {
            url += '?' + params.toString();
        }
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'GET',
            url: url,
            headers: headers,
        }
        return request(config);
    },



    post(url, data, token?: string) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        if (url == '/admins'){
            url = url + '/insert';
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
        // 设置要添加的url后缀，如果是修改数据，需要在url后面添加/update, 如果是NoParkingZone需要添加/update1
        const urlEnd = url.includes('noParkingZones') ? '/update1' : '/update';
        const config = {
            method: 'PUT',
            url: url + urlEnd,
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


    // insertData(url,token?: string, data?: any, moreUrl?: string) {
    //     const headers = {};
    //     if (token) {
    //         headers['X-Authorization-With'] = token;
    //     }   
    //     const moreUrlEnd = moreUrl ? moreUrl : '';
    //     const config = {
    //         method: 'PUT',
    //         url: url + moreUrlEnd,
    //         data: data,
    //         headers: headers
    //     }
    //     return request(config);
    // },

}