/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.AsymmetricKeyParameter
 *  org.bouncycastle.crypto.util.PrivateKeyInfoFactory
 *  org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory
 */
package org.cryptacular.adapter;

import java.io.IOException;
import java.security.Key;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.cryptacular.EncodingException;

public abstract class AbstractWrappedKey<T extends AsymmetricKeyParameter>
implements Key {
    public static final String PKCS8_FORMAT = "PKCS#8";
    public static final String X509_FORMAT = "X.509";
    protected final T delegate;

    public AbstractWrappedKey(T wrappedKey) {
        if (wrappedKey == null) {
            throw new IllegalArgumentException("Wrapped key cannot be null.");
        }
        this.delegate = wrappedKey;
    }

    @Override
    public String getFormat() {
        if (this.delegate.isPrivate()) {
            return PKCS8_FORMAT;
        }
        return X509_FORMAT;
    }

    @Override
    public byte[] getEncoded() {
        try {
            if (this.delegate.isPrivate()) {
                return PrivateKeyInfoFactory.createPrivateKeyInfo(this.delegate).getEncoded();
            }
            return SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(this.delegate).getEncoded();
        }
        catch (IOException e) {
            throw new EncodingException("Key encoding error", e);
        }
    }
}

