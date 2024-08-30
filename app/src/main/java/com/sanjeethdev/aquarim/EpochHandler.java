package com.sanjeethdev.aquarim;

import java.util.Calendar;

public class EpochHandler
{
    // {HOUR, MIN, SECS, MILLISECS}
    // Defaults
    private int[] dayStartingTime = {0,0,0,0};
    private int[] dayEndingTime = {23,59,59,999};

    // Empty Constructor
    public EpochHandler() {}

    public long todayStart()
    {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY,dayStartingTime[0]);
        todayStart.set(Calendar.MINUTE, dayStartingTime[1]);
        todayStart.set(Calendar.SECOND, dayStartingTime[2]);
        todayStart.set(Calendar.MILLISECOND, dayStartingTime[3]);
        return todayStart.getTimeInMillis();
    }

    public long todayEnd()
    {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY,dayEndingTime[0]);
        todayEnd.set(Calendar.MINUTE, dayEndingTime[1]);
        todayEnd.set(Calendar.SECOND, dayEndingTime[2]);
        todayEnd.set(Calendar.MILLISECOND, dayEndingTime[3]);
        return todayEnd.getTimeInMillis();
    }

}