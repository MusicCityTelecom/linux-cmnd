/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.bean;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import org.cryptacular.CryptoException;
import org.cryptacular.StreamException;
import org.cryptacular.bean.FactoryBean;
import org.cryptacular.io.Resource;

public class KeyStoreFactoryBean
implements FactoryBean<KeyStore> {
    public static final String DEFAULT_TYPE = "JCEKS";
    private String type = "JCEKS";
    private Resource resource;
    private String password;

    public KeyStoreFactoryBean() {
    }

    public KeyStoreFactoryBean(Resource resource, String type, String password) {
        this.setResource(resource);
        this.setType(type);
        this.setPassword(password);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public KeyStore newInstance() {
        KeyStore store;
        if (this.resource == null) {
            throw new IllegalStateException("Must provide resource.");
        }
        try {
            store = KeyStore.getInstance(this.type);
        }
        catch (KeyStoreException e) {
            String message = "Unsupported keystore type " + this.type;
            if ("BKS".equalsIgnoreCase(this.type)) {
                message = message + ". Is BC provider installed?";
            }
            throw new CryptoException(message, e);
        }
        try {
            store.load(this.resource.getInputStream(), this.password.toCharArray());
        }
        catch (NoSuchAlgorithmException | CertificateException e) {
            throw new CryptoException("Error loading keystore", e);
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
        return store;
    }
}

