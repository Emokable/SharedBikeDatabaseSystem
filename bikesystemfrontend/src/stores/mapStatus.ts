import { get } from 'http';
import { defineStore } from 'pinia';

export const useMapStatusStore = defineStore('mapStatus', {
    state: () => ({
        isMapLoading: false,  // 地图是否正在加载
        isMapLoaded: false,  // 地图是否已加载
        isMapError: false,  // 地图是否加载失败
        isMassMarksLoaded: false,  // 是否已加载大量标记
        isTrackLoaded: false,  // 是否已加载轨迹
        isForNoParkingZoneCreate: false,  // 是否用于创建禁停区
        isForNoParkingZoneEdit: false,  // 是否用于编辑禁停区
        isForNoParkingZoneDelete: false,  // 是否用于删除禁停区
        isGiveUpNoParkingZoneOperation: false,  // 是否放弃禁停区操作
        isNoParkingZoneOperationFinish: false,  // 是否正在进行禁停区操作
        isNoParkingZoneBikeVisible: false,  // 是否显示禁停区内的单车
    }),
    getters: {
        getMapLoadingStatus(state) {
            return state.isMapLoading;
        },
        getMapLoadedStatus(state) {
            return state.isMapLoaded;
        },
        getMapErrorStatus(state) {
            return state.isMapError;
        },
        getMassMarksLoadedStatus(state) {
            return state.isMassMarksLoaded;
        },
        getTrackLoadedStatus(state) {
            return state.isTrackLoaded;
        },
        getForNoParkingZoneCreateStatus(state) {
            return state.isForNoParkingZoneCreate;
        },
        getForNoParkingZoneEditStatus(state) {
            return state.isForNoParkingZoneEdit;
        },
        getForNoParkingZoneDeleteStatus(state) {
            return state.isForNoParkingZoneDelete;
        },
        getGiveUpNoParkingZoneOperationStatus(state) {
            return state.isGiveUpNoParkingZoneOperation;
        },
        getNoParkingZoneOperationFinishStatus(state) {
            return state.isNoParkingZoneOperationFinish;
        },
        getNoParkingZoneBikeVisibleStatus(state) {
            return state.isNoParkingZoneBikeVisible;
        },
    },
    actions: {
        setMapLoadingStatus(status: boolean) {
            this.isMapLoading = status;
        },
        setMapLoadedStatus(status: boolean) {
            this.isMapLoaded = status;
        },
        setMapErrorStatus(status: boolean) {
            this.isMapError = status;
        },
        setMassMarksLoadedStatus(status: boolean) {
            this.isMassMarksLoaded = status;
        },
        setTrackLoadedStatus(status: boolean) {
            this.isTrackLoaded = status;
        },
        setForNoParkingZoneCreateStatus(status: boolean) {
            this.isForNoParkingZoneCreate = status;
        },
        setForNoParkingZoneEditStatus(status: boolean) {
            this.isForNoParkingZoneEdit = status;
        },
        setForNoParkingZoneDeleteStatus(status: boolean) {
            this.isForNoParkingZoneDelete = status;
        },
        setGiveUpNoParkingZoneOperationStatus(status: boolean) {
            this.isGiveUpNoParkingZoneOperation = status;
        },
        setNoParkingZoneOperationFinishStatus(status: boolean) {
            this.isNoParkingZoneOperationFinish = status;
        },
        setNoParkingZoneBikeVisibleStatus(status: boolean) {
            this.isNoParkingZoneBikeVisible = status;
        },
    },
});

