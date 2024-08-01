package com.sanjeethdev.aquarim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Test code to look at the data and views.
        ArrayList<LiquidRecordModel> data = new ArrayList<>();
        RecyclerView recycleEntryRecord = findViewById(R.id.main_record_view);
        recycleEntryRecord.setLayoutManager(new LinearLayoutManager(this));
        data.add(new LiquidRecordModel(new Date().getTime(), 200, "Water"));
        data.add(new LiquidRecordModel(new Date().getTime(), 400, "Water"));
        data.add(new LiquidRecordModel(new Date().getTime(), 600, "Orange Juice"));
        data.add(new LiquidRecordModel(new Date().getTime(), 800, "Water"));
        LiquidRecordAdapter liquidRecordAdapter = new LiquidRecordAdapter(data);
        recycleEntryRecord.setAdapter(liquidRecordAdapter);
    }
}