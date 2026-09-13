/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.rsa.crypto;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

public class KeyStoreKeyFactory {
    private Resource resource;
    private char[] password;
    private KeyStore store;
    private Object lock = new Object();
    private String type;

    public KeyStoreKeyFactory(Resource resource, char[] password) {
        this(resource, password, KeyStoreKeyFactory.type(resource));
    }

    private static String type(Resource resource) {
        String ext = StringUtils.getFilenameExtension((String)resource.getFilename());
        return ext == null ? "jks" : ext;
    }

    public KeyStoreKeyFactory(Resource resource, char[] password, String type) {
        this.resource = resource;
        this.password = password;
        this.type = type;
    }

    public KeyPair getKeyPair(String alias) {
        return this.getKeyPair(alias, this.password);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public KeyPair getKeyPair(String alias, char[] password) {
        try {
            Object object = this.lock;
            synchronized (object) {
                if (this.store == null) {
                    Object object2 = this.lock;
                    synchronized (object2) {
                        this.store = KeyStore.getInstance(this.type);
                        InputStream stream = this.resource.getInputStream();
                        try {
                            this.store.load(stream, this.password);
                        }
                        finally {
                            if (stream != null) {
                                stream.close();
                            }
                        }
                    }
                }
            }
            RSAPrivateCrtKey key = (RSAPrivateCrtKey)this.store.getKey(alias, password);
            Certificate certificate = this.store.getCertificate(alias);
            PublicKey publicKey = null;
            if (certificate != null) {
                publicKey = certificate.getPublicKey();
            } else if (key != null) {
                RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
                publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
            }
            return new KeyPair(publicKey, key);
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot load keys from store: " + this.resource, e);
        }
    }
}

