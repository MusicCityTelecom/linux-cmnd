/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.util.PlatformUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TpvDateUtils {
    private static final Logger LOG = LoggerFactory.getLogger(TpvDateUtils.class);
    public static final String IDENTIFIER_TIME_FORMAT = "dd/MM/yyyy:HH:mm";
    public static final String CMND_PAGE_SHOW_TIMEFORMAT = "dd-MMM-yyyy-'T'HHmmss";
    public static final String DB_CREATE_UPDATE_DATE_FORMAT = "yyyy/MM/dd-HH:mm:ss";
    public static final String MESSAGE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String BILLITEM_DATE_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String MYCHOIE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String PMS_CHECK_IN_OUT_AND_API_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String JAPIT_DATE_FORMATE = "dd/MM/yyyy";
    public static final String JAPIT_TIME_FORMATE = "HH:mm";
    public static final String ROOM_NOTIFICATION_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String CAST_ANALYTICAL_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private TpvDateUtils() {
    }

    public static String convertLastEditFormat(String lastedit) {
        return TpvDateUtils.transferIndentifierTimeFormat(lastedit, TpvDateUtils.getSimpleDateFormatWithEnglishLocale(CMND_PAGE_SHOW_TIMEFORMAT));
    }

    public static SimpleDateFormat getMessageTimeFormat() {
        return TpvDateUtils.getSimpleDateFormatWithEnglishLocale(MESSAGE_TIME_FORMAT);
    }

    public static long dateDiff(Date date1, Date date2) {
        long diff = Math.abs(date2.getTime() - date1.getTime());
        return TimeUnit.DAYS.toDays(diff);
    }

    public static String getBillItemDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(BILLITEM_DATE_FORMAT);
        return df.format(date);
    }

    public static String getJapitDate(String dateStr) {
        Date date = TpvDateUtils.parseDateString(dateStr, "yyyy-MM-dd");
        SimpleDateFormat df = new SimpleDateFormat(JAPIT_DATE_FORMATE);
        return df.format(date);
    }

    public static Date parseMessageDate(String dateStr) {
        SimpleDateFormat df = TpvDateUtils.getMessageTimeFormat();
        Date sendTime = new Date();
        try {
            sendTime = df.parse(dateStr);
        }
        catch (ParseException e) {
            df = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("yyyy-MM-dd HH:mm");
            try {
                sendTime = df.parse(dateStr);
            }
            catch (ParseException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
        return sendTime;
    }

    public static Date parseDateString(String dateStr, String format) {
        Date sendTime = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            sendTime = df.parse(dateStr);
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        return sendTime;
    }

    public static String getCurrentIndentifierFormatTime() {
        return TpvDateUtils.getIdentifierFormatTime(new Date());
    }

    public static String getIdentifierFormatTime(Date date) {
        return TpvDateUtils.formatLocalDate(date, IDENTIFIER_TIME_FORMAT);
    }

    public static Date convertIndentiferTimsStringToDate(String identifierTimeStr) {
        SimpleDateFormat sdf = TpvDateUtils.getSimpleDateFormatWithEnglishLocale(IDENTIFIER_TIME_FORMAT);
        try {
            return sdf.parse(identifierTimeStr);
        }
        catch (ParseException parseException) {
            return new Date();
        }
    }

    public static String transferIndentifierTimeFormat(String identifierTime, SimpleDateFormat targetFormatPattern) {
        return TpvDateUtils.transferDateFormat(identifierTime, TpvDateUtils.getSimpleDateFormatWithEnglishLocale(IDENTIFIER_TIME_FORMAT), targetFormatPattern);
    }

    public static Date getBeforeDate(Date date, int i) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(5, cal.get(5) - i);
        return cal.getTime();
    }

    public static Date getAfterDate(Date date, int i) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(5, cal.get(5) + i);
        return cal.getTime();
    }

    public static String getDateTime() {
        return TpvDateUtils.formatLocalDate(new Date(), DB_CREATE_UPDATE_DATE_FORMAT);
    }

    public static Date convertCreateUpdateStringToDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DB_CREATE_UPDATE_DATE_FORMAT);
        try {
            return sdf.parse(dateStr);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static String getCloneDateTimeName() {
        return TpvDateUtils.formatLocalDate(new Date(), "yyyyMMdd'T'HHmmss");
    }

    public static String transferDateFormat(String date, SimpleDateFormat srcFormat, SimpleDateFormat destFormat) {
        String d = "";
        if (null != date) {
            try {
                Date dt = srcFormat.parse(date);
                if (null != dt) {
                    d = destFormat.format(dt);
                }
            }
            catch (ParseException dt) {
                // empty catch block
            }
        }
        if ("".equals(d)) {
            SimpleDateFormat format = TpvDateUtils.getSimpleDateFormatWithEnglishLocale(MESSAGE_TIME_FORMAT);
            d = format.format(new Date());
        }
        return d;
    }

    public static String getTvNameFormateString() {
        return "TV " + TpvDateUtils.formatLocalDate(new Date(), "dd MMM yyyy HH:mm:ss");
    }

    public static SimpleDateFormat getSimpleDateFormatWithEnglishLocale(String formatStr) {
        return new SimpleDateFormat(formatStr, Locale.ENGLISH);
    }

    public static String getNextDate(String currentTime) {
        Date date = TpvDateUtils.parseMessageDate(currentTime);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        ((Calendar)calendar).add(5, 1);
        date = calendar.getTime();
        return TpvDateUtils.formatLocalDate(date, MESSAGE_TIME_FORMAT);
    }

    public static String formatSiCloneIdentifiers(String platformName, Date date) {
        platformName = PlatformUtils.getPlatformName(platformName);
        SimpleDateFormat format = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("");
        if ("2K14/2K15-MS".equals(platformName)) {
            format = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("yyyy-MM-dd HH:mm");
        } else if (PlatformUtils.isContainsType(platformName)) {
            format = TpvDateUtils.getSimpleDateFormatWithEnglishLocale(DB_CREATE_UPDATE_DATE_FORMAT);
        }
        return format.format(date);
    }

    public static String formatLocalDate(Date date, String pattern) {
        SimpleDateFormat format = TpvDateUtils.getSimpleDateFormatWithEnglishLocale(pattern);
        if (date == null) {
            return null;
        }
        return format.format(date);
    }

    public static String formatStringDate(String dateStr, String pattern) {
        String format;
        if ("".equalsIgnoreCase(dateStr)) {
            return "";
        }
        List<String> dateFormats = Arrays.asList(MYCHOIE_TIME_FORMAT, BILLITEM_DATE_FORMAT, JAPIT_DATE_FORMATE, BILLITEM_DATE_FORMAT, "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyMMdd");
        Date date = null;
        Iterator<String> iterator = dateFormats.iterator();
        while (iterator.hasNext() && (date = TpvDateUtils.parseDateString(dateStr, format = iterator.next())) == null) {
        }
        return TpvDateUtils.formatLocalDate(date, pattern);
    }

    public static String formatXMLGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        GregorianCalendar ngc = xmlGregorianCalendar.toGregorianCalendar();
        SimpleDateFormat format = TpvDateUtils.getSimpleDateFormatWithEnglishLocale(MESSAGE_TIME_FORMAT);
        return format.format(ngc.getTime());
    }

    public static String getContentFormatedChangedTime(String changedTime) {
        SimpleDateFormat ftOri = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("yyyy-MM-dd'T'HH:mm:ssX");
        String date = changedTime;
        try {
            date = TpvDateUtils.getIdentifierFormatTime(ftOri.parse(changedTime));
        }
        catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        return date;
    }

    public static int differentDays(Date smallDate, Date bigDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(smallDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(bigDate);
        int day1 = cal1.get(6);
        int day2 = cal2.get(6);
        int year1 = cal1.get(1);
        int year2 = cal2.get(1);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; ++i) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                    continue;
                }
                timeDistance += 365;
            }
            return timeDistance + (day2 - day1);
        }
        return day2 - day1;
    }
}

