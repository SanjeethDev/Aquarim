package com.sanjeethdev.aquarim;

import java.time.LocalDate;
import java.time.ZoneOffset;

public class EpochHandler
{
    // Constants
    private final long EPOCH_SEC = 1000;
    private final long EPOCH_MIN = 60000;
    private final long EPOCH_HOUR = 3600000;
    private final long EPOCH_DAY = 86400000;

    // Empty Constructor
    public EpochHandler() {}

    public long addDays(int numberOfDays)
    {
        return numberOfDays * EPOCH_DAY;
    }

    public long addHours(int numberOfHours)
    {
        return numberOfHours * EPOCH_HOUR;
    }

    public long addMinutes(int numberOfMinutes)
    {
        return numberOfMinutes * EPOCH_MIN;
    }

    public long addSeconds(int numberOfSeconds)
    {
        return numberOfSeconds * EPOCH_SEC;
    }

    public long getToday()
    {

    }

}
