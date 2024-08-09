package com.sanjeethdev.aquarim;

import static com.sanjeethdev.aquarim.LiquidRecordContract.Entry.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LiquidRecordDbHelper extends SQLiteOpenHelper
{
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
                    + TABLE_NAME
                    + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME_DATE + " BIGINT, "
                    + COLUMN_NAME_LIQUID + " TEXT, "
                    + COLUMN_NAME_QUANTITY + " DOUBLE )";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public LiquidRecordDbHelper(Context context)
    {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertRecords(long datetime, String liquid_name, double quantity)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_LIQUID, liquid_name);
        contentValues.put(COLUMN_NAME_QUANTITY, quantity);
        contentValues.put(COLUMN_NAME_DATE, datetime);

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public Cursor readRecords() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM "
                        + TABLE_NAME
                        + " ORDER BY "
                        + COLUMN_NAME_DATE
                        + " DESC", null);
    }

    public boolean deleteRecord(long datetime)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,
                  COLUMN_NAME_DATE + "=?",
                new String[]{String.valueOf(datetime)}) != -1;
    }
}
