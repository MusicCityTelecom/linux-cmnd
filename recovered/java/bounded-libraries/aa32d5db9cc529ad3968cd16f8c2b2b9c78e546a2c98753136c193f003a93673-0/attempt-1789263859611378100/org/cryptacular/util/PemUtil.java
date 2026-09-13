/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.regex.Pattern;
import org.cryptacular.codec.Base64Decoder;
import org.cryptacular.util.ByteUtil;

public final class PemUtil {
    public static final int LINE_LENGTH = 64;
    public static final String HEADER_BEGIN = "-----BEGIN";
    public static final String FOOTER_END = "-----END";
    public static final String PROC_TYPE = "Proc-Type:";
    public static final String DEK_INFO = "DEK-Info:";
    private static final Pattern PEM_SPLITTER = Pattern.compile("-----(?:BEGIN|END) [A-Z ]+-----");
    private static final Pattern LINE_SPLITTER = Pattern.compile("[\r\n]+");

    private PemUtil() {
    }

    public static boolean isPem(byte[] data) {
        String start = new String(data, 0, 10, ByteUtil.ASCII_CHARSET).trim();
        if (!start.startsWith(HEADER_BEGIN) && !start.startsWith(PROC_TYPE)) {
            for (int i = 0; i < 64; ++i) {
                if (PemUtil.isBase64Char(data[i])) continue;
                if (i > 61) {
                    if (data[i] == 61) continue;
                    return false;
                }
                return false;
            }
        }
        return true;
    }

    public static boolean isBase64Char(byte b) {
        return b >= 47 && b <= 122 && (b <= 57 || b >= 65) && (b <= 90 || b >= 97) || b == 43;
    }

    public static byte[] decode(byte[] pem) {
        return PemUtil.decode(new String(pem, ByteUtil.ASCII_CHARSET));
    }

    public static byte[] decode(String pem) {
        Base64Decoder decoder = new Base64Decoder();
        CharBuffer buffer = CharBuffer.allocate(pem.length());
        ByteBuffer output = ByteBuffer.allocate(pem.length() * 3 / 4);
        for (String object : PEM_SPLITTER.split(pem)) {
            buffer.clear();
            for (String line : LINE_SPLITTER.split(object)) {
                if (line.startsWith(DEK_INFO) || line.startsWith(PROC_TYPE)) continue;
                buffer.append(line);
            }
            buffer.flip();
            decoder.decode(buffer, output);
            decoder.finalize(output);
        }
        output.flip();
        return ByteUtil.toArray(output);
    }
}

