/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.BlockCipher
 *  org.bouncycastle.crypto.Digest
 *  org.bouncycastle.crypto.digests.SHA256Digest
 *  org.bouncycastle.crypto.prng.EntropySource
 *  org.bouncycastle.crypto.prng.SP800SecureRandom
 *  org.bouncycastle.crypto.prng.drbg.HashSP800DRBG
 *  org.bouncycastle.crypto.prng.drbg.SP80090DRBG
 */
package org.cryptacular.util;

import java.lang.reflect.Method;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.prng.EntropySource;
import org.bouncycastle.crypto.prng.SP800SecureRandom;
import org.bouncycastle.crypto.prng.drbg.HashSP800DRBG;
import org.bouncycastle.crypto.prng.drbg.SP80090DRBG;
import org.cryptacular.generator.sp80038a.EncryptedNonce;
import org.cryptacular.generator.sp80038d.RBGNonce;
import org.cryptacular.util.ReflectUtil;

public final class NonceUtil {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private NonceUtil() {
    }

    public static byte[] timestampNonce(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException(length + " is invalid. Length must be positive.");
        }
        byte[] nonce = new byte[length];
        int count = 0;
        while (count < length) {
            long timestamp = System.nanoTime();
            for (int i = 0; i < 8 && count < length; ++i) {
                nonce[count++] = (byte)(timestamp & 0xFFL);
                timestamp >>= 8;
            }
        }
        return nonce;
    }

    public static byte[] randomNonce(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException(length + " is invalid. Length must be positive.");
        }
        byte[] nonce = new byte[length];
        SECURE_RANDOM.nextBytes(nonce);
        return nonce;
    }

    public static EntropySource randomEntropySource(final int length) {
        return new EntropySource(){

            public boolean isPredictionResistant() {
                return true;
            }

            public byte[] getEntropy() {
                byte[] bytes = new byte[length];
                SECURE_RANDOM.nextBytes(bytes);
                return bytes;
            }

            public int entropySize() {
                return length;
            }
        };
    }

    public static byte[] nist80038d(int length) {
        return new RBGNonce(length).generate();
    }

    public static byte[] nist80063a(BlockCipher cipher, SecretKey key) {
        BlockCipher raw = cipher;
        Method method = ReflectUtil.getMethod(cipher.getClass(), "getUnderlyingCipher", new Class[0]);
        if (method != null) {
            raw = (BlockCipher)ReflectUtil.invoke(cipher, method, new Object[0]);
        }
        return new EncryptedNonce(raw, key).generate();
    }

    public static byte[] nist80063a(SP800SecureRandom prng, int blockSize) {
        prng.setSeed(NonceUtil.randomNonce(blockSize));
        byte[] iv = new byte[blockSize];
        prng.nextBytes(iv);
        return iv;
    }

    public static byte[] nist80063a(BlockCipher cipher) {
        return new RBGNonce(cipher.getBlockSize()).generate();
    }

    public static SP80090DRBG newRBG(int length) {
        return NonceUtil.newRBG((Digest)new SHA256Digest(), length);
    }

    public static SP80090DRBG newRBG(Digest digest, int length) {
        return NonceUtil.newRBG(digest, length, NonceUtil.randomEntropySource(length));
    }

    public static SP80090DRBG newRBG(Digest digest, int length, EntropySource es) {
        return new HashSP800DRBG(digest, length, es, Thread.currentThread().getName().getBytes(), NonceUtil.timestampNonce(8));
    }

    static {
        SECURE_RANDOM.nextBytes(new byte[1]);
    }
}

