package com.urvesh.android_arch_mvp.domain.utils;


import com.urvesh.android_arch_mvp.tools.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateFormater {

    public static final String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    public static final String[] AM_PM = {"AM", "PM"};
    public static final String[] WEEKS = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
    public static final String[] WEEK_PREFIX = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};


    public static Calendar calendarInstance() {
        //return Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        return Calendar.getInstance(TimeZone.getDefault());
    }

    public static Calendar getTimeAtMidNight() {
        Calendar calendar = calendarInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static String currentTime() {
        Calendar calendar = DateFormater.calendarInstance();
        Date myDate = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return dateFormat.format(myDate);
    }

    /**
     * @param offset (in minutes)
     * @return
     */
    public static Calendar getCalendarOffset(int offset) {
        Calendar calendar = DateFormater.calendarInstance();
        calendar.add(Calendar.MINUTE, offset);
        return calendar;
    }

    public static String currentDateTimeInUTC() {
        Date myDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.ROOT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
        return dateFormat.format(myDate);
    }

    public static String nextHourTime(int offset) {
        Calendar calendar = DateFormater.calendarInstance();
        calendar.add(Calendar.HOUR_OF_DAY, offset);
        Date myDate = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return dateFormat.format(myDate);
    }

    public static boolean offTime(int offset) {
        Calendar calendar = DateFormater.calendarInstance();
        return (calendar.get(Calendar.HOUR_OF_DAY) + offset) >= 22 || (calendar.get(Calendar.HOUR_OF_DAY) + offset) < 10;
    }

    public static boolean isSameDay(int offset) {
        Calendar calendar = DateFormater.calendarInstance();
        calendar.add(Calendar.HOUR_OF_DAY, offset);
        return DateFormater.calendarInstance().get(Calendar.DATE) == calendar.get(Calendar.DATE);
    }

    public static String nextHourSlot(int offset) {
        Calendar calendar = DateFormater.calendarInstance();
        calendar.add(Calendar.HOUR_OF_DAY, offset);
        Date myDate = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return dateFormat.format(myDate);
    }

    public static int dayOffset(Calendar calendar) {
        Calendar today = DateFormater.calendarInstance();
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        long difference = (calendar.getTimeInMillis() - today.getTimeInMillis()) / 1000;
        return (int) ((difference + 1) / (24 * 60 * 60));
    }

    public static Calendar setToStartHour(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    private String getTimeStampInUnix(String timestamp) {

        String output = timestamp;

        if (timestamp == null) {
            Calendar calendar = DateFormater.calendarInstance();
            Date date = calendar.getTime();
            String currentTimestamp = new Timestamp(date.getTime()).toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            Date parsedDate = null;
            try {
                parsedDate = dateFormat.parse(currentTimestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            assert parsedDate != null;
            Timestamp givenTimestamp = new Timestamp(parsedDate.getTime());

            Calendar givenCalendar = DateFormater.calendarInstance();
            givenCalendar.setTimeInMillis(givenTimestamp.getTime());

            output = String.valueOf(givenCalendar.getTimeInMillis() / 1000L);
        }
        return output;
    }

    public String getTimeStampInUnixProper(String timestamp) {

        if (timestamp != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            Date parsedDate = null;
            try {
                parsedDate = dateFormat.parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (parsedDate == null) return null;
            Timestamp givenTimestamp = new Timestamp(parsedDate.getTime());

            Calendar givenCalendar = DateFormater.calendarInstance();
            givenCalendar.setTimeInMillis(givenTimestamp.getTime());

            return String.valueOf(givenCalendar.getTimeInMillis() / 1000L);
        } else {
            return getTimeStampInUnix(timestamp);
        }
    }

    public static Timestamp convertStringToTimestamp(String timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert parsedDate != null;
        return new Timestamp(parsedDate.getTime());
    }

    public static String dateFormatInMonth(long timestamp) {

        Timestamp localTime = new Timestamp(timestamp * 1000L);
        Date fromGmt = new Date(localTime.getTime());
        Timestamp givenTimestamp = new Timestamp(fromGmt.getTime());

        Calendar givenCalendar = calendarInstance();
        givenCalendar.setTimeInMillis(givenTimestamp.getTime());

        return String.valueOf(givenCalendar.get(Calendar.DAY_OF_MONTH)).concat(" ").concat(MONTHS[givenCalendar.get(Calendar.MONTH)]);
    }

    public static String getMonthYear(String timestamp) {
        Timestamp localTime = new Timestamp(Long.parseLong(timestamp));
        Date fromGmt = new Date(localTime.getTime());
        Timestamp givenTimestamp = new Timestamp(fromGmt.getTime());

        Calendar givenCalendar = DateFormater.calendarInstance();
        givenCalendar.setTimeInMillis(givenTimestamp.getTime());

        return MONTHS[givenCalendar.get(Calendar.MONTH)].concat(" ").concat(String.valueOf(givenCalendar.get(Calendar.YEAR)));
    }

    public static String getDateFromEpoch(long epoch) {
        Date date = new Date(epoch * 1000L);
        DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        return format.format(date);
    }

    public static String getDateTimeFromEpoch(long epoch) {
        Date passedDate = new Date(epoch * 1000L);
        Date todayDate = new Date(getCurrentTimeInEpoch() * 1000L);
        DateFormat format = new SimpleDateFormat("dd", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));

        if (format.format(passedDate).equals(format.format(todayDate))) {
            DateFormat timeFormate = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            timeFormate.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
            return timeFormate.format(passedDate);
        } else {
            format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            format.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
            return format.format(passedDate);
        }
    }

    public static Long getDateTimeFromDate(String passedValue) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.ENGLISH);
        try {
            Date date1 = myFormat.parse(passedValue);
            Date date2 = new Date();
            long diff = date2.getTime() - date1.getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTimeFromEpoch(long epoch) {
        Date date = new Date(epoch * 1000L);
        DateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        return format.format(date);
    }

    public static Long getCurrentTimeInEpoch() {
        return System.currentTimeMillis() / 1000L;
    }

    public static String getCurrentTime() {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("gmt"));
        return format.format(date);
    }

    public static String getCurrentTime(int timestamp) {
        Date date = new Date(timestamp * 1000);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("gmt"));
        return format.format(date);
    }

    public static String getPostTime(long epoch) {
        Date currentDate = new Date(System.currentTimeMillis());
        Date passedValueDate = new Date(epoch * 1000L);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));

        if (dateFormat.format(currentDate).equals(dateFormat.format(passedValueDate))) {

            DateFormat format = new SimpleDateFormat("HH.mm", Locale.ENGLISH);
            format.setTimeZone(TimeZone.getTimeZone("gmt"));
            double currentHour = Double.parseDouble(format.format(currentDate));
            double epochTime = Double.parseDouble(format.format(passedValueDate));

            int pastTime = (int) (currentHour - epochTime);
            if (pastTime <= 0) {
                return "1 hour ago";
            } else {
                return pastTime + " hours ago";
            }
        } else {
            Date date = new Date(epoch * 1000);
            DateFormat format = new SimpleDateFormat("dd-MMM HH:mm", Locale.ENGLISH);
            format.setTimeZone(TimeZone.getTimeZone("gmt"));
            return format.format(date);
        }
    }

    public static Long convertDateToEpoch(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.ENGLISH);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = d.getTime();
        return epoch;
    }

    public static String convertToServerFormat(String strDate, String fromFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.ENGLISH);
            return dateFormat2.format(sdf.parse(strDate));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertFromServerFormat(String strDate, String toFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
            SimpleDateFormat dateFormat2 = new SimpleDateFormat(toFormat.trim(), Locale.ENGLISH);
            return dateFormat2.format(sdf.parse(strDate));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertDateStringFormat(String strDate, String fromFormat, String toFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fromFormat, Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
            SimpleDateFormat dateFormat2 = new SimpleDateFormat(toFormat.trim(), Locale.US);
            return dateFormat2.format(sdf.parse(strDate));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertToDateRange(String week, String year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if(year != null) {
            calendar.set(Calendar.YEAR, Integer.parseInt(year));
        }
        //calendar.set(Calendar.YEAR, 2017);
        //calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date firstDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        //calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date lastDate = calendar.getTime();

        DateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
        String firstMonth = monthFormat.format(firstDate);
        String lastMonth = monthFormat.format(lastDate);
        if (firstMonth.equals(lastMonth)) {
            DateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
            String firstDay = dateFormat.format(firstDate);
            String lastDay = dateFormat.format(lastDate);
            return firstDay + "-" + lastDay + "\n" + lastMonth;
        } else {
            DateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
            String firstDay = dateFormat.format(firstDate);
            String lastDay = dateFormat.format(lastDate);
            return firstDay + "-\n" + lastDay;
        }
    }

    public static long getDateInMillis(String expiresAt) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.ENGLISH);
        desiredFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        long dateInMillis;
        try {
            Date date = desiredFormat.parse(expiresAt);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException e) {
            //Log.d("Exception while parsing date. " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public static long getUpComingDaysCount(String toDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
        try {
            Date date2 = myFormat.parse(convertFromServerFormat(toDate, "dd MM yyyy"));
            long diff = date2.getTime() - new Date().getTime();
            System.out.println("getUpComingDaysCount: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getPastDaysCount(String toDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy", Locale.US);
        try {
            Date date2 = myFormat.parse(convertFromServerFormat(toDate, "dd MM yyyy"));
            long diff = new Date().getTime() - date2.getTime();
            System.out.println("getPastDaysCount: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getMonthFromNumber(String month) {
        String[] str = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        if (month != null) {
            try {
                return str[Integer.parseInt(month)];
            } catch (Exception e) {
                Logger.exception("MonthFromNumber", e);
                return "";
            }
        }
        return "";
    }
}