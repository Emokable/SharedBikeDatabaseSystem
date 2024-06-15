/*
 * @Description: 
 * @Version: 
 * @Author: DZQ
 * @Date: 2024-06-13 10:08:50
 * @LastEditors: DZQ
 * @LastEditTime: 2024-06-14 18:01:50
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
