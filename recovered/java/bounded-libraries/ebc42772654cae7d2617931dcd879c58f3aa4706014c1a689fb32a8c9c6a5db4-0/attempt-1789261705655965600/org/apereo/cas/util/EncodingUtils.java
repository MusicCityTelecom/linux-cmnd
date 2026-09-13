/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.codec.binary.Base32
 *  org.apache.commons.codec.binary.Base64
 *  org.apache.commons.codec.binary.Hex
 *  org.apache.commons.lang3.StringUtils
 *  org.jooq.lambda.Unchecked
 *  org.jose4j.json.JsonUtil
 *  org.jose4j.jwe.JsonWebEncryption
 *  org.jose4j.jwk.JsonWebKey
 *  org.jose4j.jwk.JsonWebKey$Factory
 *  org.jose4j.jwk.JsonWebKey$OutputControlLevel
 *  org.jose4j.jwk.OctJwkGenerator
 *  org.jose4j.jwk.OctetSequenceJsonWebKey
 *  org.jose4j.jwk.RsaJwkGenerator
 *  org.jose4j.jws.JsonWebSignature
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util;

import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.RandomUtils;
import org.apereo.cas.util.crypto.DecryptionException;
import org.apereo.cas.util.jwt.JsonWebTokenEncryptor;
import org.apereo.cas.util.jwt.JsonWebTokenSigner;
import org.jooq.lambda.Unchecked;
import org.jose4j.json.JsonUtil;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.OctJwkGenerator;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.JsonWebSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EncodingUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(EncodingUtils.class);
    public static final String JSON_WEB_KEY = "k";
    private static final Base32 BASE32_CHUNKED_ENCODER = new Base32(76, new byte[]{10});
    private static final Base32 BASE32_UNCHUNKED_ENCODER = new Base32(0, new byte[]{10});
    private static final Base64 BASE64_CHUNKED_ENCODER = new Base64(76, new byte[]{10});
    private static final Base64 BASE64_UNCHUNKED_ENCODER = new Base64(0, new byte[]{10});

    public static String hexDecode(String data) {
        if (StringUtils.isNotBlank((CharSequence)data)) {
            return EncodingUtils.hexDecode(data.toCharArray());
        }
        return null;
    }

    public static String hexDecode(char[] data) {
        try {
            byte[] result = Hex.decodeHex((char[])data);
            return new String(result, StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String hexEncode(String data) {
        try {
            char[] result = Hex.encodeHex((byte[])data.getBytes(StandardCharsets.UTF_8));
            return new String(result);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String hexEncode(byte[] data) {
        try {
            char[] result = Hex.encodeHex((byte[])data);
            return new String(result);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String encodeUrlSafeBase64(byte[] data) {
        return Base64.encodeBase64URLSafeString((byte[])data);
    }

    public static byte[] decodeUrlSafeBase64(String data) {
        return EncodingUtils.decodeBase64(data);
    }

    public static String encodeBase64(byte[] data) {
        return Base64.encodeBase64String((byte[])data);
    }

    public static String encodeBase64(byte[] data, boolean chunked) {
        if (data != null && data.length > 0) {
            if (chunked) {
                return BASE64_CHUNKED_ENCODER.encodeToString(data).trim();
            }
            return BASE64_UNCHUNKED_ENCODER.encodeToString(data).trim();
        }
        return "";
    }

    public static String encodeBase64(String data) {
        return Base64.encodeBase64String((byte[])data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decodeBase64(String data) {
        return Base64.decodeBase64((String)data);
    }

    public static byte[] decodeBase64(byte[] data) {
        return Base64.decodeBase64((byte[])data);
    }

    public static String decodeBase64ToString(String data) {
        return new String(EncodingUtils.decodeBase64(data), StandardCharsets.UTF_8);
    }

    public static byte[] encodeBase64ToByteArray(byte[] data) {
        return Base64.encodeBase64((byte[])data);
    }

    public static String encodeBase32(byte[] data, boolean chunked) {
        if (chunked) {
            return BASE32_CHUNKED_ENCODER.encodeToString(data).trim();
        }
        return BASE32_UNCHUNKED_ENCODER.encodeToString(data).trim();
    }

    public static String urlEncode(String value) {
        return EncodingUtils.urlEncode(value, StandardCharsets.UTF_8.name());
    }

    public static String urlEncode(String value, String encoding) {
        return (String)Unchecked.supplier(() -> URLEncoder.encode(value, encoding)).get();
    }

    public static String urlDecode(String value) {
        return StringUtils.isBlank((CharSequence)value) ? value : (String)Unchecked.supplier(() -> URLDecoder.decode(value, StandardCharsets.UTF_8.name())).get();
    }

    public static boolean isBase64(String value) {
        return Base64.isBase64((String)value);
    }

    public static byte[] verifyJwsSignature(Key signingKey, String asString) {
        return (byte[])Unchecked.supplier(() -> {
            JsonWebSignature jws = new JsonWebSignature();
            jws.setCompactSerialization(asString);
            jws.setKey(signingKey);
            boolean verified = jws.verifySignature();
            if (verified) {
                String payload = jws.getEncodedPayload();
                LOGGER.trace("Successfully decoded value. Result in Base64url-encoding is [{}]", (Object)payload);
                return EncodingUtils.decodeUrlSafeBase64(payload);
            }
            return null;
        }).get();
    }

    public static byte[] verifyJwsSignature(Key signingKey, byte[] value) {
        String asString = new String(value, StandardCharsets.UTF_8);
        return EncodingUtils.verifyJwsSignature(signingKey, asString);
    }

    public static boolean isJsonWebKey(String key) {
        try {
            Map<String, Object> results = EncodingUtils.parseJsonWebKey(key);
            return !results.isEmpty();
        }
        catch (Exception e) {
            LOGGER.trace(e.getMessage());
            return false;
        }
    }

    public static JsonWebKey newJsonWebKey(String json) {
        return (JsonWebKey)Unchecked.supplier(() -> JsonWebKey.Factory.newJwk((String)json)).get();
    }

    public static JsonWebKey newJsonWebKey(int size) {
        return (JsonWebKey)Unchecked.supplier(() -> RsaJwkGenerator.generateJwk((int)size, null, (SecureRandom)RandomUtils.getNativeInstance())).get();
    }

    public static String generateJsonWebKey(int size) {
        OctetSequenceJsonWebKey octetKey = OctJwkGenerator.generateJwk((int)size);
        Map params = octetKey.toParams(JsonWebKey.OutputControlLevel.INCLUDE_SYMMETRIC);
        return params.get(JSON_WEB_KEY).toString();
    }

    public static Key generateJsonWebKey(String secret) {
        HashMap<String, Object> keys = new HashMap<String, Object>(2);
        keys.put("kty", "oct");
        keys.put(JSON_WEB_KEY, secret);
        return EncodingUtils.generateJsonWebKey(keys);
    }

    public static Key generateJsonWebKey(Map<String, Object> params) {
        return (Key)Unchecked.supplier(() -> {
            JsonWebKey jwk = JsonWebKey.Factory.newJwk((Map)params);
            return jwk.getKey();
        }).get();
    }

    public static byte[] signJwsHMACSha512(Key key, byte[] value, Map<String, Object> headers) {
        return ((JsonWebTokenSigner)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)JsonWebTokenSigner.builder().key(key)).headers(headers)).algorithm("HS512")).build()).sign(value);
    }

    public static byte[] signJwsHMACSha256(Key key, byte[] value, Map<String, Object> headers) {
        return ((JsonWebTokenSigner)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)JsonWebTokenSigner.builder().key(key)).headers(headers)).algorithm("HS256")).build()).sign(value);
    }

    public static byte[] signJwsRSASha512(Key key, byte[] value, Map<String, Object> headers) {
        return ((JsonWebTokenSigner)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)((JsonWebTokenSigner.JsonWebTokenSignerBuilder)JsonWebTokenSigner.builder().key(key)).headers(headers)).algorithm("RS512")).build()).sign(value);
    }

    public static String encryptValueAsJwtDirectAes128Sha256(Key key, Serializable value) {
        return ((JsonWebTokenEncryptor)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)JsonWebTokenEncryptor.builder().key(key)).algorithm("dir")).encryptionMethod("A128CBC-HS256")).build()).encrypt(value);
    }

    public static String encryptValueAsJwtRsaOeap256Aes256Sha512(Key key, Serializable value) {
        return ((JsonWebTokenEncryptor)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)((JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder)JsonWebTokenEncryptor.builder().key(key)).algorithm("RSA-OAEP-256")).encryptionMethod("A128CBC-HS256")).build()).encrypt(value);
    }

    public static String decryptJwtValue(Key secretKeyEncryptionKey, String value) {
        try {
            JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setKey(secretKeyEncryptionKey);
            jwe.setCompactSerialization(value);
            LOGGER.trace("Decrypting value...");
            return jwe.getPayload();
        }
        catch (Exception e) {
            if (LOGGER.isTraceEnabled()) {
                throw new DecryptionException(e);
            }
            throw new DecryptionException();
        }
    }

    public static Map<String, Object> parseJsonWebKey(String key) throws Exception {
        return JsonUtil.parseJson((String)key);
    }

    @Generated
    private EncodingUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

