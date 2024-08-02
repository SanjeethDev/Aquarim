package com.sanjeethdev.aquarim;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LiquidRecordDbHelper extends SQLiteOpenHelper
{
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
                    + LiquidRecordContract.LiquidRecordContractEntry.TABLE_NAME
                    + " ("
                    + LiquidRecordContract.LiquidRecordContractEntry.COLUMN_ID + "INT PRIMARY KEY, "
                    + LiquidRecordContract.LiquidRecordContractEntry.COLUMN_NAME_DATE + " BIGINT, "
                    + LiquidRecordContract.LiquidRecordContractEntry.COLUMN_NAME_LIQUID + " TEXT, "
                    + LiquidRecordContract.LiquidRecordContractEntry.COLUMN_NAME_QUANTITY + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LiquidRecordContract.LiquidRecordContractEntry.TABLE_NAME;

    public LiquidRecordDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
