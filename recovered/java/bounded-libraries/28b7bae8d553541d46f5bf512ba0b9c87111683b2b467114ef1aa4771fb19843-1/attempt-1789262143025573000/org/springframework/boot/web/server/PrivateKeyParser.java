/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Base64Utils
 *  org.springframework.util.FileCopyUtils
 *  org.springframework.util.ResourceUtils
 */
package org.springframework.boot.web.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

final class PrivateKeyParser {
    private static final String PKCS1_HEADER = "-+BEGIN\\s+RSA\\s+PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+";
    private static final String PKCS1_FOOTER = "-+END\\s+RSA\\s+PRIVATE\\s+KEY[^-]*-+";
    private static final String PKCS8_FOOTER = "-+END\\s+PRIVATE\\s+KEY[^-]*-+";
    private static final String PKCS8_HEADER = "-+BEGIN\\s+PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+";
    private static final String BASE64_TEXT = "([a-z0-9+/=\\r\\n]+)";
    private static final Pattern PKCS1_PATTERN = Pattern.compile("-+BEGIN\\s+RSA\\s+PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+RSA\\s+PRIVATE\\s+KEY[^-]*-+", 2);
    private static final Pattern PKCS8_KEY_PATTERN = Pattern.compile("-+BEGIN\\s+PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+PRIVATE\\s+KEY[^-]*-+", 2);

    private PrivateKeyParser() {
    }

    static PrivateKey parse(String resource) {
        try {
            String text = PrivateKeyParser.readText(resource);
            Matcher matcher = PKCS1_PATTERN.matcher(text);
            if (matcher.find()) {
                return PrivateKeyParser.parsePkcs1(PrivateKeyParser.decodeBase64(matcher.group(1)));
            }
            matcher = PKCS8_KEY_PATTERN.matcher(text);
            if (matcher.find()) {
                return PrivateKeyParser.parsePkcs8(PrivateKeyParser.decodeBase64(matcher.group(1)));
            }
            throw new IllegalStateException("Unrecognized private key format in " + resource);
        }
        catch (IOException | GeneralSecurityException ex) {
            throw new IllegalStateException("Error loading private key file " + resource, ex);
        }
    }

    private static PrivateKey parsePkcs1(byte[] privateKeyBytes) throws GeneralSecurityException {
        byte[] pkcs8Bytes = PrivateKeyParser.convertPkcs1ToPkcs8(privateKeyBytes);
        return PrivateKeyParser.parsePkcs8(pkcs8Bytes);
    }

    private static byte[] convertPkcs1ToPkcs8(byte[] pkcs1) {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            int pkcs1Length = pkcs1.length;
            int totalLength = pkcs1Length + 22;
            result.write(PrivateKeyParser.bytes(48, 130));
            result.write(totalLength >> 8 & 0xFF);
            result.write(totalLength & 0xFF);
            result.write(PrivateKeyParser.bytes(2, 1, 0));
            result.write(PrivateKeyParser.bytes(48, 13, 6, 9, 42, 134, 72, 134, 247, 13, 1, 1, 1, 5, 0));
            result.write(PrivateKeyParser.bytes(4, 130));
            result.write(pkcs1Length >> 8 & 0xFF);
            result.write(pkcs1Length & 0xFF);
            result.write(pkcs1);
            return result.toByteArray();
        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static byte[] bytes(int ... elements) {
        byte[] result = new byte[elements.length];
        for (int i = 0; i < elements.length; ++i) {
            result[i] = (byte)elements[i];
        }
        return result;
    }

    private static PrivateKey parsePkcs8(byte[] privateKeyBytes) throws GeneralSecurityException {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        }
        catch (InvalidKeySpecException ex) {
            throw new IllegalArgumentException("Unexpected key format", ex);
        }
    }

    private static String readText(String resource) throws IOException {
        URL url = ResourceUtils.getURL((String)resource);
        try (InputStreamReader reader = new InputStreamReader(url.openStream());){
            String string = FileCopyUtils.copyToString((Reader)reader);
            return string;
        }
    }

    private static byte[] decodeBase64(String content) {
        byte[] contentBytes = content.replaceAll("\r", "").replaceAll("\n", "").getBytes();
        return Base64Utils.decode((byte[])contentBytes);
    }
}

