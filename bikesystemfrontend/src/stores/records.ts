/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-15 03:18:58
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-16 16:40:42
 */
import { ref } from 'vue'
import { defineStore } from 'pinia'
import { recordData } from '../types/record'
import { http } from '../utils/http'
import { TableConfig } from '../types/table'




export const useRecordStore = defineStore('record', () => {
    // 初始化存储被选中记录的变量
    const selectedRecords = ref<recordData[]>([]);
    const selectNew = ref(-1);
    const removeOld = ref(-1);

    // 添加记录到selectedRecords
    const addRecord = (token: string, recordId: number) => {
        const index = selectedRecords.value.findIndex((r) => r.orderid === recordId);
        if (index === -1) { // 确保不重复添加
            http.getByID('/rideRecords', token, recordId).then((res) => {
                selectedRecords.value.push(res.data.data);
                console.log(selectedRecords.value);
            });
            selectNew.value = recordId;
        }
    };

    // 根据记录ID撤销选择
    const removeRecord = (recordId: number) => {
        const index = selectedRecords.value.findIndex((r) => r.orderid === recordId);
        if (index !== -1) {
            selectedRecords.value.splice(index, 1);
            removeOld.value = recordId;
        }
    };

    const isSelected = (recordId: number): boolean => {
        return selectedRecords.value.findIndex((r) => r.orderid === recordId) !== -1;
    };
    // 返回公开的方法和变量
    return { selectedRecords, addRecord, removeRecord, isSelected }
});