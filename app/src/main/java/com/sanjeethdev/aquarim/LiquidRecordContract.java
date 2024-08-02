package com.sanjeethdev.aquarim;

import android.provider.BaseColumns;

public final class LiquidRecordContract
{
    // Prevent accidental instantiation.
    private LiquidRecordContract() {}

    // All the constants required for the database.
    public static class LiquidRecordContractEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "LiquidIntake";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_NAME_LIQUID = "liquid";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_DATE = "datetime";
    }

}
