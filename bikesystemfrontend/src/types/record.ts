/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 15:03:25
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 15:06:56
 */

export interface RideRecord {
    orderid: number;
    bikeid: number;
    userid: number;
    start_time: string; 
    start_location_x: number;
    start_location_y: number;
    end_time: string; 
    end_location_x: number;
    end_location_y: number;
    track: string; 
  }

export type recordData = RideRecord;