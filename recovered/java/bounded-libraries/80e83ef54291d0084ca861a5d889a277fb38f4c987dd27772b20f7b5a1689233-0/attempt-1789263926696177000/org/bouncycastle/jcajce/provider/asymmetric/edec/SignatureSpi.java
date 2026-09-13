/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.CryptoException
 *  org.bouncycastle.crypto.Signer
 *  org.bouncycastle.crypto.params.AsymmetricKeyParameter
 *  org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
 *  org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
 *  org.bouncycastle.crypto.params.Ed448PrivateKeyParameters
 *  org.bouncycastle.crypto.params.Ed448PublicKeyParameters
 *  org.bouncycastle.crypto.signers.Ed25519Signer
 *  org.bouncycastle.crypto.signers.Ed448Signer
 *  org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPrivateKey
 *  org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey
 *  org.bouncycastle.util.Arrays
 *  org.bouncycastle.util.BigIntegers
 */
package org.bouncycastle.jcajce.provider.asymmetric.edec;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.interfaces.EdECPrivateKey;
import java.security.interfaces.EdECPublicKey;
import java.security.spec.EdECPoint;
import java.security.spec.NamedParameterSpec;
import java.util.Optional;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.params.Ed448PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed448PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.crypto.signers.Ed448Signer;
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class SignatureSpi
extends java.security.SignatureSpi {
    private static final byte[] EMPTY_CONTEXT = new byte[0];
    private final String algorithm;
    private Signer signer;

    SignatureSpi(String string) {
        this.algorithm = string;
    }

    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        AsymmetricKeyParameter asymmetricKeyParameter = SignatureSpi.getLwEdDSAKeyPublic(publicKey);
        if (asymmetricKeyParameter instanceof Ed25519PublicKeyParameters) {
            this.signer = this.getSigner("Ed25519");
        } else if (asymmetricKeyParameter instanceof Ed448PublicKeyParameters) {
            this.signer = this.getSigner("Ed448");
        } else {
            throw new IllegalStateException("unsupported public key type");
        }
        this.signer.init(false, (CipherParameters)asymmetricKeyParameter);
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        AsymmetricKeyParameter asymmetricKeyParameter = SignatureSpi.getLwEdDSAKeyPrivate(privateKey);
        if (asymmetricKeyParameter instanceof Ed25519PrivateKeyParameters) {
            this.signer = this.getSigner("Ed25519");
        } else if (asymmetricKeyParameter instanceof Ed448PrivateKeyParameters) {
            this.signer = this.getSigner("Ed448");
        } else {
            throw new IllegalStateException("unsupported private key type");
        }
        this.signer.init(true, (CipherParameters)asymmetricKeyParameter);
    }

    private static Ed25519PrivateKeyParameters getEd25519PrivateKey(byte[] byArray) throws InvalidKeyException {
        if (32 != byArray.length) {
            throw new InvalidKeyException("cannot use EdEC private key (Ed25519) with bytes of incorrect length");
        }
        return new Ed25519PrivateKeyParameters(byArray, 0);
    }

    private static Ed25519PublicKeyParameters getEd25519PublicKey(EdECPoint edECPoint) throws InvalidKeyException {
        byte[] byArray = SignatureSpi.getPublicKeyData(32, edECPoint);
        return new Ed25519PublicKeyParameters(byArray, 0);
    }

    private static Ed448PrivateKeyParameters getEd448PrivateKey(byte[] byArray) throws InvalidKeyException {
        if (57 != byArray.length) {
            throw new InvalidKeyException("cannot use EdEC private key (Ed448) with bytes of incorrect length");
        }
        return new Ed448PrivateKeyParameters(byArray, 0);
    }

    private static Ed448PublicKeyParameters getEd448PublicKey(EdECPoint edECPoint) throws InvalidKeyException {
        byte[] byArray = SignatureSpi.getPublicKeyData(57, edECPoint);
        return new Ed448PublicKeyParameters(byArray, 0);
    }

    private static AsymmetricKeyParameter getLwEdDSAKeyPrivate(Key key) throws InvalidKeyException {
        if (key instanceof BCEdDSAPrivateKey) {
            return ((BCEdDSAPrivateKey)key).engineGetKeyParameters();
        }
        if (key instanceof EdECPrivateKey) {
            NamedParameterSpec namedParameterSpec;
            EdECPrivateKey edECPrivateKey = (EdECPrivateKey)key;
            Optional<byte[]> optional = edECPrivateKey.getBytes();
            if (!optional.isPresent()) {
                throw new InvalidKeyException("cannot use EdEC private key without bytes");
            }
            String string = edECPrivateKey.getAlgorithm();
            if ("Ed25519".equalsIgnoreCase(string)) {
                return SignatureSpi.getEd25519PrivateKey(optional.get());
            }
            if ("Ed448".equalsIgnoreCase(string)) {
                return SignatureSpi.getEd448PrivateKey(optional.get());
            }
            if ("EdDSA".equalsIgnoreCase(string) && (namedParameterSpec = edECPrivateKey.getParams()) instanceof NamedParameterSpec) {
                NamedParameterSpec namedParameterSpec2 = namedParameterSpec;
                String string2 = namedParameterSpec2.getName();
                if ("Ed25519".equalsIgnoreCase(string2)) {
                    return SignatureSpi.getEd25519PrivateKey(optional.get());
                }
                if ("Ed448".equalsIgnoreCase(string2)) {
                    return SignatureSpi.getEd448PrivateKey(optional.get());
                }
            }
            throw new InvalidKeyException("cannot use EdEC private key with unknown algorithm");
        }
        throw new InvalidKeyException("cannot identify EdDSA private key");
    }

    private static AsymmetricKeyParameter getLwEdDSAKeyPublic(Key key) throws InvalidKeyException {
        if (key instanceof BCEdDSAPublicKey) {
            return ((BCEdDSAPublicKey)key).engineGetKeyParameters();
        }
        if (key instanceof EdECPublicKey) {
            NamedParameterSpec namedParameterSpec;
            EdECPublicKey edECPublicKey = (EdECPublicKey)key;
            EdECPoint edECPoint = edECPublicKey.getPoint();
            String string = edECPublicKey.getAlgorithm();
            if ("Ed25519".equalsIgnoreCase(string)) {
                return SignatureSpi.getEd25519PublicKey(edECPoint);
            }
            if ("Ed448".equalsIgnoreCase(string)) {
                return SignatureSpi.getEd448PublicKey(edECPoint);
            }
            if ("EdDSA".equalsIgnoreCase(string) && (namedParameterSpec = edECPublicKey.getParams()) instanceof NamedParameterSpec) {
                NamedParameterSpec namedParameterSpec2 = namedParameterSpec;
                String string2 = namedParameterSpec2.getName();
                if ("Ed25519".equalsIgnoreCase(string2)) {
                    return SignatureSpi.getEd25519PublicKey(edECPoint);
                }
                if ("Ed448".equalsIgnoreCase(string2)) {
                    return SignatureSpi.getEd448PublicKey(edECPoint);
                }
            }
            throw new InvalidKeyException("cannot use EdEC public key with unknown algorithm");
        }
        throw new InvalidKeyException("cannot identify EdDSA public key");
    }

    private static byte[] getPublicKeyData(int n, EdECPoint edECPoint) throws InvalidKeyException {
        BigInteger bigInteger = edECPoint.getY();
        if (bigInteger.signum() < 0) {
            throw new InvalidKeyException("cannot use EdEC public key with negative Y value");
        }
        try {
            byte[] byArray = BigIntegers.asUnsignedByteArray((int)n, (BigInteger)bigInteger);
            if ((byArray[0] & 0x80) == 0) {
                if (edECPoint.isXOdd()) {
                    byArray[0] = (byte)(byArray[0] | 0x80);
                }
                return Arrays.reverseInPlace((byte[])byArray);
            }
        }
        catch (RuntimeException runtimeException) {
            // empty catch block
        }
        throw new InvalidKeyException("cannot use EdEC public key with invalid Y value");
    }

    private Signer getSigner(String string) throws InvalidKeyException {
        if (this.algorithm != null && !string.equals(this.algorithm)) {
            throw new InvalidKeyException("inappropriate key for " + this.algorithm);
        }
        if (string.equals("Ed448")) {
            return new Ed448Signer(EMPTY_CONTEXT);
        }
        return new Ed25519Signer();
    }

    @Override
    protected void engineUpdate(byte by) throws SignatureException {
        this.signer.update(by);
    }

    @Override
    protected void engineUpdate(byte[] byArray, int n, int n2) throws SignatureException {
        this.signer.update(byArray, n, n2);
    }

    @Override
    protected byte[] engineSign() throws SignatureException {
        try {
            return this.signer.generateSignature();
        }
        catch (CryptoException cryptoException) {
            throw new SignatureException(cryptoException.getMessage());
        }
    }

    @Override
    protected boolean engineVerify(byte[] byArray) throws SignatureException {
        return this.signer.verifySignature(byArray);
    }

    @Override
    protected void engineSetParameter(String string, Object object) throws InvalidParameterException {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override
    protected Object engineGetParameter(String string) throws InvalidParameterException {
        throw new UnsupportedOperationException("engineGetParameter unsupported");
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    public static final class Ed25519
    extends SignatureSpi {
        public Ed25519() {
            super("Ed25519");
        }
    }

    public static final class Ed448
    extends SignatureSpi {
        public Ed448() {
            super("Ed448");
        }
    }

    public static final class EdDSA
    extends SignatureSpi {
        public EdDSA() {
            super(null);
        }
    }
}

