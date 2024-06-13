export interface TableConfigInterFace {
    api: string;
    belongsTo?: string;
    columns: {
        prop: string;
        label?: string;
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
