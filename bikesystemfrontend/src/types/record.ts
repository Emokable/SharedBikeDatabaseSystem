/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 15:03:25
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-17 21:35:20
 */

export interface RideRecord {
    orderid: number;
    bikeid: number;
    userid: number;
    starttime: string; 
    startlocationx: number;
    startlocationy: number;
    endtime: string; 
    endlocationx: number;
    endlocationy: number;
    track: string; 
  }

export type recordData = RideRecord;