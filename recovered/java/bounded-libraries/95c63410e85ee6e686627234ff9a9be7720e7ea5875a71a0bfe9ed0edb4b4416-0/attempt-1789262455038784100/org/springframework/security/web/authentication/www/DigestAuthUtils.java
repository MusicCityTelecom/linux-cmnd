/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.crypto.codec.Hex
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.authentication.www;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

final class DigestAuthUtils {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private DigestAuthUtils() {
    }

    static String encodePasswordInA1Format(String username, String realm, String password) {
        String a1 = username + ":" + realm + ":" + password;
        return DigestAuthUtils.md5Hex(a1);
    }

    static String[] splitIgnoringQuotes(String str, char separatorChar) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return EMPTY_STRING_ARRAY;
        }
        ArrayList<String> list = new ArrayList<String>();
        int i = 0;
        int start = 0;
        boolean match = false;
        while (i < len) {
            if (str.charAt(i) == '\"') {
                ++i;
                while (i < len) {
                    if (str.charAt(i) == '\"') {
                        ++i;
                        break;
                    }
                    ++i;
                }
                match = true;
                continue;
            }
            if (str.charAt(i) == separatorChar) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
                continue;
            }
            match = true;
            ++i;
        }
        if (match) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[0]);
    }

    static String generateDigest(boolean passwordAlreadyEncoded, String username, String realm, String password, String httpMethod, String uri, String qop, String nonce, String nc, String cnonce) throws IllegalArgumentException {
        String a2 = httpMethod + ":" + uri;
        String a1Md5 = !passwordAlreadyEncoded ? DigestAuthUtils.encodePasswordInA1Format(username, realm, password) : password;
        String a2Md5 = DigestAuthUtils.md5Hex(a2);
        if (qop == null) {
            return DigestAuthUtils.md5Hex(a1Md5 + ":" + nonce + ":" + a2Md5);
        }
        if ("auth".equals(qop)) {
            return DigestAuthUtils.md5Hex(a1Md5 + ":" + nonce + ":" + nc + ":" + cnonce + ":" + qop + ":" + a2Md5);
        }
        throw new IllegalArgumentException("This method does not support a qop: '" + qop + "'");
    }

    static Map<String, String> splitEachArrayElementAndCreateMap(String[] array, String delimiter, String removeCharacters) {
        if (array == null || array.length == 0) {
            return null;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        for (String s : array) {
            String postRemove = removeCharacters != null ? StringUtils.replace((String)s, (String)removeCharacters, (String)"") : s;
            String[] splitThisArrayElement = DigestAuthUtils.split(postRemove, delimiter);
            if (splitThisArrayElement == null) continue;
            map.put(splitThisArrayElement[0].trim(), splitThisArrayElement[1].trim());
        }
        return map;
    }

    static String[] split(String toSplit, String delimiter) {
        Assert.hasLength((String)toSplit, (String)"Cannot split a null or empty string");
        Assert.hasLength((String)delimiter, (String)"Cannot use a null or empty delimiter to split a string");
        Assert.isTrue((delimiter.length() == 1 ? 1 : 0) != 0, (String)"Delimiter can only be one character in length");
        int offset = toSplit.indexOf(delimiter);
        if (offset < 0) {
            return null;
        }
        String beforeDelimiter = toSplit.substring(0, offset);
        String afterDelimiter = toSplit.substring(offset + 1);
        return new String[]{beforeDelimiter, afterDelimiter};
    }

    static String md5Hex(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return new String(Hex.encode((byte[])digest.digest(data.getBytes())));
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
    }
}

