package com.tpvision.smartinstall.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

public class TpvStringUtils {
   private static final Logger LOG = LoggerFactory.getLogger(TpvStringUtils.class);
   private static final List<String> VALID_SORT_DIRECTION = Arrays.asList("desc", "asc");

   private TpvStringUtils() {
   }

   public static String removeNL(String s) {
      return null != s ? s.replaceAll("[\\t\\n\\r]", " ") : s;
   }

   public static String getTvRoomId(String tvRoomId) {
      String _tvRoomId = tvRoomId;
      if (null != tvRoomId) {
         int tvRoomIdLen = tvRoomId.length();
         if (tvRoomIdLen < 5) {
            switch (tvRoomIdLen) {
               case 0:
                  _tvRoomId = "00000";
                  break;
               case 1:
                  _tvRoomId = "0000" + tvRoomId;
                  break;
               case 2:
                  _tvRoomId = "000" + tvRoomId;
                  break;
               case 3:
                  _tvRoomId = "00" + tvRoomId;
                  break;
               case 4:
                  _tvRoomId = "0" + tvRoomId;
                  break;
               default:
                  _tvRoomId = "00000";
            }
         }
      }

      return _tvRoomId;
   }

   public static Set<String> getValidTvRoomIdSet(String tvRoomId) {
      Set<String> roomIdList = new HashSet<>();
      roomIdList.add(tvRoomId);
      if (StringUtils.isNumeric(tvRoomId)) {
         roomIdList.add(String.valueOf(Integer.parseInt(tvRoomId)));
         roomIdList.add(getTvRoomId(tvRoomId));
      }

      return roomIdList;
   }

   public static String fixPlayoutRoomNos(String oldRoomNos) {
      List<String> splitArrays = new ArrayList<>();
      StringBuilder roomNo = new StringBuilder();
      int i = 0;

      for (int j = oldRoomNos.length(); i < j; i++) {
         String charValue = oldRoomNos.substring(i, i + 1);
         if (!StringUtils.isNumeric(charValue)) {
            if (roomNo.length() > 0) {
               splitArrays.add(roomNo.toString());
               roomNo = new StringBuilder();
            }

            splitArrays.add(charValue);
         } else {
            roomNo.append(charValue);
         }
      }

      if (roomNo.length() > 0) {
         splitArrays.add(roomNo.toString());
      }

      i = 0;

      for (int j = splitArrays.size(); i < j; i++) {
         String splitValue = splitArrays.get(i);
         if (StringUtils.isNumeric(splitValue)) {
            String fixRoomId = getTvRoomId(splitValue);
            splitArrays.set(i, fixRoomId);
         }
      }

      return String.join("", splitArrays);
   }

   public static String getPureTvRoomId(String tvRoomId) {
      int roomId = Integer.parseInt(tvRoomId);
      return String.valueOf(roomId);
   }

   public static String getTVMAVAddress(String tvMAC) {
      String tvMACAddress = "1A:2B:3C:4D:5E:6F";
      StringBuilder tvMACABuff = new StringBuilder(tvMAC);
      if (tvMACABuff.length() == 12) {
         tvMACABuff.insert(2, ":");
         tvMACABuff.insert(5, ":");
         tvMACABuff.insert(8, ":");
         tvMACABuff.insert(11, ":");
         tvMACABuff.insert(14, ":");
         tvMACAddress = tvMACABuff.toString().toUpperCase();
      }

      return tvMACAddress;
   }

   public static boolean isValidMysqlFieldName(String testFieldName) {
      return !testFieldName.contains(",") && !testFieldName.contains("'") && !testFieldName.contains("\"") && !testFieldName.contains(";");
   }

   public static int tryParseInt(String value, int defaultValue) {
      int retVal;
      try {
         retVal = Integer.parseInt(value);
      } catch (NumberFormatException nfe) {
         retVal = defaultValue;
      }

      return retVal;
   }

   public static double tryParseDouble(String value, double defaultValue) {
      double retVal;
      try {
         retVal = Double.parseDouble(value);
      } catch (NumberFormatException nfe) {
         retVal = defaultValue;
      }

      return retVal;
   }

   public static String string2UnicodeForASTA(String src) {
      return string2Unicode(src, 2);
   }

   public static String string2Unicode(String string) {
      return string2Unicode(string, 4);
   }

   public static String unicode2ASTA(String unicode) {
      return unicode.replace("\\u00", "\\u");
   }

   private static String string2Unicode(String string, int unicodeLength) {
      if (null == string) {
         return "";
      }

      StringBuilder unicode = new StringBuilder();

      for (int i = 0; i < string.length(); i++) {
         char c = string.charAt(i);
         String s = Integer.toHexString(c);
         if (s.length() < unicodeLength) {
            StringBuilder sb = new StringBuilder(s);

            for (int j = 0; j < unicodeLength - s.length(); j++) {
               sb.insert(0, "0");
            }

            s = sb.toString();
         }

         unicode.append("\\u" + s);
      }

      return unicode.toString();
   }

   public static String unicode2String(String unicode) {
      StringBuilder string = new StringBuilder();
      String[] hex = unicode.split("\\\\u");

      for (int i = 1; i < hex.length; i++) {
         int data = Integer.parseInt(hex[i], 16);
         string.append((char)data);
      }

      return string.toString();
   }

   public static String getSortDirectionValueFromRequest(HttpServletRequest request, String paramName) {
      String value = request.getParameter(paramName);
      return !StringUtils.isBlank(value) && VALID_SORT_DIRECTION.contains(value.toLowerCase()) ? value : null;
   }

   public static int countStr(String src, String inc) {
      if (!StringUtils.isEmpty(src) && !StringUtils.isEmpty(inc)) {
         int counter = 0;
         if (src.indexOf(inc) == -1) {
            return 0;
         }

         while (src.indexOf(inc) != -1) {
            counter++;
            src = src.substring(src.indexOf(inc) + inc.length());
         }

         return counter;
      } else {
         return 0;
      }
   }

   public static List<Integer> splitStringToIntList(String targetStr, String divideStr, int defaultValue) {
      List<Integer> resultList = new ArrayList<>();
      if (StringUtils.isNotBlank(targetStr)) {
         String[] splitStringArray = targetStr.split(divideStr);

         for (int i = 0; i < splitStringArray.length; i++) {
            resultList.add(tryParseInt(splitStringArray[i], defaultValue));
         }
      }

      return resultList;
   }

   public static int[] splitStringToIntArray(String targetStr, String divideStr, int defaultValue) {
      if (StringUtils.isBlank(targetStr)) {
         return new int[0];
      }

      String[] splitStringArray = targetStr.split(divideStr);
      int[] resultArray = new int[splitStringArray.length];

      for (int i = 0; i < splitStringArray.length; i++) {
         resultArray[i] = tryParseInt(splitStringArray[i], defaultValue);
      }

      return resultArray;
   }

   public static String limitStringLength(String str, int maxStringLen) {
      if (null != str) {
         return str.length() > maxStringLen ? str.substring(0, maxStringLen) : str;
      } else {
         return str;
      }
   }

   public static JSONObject getJSONObjectFromString(String data) {
      if (data != null && data.contains("{") && data.contains("}")) {
         JSONObject jsonObject = null;

         try {
            if (data.contains("jsonData=")) {
               jsonObject = new JSONObject(data.substring(data.indexOf(123), data.lastIndexOf(125) + 1));
            } else {
               jsonObject = new JSONObject(data);
            }

            return jsonObject;
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
         }
      } else {
         return null;
      }
   }

   public static boolean isJSONString(String str) {
      try {
         new JSONObject(str);
         return true;
      } catch (Exception ex) {
         return false;
      }
   }

   public static boolean isValidJson(String json) {
      try {
         new JSONObject(json);
         return true;
      } catch (Exception ex1) {
         try {
            new JSONArray(json);
            return true;
         } catch (Exception ex2) {
            return false;
         }
      }
   }

   public static String getRandomString(int length) {
      String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < length; i++) {
         int number = RandomUtils.nextInt(1, 62);
         sb.append(str.charAt(number));
      }

      return sb.toString();
   }

   public static String format(String msg, Object... objs) {
      return MessageFormatter.arrayFormat(msg, objs).getMessage();
   }

   public static String removeHeadZeorForIntegerString(String number) {
      return StringUtils.isNumeric(number) ? String.valueOf(Integer.parseInt(number)) : number;
   }

   public static String removeSpaceFromTvPlatform(String platform) {
      return StringUtils.isBlank(platform) ? platform : platform.replace(" ", "");
   }
}
