/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 15:05:58
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-15 03:22:18
 */
export interface Bike {
  bikeid: number;
  brand: string;
  status: string;
  releasedate: string;
  warrantyPeriod: number;
  lastusetime: string;
  locationX: number; 
  locationY: number;

  }

export type bikeData = Bike;