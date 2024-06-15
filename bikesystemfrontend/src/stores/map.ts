// src/store/index.ts
import { defineStore } from 'pinia';

// 定义地理位置信息的类型
interface GeoLocation {
  latitude: number;
  longitude: number;
}

interface Bike {
  id: string;
  location: GeoLocation;
}

interface NoParkingZone {
  id: string;
  boundary: GeoLocation[];
}

interface Hotspot {
  id: string;
  area: GeoLocation[];
  name: string;
}

// 定义 Store
export const useMapStore = defineStore('map', {
  state: () => ({
    availBikes: [] as Bike[],  // 空闲单车数据
    noParkingZones: [] as NoParkingZone[],  // 禁停区数据
    hotspots: [] as Hotspot[],  // 热点区域数据
    selectedBike: null as Bike | null,  // 当前选中的单车
    selectedZones: [] as NoParkingZone[],  // 当前选中的禁停区（多选）
  }),
  getters: {
    getFreeBikes(state) {
      return state.availBikes;
    },
    getNoParkingZones(state) {
      return state.noParkingZones;
    },
    getHotspots(state) {
      return state.hotspots;
    },
    getBikeById: (state) => (id: string) => {
      return state.availBikes.find(bike => bike.id === id);
    },
    isZoneSelected: (state) => (zoneId: string) => {
      return state.selectedZones.some(zone => zone.id === zoneId);
    },
  },
  actions: {
    addBike(bike: Bike) {
      this.availBikes.push(bike);
    },
    updateBike(id: string, updatedBike: Partial<Bike>) {
      const index = this.availBikes.findIndex(bike => bike.id === id);
      if (index !== -1) {
        this.availBikes[index] = { ...this.availBikes[index], ...updatedBike };
      }
    },
    removeBike(id: string) {
      this.availBikes = this.availBikes.filter(bike => bike.id !== id);
    },
    addNoParkingZone(zone: NoParkingZone) {
      this.noParkingZones.push(zone);
    },
    updateNoParkingZone(id: string, updatedZone: Partial<NoParkingZone>) {
      const index = this.noParkingZones.findIndex(zone => zone.id === id);
      if (index !== -1) {
        this.noParkingZones[index] = { ...this.noParkingZones[index], ...updatedZone };
      }
    },
    removeNoParkingZone(id: string) {
      this.noParkingZones = this.noParkingZones.filter(zone => zone.id !== id);
    },
    addHotspot(hotspot: Hotspot) {
      this.hotspots.push(hotspot);
    },
    updateHotspot(id: string, updatedHotspot: Partial<Hotspot>) {
      const index = this.hotspots.findIndex(hotspot => hotspot.id === id);
      if (index !== -1) {
        this.hotspots[index] = { ...this.hotspots[index], ...updatedHotspot };
      }
    },
    removeHotspot(id: string) {
      this.hotspots = this.hotspots.filter(hotspot => hotspot.id !== id);
    },
    selectBike(bike: Bike) {
      this.selectedBike = bike;
    },
    deselectBike() {
      this.selectedBike = null;
    },
    selectZone(zone: NoParkingZone) {
      if (!this.selectedZones.some(selectedZone => selectedZone.id === zone.id)) {
        this.selectedZones.push(zone);
      }
    },
    deselectZone(zoneId: string) {
      this.selectedZones = this.selectedZones.filter(zone => zone.id !== zoneId);
    },
    clearSelectedZones() {
      this.selectedZones = [];
    },
  },
});
