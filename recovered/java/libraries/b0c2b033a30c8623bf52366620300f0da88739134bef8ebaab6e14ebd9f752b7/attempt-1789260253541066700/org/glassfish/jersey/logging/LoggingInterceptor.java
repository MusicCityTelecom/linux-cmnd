/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.logging;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.message.MessageUtils;

abstract class LoggingInterceptor
implements WriterInterceptor {
    static final String REQUEST_PREFIX = "> ";
    static final String RESPONSE_PREFIX = "< ";
    static final String ENTITY_LOGGER_PROPERTY = LoggingFeature.class.getName() + ".entityLogger";
    static final String LOGGING_ID_PROPERTY = LoggingFeature.class.getName() + ".id";
    private static final String NOTIFICATION_PREFIX = "* ";
    private static final MediaType TEXT_MEDIA_TYPE = new MediaType("text", "*");
    private static final MediaType APPLICATION_VND_API_JSON = new MediaType("application", "vnd.api+json");
    private static final Set<MediaType> READABLE_APP_MEDIA_TYPES = new HashSet<MediaType>(){
        {
            this.add(TEXT_MEDIA_TYPE);
            this.add(APPLICATION_VND_API_JSON);
            this.add(MediaType.APPLICATION_ATOM_XML_TYPE);
            this.add(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
            this.add(MediaType.APPLICATION_JSON_TYPE);
            this.add(MediaType.APPLICATION_SVG_XML_TYPE);
            this.add(MediaType.APPLICATION_XHTML_XML_TYPE);
            this.add(MediaType.APPLICATION_XML_TYPE);
        }
    };
    private static final Comparator<Map.Entry<String, List<String>>> COMPARATOR = new Comparator<Map.Entry<String, List<String>>>(){

        @Override
        public int compare(Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    };
    final Logger logger;
    final Level level;
    final AtomicLong _id = new AtomicLong(0L);
    final LoggingFeature.Verbosity verbosity;
    final int maxEntitySize;

    LoggingInterceptor(Logger logger, Level level, LoggingFeature.Verbosity verbosity, int maxEntitySize) {
        this.logger = logger;
        this.level = level;
        this.verbosity = verbosity;
        this.maxEntitySize = Math.max(0, maxEntitySize);
    }

    void log(StringBuilder b) {
        if (this.logger != null && this.logger.isLoggable(this.level)) {
            this.logger.log(this.level, b.toString());
        }
    }

    private StringBuilder prefixId(StringBuilder b, long id) {
        b.append(Long.toString(id)).append(" ");
        return b;
    }

    void printRequestLine(StringBuilder b, String note, long id, String method, URI uri) {
        this.prefixId(b, id).append(NOTIFICATION_PREFIX).append(note).append(" on thread ").append(Thread.currentThread().getName()).append("\n");
        this.prefixId(b, id).append(REQUEST_PREFIX).append(method).append(" ").append(uri.toASCIIString()).append("\n");
    }

    void printResponseLine(StringBuilder b, String note, long id, int status) {
        this.prefixId(b, id).append(NOTIFICATION_PREFIX).append(note).append(" on thread ").append(Thread.currentThread().getName()).append("\n");
        this.prefixId(b, id).append(RESPONSE_PREFIX).append(Integer.toString(status)).append("\n");
    }

    void printPrefixedHeaders(StringBuilder b, long id, String prefix, MultivaluedMap<String, String> headers) {
        for (Map.Entry<String, List<String>> headerEntry : this.getSortedHeaders(headers.entrySet())) {
            List<String> val = headerEntry.getValue();
            String header = headerEntry.getKey();
            if (val.size() == 1) {
                this.prefixId(b, id).append(prefix).append(header).append(": ").append((Object)val.get(0)).append("\n");
                continue;
            }
            StringBuilder sb = new StringBuilder();
            boolean add = false;
            for (String s : val) {
                if (add) {
                    sb.append(',');
                }
                add = true;
                sb.append((Object)s);
            }
            this.prefixId(b, id).append(prefix).append(header).append(": ").append(sb.toString()).append("\n");
        }
    }

    Set<Map.Entry<String, List<String>>> getSortedHeaders(Set<Map.Entry<String, List<String>>> headers) {
        TreeSet<Map.Entry<String, List<String>>> sortedHeaders = new TreeSet<Map.Entry<String, List<String>>>(COMPARATOR);
        sortedHeaders.addAll(headers);
        return sortedHeaders;
    }

    InputStream logInboundEntity(StringBuilder b, InputStream stream, Charset charset) throws IOException {
        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }
        stream.mark(this.maxEntitySize + 1);
        byte[] entity = new byte[this.maxEntitySize + 1];
        int entitySize = stream.read(entity);
        b.append(new String(entity, 0, Math.min(entitySize, this.maxEntitySize), charset));
        if (entitySize > this.maxEntitySize) {
            b.append("...more...");
        }
        b.append('\n');
        stream.reset();
        return stream;
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        LoggingStream stream = (LoggingStream)writerInterceptorContext.getProperty(ENTITY_LOGGER_PROPERTY);
        writerInterceptorContext.proceed();
        if (this.logger.isLoggable(this.level) && LoggingInterceptor.printEntity(this.verbosity, writerInterceptorContext.getMediaType()) && stream != null) {
            this.log(stream.getStringBuilder(MessageUtils.getCharset(writerInterceptorContext.getMediaType())));
        }
    }

    static boolean isReadable(MediaType mediaType) {
        if (mediaType != null) {
            for (MediaType readableMediaType : READABLE_APP_MEDIA_TYPES) {
                if (!readableMediaType.isCompatible(mediaType)) continue;
                return true;
            }
        }
        return false;
    }

    static boolean printEntity(LoggingFeature.Verbosity verbosity, MediaType mediaType) {
        return verbosity == LoggingFeature.Verbosity.PAYLOAD_ANY || verbosity == LoggingFeature.Verbosity.PAYLOAD_TEXT && LoggingInterceptor.isReadable(mediaType);
    }

    class LoggingStream
    extends FilterOutputStream {
        private final StringBuilder b;
        private final ByteArrayOutputStream baos;

        LoggingStream(StringBuilder b, OutputStream inner) {
            super(inner);
            this.baos = new ByteArrayOutputStream();
            this.b = b;
        }

        StringBuilder getStringBuilder(Charset charset) {
            byte[] entity = this.baos.toByteArray();
            this.b.append(new String(entity, 0, Math.min(entity.length, LoggingInterceptor.this.maxEntitySize), charset));
            if (entity.length > LoggingInterceptor.this.maxEntitySize) {
                this.b.append("...more...");
            }
            this.b.append('\n');
            return this.b;
        }

        @Override
        public void write(int i) throws IOException {
            if (this.baos.size() <= LoggingInterceptor.this.maxEntitySize) {
                this.baos.write(i);
            }
            this.out.write(i);
        }
    }
}

