/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import javax.crypto.SecretKey;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.CiphertextHeaderV2;
import org.cryptacular.CryptoException;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.bean.CipherBean;
import org.cryptacular.generator.Nonce;
import org.cryptacular.util.CipherUtil;

public abstract class AbstractCipherBean
implements CipherBean {
    private KeyStore keyStore;
    private String keyAlias;
    private String keyPassword;
    private Nonce nonce;

    public AbstractCipherBean() {
    }

    public AbstractCipherBean(KeyStore keyStore, String keyAlias, String keyPassword, Nonce nonce) {
        this.setKeyStore(keyStore);
        this.setKeyAlias(keyAlias);
        this.setKeyPassword(keyPassword);
        this.setNonce(nonce);
    }

    public KeyStore getKeyStore() {
        return this.keyStore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public String getKeyAlias() {
        return this.keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public Nonce getNonce() {
        return this.nonce;
    }

    public void setNonce(Nonce nonce) {
        this.nonce = nonce;
    }

    @Override
    public byte[] encrypt(byte[] input) throws CryptoException {
        return this.process(this.header(), true, input);
    }

    @Override
    public void encrypt(InputStream input, OutputStream output) throws CryptoException, StreamException {
        CiphertextHeaderV2 header = this.header();
        try {
            output.write(header.encode());
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
        this.process(header, true, input, output);
    }

    @Override
    public byte[] decrypt(byte[] input) throws CryptoException, EncodingException {
        return this.process(CipherUtil.decodeHeader(input, this::lookupKey), false, input);
    }

    @Override
    public void decrypt(InputStream input, OutputStream output) throws CryptoException, EncodingException, StreamException {
        this.process(CipherUtil.decodeHeader(input, this::lookupKey), false, input, output);
    }

    protected SecretKey lookupKey(String alias) {
        Key key;
        try {
            key = this.keyStore.getKey(alias, this.keyPassword.toCharArray());
        }
        catch (Exception e) {
            throw new CryptoException("Error accessing keystore entry " + alias, e);
        }
        if (key instanceof SecretKey) {
            return (SecretKey)key;
        }
        throw new CryptoException(alias + " is not a secret key");
    }

    protected abstract byte[] process(CiphertextHeader var1, boolean var2, byte[] var3);

    protected abstract void process(CiphertextHeader var1, boolean var2, InputStream var3, OutputStream var4);

    private CiphertextHeaderV2 header() {
        CiphertextHeaderV2 header = new CiphertextHeaderV2(this.nonce.generate(), this.keyAlias);
        header.setKeyLookup(this::lookupKey);
        return header;
    }
}

