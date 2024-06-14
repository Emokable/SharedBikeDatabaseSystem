/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-14 15:15:12
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 15:17:04
 */

import { adminData } from './admin'
import { riderData } from './rider'
import { bikeData } from './bike'
import { noParkingZoneData } from './noParkingZone'
import { recordData } from './record'

// 定义类型映射
export interface FormMap {
    "/admin": adminData;
    "/rider": riderData;
      "/bike": bikeData;
      "/noParkingZone": noParkingZoneData;
      "/record": recordData;
  }
// 动态表单类型
export type FormType<T extends keyof FormMap> = FormMap[T];