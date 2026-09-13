/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.GPFlags;
import com.google.common.base.Charsets;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;

public class EncodeUtils {
    private EncodeUtils() {
    }

    public static String decode(ByteBuffer bytes, int length, GPFlags flags) throws IOException {
        if (bytes.remaining() < length) {
            throw new IOException("Only " + bytes.remaining() + " bytes exist in the buffer, but length is " + length + ".");
        }
        byte[] stringBytes = new byte[length];
        bytes.get(stringBytes);
        return EncodeUtils.decode(stringBytes, flags);
    }

    public static String decode(byte[] data, GPFlags flags) {
        return EncodeUtils.decode(data, EncodeUtils.flagsCharset(flags));
    }

    private static String decode(byte[] data, Charset charset) {
        try {
            return charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(data)).toString();
        }
        catch (CharacterCodingException e2) {
            if (charset.equals(Charsets.US_ASCII)) {
                return EncodeUtils.decode(data, Charsets.UTF_8);
            }
            return charset.decode(ByteBuffer.wrap(data)).toString();
        }
    }

    public static byte[] encode(String name, GPFlags flags) {
        Charset charset = EncodeUtils.flagsCharset(flags);
        ByteBuffer bytes = charset.encode(name);
        byte[] result = new byte[bytes.remaining()];
        bytes.get(result);
        return result;
    }

    private static Charset flagsCharset(GPFlags flags) {
        if (flags.isUtf8FileName()) {
            return Charsets.UTF_8;
        }
        return Charsets.US_ASCII;
    }

    public static boolean canAsciiEncode(String text) {
        return Charsets.US_ASCII.newEncoder().canEncode(text);
    }
}

