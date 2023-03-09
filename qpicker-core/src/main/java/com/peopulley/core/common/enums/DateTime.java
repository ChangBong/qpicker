package com.peopulley.core.common.enums;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class DateTime {

    public static String korParseDate(LocalDate localDate) {
        String date = localDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        return date;
    }

    public static String dotParseDate(LocalDate localDate) {
        String date = localDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return date;
    }

    public static String parseDate(LocalDate localDate, String pattern) {
        String date = localDate.format(DateTimeFormatter.ofPattern(pattern));
        return date;
    }

    public static String parseDateTime(LocalDateTime localDateTime, String pattern) {
        String date = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        return date;
    }

    public static LocalDate parseLocalDateTimeToLocalDate(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    public static LocalDate parseStringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

    public static String korDateOfWeekShort(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String dispDayOfWeek = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
        return dispDayOfWeek;
    }

    public static Long timeStampDiff(LocalTime time) {
        LocalTime thisTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
/*
        if(thisTime.isAfter(time)){
            localDate = localDate.plusDays(1);
        }*/

        LocalDateTime getLocalDateTime = LocalDateTime.of(localDate, time);
        Long timeDiffSecond = Math.abs(Duration.between(LocalDateTime.now(), getLocalDateTime).getSeconds());
//        Long timeDiffSecond = Duration.between(LocalDateTime.now(), getLocalDateTime).getSeconds();
        return timeDiffSecond;
    }

    public static int dateDiff(LocalDate date) {
        Period period = Period.between(date, LocalDate.now());
        return period.getDays();
    }

    public static String parseTimeHourMin(LocalTime localTime) {
        String time = localTime.format(DateTimeFormatter.ofPattern("hh:mm"));
        return time;
    }

    public static boolean validateDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 특정 unix timestamp 를 String 날짜로 변환(YYYYMMDD)
     */
    public static String getUnixTimeToString(String unixTS) {
        String convDate = "";
        long tmpDateTime = new BigDecimal(unixTS).longValue();

        if (String.valueOf(tmpDateTime).length() == 13) {
            tmpDateTime = tmpDateTime / 1000;
        } else {
            //예외처리
            tmpDateTime = 0;
        }

        if (tmpDateTime != 0) {
            Date date = new Date(tmpDateTime * 1000L);
            convDate = new SimpleDateFormat("yyyyMMdd").format(date);
        }

        return convDate;
    }

    /**
     * 특정 unix timestamp 를 String 날짜로 변환(YYYYMMDDHHmmss)
     */
    public static String getUnixTimeToStringDatetime(String unixTS) {
        String convDate = "";
        long tmpDateTime = new BigDecimal(unixTS).longValue();

        //예외처리
        if (String.valueOf(tmpDateTime).length() != 13) {
            tmpDateTime = 0;
        }

        if (tmpDateTime != 0) {
            Date date = new Date(tmpDateTime);
            convDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
        }

        return convDate;
    }
}
