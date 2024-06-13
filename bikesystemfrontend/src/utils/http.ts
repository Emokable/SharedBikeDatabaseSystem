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

    delete(url, token?: string) {
        const headers = {};
        if (token) {
            headers['X-Authorization-With'] = token;
        }
        const config = {
            method: 'DELETE',
            url: url,
            headers: headers
        }
        return request(config);
    }
}