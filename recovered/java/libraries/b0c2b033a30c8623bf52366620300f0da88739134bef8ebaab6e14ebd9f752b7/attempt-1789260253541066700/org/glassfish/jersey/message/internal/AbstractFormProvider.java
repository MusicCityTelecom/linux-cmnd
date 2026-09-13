/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.message.internal.AbstractMessageReaderWriterProvider;
import org.glassfish.jersey.message.internal.ReaderWriter;

public abstract class AbstractFormProvider<T>
extends AbstractMessageReaderWriterProvider<T> {
    public <M extends MultivaluedMap<String, String>> M readFrom(M map, MediaType mediaType, boolean decode, InputStream entityStream) throws IOException {
        String encoded = AbstractFormProvider.readFromAsString(entityStream, mediaType);
        String charsetName = ReaderWriter.getCharset(mediaType).name();
        StringTokenizer tokenizer = new StringTokenizer(encoded, "&");
        try {
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                int idx = token.indexOf(61);
                if (idx < 0) {
                    map.add((String)(decode ? URLDecoder.decode(token, charsetName) : token), null);
                    continue;
                }
                if (idx <= 0) continue;
                if (decode) {
                    map.add((String)URLDecoder.decode(token.substring(0, idx), charsetName), (String)URLDecoder.decode(token.substring(idx + 1), charsetName));
                    continue;
                }
                map.add((String)token.substring(0, idx), (String)token.substring(idx + 1));
            }
            return map;
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    public <M extends MultivaluedMap<String, String>> void writeTo(M t, MediaType mediaType, OutputStream entityStream) throws IOException {
        String charsetName = ReaderWriter.getCharset(mediaType).name();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry e : t.entrySet()) {
            for (String value : (List)e.getValue()) {
                if (sb.length() > 0) {
                    sb.append('&');
                }
                sb.append(URLEncoder.encode((String)e.getKey(), charsetName));
                if (value == null) continue;
                sb.append('=');
                sb.append(URLEncoder.encode(value, charsetName));
            }
        }
        AbstractFormProvider.writeToAsString(sb.toString(), entityStream, mediaType);
    }
}

