package in.co.tripin.chahiyecustomer.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatHelper {

    private static final String PATTERN_FORMATTED = "dd MMM";
    private static final String PATTERN_FORMATTED1 = "dd MMM, yyyy 'at' HH:mm a";
    private static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static String getDisplayableDateFromISOString(String isoString) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_ISO);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        try {
            Date date = simpleDateFormat.parse(isoString);
            DateFormat df = new SimpleDateFormat(PATTERN_FORMATTED);
            String formattedDateString = df.format(date);
            return formattedDateString;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static String getDisplayableDate(String isoString) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_ISO);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        try {
            Date date = simpleDateFormat.parse(isoString);
            DateFormat df = new SimpleDateFormat(PATTERN_FORMATTED1);
            String formattedDateString = df.format(date);
            return formattedDateString;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static String getDisplayableDate(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_ISO);
        DateFormat df = new SimpleDateFormat(PATTERN_FORMATTED1);
        String formattedDateString = df.format(date);
        return formattedDateString;

    }

    public static String getDisplayableDateForHotelProfile(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_ISO);
        DateFormat df = new SimpleDateFormat(PATTERN_FORMATTED);
        String formattedDateString = df.format(date);
        return formattedDateString;

    }

    public static String getISOString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_ISO);
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        return simpleDateFormat.format(date);
    }

}