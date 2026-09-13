/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.crypto.encrypt.BytesEncryptor
 *  org.springframework.security.crypto.encrypt.TextEncryptor
 *  org.springframework.util.Assert
 *  org.springframework.util.Base64Utils
 */
package org.springframework.security.rsa.crypto;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.Cipher;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.rsa.crypto.RsaAlgorithm;
import org.springframework.security.rsa.crypto.RsaKeyHelper;
import org.springframework.security.rsa.crypto.RsaKeyHolder;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

public class RsaRawEncryptor
implements BytesEncryptor,
TextEncryptor,
RsaKeyHolder {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private RsaAlgorithm algorithm = RsaAlgorithm.DEFAULT;
    private Charset charset;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Charset defaultCharset;

    public RsaRawEncryptor(RsaAlgorithm algorithm) {
        this(RsaKeyHelper.generateKeyPair(), algorithm);
    }

    public RsaRawEncryptor() {
        this(RsaKeyHelper.generateKeyPair());
    }

    public RsaRawEncryptor(KeyPair keyPair, RsaAlgorithm algorithm) {
        this(DEFAULT_ENCODING, keyPair.getPublic(), keyPair.getPrivate(), algorithm);
    }

    public RsaRawEncryptor(KeyPair keyPair) {
        this(DEFAULT_ENCODING, keyPair.getPublic(), keyPair.getPrivate());
    }

    public RsaRawEncryptor(String pemData) {
        this(RsaKeyHelper.parseKeyPair(pemData));
    }

    public RsaRawEncryptor(PublicKey publicKey) {
        this(DEFAULT_ENCODING, publicKey, null);
    }

    public RsaRawEncryptor(String encoding, PublicKey publicKey, PrivateKey privateKey) {
        this(encoding, publicKey, privateKey, RsaAlgorithm.DEFAULT);
    }

    public RsaRawEncryptor(String encoding, PublicKey publicKey, PrivateKey privateKey, RsaAlgorithm algorithm) {
        this.charset = Charset.forName(encoding);
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.defaultCharset = Charset.forName(DEFAULT_ENCODING);
        this.algorithm = algorithm;
    }

    @Override
    public String getPublicKey() {
        return RsaKeyHelper.encodePublicKey((RSAPublicKey)this.publicKey, "application");
    }

    public String encrypt(String text) {
        return new String(Base64Utils.encode((byte[])this.encrypt(text.getBytes(this.charset))), this.defaultCharset);
    }

    public String decrypt(String encryptedText) {
        Assert.state((this.privateKey != null ? 1 : 0) != 0, (String)"Private key must be provided for decryption");
        return new String(this.decrypt(Base64Utils.decode((byte[])encryptedText.getBytes(this.defaultCharset))), this.charset);
    }

    public byte[] encrypt(byte[] byteArray) {
        return RsaRawEncryptor.encrypt(byteArray, this.publicKey, this.algorithm);
    }

    public byte[] decrypt(byte[] encryptedByteArray) {
        return RsaRawEncryptor.decrypt(encryptedByteArray, this.privateKey, this.algorithm);
    }

    private static byte[] encrypt(byte[] text, PublicKey key, RsaAlgorithm alg) {
        ByteArrayOutputStream output = new ByteArrayOutputStream(text.length);
        try {
            Cipher cipher = Cipher.getInstance(alg.getJceName());
            int limit = Math.min(text.length, alg.getMaxLength());
            int pos = 0;
            while (pos < text.length) {
                cipher.init(1, key);
                cipher.update(text, pos, limit);
                limit = Math.min(text.length - (pos += limit), alg.getMaxLength());
                byte[] buffer = cipher.doFinal();
                output.write(buffer, 0, buffer.length);
            }
            return output.toByteArray();
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot encrypt", e);
        }
    }

    private static byte[] decrypt(byte[] text, PrivateKey key, RsaAlgorithm alg) {
        ByteArrayOutputStream output = new ByteArrayOutputStream(text.length);
        try {
            Cipher cipher = Cipher.getInstance(alg.getJceName());
            int limit = Math.min(text.length, 128);
            int pos = 0;
            while (pos < text.length) {
                cipher.init(2, key);
                cipher.update(text, pos, limit);
                limit = Math.min(text.length - (pos += limit), 128);
                byte[] buffer = cipher.doFinal();
                output.write(buffer, 0, buffer.length);
            }
            return output.toByteArray();
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot decrypt", e);
        }
    }
}

