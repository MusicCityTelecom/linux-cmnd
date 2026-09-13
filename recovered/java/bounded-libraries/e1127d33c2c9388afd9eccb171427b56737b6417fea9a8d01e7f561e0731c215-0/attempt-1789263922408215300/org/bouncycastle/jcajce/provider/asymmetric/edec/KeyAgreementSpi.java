/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.DerivationFunction
 *  org.bouncycastle.crypto.RawAgreement
 *  org.bouncycastle.crypto.agreement.X25519Agreement
 *  org.bouncycastle.crypto.agreement.X448Agreement
 *  org.bouncycastle.crypto.agreement.XDHUnifiedAgreement
 *  org.bouncycastle.crypto.agreement.kdf.ConcatenationKDFGenerator
 *  org.bouncycastle.crypto.generators.KDF2BytesGenerator
 *  org.bouncycastle.crypto.params.AsymmetricKeyParameter
 *  org.bouncycastle.crypto.params.X25519PrivateKeyParameters
 *  org.bouncycastle.crypto.params.X25519PublicKeyParameters
 *  org.bouncycastle.crypto.params.X448PrivateKeyParameters
 *  org.bouncycastle.crypto.params.X448PublicKeyParameters
 *  org.bouncycastle.crypto.params.XDHUPrivateParameters
 *  org.bouncycastle.crypto.params.XDHUPublicParameters
 *  org.bouncycastle.crypto.util.DigestFactory
 *  org.bouncycastle.jcajce.provider.asymmetric.edec.BCXDHPrivateKey
 *  org.bouncycastle.jcajce.provider.asymmetric.edec.BCXDHPublicKey
 *  org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi
 *  org.bouncycastle.jcajce.spec.DHUParameterSpec
 *  org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec
 *  org.bouncycastle.util.Arrays
 *  org.bouncycastle.util.BigIntegers
 */
package org.bouncycastle.jcajce.provider.asymmetric.edec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.interfaces.XECPrivateKey;
import java.security.interfaces.XECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.NamedParameterSpec;
import java.util.Optional;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.RawAgreement;
import org.bouncycastle.crypto.agreement.X25519Agreement;
import org.bouncycastle.crypto.agreement.X448Agreement;
import org.bouncycastle.crypto.agreement.XDHUnifiedAgreement;
import org.bouncycastle.crypto.agreement.kdf.ConcatenationKDFGenerator;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.crypto.params.X448PrivateKeyParameters;
import org.bouncycastle.crypto.params.X448PublicKeyParameters;
import org.bouncycastle.crypto.params.XDHUPrivateParameters;
import org.bouncycastle.crypto.params.XDHUPublicParameters;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCXDHPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCXDHPublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.bouncycastle.jcajce.spec.DHUParameterSpec;
import org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class KeyAgreementSpi
extends BaseAgreementSpi {
    private RawAgreement agreement;
    private DHUParameterSpec dhuSpec;
    private byte[] result;

    KeyAgreementSpi(String string) {
        super(string, null);
    }

    KeyAgreementSpi(String string, DerivationFunction derivationFunction) {
        super(string, derivationFunction);
    }

    protected byte[] calcSecret() {
        return this.result;
    }

    protected void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        AsymmetricKeyParameter asymmetricKeyParameter = KeyAgreementSpi.getLwXDHKeyPrivate(key);
        if (asymmetricKeyParameter instanceof X25519PrivateKeyParameters) {
            this.agreement = this.getAgreement("X25519");
        } else if (asymmetricKeyParameter instanceof X448PrivateKeyParameters) {
            this.agreement = this.getAgreement("X448");
        } else {
            throw new IllegalStateException("unsupported private key type");
        }
        this.agreement.init((CipherParameters)asymmetricKeyParameter);
        this.ukmParameters = (byte[])(this.kdf != null ? new byte[0] : null);
    }

    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        AsymmetricKeyParameter asymmetricKeyParameter = KeyAgreementSpi.getLwXDHKeyPrivate(key);
        if (asymmetricKeyParameter instanceof X25519PrivateKeyParameters) {
            this.agreement = this.getAgreement("X25519");
        } else if (asymmetricKeyParameter instanceof X448PrivateKeyParameters) {
            this.agreement = this.getAgreement("X448");
        } else {
            throw new IllegalStateException("unsupported private key type");
        }
        this.ukmParameters = null;
        if (algorithmParameterSpec instanceof DHUParameterSpec) {
            if (this.kaAlgorithm.indexOf(85) < 0) {
                throw new InvalidAlgorithmParameterException("agreement algorithm not DHU based");
            }
            this.dhuSpec = (DHUParameterSpec)algorithmParameterSpec;
            this.ukmParameters = this.dhuSpec.getUserKeyingMaterial();
            this.agreement.init((CipherParameters)new XDHUPrivateParameters(asymmetricKeyParameter, ((BCXDHPrivateKey)this.dhuSpec.getEphemeralPrivateKey()).engineGetKeyParameters(), ((BCXDHPublicKey)this.dhuSpec.getEphemeralPublicKey()).engineGetKeyParameters()));
        } else {
            this.agreement.init((CipherParameters)asymmetricKeyParameter);
            if (algorithmParameterSpec instanceof UserKeyingMaterialSpec) {
                if (this.kdf == null) {
                    throw new InvalidAlgorithmParameterException("no KDF specified for UserKeyingMaterialSpec");
                }
                this.ukmParameters = ((UserKeyingMaterialSpec)algorithmParameterSpec).getUserKeyingMaterial();
            } else {
                throw new InvalidAlgorithmParameterException("unknown ParameterSpec");
            }
        }
        if (this.kdf != null && this.ukmParameters == null) {
            this.ukmParameters = new byte[0];
        }
    }

    protected Key engineDoPhase(Key key, boolean bl) throws InvalidKeyException, IllegalStateException {
        if (this.agreement == null) {
            throw new IllegalStateException(this.kaAlgorithm + " not initialised.");
        }
        if (!bl) {
            throw new IllegalStateException(this.kaAlgorithm + " can only be between two parties.");
        }
        AsymmetricKeyParameter asymmetricKeyParameter = this.getLwXDHKeyPublic(key);
        this.result = new byte[this.agreement.getAgreementSize()];
        if (this.dhuSpec != null) {
            this.agreement.calculateAgreement((CipherParameters)new XDHUPublicParameters(asymmetricKeyParameter, ((BCXDHPublicKey)this.dhuSpec.getOtherPartyEphemeralKey()).engineGetKeyParameters()), this.result, 0);
        } else {
            this.agreement.calculateAgreement((CipherParameters)asymmetricKeyParameter, this.result, 0);
        }
        return null;
    }

    private RawAgreement getAgreement(String string) throws InvalidKeyException {
        if (!this.kaAlgorithm.equals("XDH") && !this.kaAlgorithm.startsWith(string)) {
            throw new InvalidKeyException("inappropriate key for " + this.kaAlgorithm);
        }
        if (this.kaAlgorithm.indexOf(85) > 0) {
            if (string.startsWith("X448")) {
                return new XDHUnifiedAgreement((RawAgreement)new X448Agreement());
            }
            return new XDHUnifiedAgreement((RawAgreement)new X25519Agreement());
        }
        if (string.startsWith("X448")) {
            return new X448Agreement();
        }
        return new X25519Agreement();
    }

    private static AsymmetricKeyParameter getLwXDHKeyPrivate(Key key) throws InvalidKeyException {
        if (key instanceof BCXDHPrivateKey) {
            return ((BCXDHPrivateKey)key).engineGetKeyParameters();
        }
        if (key instanceof XECPrivateKey) {
            AlgorithmParameterSpec algorithmParameterSpec;
            XECPrivateKey xECPrivateKey = (XECPrivateKey)key;
            Optional<byte[]> optional = xECPrivateKey.getScalar();
            if (!optional.isPresent()) {
                throw new InvalidKeyException("cannot use XEC private key without scalar");
            }
            String string = xECPrivateKey.getAlgorithm();
            if ("X25519".equalsIgnoreCase(string)) {
                return KeyAgreementSpi.getX25519PrivateKey(optional.get());
            }
            if ("X448".equalsIgnoreCase(string)) {
                return KeyAgreementSpi.getX448PrivateKey(optional.get());
            }
            if ("XDH".equalsIgnoreCase(string) && (algorithmParameterSpec = xECPrivateKey.getParams()) instanceof NamedParameterSpec) {
                NamedParameterSpec namedParameterSpec = (NamedParameterSpec)algorithmParameterSpec;
                String string2 = namedParameterSpec.getName();
                if ("X25519".equalsIgnoreCase(string2)) {
                    return KeyAgreementSpi.getX25519PrivateKey(optional.get());
                }
                if ("X448".equalsIgnoreCase(string2)) {
                    return KeyAgreementSpi.getX448PrivateKey(optional.get());
                }
            }
            throw new InvalidKeyException("cannot use XEC private key with unknown algorithm");
        }
        throw new InvalidKeyException("cannot identify XDH private key");
    }

    private AsymmetricKeyParameter getLwXDHKeyPublic(Key key) throws InvalidKeyException {
        if (key instanceof BCXDHPublicKey) {
            return ((BCXDHPublicKey)key).engineGetKeyParameters();
        }
        if (key instanceof XECPublicKey) {
            AlgorithmParameterSpec algorithmParameterSpec;
            XECPublicKey xECPublicKey = (XECPublicKey)key;
            BigInteger bigInteger = xECPublicKey.getU();
            if (bigInteger.signum() < 0) {
                throw new InvalidKeyException("cannot use XEC public key with negative U value");
            }
            String string = xECPublicKey.getAlgorithm();
            if ("X25519".equalsIgnoreCase(string)) {
                return KeyAgreementSpi.getX25519PublicKey(bigInteger);
            }
            if ("X448".equalsIgnoreCase(string)) {
                return KeyAgreementSpi.getX448PublicKey(bigInteger);
            }
            if ("XDH".equalsIgnoreCase(string) && (algorithmParameterSpec = xECPublicKey.getParams()) instanceof NamedParameterSpec) {
                NamedParameterSpec namedParameterSpec = (NamedParameterSpec)algorithmParameterSpec;
                String string2 = namedParameterSpec.getName();
                if ("X25519".equalsIgnoreCase(string2)) {
                    return KeyAgreementSpi.getX25519PublicKey(bigInteger);
                }
                if ("X448".equalsIgnoreCase(string2)) {
                    return KeyAgreementSpi.getX448PublicKey(bigInteger);
                }
            }
            throw new InvalidKeyException("cannot use XEC public key with unknown algorithm");
        }
        throw new InvalidKeyException("cannot identify XDH public key");
    }

    private static byte[] getPublicKeyData(int n, BigInteger bigInteger) throws InvalidKeyException {
        try {
            return Arrays.reverseInPlace((byte[])BigIntegers.asUnsignedByteArray((int)n, (BigInteger)bigInteger));
        }
        catch (RuntimeException runtimeException) {
            throw new InvalidKeyException("cannot use XEC public key with invalid U value");
        }
    }

    private static X25519PrivateKeyParameters getX25519PrivateKey(byte[] byArray) throws InvalidKeyException {
        if (32 != byArray.length) {
            throw new InvalidKeyException("cannot use XEC private key (X25519) with scalar of incorrect length");
        }
        return new X25519PrivateKeyParameters(byArray, 0);
    }

    private static X25519PublicKeyParameters getX25519PublicKey(BigInteger bigInteger) throws InvalidKeyException {
        byte[] byArray = KeyAgreementSpi.getPublicKeyData(32, bigInteger);
        return new X25519PublicKeyParameters(byArray, 0);
    }

    private static X448PrivateKeyParameters getX448PrivateKey(byte[] byArray) throws InvalidKeyException {
        if (56 != byArray.length) {
            throw new InvalidKeyException("cannot use XEC private key (X448) with scalar of incorrect length");
        }
        return new X448PrivateKeyParameters(byArray, 0);
    }

    private static X448PublicKeyParameters getX448PublicKey(BigInteger bigInteger) throws InvalidKeyException {
        byte[] byArray = KeyAgreementSpi.getPublicKeyData(56, bigInteger);
        return new X448PublicKeyParameters(byArray, 0);
    }

    public static class X448UwithSHA512KDF
    extends KeyAgreementSpi {
        public X448UwithSHA512KDF() {
            super("X448UwithSHA512KDF", (DerivationFunction)new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    public static class X25519UwithSHA256KDF
    extends KeyAgreementSpi {
        public X25519UwithSHA256KDF() {
            super("X25519UwithSHA256KDF", (DerivationFunction)new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    public static class X448UwithSHA512CKDF
    extends KeyAgreementSpi {
        public X448UwithSHA512CKDF() {
            super("X448UwithSHA512CKDF", (DerivationFunction)new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
        }
    }

    public static class X25519UwithSHA256CKDF
    extends KeyAgreementSpi {
        public X25519UwithSHA256CKDF() {
            super("X25519UwithSHA256CKDF", (DerivationFunction)new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
        }
    }

    public static final class X448withSHA512KDF
    extends KeyAgreementSpi {
        public X448withSHA512KDF() {
            super("X448withSHA512KDF", (DerivationFunction)new KDF2BytesGenerator(DigestFactory.createSHA512()));
        }
    }

    public static final class X25519withSHA256KDF
    extends KeyAgreementSpi {
        public X25519withSHA256KDF() {
            super("X25519withSHA256KDF", (DerivationFunction)new KDF2BytesGenerator(DigestFactory.createSHA256()));
        }
    }

    public static final class X448withSHA512CKDF
    extends KeyAgreementSpi {
        public X448withSHA512CKDF() {
            super("X448withSHA512CKDF", (DerivationFunction)new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
        }
    }

    public static class X448withSHA384CKDF
    extends KeyAgreementSpi {
        public X448withSHA384CKDF() {
            super("X448withSHA384CKDF", (DerivationFunction)new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
        }
    }

    public static final class X448withSHA256CKDF
    extends KeyAgreementSpi {
        public X448withSHA256CKDF() {
            super("X448withSHA256CKDF", (DerivationFunction)new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
        }
    }

    public static class X25519withSHA512CKDF
    extends KeyAgreementSpi {
        public X25519withSHA512CKDF() {
            super("X25519withSHA512CKDF", (DerivationFunction)new ConcatenationKDFGenerator(DigestFactory.createSHA512()));
        }
    }

    public static class X25519withSHA384CKDF
    extends KeyAgreementSpi {
        public X25519withSHA384CKDF() {
            super("X25519withSHA384CKDF", (DerivationFunction)new ConcatenationKDFGenerator(DigestFactory.createSHA384()));
        }
    }

    public static final class X25519withSHA256CKDF
    extends KeyAgreementSpi {
        public X25519withSHA256CKDF() {
            super("X25519withSHA256CKDF", (DerivationFunction)new ConcatenationKDFGenerator(DigestFactory.createSHA256()));
        }
    }

    public static final class X25519
    extends KeyAgreementSpi {
        public X25519() {
            super("X25519");
        }
    }

    public static final class X448
    extends KeyAgreementSpi {
        public X448() {
            super("X448");
        }
    }

    public static final class XDH
    extends KeyAgreementSpi {
        public XDH() {
            super("XDH");
        }
    }
}

