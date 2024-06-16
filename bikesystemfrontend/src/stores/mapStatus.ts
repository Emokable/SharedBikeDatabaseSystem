import { defineStore } from 'pinia';

export const useMapStatusStore = defineStore('mapStatus', {
    state: () => ({
        isMapLoading: false,  // 地图是否正在加载
        isMapLoaded: false,  // 地图是否已加载
        isMapError: false,  // 地图是否加载失败
        isMassMarksLoaded: false,  // 是否已加载大量标记
        isTrackLoaded: false,  // 是否已加载轨迹
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
    },
});

