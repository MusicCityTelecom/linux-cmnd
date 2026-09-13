/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.pkcs.RSAPrivateKey
 *  org.bouncycastle.asn1.pkcs.RSAPublicKey
 *  org.springframework.util.Base64Utils
 */
package org.springframework.security.rsa.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.springframework.util.Base64Utils;

class RsaKeyHelper {
    private static Charset UTF8 = Charset.forName("UTF-8");
    private static final String BEGIN = "-----BEGIN";
    private static final Pattern PEM_DATA = Pattern.compile("-----BEGIN (.*)-----(.*)-----END (.*)-----", 32);
    private static final byte[] PREFIX = new byte[]{0, 0, 0, 7, 115, 115, 104, 45, 114, 115, 97};
    private static final Pattern SSH_PUB_KEY = Pattern.compile("ssh-(rsa|dsa) ([A-Za-z0-9/+]+=*) (.*)");

    RsaKeyHelper() {
    }

    static KeyPair parseKeyPair(String pemData) {
        Matcher m = PEM_DATA.matcher(pemData.trim());
        if (!m.matches()) {
            try {
                return new KeyPair(RsaKeyHelper.extractPublicKey(pemData), null);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("String is not PEM encoded data, nor a public key encoded for ssh");
            }
        }
        String type = m.group(1);
        byte[] content = RsaKeyHelper.base64Decode(m.group(2));
        PrivateKey privateKey = null;
        try {
            PublicKey publicKey;
            KeyFactory fact = KeyFactory.getInstance("RSA");
            if (type.equals("RSA PRIVATE KEY")) {
                ASN1Sequence seq = ASN1Sequence.getInstance((Object)content);
                if (seq.size() != 9) {
                    throw new IllegalArgumentException("Invalid RSA Private Key ASN1 sequence.");
                }
                RSAPrivateKey key = RSAPrivateKey.getInstance((Object)seq);
                RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
                RSAPrivateCrtKeySpec privSpec = new RSAPrivateCrtKeySpec(key.getModulus(), key.getPublicExponent(), key.getPrivateExponent(), key.getPrime1(), key.getPrime2(), key.getExponent1(), key.getExponent2(), key.getCoefficient());
                publicKey = fact.generatePublic(pubSpec);
                privateKey = fact.generatePrivate(privSpec);
            } else if (type.equals("PUBLIC KEY")) {
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(content);
                publicKey = fact.generatePublic(keySpec);
            } else if (type.equals("RSA PUBLIC KEY")) {
                ASN1Sequence seq = ASN1Sequence.getInstance((Object)content);
                RSAPublicKey key = RSAPublicKey.getInstance((Object)seq);
                RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
                publicKey = fact.generatePublic(pubSpec);
            } else {
                throw new IllegalArgumentException(type + " is not a supported format");
            }
            return new KeyPair(publicKey, privateKey);
        }
        catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static byte[] base64Decode(String string) {
        try {
            ByteBuffer bytes = UTF8.newEncoder().encode(CharBuffer.wrap(string));
            byte[] bytesCopy = new byte[bytes.limit()];
            System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());
            return Base64Utils.decode((byte[])bytesCopy);
        }
        catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }

    static String base64Encode(byte[] bytes) {
        try {
            return UTF8.newDecoder().decode(ByteBuffer.wrap(Base64Utils.encode((byte[])bytes))).toString();
        }
        catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }

    static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            return keyGen.generateKeyPair();
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static java.security.interfaces.RSAPublicKey extractPublicKey(String key) {
        Matcher m = SSH_PUB_KEY.matcher(key);
        if (m.matches()) {
            String alg = m.group(1);
            String encKey = m.group(2);
            if (!"rsa".equalsIgnoreCase(alg)) {
                throw new IllegalArgumentException("Only RSA is currently supported, but algorithm was " + alg);
            }
            return RsaKeyHelper.parseSSHPublicKey(encKey);
        }
        if (!key.startsWith(BEGIN)) {
            return RsaKeyHelper.parseSSHPublicKey(key);
        }
        return null;
    }

    static java.security.interfaces.RSAPublicKey parsePublicKey(String key) {
        java.security.interfaces.RSAPublicKey publicKey = RsaKeyHelper.extractPublicKey(key);
        if (publicKey != null) {
            return publicKey;
        }
        KeyPair kp = RsaKeyHelper.parseKeyPair(key);
        if (kp.getPublic() == null) {
            throw new IllegalArgumentException("Key data does not contain a public key");
        }
        return (java.security.interfaces.RSAPublicKey)kp.getPublic();
    }

    static String encodePublicKey(java.security.interfaces.RSAPublicKey key, String id) {
        StringWriter output = new StringWriter();
        output.append("ssh-rsa ");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            stream.write(PREFIX);
            RsaKeyHelper.writeBigInteger(stream, key.getPublicExponent());
            RsaKeyHelper.writeBigInteger(stream, key.getModulus());
        }
        catch (IOException e) {
            throw new IllegalStateException("Cannot encode key", e);
        }
        output.append(RsaKeyHelper.base64Encode(stream.toByteArray()));
        output.append(" " + id);
        return output.toString();
    }

    private static java.security.interfaces.RSAPublicKey parseSSHPublicKey(String encKey) {
        ByteArrayInputStream in = new ByteArrayInputStream(RsaKeyHelper.base64Decode(encKey));
        byte[] prefix = new byte[11];
        try {
            if (in.read(prefix) != 11 || !Arrays.equals(PREFIX, prefix)) {
                throw new IllegalArgumentException("SSH key prefix not found");
            }
            BigInteger e = new BigInteger(RsaKeyHelper.readBigInteger(in));
            BigInteger n = new BigInteger(RsaKeyHelper.readBigInteger(in));
            return RsaKeyHelper.createPublicKey(n, e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static java.security.interfaces.RSAPublicKey createPublicKey(BigInteger n, BigInteger e) {
        try {
            return (java.security.interfaces.RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(n, e));
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void writeBigInteger(ByteArrayOutputStream stream, BigInteger num) throws IOException {
        int length = num.toByteArray().length;
        byte[] data = new byte[]{(byte)(length >> 24 & 0xFF), (byte)(length >> 16 & 0xFF), (byte)(length >> 8 & 0xFF), (byte)(length & 0xFF)};
        stream.write(data);
        stream.write(num.toByteArray());
    }

    private static byte[] readBigInteger(ByteArrayInputStream in) throws IOException {
        byte[] b = new byte[4];
        if (in.read(b) != 4) {
            throw new IOException("Expected length data as 4 bytes");
        }
        int l = (b[0] & 0xFF) << 24 | (b[1] & 0xFF) << 16 | (b[2] & 0xFF) << 8 | b[3] & 0xFF;
        if (in.read(b = new byte[l]) != l) {
            throw new IOException("Expected " + l + " key bytes");
        }
        return b;
    }
}

