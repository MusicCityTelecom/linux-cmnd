/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.x509.dn;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import javax.security.auth.x500.X500Principal;
import org.cryptacular.codec.HexEncoder;
import org.cryptacular.x509.dn.Attribute;
import org.cryptacular.x509.dn.AttributeType;
import org.cryptacular.x509.dn.NameFormatter;
import org.cryptacular.x509.dn.NameReader;
import org.cryptacular.x509.dn.RDN;
import org.cryptacular.x509.dn.RDNSequence;
import org.cryptacular.x509.dn.StandardAttributeType;

public class LdapNameFormatter
implements NameFormatter {
    public static final char RDN_SEPARATOR = ',';
    public static final char ATV_SEPARATOR = '+';
    public static final char ESCAPE_CHAR = '\\';
    public static final String RESERVED_CHARS = ",+\"\\<>;";
    private static final HexEncoder ENCODER = new HexEncoder();

    @Override
    public String format(X500Principal dn) {
        StringBuilder builder = new StringBuilder();
        RDNSequence sequence = NameReader.readX500Principal(dn);
        int i = 0;
        for (RDN rdn : sequence.backward()) {
            if (i++ > 0) {
                builder.append(',');
            }
            int j = 0;
            for (Attribute attr : rdn.getAttributes()) {
                if (j++ > 0) {
                    builder.append('+');
                }
                builder.append(attr.getType()).append('=');
                AttributeType type = attr.getType();
                if (type instanceof StandardAttributeType) {
                    LdapNameFormatter.escape(attr.getValue(), builder);
                    continue;
                }
                LdapNameFormatter.encode(attr.getValue(), builder);
            }
        }
        return builder.toString();
    }

    private static void escape(String value, StringBuilder output) {
        char c = value.charAt(0);
        if (c == ' ' || c == '#') {
            output.append('\\');
        }
        output.append(c);
        int nmax = value.length() - 1;
        for (int n = 1; n < nmax; ++n) {
            c = value.charAt(n);
            if (RESERVED_CHARS.indexOf(c) > -1) {
                output.append('\\');
            }
            output.append(c);
        }
        c = value.charAt(nmax);
        if (c == ' ') {
            output.append('\\');
        }
        output.append(c);
    }

    private static void encode(String value, StringBuilder output) {
        output.append('#');
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        CharBuffer out = CharBuffer.allocate(bytes.length * 2);
        ENCODER.encode(ByteBuffer.wrap(bytes), out);
        output.append(out.flip());
    }
}

