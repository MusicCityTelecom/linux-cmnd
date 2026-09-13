package com.tpvision.smartinstall.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
      return transferIndentifierTimeFormat(lastedit, getSimpleDateFormatWithEnglishLocale("dd-MMM-yyyy-'T'HHmmss"));
   }

   public static SimpleDateFormat getMessageTimeFormat() {
      return getSimpleDateFormatWithEnglishLocale("yyyy-MM-dd HH:mm:ss");
   }

   public static long dateDiff(Date date1, Date date2) {
      long diff = Math.abs(date2.getTime() - date1.getTime());
      return TimeUnit.DAYS.toDays(diff);
   }

   public static String getBillItemDate(Date date) {
      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
      return df.format(date);
   }

   public static String getJapitDate(String dateStr) {
      Date date = parseDateString(dateStr, "yyyy-MM-dd");
      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      return df.format(date);
   }

   public static Date parseMessageDate(String dateStr) {
      SimpleDateFormat df = getMessageTimeFormat();
      Date sendTime = new Date();

      try {
         sendTime = df.parse(dateStr);
      } catch (ParseException e) {
         df = getSimpleDateFormatWithEnglishLocale("yyyy-MM-dd HH:mm");

         try {
            sendTime = df.parse(dateStr);
         } catch (ParseException e1) {
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
      } catch (ParseException var4) {
      }

      return sendTime;
   }

   public static String getCurrentIndentifierFormatTime() {
      return getIdentifierFormatTime(new Date());
   }

   public static String getIdentifierFormatTime(Date date) {
      return formatLocalDate(date, "dd/MM/yyyy:HH:mm");
   }

   public static Date convertIndentiferTimsStringToDate(String identifierTimeStr) {
      SimpleDateFormat sdf = getSimpleDateFormatWithEnglishLocale("dd/MM/yyyy:HH:mm");

      try {
         return sdf.parse(identifierTimeStr);
      } catch (ParseException var3) {
         return new Date();
      }
   }

   public static String transferIndentifierTimeFormat(String identifierTime, SimpleDateFormat targetFormatPattern) {
      return transferDateFormat(identifierTime, getSimpleDateFormatWithEnglishLocale("dd/MM/yyyy:HH:mm"), targetFormatPattern);
   }

   public static Date getBeforeDate(Date date, int i) {
      Calendar cal = new GregorianCalendar();
      cal.setTime(date);
      cal.set(5, cal.get(5) - i);
      return cal.getTime();
   }

   public static Date getAfterDate(Date date, int i) {
      Calendar cal = new GregorianCalendar();
      cal.setTime(date);
      cal.set(5, cal.get(5) + i);
      return cal.getTime();
   }

   public static String getDateTime() {
      return formatLocalDate(new Date(), "yyyy/MM/dd-HH:mm:ss");
   }

   public static Date convertCreateUpdateStringToDate(String dateStr) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");

      try {
         return sdf.parse(dateStr);
      } catch (Exception var3) {
         return null;
      }
   }

   public static String getCloneDateTimeName() {
      return formatLocalDate(new Date(), "yyyyMMdd'T'HHmmss");
   }

   public static String transferDateFormat(String date, SimpleDateFormat srcFormat, SimpleDateFormat destFormat) {
      String d = "";
      if (null != date) {
         try {
            Date dt = srcFormat.parse(date);
            if (null != dt) {
               d = destFormat.format(dt);
            }
         } catch (ParseException var5) {
         }
      }

      if ("".equals(d)) {
         SimpleDateFormat format = getSimpleDateFormatWithEnglishLocale("yyyy-MM-dd HH:mm:ss");
         d = format.format(new Date());
      }

      return d;
   }

   public static String getTvNameFormateString() {
      return "TV " + formatLocalDate(new Date(), "dd MMM yyyy HH:mm:ss");
   }

   public static SimpleDateFormat getSimpleDateFormatWithEnglishLocale(String formatStr) {
      return new SimpleDateFormat(formatStr, Locale.ENGLISH);
   }

   public static String getNextDate(String currentTime) {
      Date date = parseMessageDate(currentTime);
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      calendar.add(5, 1);
      date = calendar.getTime();
      return formatLocalDate(date, "yyyy-MM-dd HH:mm:ss");
   }

   public static String formatSiCloneIdentifiers(String platformName, Date date) {
      platformName = PlatformUtils.getPlatformName(platformName);
      SimpleDateFormat format = getSimpleDateFormatWithEnglishLocale("");
      if ("2K14/2K15-MS".equals(platformName)) {
         format = getSimpleDateFormatWithEnglishLocale("yyyy-MM-dd HH:mm");
      } else if (PlatformUtils.isContainsType(platformName)) {
         format = getSimpleDateFormatWithEnglishLocale("yyyy/MM/dd-HH:mm:ss");
      }

      return format.format(date);
   }

   public static String formatLocalDate(Date date, String pattern) {
      SimpleDateFormat format = getSimpleDateFormatWithEnglishLocale(pattern);
      return date == null ? null : format.format(date);
   }

   public static String formatStringDate(String dateStr, String pattern) {
      if ("".equalsIgnoreCase(dateStr)) {
         return "";
      }

      List<String> dateFormats = Arrays.asList(
         "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy HH:mm", "dd/MM/yyyy", "dd/MM/yyyy HH:mm", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyMMdd"
      );
      Date date = null;

      for (String format : dateFormats) {
         date = parseDateString(dateStr, format);
         if (date != null) {
            break;
         }
      }

      return formatLocalDate(date, pattern);
   }

   public static String formatXMLGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
      GregorianCalendar ngc = xmlGregorianCalendar.toGregorianCalendar();
      SimpleDateFormat format = getSimpleDateFormatWithEnglishLocale("yyyy-MM-dd HH:mm:ss");
      return format.format(ngc.getTime());
   }

   public static String getContentFormatedChangedTime(String changedTime) {
      SimpleDateFormat ftOri = getSimpleDateFormatWithEnglishLocale("yyyy-MM-dd'T'HH:mm:ssX");
      String date = changedTime;

      try {
         date = getIdentifierFormatTime(ftOri.parse(changedTime));
      } catch (ParseException e) {
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
      if (year1 == year2) {
         return day2 - day1;
      }

      int timeDistance = 0;

      for (int i = year1; i < year2; i++) {
         if ((i % 4 != 0 || i % 100 == 0) && i % 400 != 0) {
            timeDistance += 365;
         } else {
            timeDistance += 366;
         }
      }

      return timeDistance + (day2 - day1);
   }
}
