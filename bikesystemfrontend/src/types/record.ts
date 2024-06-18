/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 15:03:25
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 11:05:47
 */

export interface RideRecord {
    orderid: number;
    bikeid: number;
    userid: number;
    startTime: string; 
    startLocationX: number;
    startLocationY: number;
    endTime: string; 
    endLocationX: number;
    endLocationY: number;
    track: string; 
  }

export type recordData = RideRecord;