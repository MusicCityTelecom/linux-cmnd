/*
 * Decompiled with CFR 0.152.
 */
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
        if (null != s) {
            return s.replaceAll("[\\t\\n\\r]", " ");
        }
        return s;
    }

    public static String getTvRoomId(String tvRoomId) {
        int tvRoomIdLen;
        String _tvRoomId = tvRoomId;
        if (null != tvRoomId && (tvRoomIdLen = tvRoomId.length()) < 5) {
            switch (tvRoomIdLen) {
                case 0: {
                    _tvRoomId = "00000";
                    break;
                }
                case 1: {
                    _tvRoomId = "0000" + tvRoomId;
                    break;
                }
                case 2: {
                    _tvRoomId = "000" + tvRoomId;
                    break;
                }
                case 3: {
                    _tvRoomId = "00" + tvRoomId;
                    break;
                }
                case 4: {
                    _tvRoomId = "0" + tvRoomId;
                    break;
                }
                default: {
                    _tvRoomId = "00000";
                }
            }
        }
        return _tvRoomId;
    }

    public static Set<String> getValidTvRoomIdSet(String tvRoomId) {
        HashSet<String> roomIdList = new HashSet<String>();
        roomIdList.add(tvRoomId);
        if (StringUtils.isNumeric(tvRoomId)) {
            roomIdList.add(String.valueOf(Integer.parseInt(tvRoomId)));
            roomIdList.add(TpvStringUtils.getTvRoomId(tvRoomId));
        }
        return roomIdList;
    }

    public static String fixPlayoutRoomNos(String oldRoomNos) {
        int i;
        ArrayList<String> splitArrays = new ArrayList<String>();
        StringBuilder roomNo = new StringBuilder();
        int j = oldRoomNos.length();
        for (i = 0; i < j; ++i) {
            String charValue = oldRoomNos.substring(i, i + 1);
            if (!StringUtils.isNumeric(charValue)) {
                if (roomNo.length() > 0) {
                    splitArrays.add(roomNo.toString());
                    roomNo = new StringBuilder();
                }
                splitArrays.add(charValue);
                continue;
            }
            roomNo.append(charValue);
        }
        if (roomNo.length() > 0) {
            splitArrays.add(roomNo.toString());
        }
        j = splitArrays.size();
        for (i = 0; i < j; ++i) {
            String splitValue = (String)splitArrays.get(i);
            if (!StringUtils.isNumeric(splitValue)) continue;
            String fixRoomId = TpvStringUtils.getTvRoomId(splitValue);
            splitArrays.set(i, fixRoomId);
        }
        return String.join((CharSequence)"", splitArrays);
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
        }
        catch (NumberFormatException nfe) {
            retVal = defaultValue;
        }
        return retVal;
    }

    public static double tryParseDouble(String value, double defaultValue) {
        double retVal;
        try {
            retVal = Double.parseDouble(value);
        }
        catch (NumberFormatException nfe) {
            retVal = defaultValue;
        }
        return retVal;
    }

    public static String string2UnicodeForASTA(String src) {
        return TpvStringUtils.string2Unicode(src, 2);
    }

    public static String string2Unicode(String string) {
        return TpvStringUtils.string2Unicode(string, 4);
    }

    public static String unicode2ASTA(String unicode) {
        return unicode.replace("\\u00", "\\u");
    }

    private static String string2Unicode(String string, int unicodeLength) {
        if (null == string) {
            return "";
        }
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            String s = Integer.toHexString(c);
            if (s.length() < unicodeLength) {
                StringBuilder sb = new StringBuilder(s);
                for (int j = 0; j < unicodeLength - s.length(); ++j) {
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
        for (int i = 1; i < hex.length; ++i) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char)data);
        }
        return string.toString();
    }

    public static String getSortDirectionValueFromRequest(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (StringUtils.isBlank(value) || !VALID_SORT_DIRECTION.contains(value.toLowerCase())) {
            return null;
        }
        return value;
    }

    public static int countStr(String src, String inc) {
        if (StringUtils.isEmpty(src) || StringUtils.isEmpty(inc)) {
            return 0;
        }
        int counter = 0;
        if (src.indexOf(inc) == -1) {
            return 0;
        }
        while (src.indexOf(inc) != -1) {
            ++counter;
            src = src.substring(src.indexOf(inc) + inc.length());
        }
        return counter;
    }

    public static List<Integer> splitStringToIntList(String targetStr, String divideStr, int defaultValue) {
        ArrayList<Integer> resultList = new ArrayList<Integer>();
        if (StringUtils.isNotBlank(targetStr)) {
            String[] splitStringArray = targetStr.split(divideStr);
            for (int i = 0; i < splitStringArray.length; ++i) {
                resultList.add(TpvStringUtils.tryParseInt(splitStringArray[i], defaultValue));
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
        for (int i = 0; i < splitStringArray.length; ++i) {
            resultArray[i] = TpvStringUtils.tryParseInt(splitStringArray[i], defaultValue);
        }
        return resultArray;
    }

    public static String limitStringLength(String str, int maxStringLen) {
        if (null != str) {
            if (str.length() > maxStringLen) {
                return str.substring(0, maxStringLen);
            }
            return str;
        }
        return str;
    }

    public static JSONObject getJSONObjectFromString(String data) {
        if (data == null || !data.contains("{") || !data.contains("}")) {
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = data.contains("jsonData=") ? new JSONObject(data.substring(data.indexOf(123), data.lastIndexOf(125) + 1)) : new JSONObject(data);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
        return jsonObject;
    }

    public static boolean isJSONString(String str) {
        try {
            new JSONObject(str);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean isValidJson(String json) {
        try {
            new JSONObject(json);
            return true;
        }
        catch (Exception ex1) {
            try {
                new JSONArray(json);
                return true;
            }
            catch (Exception ex2) {
                return false;
            }
        }
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            int number = RandomUtils.nextInt(1, 62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String format(String msg, Object ... objs) {
        return MessageFormatter.arrayFormat(msg, objs).getMessage();
    }

    public static String removeHeadZeorForIntegerString(String number) {
        return StringUtils.isNumeric(number) ? String.valueOf(Integer.parseInt(number)) : number;
    }

    public static String removeSpaceFromTvPlatform(String platform) {
        return StringUtils.isBlank(platform) ? platform : platform.replace(" ", "");
    }
}

