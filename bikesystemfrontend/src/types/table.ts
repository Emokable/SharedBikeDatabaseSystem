/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 10:08:50
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-18 11:39:09
 */
export interface TableConfigInterFace {
    api: string;
    canEdit?: boolean;
    canDelete?: boolean;
    useMap?:boolean;
    columns: {
        prop: string;
        label?: string;
        isEnum?: boolean;
        canSort?: boolean;
        enumOptions?: string[];
        isTime?: boolean;
        formatter?: (row: unknown) => string;
        tooltip?: string;
        width?: string;
        sortable?: boolean;
        style?: string;
        labelStyle?: string;
    }[];
    layout?: string;
}

export type TableConfig = TableConfigInterFace;
