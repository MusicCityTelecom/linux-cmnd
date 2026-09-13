/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.BlockCipher
 *  org.bouncycastle.crypto.BufferedBlockCipher
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.modes.AEADBlockCipher
 *  org.bouncycastle.crypto.paddings.BlockCipherPadding
 *  org.bouncycastle.crypto.paddings.PKCS7Padding
 *  org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher
 *  org.bouncycastle.crypto.params.AEADParameters
 *  org.bouncycastle.crypto.params.KeyParameter
 *  org.bouncycastle.crypto.params.ParametersWithIV
 */
package org.cryptacular.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.cryptacular.CiphertextHeader;
import org.cryptacular.CiphertextHeaderV2;
import org.cryptacular.CryptoException;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.adapter.AEADBlockCipherAdapter;
import org.cryptacular.adapter.BlockCipherAdapter;
import org.cryptacular.adapter.BufferedBlockCipherAdapter;
import org.cryptacular.generator.Nonce;

public final class CipherUtil {
    private static final int MAC_SIZE_BITS = 128;

    private CipherUtil() {
    }

    public static byte[] encrypt(AEADBlockCipher cipher, SecretKey key, Nonce nonce, byte[] data) throws CryptoException {
        byte[] iv = nonce.generate();
        byte[] header = new CiphertextHeaderV2(iv, "1").encode(key);
        cipher.init(true, (CipherParameters)new AEADParameters(new KeyParameter(key.getEncoded()), 128, iv, header));
        return CipherUtil.encrypt(new AEADBlockCipherAdapter(cipher), header, data);
    }

    public static void encrypt(AEADBlockCipher cipher, SecretKey key, Nonce nonce, InputStream input, OutputStream output) throws CryptoException, StreamException {
        byte[] iv = nonce.generate();
        byte[] header = new CiphertextHeaderV2(iv, "1").encode(key);
        cipher.init(true, (CipherParameters)new AEADParameters(new KeyParameter(key.getEncoded()), 128, iv, header));
        CipherUtil.writeHeader(header, output);
        CipherUtil.process(new AEADBlockCipherAdapter(cipher), input, output);
    }

    public static byte[] decrypt(AEADBlockCipher cipher, SecretKey key, byte[] data) throws CryptoException, EncodingException {
        CiphertextHeader header = CipherUtil.decodeHeader(data, (String String2) -> key);
        byte[] nonce = header.getNonce();
        byte[] hbytes = header.encode();
        cipher.init(false, (CipherParameters)new AEADParameters(new KeyParameter(key.getEncoded()), 128, nonce, hbytes));
        return CipherUtil.decrypt(new AEADBlockCipherAdapter(cipher), data, header.getLength());
    }

    public static void decrypt(AEADBlockCipher cipher, SecretKey key, InputStream input, OutputStream output) throws CryptoException, EncodingException, StreamException {
        CiphertextHeader header = CipherUtil.decodeHeader(input, (String String2) -> key);
        byte[] nonce = header.getNonce();
        byte[] hbytes = header.encode();
        cipher.init(false, (CipherParameters)new AEADParameters(new KeyParameter(key.getEncoded()), 128, nonce, hbytes));
        CipherUtil.process(new AEADBlockCipherAdapter(cipher), input, output);
    }

    public static byte[] encrypt(BlockCipher cipher, SecretKey key, Nonce nonce, byte[] data) throws CryptoException {
        byte[] iv = nonce.generate();
        byte[] header = new CiphertextHeaderV2(iv, "1").encode(key);
        PaddedBufferedBlockCipher padded = new PaddedBufferedBlockCipher(cipher, (BlockCipherPadding)new PKCS7Padding());
        padded.init(true, (CipherParameters)new ParametersWithIV((CipherParameters)new KeyParameter(key.getEncoded()), iv));
        return CipherUtil.encrypt(new BufferedBlockCipherAdapter((BufferedBlockCipher)padded), header, data);
    }

    public static void encrypt(BlockCipher cipher, SecretKey key, Nonce nonce, InputStream input, OutputStream output) throws CryptoException, StreamException {
        byte[] iv = nonce.generate();
        byte[] header = new CiphertextHeaderV2(iv, "1").encode(key);
        PaddedBufferedBlockCipher padded = new PaddedBufferedBlockCipher(cipher, (BlockCipherPadding)new PKCS7Padding());
        padded.init(true, (CipherParameters)new ParametersWithIV((CipherParameters)new KeyParameter(key.getEncoded()), iv));
        CipherUtil.writeHeader(header, output);
        CipherUtil.process(new BufferedBlockCipherAdapter((BufferedBlockCipher)padded), input, output);
    }

    public static byte[] decrypt(BlockCipher cipher, SecretKey key, byte[] data) throws CryptoException, EncodingException {
        CiphertextHeader header = CipherUtil.decodeHeader(data, (String String2) -> key);
        PaddedBufferedBlockCipher padded = new PaddedBufferedBlockCipher(cipher, (BlockCipherPadding)new PKCS7Padding());
        padded.init(false, (CipherParameters)new ParametersWithIV((CipherParameters)new KeyParameter(key.getEncoded()), header.getNonce()));
        return CipherUtil.decrypt(new BufferedBlockCipherAdapter((BufferedBlockCipher)padded), data, header.getLength());
    }

    public static void decrypt(BlockCipher cipher, SecretKey key, InputStream input, OutputStream output) throws CryptoException, EncodingException, StreamException {
        CiphertextHeader header = CipherUtil.decodeHeader(input, (String String2) -> key);
        PaddedBufferedBlockCipher padded = new PaddedBufferedBlockCipher(cipher, (BlockCipherPadding)new PKCS7Padding());
        padded.init(false, (CipherParameters)new ParametersWithIV((CipherParameters)new KeyParameter(key.getEncoded()), header.getNonce()));
        CipherUtil.process(new BufferedBlockCipherAdapter((BufferedBlockCipher)padded), input, output);
    }

    public static CiphertextHeader decodeHeader(byte[] data, Function<String, SecretKey> keyLookup) {
        try {
            return CiphertextHeaderV2.decode(data, keyLookup);
        }
        catch (EncodingException e) {
            return CiphertextHeader.decode(data);
        }
    }

    public static CiphertextHeader decodeHeader(InputStream in, Function<String, SecretKey> keyLookup) {
        CiphertextHeader header;
        try {
            if (in.markSupported()) {
                in.mark(4);
            }
            header = CiphertextHeaderV2.decode(in, keyLookup);
        }
        catch (EncodingException e) {
            try {
                in.reset();
            }
            catch (IOException ioe) {
                throw new StreamException("Stream error trying to process old header format: " + ioe.getMessage());
            }
            header = CiphertextHeader.decode(in);
        }
        return header;
    }

    private static byte[] encrypt(BlockCipherAdapter cipher, byte[] header, byte[] data) {
        int outSize = header.length + cipher.getOutputSize(data.length);
        byte[] output = new byte[outSize];
        System.arraycopy(header, 0, output, 0, header.length);
        int outOff = header.length;
        outOff += cipher.processBytes(data, 0, data.length, output, outOff);
        cipher.doFinal(output, outOff);
        cipher.reset();
        return output;
    }

    private static byte[] decrypt(BlockCipherAdapter cipher, byte[] data, int inOff) {
        int len = data.length - inOff;
        int outSize = cipher.getOutputSize(len);
        byte[] output = new byte[outSize];
        int outOff = cipher.processBytes(data, inOff, len, output, 0);
        outOff += cipher.doFinal(output, outOff);
        cipher.reset();
        if (outOff < output.length) {
            byte[] temp = new byte[outOff];
            System.arraycopy(output, 0, temp, 0, outOff);
            return temp;
        }
        return output;
    }

    private static void process(BlockCipherAdapter cipher, InputStream input, OutputStream output) {
        int inSize = 1024;
        int outSize = cipher.getOutputSize(1024);
        byte[] inBuf = new byte[1024];
        byte[] outBuf = new byte[outSize > 1024 ? outSize : 1024];
        try {
            int writeLen;
            int readLen;
            while ((readLen = input.read(inBuf)) > 0) {
                writeLen = cipher.processBytes(inBuf, 0, readLen, outBuf, 0);
                output.write(outBuf, 0, writeLen);
            }
            writeLen = cipher.doFinal(outBuf, 0);
            output.write(outBuf, 0, writeLen);
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
    }

    private static void writeHeader(byte[] header, OutputStream output) {
        try {
            output.write(header, 0, header.length);
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
    }
}

