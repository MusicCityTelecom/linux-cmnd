/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.params.AsymmetricKeyParameter
 *  org.bouncycastle.crypto.params.DSAPrivateKeyParameters
 *  org.bouncycastle.crypto.params.DSAPublicKeyParameters
 *  org.bouncycastle.crypto.params.ECPrivateKeyParameters
 *  org.bouncycastle.crypto.params.ECPublicKeyParameters
 *  org.bouncycastle.crypto.params.RSAKeyParameters
 *  org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters
 */
package org.cryptacular.adapter;

import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.cryptacular.adapter.AbstractWrappedKey;
import org.cryptacular.adapter.WrappedDSAPrivateKey;
import org.cryptacular.adapter.WrappedDSAPublicKey;
import org.cryptacular.adapter.WrappedECPrivateKey;
import org.cryptacular.adapter.WrappedECPublicKey;
import org.cryptacular.adapter.WrappedRSAPrivateCrtKey;
import org.cryptacular.adapter.WrappedRSAPublicKey;

public final class Converter {
    private Converter() {
    }

    public static PrivateKey convertPrivateKey(AsymmetricKeyParameter bcKey) {
        AbstractWrappedKey key;
        if (!bcKey.isPrivate()) {
            throw new IllegalArgumentException("AsymmetricKeyParameter is not a private key: " + bcKey);
        }
        if (bcKey instanceof DSAPrivateKeyParameters) {
            key = new WrappedDSAPrivateKey((DSAPrivateKeyParameters)bcKey);
        } else if (bcKey instanceof ECPrivateKeyParameters) {
            key = new WrappedECPrivateKey((ECPrivateKeyParameters)bcKey);
        } else if (bcKey instanceof RSAPrivateCrtKeyParameters) {
            key = new WrappedRSAPrivateCrtKey((RSAPrivateCrtKeyParameters)bcKey);
        } else {
            throw new IllegalArgumentException("Unsupported private key " + bcKey);
        }
        return key;
    }

    public static PublicKey convertPublicKey(AsymmetricKeyParameter bcKey) {
        AbstractWrappedKey key;
        if (bcKey.isPrivate()) {
            throw new IllegalArgumentException("AsymmetricKeyParameter is not a public key: " + bcKey);
        }
        if (bcKey instanceof DSAPublicKeyParameters) {
            key = new WrappedDSAPublicKey((DSAPublicKeyParameters)bcKey);
        } else if (bcKey instanceof ECPublicKeyParameters) {
            key = new WrappedECPublicKey((ECPublicKeyParameters)bcKey);
        } else if (bcKey instanceof RSAKeyParameters) {
            key = new WrappedRSAPublicKey((RSAKeyParameters)bcKey);
        } else {
            throw new IllegalArgumentException("Unsupported public key " + bcKey);
        }
        return key;
    }
}

