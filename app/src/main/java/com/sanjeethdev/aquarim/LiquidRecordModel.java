package com.sanjeethdev.aquarim;

import java.util.ArrayList;
import java.util.Date;

public class LiquidRecordModel
{
    // All the data required for a record.
    // Record == Water intake information.
    private long datetime;
    private double quantity;
    private String liquid;

    // Constructor
    public LiquidRecordModel(long datetime, double quantity, String liquid)
    {
        this.datetime = datetime;
        this.quantity = quantity;
        this.liquid = liquid;
    }

    // Empty Constructor
    public LiquidRecordModel() {}

    public String printRecord() {
        return liquid + " " + datetime + " " + quantity;
    }

    // Getters and Setters.
    // Also used for sql data insert.
    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public double getQuantity()
    {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getLiquid() {
        return liquid;
    }

    public void setLiquid(String liquid) {
        this.liquid = liquid;
    }
}
