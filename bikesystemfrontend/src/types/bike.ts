export interface Bike {
    bikeid: number;
    brand: string;
    release_date: string; // or Date if you handle date objects
    warranty_period: number;
    status: boolean; // true if locked/damaged, false otherwise
  }

export type bikeData = Bike;