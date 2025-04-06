package com.sanjeethdev.aquarim.Models;

import java.util.Calendar;

public class EpochTime
{
    // {HOUR, MIN, SECS, MILLISECS}
    // Defaults
    private final int[] dayStartingTime = {0,0,0,0};
    private final int[] dayEndingTime = {23,59,59,999};

    // Empty Constructor
    public EpochTime() {}

    public long todayStartInMillis()
    {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY,dayStartingTime[0]);
        todayStart.set(Calendar.MINUTE, dayStartingTime[1]);
        todayStart.set(Calendar.SECOND, dayStartingTime[2]);
        todayStart.set(Calendar.MILLISECOND, dayStartingTime[3]);
        return todayStart.getTimeInMillis();
    }

    public long todayEndInMillis()
    {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY,dayEndingTime[0]);
        todayEnd.set(Calendar.MINUTE, dayEndingTime[1]);
        todayEnd.set(Calendar.SECOND, dayEndingTime[2]);
        todayEnd.set(Calendar.MILLISECOND, dayEndingTime[3]);
        return todayEnd.getTimeInMillis();
    }

}
