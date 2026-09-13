/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.Digest
 *  org.bouncycastle.crypto.digests.SHA256Digest
 *  org.bouncycastle.crypto.macs.HMac
 */
package org.cryptacular;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.util.ByteUtil;

public class CiphertextHeaderV2
extends CiphertextHeader {
    private static final int VERSION = -2;
    private static final int HMAC_SIZE = 32;
    private Function<String, SecretKey> keyLookup;

    public CiphertextHeaderV2(byte[] nonce, String keyName) {
        super(nonce, keyName);
        if (keyName == null || keyName.isEmpty()) {
            throw new IllegalArgumentException("Key name is required");
        }
    }

    public void setKeyLookup(Function<String, SecretKey> keyLookup) {
        this.keyLookup = keyLookup;
    }

    @Override
    public byte[] encode() {
        SecretKey key;
        SecretKey secretKey = key = this.keyLookup != null ? this.keyLookup.apply(this.keyName) : null;
        if (key == null) {
            throw new IllegalStateException("Could not resolve secret key to generate header HMAC");
        }
        return this.encode(key);
    }

    public byte[] encode(SecretKey hmacKey) {
        if (hmacKey == null) {
            throw new IllegalArgumentException("Secret key cannot be null");
        }
        ByteBuffer bb = ByteBuffer.allocate(this.length);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(-2);
        bb.put(ByteUtil.toBytes(this.keyName));
        bb.put((byte)0);
        bb.put(ByteUtil.toUnsignedByte(this.nonce.length));
        bb.put(this.nonce);
        bb.put(CiphertextHeaderV2.hmac(bb.array(), 0, bb.limit() - 32));
        return bb.array();
    }

    @Override
    protected int computeLength() {
        return 4 + ByteUtil.toBytes(this.keyName).length + 2 + this.nonce.length + 32;
    }

    public static CiphertextHeaderV2 decode(byte[] data, Function<String, SecretKey> keyLookup) throws EncodingException {
        ByteBuffer bb = ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN);
        return CiphertextHeaderV2.decodeInternal(ByteBuffer.wrap(data).order(ByteOrder.BIG_ENDIAN), keyLookup, ByteBuffer2 -> bb.getInt(), ByteBuffer2 -> bb.get(), (ByteBuffer2, output) -> bb.get((byte[])output));
    }

    public static CiphertextHeaderV2 decode(InputStream input, Function<String, SecretKey> keyLookup) throws EncodingException, StreamException {
        return CiphertextHeaderV2.decodeInternal(input, keyLookup, ByteUtil::readInt, CiphertextHeaderV2::readByte, CiphertextHeaderV2::readInto);
    }

    private static <T> CiphertextHeaderV2 decodeInternal(T source, Function<String, SecretKey> keyLookup, Function<T, Integer> readIntFn, Function<T, Byte> readByteFn, BiConsumer<T, byte[]> readBytesConsumer) {
        byte[] hmac;
        byte[] nonce;
        SecretKey key;
        String keyName;
        try {
            int version = readIntFn.apply(source);
            if (version != -2) {
                throw new EncodingException("Unsupported ciphertext header version");
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream(100);
            byte b = 0;
            int count = 0;
            while ((b = readByteFn.apply(source).byteValue()) != 0) {
                out.write(b);
                if (out.size() > 500) {
                    throw new EncodingException("Bad ciphertext header: maximum nonce length exceeded");
                }
                ++count;
            }
            keyName = ByteUtil.toString(out.toByteArray(), 0, count);
            key = keyLookup.apply(keyName);
            if (key == null) {
                throw new IllegalStateException("Symbolic key name mentioned in header was not found");
            }
            int nonceLen = ByteUtil.toInt(readByteFn.apply(source));
            nonce = new byte[nonceLen];
            readBytesConsumer.accept(source, nonce);
            hmac = new byte[32];
            readBytesConsumer.accept(source, hmac);
        }
        catch (IndexOutOfBoundsException | BufferUnderflowException e) {
            throw new EncodingException("Bad ciphertext header");
        }
        CiphertextHeaderV2 header = new CiphertextHeaderV2(nonce, keyName);
        byte[] encoded = header.encode(key);
        if (!CiphertextHeaderV2.arraysEqual(hmac, 0, encoded, encoded.length - 32, 32)) {
            throw new EncodingException("Ciphertext header HMAC verification failed");
        }
        header.setKeyLookup(keyLookup);
        return header;
    }

    private static byte[] hmac(byte[] input, int offset, int length) {
        HMac hmac = new HMac((Digest)new SHA256Digest());
        byte[] output = new byte[32];
        hmac.update(input, offset, length);
        hmac.doFinal(output, 0);
        return output;
    }

    private static void readInto(InputStream input, byte[] output) {
        try {
            input.read(output);
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
    }

    private static byte readByte(InputStream input) {
        try {
            return (byte)input.read();
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
    }

    private static boolean arraysEqual(byte[] a, int aOff, byte[] b, int bOff, int length) {
        if (length + aOff > a.length || length + bOff > b.length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (a[i + aOff] == b[i + bOff]) continue;
            return false;
        }
        return true;
    }
}

