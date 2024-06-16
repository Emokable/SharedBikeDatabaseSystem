/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-15 03:18:58
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-16 19:50:11
 */
import { ref } from 'vue'
import { defineStore } from 'pinia'
import { recordData } from '../types/record'
import { http } from '../utils/http'
import { TableConfig } from '../types/table'
import { get } from 'http'

interface Location {
    lat: number;
    lng: number;
}

interface RecordTrack {
    orderid: number;
    track: Location[];
}

interface RecordData {
    orderid: number;
    track: string; // Assuming track is a string that needs parsing
}

export const useRecordStore = defineStore('record', {
    state: () => ({
        selectedRecords: [] as RecordData[],
        recordTracks: [] as RecordTrack[],
        selectNew: -1,
        removeOld: -1,
    }),
    actions: {
        getRecordTrack(record: RecordData) {
            // 将轨迹字符串转换为 Location 对象数组
            let pointsArray = record.track.split('#');
            let coordinatesArray: Location[] = pointsArray.map(point => {
              let [longitude, latitude] = point.split(',').map(Number);
              return { lat: latitude, lng: longitude };
            });
          
            // 计算两点之间的 Haversine 距离
            function haversineDistance(point1: Location, point2: Location): number {
              const R = 6371; // 地球半径，单位为公里
              const dLat = (point2.lat - point1.lat) * Math.PI / 180;
              const dLng = (point2.lng - point1.lng) * Math.PI / 180;
              const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(point1.lat * Math.PI / 180) * Math.cos(point2.lat * Math.PI / 180) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
              const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
              return R * c; // 返回距离，单位为公里
            }
          
            // 找到距离最大的两点
            function findFurthestPoints(points: Location[]): [number, number] {
              let maxDistance = 0;
              let start = 0;
              let end = 0;
          
              for (let i = 0; i < points.length; i++) {
                for (let j = i + 1; j < points.length; j++) {
                  const distance = haversineDistance(points[i], points[j]);
                  if (distance > maxDistance) {
                    maxDistance = distance;
                    start = i;
                    end = j;
                  }
                }
              }
              return [start, end];
            }
          
            // 按照距离重新排序路径
            function sortPath(points: Location[], start: number): Location[] {
              const visited = new Set<number>(); // 初始化 Set
              const sortedPath: Location[] = [];
              let current = start;

              while (visited.size < points.length) {
                sortedPath.push(points[current]);
                visited.add(current);
                let nearest = -1;
                let minDistance = Infinity;
                for (let i = 0; i < points.length; i++) {
                  if (!visited.has(i)) {
                    const distance = haversineDistance(points[current], points[i]);
                    if (distance < minDistance) {
                      minDistance = distance;
                      nearest = i;
                    }
                  }
                }
                // 如果 nearest 还是 -1，说明所有点都访问过了
                if (nearest === -1) break;
                current = nearest;
              }
          
              return sortedPath;
            }
          
            // 找到距离最大的两点作为起点和终点
            let [start, end] = findFurthestPoints(coordinatesArray);
          
            // 使用找到的起点重新排序路径
            let sortedPath = sortPath(coordinatesArray, start);
          
            // 添加排序后的路径到记录
            this.recordTracks.push({ orderid: record.orderid, track: sortedPath });
          },
          

        getRecordTrackByID(recordId: number): RecordTrack | undefined {
            return this.recordTracks.find((r) => r.orderid === recordId);
        },

        addRecord(token: string, recordId: number) {
            const index = this.selectedRecords.findIndex((r) => r.orderid === recordId);
            if (index === -1) { // Ensure no duplicates
                http.getByID('/rideRecords', token, recordId).then((res) => {
                    this.selectedRecords.push(res.data.data);
                    this.getRecordTrack(res.data.data);
                    this.selectNew = recordId;
                    console.log(this.selectNew);
                });
            }
            console.log(this.recordTracks);
        },

        removeRecord(recordId: number) {
            const index = this.selectedRecords.findIndex((r) => r.orderid === recordId);
            if (index !== -1) {
                this.selectedRecords.splice(index, 1);
                this.recordTracks.splice(index, 1); // This might not correctly remove the corresponding track
                this.removeOld = recordId;
            }
            console.log(this.recordTracks);
        },

        setSelectNew(recordId: number) {
            this.selectNew = recordId;
        },

        setRemoveOld(recordId: number) {
            this.removeOld = recordId;
        },
    },
    getters: {
        isSelected: (state) => {
            return (recordId: number) => state.selectedRecords.findIndex((r) => r.orderid === recordId) !== -1;
        },
    },
});