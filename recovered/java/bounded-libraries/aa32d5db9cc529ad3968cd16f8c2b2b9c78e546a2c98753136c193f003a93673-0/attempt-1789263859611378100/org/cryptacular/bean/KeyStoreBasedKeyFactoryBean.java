/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.bean;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import org.cryptacular.CryptoException;
import org.cryptacular.bean.FactoryBean;

public class KeyStoreBasedKeyFactoryBean<T extends Key>
implements FactoryBean<T> {
    private KeyStore keyStore;
    private String alias;
    private String password;

    public KeyStoreBasedKeyFactoryBean() {
    }

    public KeyStoreBasedKeyFactoryBean(KeyStore keyStore, String alias, String password) {
        this.setKeyStore(keyStore);
        this.setAlias(alias);
        this.setPassword(password);
    }

    public KeyStore getKeyStore() {
        return this.keyStore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public T newInstance() {
        Key key;
        try {
            key = this.keyStore.getKey(this.alias, this.password.toCharArray());
        }
        catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new CryptoException("Error accessing keystore entry " + this.alias, e);
        }
        return (T)key;
    }
}

