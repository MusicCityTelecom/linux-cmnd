/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.util.ByteUtil;

@Deprecated
public class CiphertextHeader {
    protected static final int MAX_NONCE_LEN = 255;
    protected static final int MAX_KEYNAME_LEN = 500;
    protected final byte[] nonce;
    protected String keyName;
    protected int length;

    public CiphertextHeader(byte[] nonce) {
        this(nonce, null);
    }

    public CiphertextHeader(byte[] nonce, String keyName) {
        if (nonce.length > 255) {
            throw new IllegalArgumentException("Nonce exceeds size limit in bytes (255)");
        }
        if (keyName != null && ByteUtil.toBytes(keyName).length > 500) {
            throw new IllegalArgumentException("Key name exceeds size limit in bytes (500)");
        }
        this.nonce = nonce;
        this.keyName = keyName;
        this.length = this.computeLength();
    }

    public int getLength() {
        return this.length;
    }

    public byte[] getNonce() {
        return this.nonce;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public byte[] encode() {
        ByteBuffer bb = ByteBuffer.allocate(this.length);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(this.length);
        bb.putInt(this.nonce.length);
        bb.put(this.nonce);
        if (this.keyName != null) {
            byte[] b = this.keyName.getBytes();
            bb.putInt(b.length);
            bb.put(b);
        }
        return bb.array();
    }

    protected int computeLength() {
        int len = 8 + this.nonce.length;
        if (this.keyName != null) {
            len += 4 + this.keyName.getBytes().length;
        }
        return len;
    }

    public static CiphertextHeader decode(byte[] data) throws EncodingException {
        byte[] nonce;
        ByteBuffer bb = ByteBuffer.wrap(data);
        bb.order(ByteOrder.BIG_ENDIAN);
        int length = bb.getInt();
        if (length < 0) {
            throw new EncodingException("Bad ciphertext header");
        }
        int nonceLen = 0;
        try {
            nonceLen = bb.getInt();
            if (nonceLen > 255) {
                throw new EncodingException("Bad ciphertext header: maximum nonce length exceeded");
            }
            nonce = new byte[nonceLen];
            bb.get(nonce);
        }
        catch (IndexOutOfBoundsException | BufferUnderflowException e) {
            throw new EncodingException("Bad ciphertext header");
        }
        String keyName = null;
        if (length > nonce.length + 8) {
            int keyLen = 0;
            try {
                keyLen = bb.getInt();
                if (keyLen > 500) {
                    throw new EncodingException("Bad ciphertext header: maximum key length exceeded");
                }
                byte[] b = new byte[keyLen];
                bb.get(b);
                keyName = new String(b);
            }
            catch (IndexOutOfBoundsException | BufferUnderflowException e) {
                throw new EncodingException("Bad ciphertext header");
            }
        }
        return new CiphertextHeader(nonce, keyName);
    }

    public static CiphertextHeader decode(InputStream input) throws EncodingException, StreamException {
        byte[] nonce;
        int length = ByteUtil.readInt(input);
        if (length < 0) {
            throw new EncodingException("Bad ciphertext header");
        }
        int nonceLen = 0;
        try {
            nonceLen = ByteUtil.readInt(input);
            if (nonceLen > 255) {
                throw new EncodingException("Bad ciphertext header: maximum nonce size exceeded");
            }
            nonce = new byte[nonceLen];
            input.read(nonce);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new EncodingException("Bad ciphertext header");
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
        String keyName = null;
        if (length > nonce.length + 8) {
            byte[] b;
            int keyLen = 0;
            try {
                keyLen = ByteUtil.readInt(input);
                if (keyLen > 500) {
                    throw new EncodingException("Bad ciphertext header: maximum key length exceeded");
                }
                b = new byte[keyLen];
                input.read(b);
            }
            catch (ArrayIndexOutOfBoundsException e) {
                throw new EncodingException("Bad ciphertext header");
            }
            catch (IOException e) {
                throw new StreamException(e);
            }
            keyName = new String(b);
        }
        return new CiphertextHeader(nonce, keyName);
    }
}

