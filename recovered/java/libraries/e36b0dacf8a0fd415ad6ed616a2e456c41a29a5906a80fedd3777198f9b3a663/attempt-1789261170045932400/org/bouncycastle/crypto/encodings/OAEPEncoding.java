/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.crypto.encodings;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class OAEPEncoding
implements AsymmetricBlockCipher {
    private byte[] defHash;
    private Digest mgf1Hash;
    private AsymmetricBlockCipher engine;
    private SecureRandom random;
    private boolean forEncryption;

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this(asymmetricBlockCipher, DigestFactory.createSHA1(), null);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this(asymmetricBlockCipher, digest, null);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, byte[] byArray) {
        this(asymmetricBlockCipher, digest, digest, byArray);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, byte[] byArray) {
        this.engine = asymmetricBlockCipher;
        this.mgf1Hash = digest2;
        this.defHash = new byte[digest.getDigestSize()];
        digest.reset();
        if (byArray != null) {
            digest.update(byArray, 0, byArray.length);
        }
        digest.doFinal(this.defHash, 0);
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom)cipherParameters;
            this.random = parametersWithRandom.getRandom();
        } else {
            this.random = CryptoServicesRegistrar.getSecureRandom();
        }
        this.engine.init(bl, cipherParameters);
        this.forEncryption = bl;
    }

    @Override
    public int getInputBlockSize() {
        int n = this.engine.getInputBlockSize();
        if (this.forEncryption) {
            return n - 1 - 2 * this.defHash.length;
        }
        return n;
    }

    @Override
    public int getOutputBlockSize() {
        int n = this.engine.getOutputBlockSize();
        if (this.forEncryption) {
            return n;
        }
        return n - 1 - 2 * this.defHash.length;
    }

    @Override
    public byte[] processBlock(byte[] byArray, int n, int n2) throws InvalidCipherTextException {
        if (this.forEncryption) {
            return this.encodeBlock(byArray, n, n2);
        }
        return this.decodeBlock(byArray, n, n2);
    }

    public byte[] encodeBlock(byte[] byArray, int n, int n2) throws InvalidCipherTextException {
        int n3;
        if (n2 > this.getInputBlockSize()) {
            throw new DataLengthException("input data too long");
        }
        byte[] byArray2 = new byte[this.getInputBlockSize() + 1 + 2 * this.defHash.length];
        System.arraycopy(byArray, n, byArray2, byArray2.length - n2, n2);
        byArray2[byArray2.length - n2 - 1] = 1;
        System.arraycopy(this.defHash, 0, byArray2, this.defHash.length, this.defHash.length);
        byte[] byArray3 = new byte[this.defHash.length];
        this.random.nextBytes(byArray3);
        byte[] byArray4 = this.maskGeneratorFunction1(byArray3, 0, byArray3.length, byArray2.length - this.defHash.length);
        for (n3 = this.defHash.length; n3 != byArray2.length; ++n3) {
            int n4 = n3;
            byArray2[n4] = (byte)(byArray2[n4] ^ byArray4[n3 - this.defHash.length]);
        }
        System.arraycopy(byArray3, 0, byArray2, 0, this.defHash.length);
        byArray4 = this.maskGeneratorFunction1(byArray2, this.defHash.length, byArray2.length - this.defHash.length, this.defHash.length);
        for (n3 = 0; n3 != this.defHash.length; ++n3) {
            int n5 = n3;
            byArray2[n5] = (byte)(byArray2[n5] ^ byArray4[n3]);
        }
        return this.engine.processBlock(byArray2, 0, byArray2.length);
    }

    public byte[] decodeBlock(byte[] byArray, int n, int n2) throws InvalidCipherTextException {
        int n3;
        byte[] byArray2 = this.engine.processBlock(byArray, n, n2);
        byte[] byArray3 = new byte[this.engine.getOutputBlockSize()];
        int n4 = byArray3.length - (2 * this.defHash.length + 1) >> 31;
        if (byArray2.length <= byArray3.length) {
            System.arraycopy(byArray2, 0, byArray3, byArray3.length - byArray2.length, byArray2.length);
        } else {
            System.arraycopy(byArray2, 0, byArray3, 0, byArray3.length);
            n4 |= 1;
        }
        byte[] byArray4 = this.maskGeneratorFunction1(byArray3, this.defHash.length, byArray3.length - this.defHash.length, this.defHash.length);
        for (n3 = 0; n3 != this.defHash.length; ++n3) {
            int n5 = n3;
            byArray3[n5] = (byte)(byArray3[n5] ^ byArray4[n3]);
        }
        byArray4 = this.maskGeneratorFunction1(byArray3, 0, this.defHash.length, byArray3.length - this.defHash.length);
        for (n3 = this.defHash.length; n3 != byArray3.length; ++n3) {
            int n6 = n3;
            byArray3[n6] = (byte)(byArray3[n6] ^ byArray4[n3 - this.defHash.length]);
        }
        for (n3 = 0; n3 != this.defHash.length; ++n3) {
            n4 |= this.defHash[n3] ^ byArray3[this.defHash.length + n3];
        }
        n3 = -1;
        for (int i = 2 * this.defHash.length; i != byArray3.length; ++i) {
            int n7 = byArray3[i] & 0xFF;
            int n8 = (-n7 & n3) >> 31;
            n3 += i & n8;
        }
        n4 |= n3 >> 31;
        if ((n4 |= byArray3[++n3] ^ 1) != 0) {
            Arrays.fill(byArray3, (byte)0);
            throw new InvalidCipherTextException("data wrong");
        }
        byte[] byArray5 = new byte[byArray3.length - ++n3];
        System.arraycopy(byArray3, n3, byArray5, 0, byArray5.length);
        Arrays.fill(byArray3, (byte)0);
        return byArray5;
    }

    private byte[] maskGeneratorFunction1(byte[] byArray, int n, int n2, int n3) {
        int n4;
        byte[] byArray2 = new byte[n3];
        byte[] byArray3 = new byte[this.mgf1Hash.getDigestSize()];
        byte[] byArray4 = new byte[4];
        this.mgf1Hash.reset();
        for (n4 = 0; n4 < n3 / byArray3.length; ++n4) {
            Pack.intToBigEndian(n4, byArray4, 0);
            this.mgf1Hash.update(byArray, n, n2);
            this.mgf1Hash.update(byArray4, 0, byArray4.length);
            this.mgf1Hash.doFinal(byArray3, 0);
            System.arraycopy(byArray3, 0, byArray2, n4 * byArray3.length, byArray3.length);
        }
        if (n4 * byArray3.length < n3) {
            Pack.intToBigEndian(n4, byArray4, 0);
            this.mgf1Hash.update(byArray, n, n2);
            this.mgf1Hash.update(byArray4, 0, byArray4.length);
            this.mgf1Hash.doFinal(byArray3, 0);
            System.arraycopy(byArray3, 0, byArray2, n4 * byArray3.length, byArray2.length - n4 * byArray3.length);
        }
        return byArray2;
    }
}

