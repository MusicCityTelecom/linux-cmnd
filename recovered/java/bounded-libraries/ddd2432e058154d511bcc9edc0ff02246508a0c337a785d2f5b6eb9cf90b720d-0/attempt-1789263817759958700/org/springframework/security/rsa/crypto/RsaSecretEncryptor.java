/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.crypto.codec.Hex
 *  org.springframework.security.crypto.encrypt.BytesEncryptor
 *  org.springframework.security.crypto.encrypt.Encryptors
 *  org.springframework.security.crypto.encrypt.TextEncryptor
 *  org.springframework.security.crypto.keygen.KeyGenerators
 *  org.springframework.util.Assert
 *  org.springframework.util.Base64Utils
 */
package org.springframework.security.rsa.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.rsa.crypto.RsaAlgorithm;
import org.springframework.security.rsa.crypto.RsaKeyHelper;
import org.springframework.security.rsa.crypto.RsaKeyHolder;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

public class RsaSecretEncryptor
implements BytesEncryptor,
TextEncryptor,
RsaKeyHolder {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String DEFAULT_SALT = "deadbeef";
    private String salt;
    private RsaAlgorithm algorithm = RsaAlgorithm.DEFAULT;
    private Charset charset;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Charset defaultCharset;
    private boolean gcm;

    public RsaSecretEncryptor(RsaAlgorithm algorithm, String salt, boolean gcm) {
        this(RsaKeyHelper.generateKeyPair(), algorithm, salt, gcm);
    }

    public RsaSecretEncryptor(RsaAlgorithm algorithm, String salt) {
        this(RsaKeyHelper.generateKeyPair(), algorithm, salt);
    }

    public RsaSecretEncryptor(RsaAlgorithm algorithm, boolean gcm) {
        this(RsaKeyHelper.generateKeyPair(), algorithm, DEFAULT_SALT, gcm);
    }

    public RsaSecretEncryptor(RsaAlgorithm algorithm) {
        this(RsaKeyHelper.generateKeyPair(), algorithm);
    }

    public RsaSecretEncryptor() {
        this(RsaKeyHelper.generateKeyPair());
    }

    public RsaSecretEncryptor(KeyPair keyPair, RsaAlgorithm algorithm, String salt, boolean gcm) {
        this(DEFAULT_ENCODING, keyPair.getPublic(), keyPair.getPrivate(), algorithm, salt, gcm);
    }

    public RsaSecretEncryptor(KeyPair keyPair, RsaAlgorithm algorithm, String salt) {
        this(DEFAULT_ENCODING, keyPair.getPublic(), keyPair.getPrivate(), algorithm, salt, false);
    }

    public RsaSecretEncryptor(KeyPair keyPair, RsaAlgorithm algorithm) {
        this(DEFAULT_ENCODING, keyPair.getPublic(), keyPair.getPrivate(), algorithm);
    }

    public RsaSecretEncryptor(KeyPair keyPair) {
        this(DEFAULT_ENCODING, keyPair.getPublic(), keyPair.getPrivate());
    }

    public RsaSecretEncryptor(String pemData, RsaAlgorithm algorithm, String salt) {
        this(RsaKeyHelper.parseKeyPair(pemData), algorithm, salt);
    }

    public RsaSecretEncryptor(String pemData, RsaAlgorithm algorithm) {
        this(RsaKeyHelper.parseKeyPair(pemData), algorithm);
    }

    public RsaSecretEncryptor(String pemData) {
        this(RsaKeyHelper.parseKeyPair(pemData));
    }

    public RsaSecretEncryptor(PublicKey publicKey, RsaAlgorithm algorithm, String salt, boolean gcm) {
        this(DEFAULT_ENCODING, publicKey, null, algorithm, salt, gcm);
    }

    public RsaSecretEncryptor(PublicKey publicKey, RsaAlgorithm algorithm, String salt) {
        this(DEFAULT_ENCODING, publicKey, null, algorithm, salt, false);
    }

    public RsaSecretEncryptor(PublicKey publicKey, RsaAlgorithm algorithm) {
        this(DEFAULT_ENCODING, publicKey, null, algorithm);
    }

    public RsaSecretEncryptor(PublicKey publicKey) {
        this(DEFAULT_ENCODING, publicKey, null);
    }

    public RsaSecretEncryptor(String encoding, PublicKey publicKey, PrivateKey privateKey) {
        this(encoding, publicKey, privateKey, RsaAlgorithm.DEFAULT);
    }

    public RsaSecretEncryptor(String encoding, PublicKey publicKey, PrivateKey privateKey, RsaAlgorithm algorithm) {
        this(encoding, publicKey, privateKey, algorithm, DEFAULT_SALT, false);
    }

    public RsaSecretEncryptor(String encoding, PublicKey publicKey, PrivateKey privateKey, RsaAlgorithm algorithm, String salt, boolean gcm) {
        this.charset = Charset.forName(encoding);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.defaultCharset = Charset.forName(DEFAULT_ENCODING);
        this.algorithm = algorithm;
        this.salt = RsaSecretEncryptor.isHex(salt) ? salt : new String(Hex.encode((byte[])salt.getBytes(this.defaultCharset)));
        this.gcm = gcm;
    }

    @Override
    public String getPublicKey() {
        return RsaKeyHelper.encodePublicKey((RSAPublicKey)this.publicKey, "application");
    }

    public String encrypt(String text) {
        return new String(Base64Utils.encode((byte[])this.encrypt(text.getBytes(this.charset))), this.defaultCharset);
    }

    public String decrypt(String encryptedText) {
        Assert.state((boolean)this.canDecrypt(), (String)"Encryptor is not configured for decryption");
        return new String(this.decrypt(Base64Utils.decode((byte[])encryptedText.getBytes(this.defaultCharset))), this.charset);
    }

    public byte[] encrypt(byte[] byteArray) {
        return RsaSecretEncryptor.encrypt(byteArray, this.publicKey, this.algorithm, this.salt, this.gcm);
    }

    public byte[] decrypt(byte[] encryptedByteArray) {
        Assert.state((boolean)this.canDecrypt(), (String)"Encryptor is not configured for decryption");
        return RsaSecretEncryptor.decrypt(encryptedByteArray, this.privateKey, this.algorithm, this.salt, this.gcm);
    }

    private static byte[] encrypt(byte[] text, PublicKey key, RsaAlgorithm alg, String salt, boolean gcm) {
        byte[] random = KeyGenerators.secureRandom((int)16).generateKey();
        BytesEncryptor aes = gcm ? Encryptors.stronger((CharSequence)new String(Hex.encode((byte[])random)), (CharSequence)salt) : Encryptors.standard((CharSequence)new String(Hex.encode((byte[])random)), (CharSequence)salt);
        try {
            Cipher cipher = Cipher.getInstance(alg.getJceName());
            cipher.init(1, key);
            byte[] secret = cipher.doFinal(random);
            ByteArrayOutputStream result = new ByteArrayOutputStream(text.length + 20);
            RsaSecretEncryptor.writeInt(result, secret.length);
            result.write(secret);
            result.write(aes.encrypt(text));
            return result.toByteArray();
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot encrypt", e);
        }
    }

    private static void writeInt(ByteArrayOutputStream result, int length) throws IOException {
        byte[] data = new byte[]{(byte)(length >> 8 & 0xFF), (byte)(length & 0xFF)};
        result.write(data);
    }

    private static int readInt(ByteArrayInputStream result) throws IOException {
        byte[] b = new byte[2];
        result.read(b);
        return (b[0] & 0xFF) << 8 | b[1] & 0xFF;
    }

    private static byte[] decrypt(byte[] text, PrivateKey key, RsaAlgorithm alg, String salt, boolean gcm) {
        ByteArrayInputStream input = new ByteArrayInputStream(text);
        ByteArrayOutputStream output = new ByteArrayOutputStream(text.length);
        try {
            int length = RsaSecretEncryptor.readInt(input);
            byte[] random = new byte[length];
            input.read(random);
            Cipher cipher = Cipher.getInstance(alg.getJceName());
            cipher.init(2, key);
            String secret = new String(Hex.encode((byte[])cipher.doFinal(random)));
            byte[] buffer = new byte[text.length - random.length - 2];
            input.read(buffer);
            BytesEncryptor aes = gcm ? Encryptors.stronger((CharSequence)secret, (CharSequence)salt) : Encryptors.standard((CharSequence)secret, (CharSequence)salt);
            output.write(aes.decrypt(buffer));
            return output.toByteArray();
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot decrypt", e);
        }
    }

    private static boolean isHex(String input) {
        try {
            Hex.decode((CharSequence)input);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean canDecrypt() {
        return this.privateKey != null;
    }
}

