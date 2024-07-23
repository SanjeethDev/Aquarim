package com.sanjeethdev.aquarim;

import java.util.Date;

public class LiquidRecordModel
{
    // All the data required for a record.
    // Record == Water intake information.
    private Date datetime;
    private int quantity;
    private String liquid;

    // Constructor
    public LiquidRecordModel(Date datetime, int quantity, String liquid)
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
    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLiquid() {
        return liquid;
    }

    public void setLiquid(String liquid) {
        this.liquid = liquid;
    }
}
