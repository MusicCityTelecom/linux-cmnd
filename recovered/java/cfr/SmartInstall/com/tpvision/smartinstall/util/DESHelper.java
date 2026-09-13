/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DESHelper {
    private static final Logger LOG = LoggerFactory.getLogger(DESHelper.class);
    private static final String ALGORITHM = "DES";

    private DESHelper() {
    }

    public static String encode(String data, String password) {
        try {
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKey securekey = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(1, (Key)securekey, new SecureRandom());
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    public static String decode(String data, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] encodeByte = Base64.getDecoder().decode(data);
            cipher.init(2, (Key)securekey, random);
            byte[] decoder = cipher.doFinal(encodeByte);
            return new String(decoder, StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}

