/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 00:08:04
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 14:16:09
 */
import { ref } from 'vue'
import { defineStore } from 'pinia'
import router from '../router'
import { log } from 'console'
import { http } from '../utils/http'
import { noParkingZoneData } from '../types/noParkingZone'
import { bikeData } from '../types/bike'

interface Location {
    lat: number;
    lng: number;
}

interface ZoneRecord {
    zoneid: number;
    edges: Location[];
}

interface BikesInZone {
    zoneid: number;
    bikes: bikeData[];
}

function convertArrayToString(locationArray) {
    // 将每个坐标对转换为字符串
    let pointsArray = locationArray.map(point => {
      return point.join(',');
    });
    // 使用 '#' 连接所有的坐标字符串
    let resultString = pointsArray.join('#');
    return resultString;
  }

function convertStringToArray(locationString) {
    // 使用 '#' 分割字符串
    let pointsArray = locationString.split('#');
    // 将每个坐标字符串转换为坐标对
    let locationArray = pointsArray.map(point => {
      return point.split(',').map(Number);
    });
    return locationArray;
  }
  

export const useNoParkingZoneStore = defineStore('noParkingZone', {
    state: () => ({
        visiableNoParkingZones: [] as noParkingZoneData[],
        recordSides: [] as ZoneRecord[],
        editingNoParkingZone: -1,
        editingNoParkingZoneName: '',
        editingNoParkingZoneLocation: [] as Location[],
        visibleBikes: [] as BikesInZone[],
        newNoparkingZoneName: '',
        visibleNew: -1,
        removeOld: -1,
        deleteId: -1,
    }),
    getters: {
        getEditingNoParkingZone(state) {
            return state.editingNoParkingZone;
        },

        getIsvisible(state) {
            //return (recordId: number) => state.selectedRecords.findIndex((r) => r.orderid === recordId) !== -1;
            return (zoneid: number) => state.visiableNoParkingZones.findIndex((zone) => zone.zoneid === zoneid) !== -1;
        }

    },
    actions: {
        setEditingNoParkingZone(id: number) {
            this.editingNoParkingZone = id;
        },

        getZoneSidesByID(recordId: number): ZoneRecord | undefined {
            return this.recordSides.find((r) => r.zoneid === recordId);
        },

        async getEditingZoneDataByID(token: string) {
            // 根据 zoneid 从服务器获取禁停区数据
            console.log("待编辑ID"+this.editingNoParkingZone);
            await http.getByID('/noParkingZones', token, this.editingNoParkingZone).then((res) => {
                this.editingNoParkingZoneName = res.data.data.name;
                console.log("待编辑数据名"+ this.editingNoParkingZoneName);
                console.log("待编辑数据坐标"+ res.data.data.location);
                this.editingNoParkingZoneLocation = convertStringToArray(res.data.data.location);
            }
            );
        },

        async upDateNoParkingZone(path: Array<Array<number>>, token: string) {
            let pathString = convertArrayToString(path);
            let data = {
                zoneid: this.editingNoParkingZone,
                name: this.editingNoParkingZoneName,
                location: pathString,
            };
            await http.editData('/noParkingZones', token, data);
            // 清空编辑状态
            this.editingNoParkingZone = -1;
            this.editingNoParkingZoneName = '';
            this.editingNoParkingZoneLocation = [];
        },

        async createNoParkingZone(path: Array<Array<number>>, token: string) {
            let pathString = convertArrayToString(path);
            const zoneID = ref(0);
            const res = await http.getMaxID('/noParkingZones', token)
            zoneID.value = res.data.data
            console.log(zoneID);
            let data = {
                zoneid: zoneID.value,
                name: this.newNoparkingZoneName,
                location: pathString,
            };
            let response = await http.post('/noParkingZones', data, token);
        },

        addVisibleNoParkingZone(token: string, zoneid: number) {
            const index = this.visiableNoParkingZones.findIndex((zone) => zone.zoneid === zoneid);
            if (index === -1) {
                http.getByID('/noParkingZones', token, zoneid).then((res) => {
                    this.visiableNoParkingZones.push(res.data.data);
                    let locationArray = convertStringToArray(res.data.data.location);
                    http.getBikeInZone('/bikes', token, res.data.data.location).then((res2) => {
                        this.visibleBikes.push({ zoneid: zoneid, bikes: res2.data.data});
                    });
                    this.recordSides.push({ zoneid: zoneid, edges: locationArray });
                    this.visibleNew = zoneid;
                });
            }
        },

        removeVisibleNoParkingZone(zoneid: number) {
            const index = this.visiableNoParkingZones.findIndex((zone) => zone.zoneid === zoneid);
            if (index !== -1) {
                this.visiableNoParkingZones.splice(index, 1);
                this.recordSides.splice(index, 1);
                this.removeOld = zoneid;
                // 根据 zoneid 删除 visibleBikes 中的数据
                const bikeIndex = this.visibleBikes.findIndex((b) => b.zoneid === zoneid);
                if (bikeIndex !== -1) {
                    this.visibleBikes.splice(bikeIndex, 1);
                }
            }
        },

        setNewVisible(zoneid: number) {
            this.visibleNew = zoneid;
        },

        setRemoveOld(zoneid: number) {
            this.removeOld = zoneid;
        },

        setDeleteId(id: number) {
            this.deleteId = id;
        },
    },
});