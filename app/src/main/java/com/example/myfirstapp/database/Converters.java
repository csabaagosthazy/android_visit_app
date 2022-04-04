package com.example.myfirstapp.database;

import androidx.room.TypeConverter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
