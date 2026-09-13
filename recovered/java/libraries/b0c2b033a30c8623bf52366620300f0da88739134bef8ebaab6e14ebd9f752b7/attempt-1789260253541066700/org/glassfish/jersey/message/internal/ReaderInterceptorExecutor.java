/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionManagerSupplier;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.internal.CompletableReader;
import org.glassfish.jersey.message.internal.EntityInputStream;
import org.glassfish.jersey.message.internal.InterceptorExecutor;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.glassfish.jersey.message.internal.MsgTraceEvent;
import org.glassfish.jersey.message.internal.TracingLogger;

public final class ReaderInterceptorExecutor
extends InterceptorExecutor<ReaderInterceptor>
implements ReaderInterceptorContext,
InjectionManagerSupplier {
    private static final Logger LOGGER = Logger.getLogger(ReaderInterceptorExecutor.class.getName());
    private final MultivaluedMap<String, String> headers;
    private final Iterator<ReaderInterceptor> interceptors;
    private final MessageBodyWorkers workers;
    private final boolean translateNce;
    private final InjectionManager injectionManager;
    private InputStream inputStream;
    private int processedCount;

    ReaderInterceptorExecutor(Class<?> rawType, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> headers, PropertiesDelegate propertiesDelegate, InputStream inputStream, MessageBodyWorkers workers, Iterable<ReaderInterceptor> readerInterceptors, boolean translateNce, InjectionManager injectionManager) {
        super(rawType, type, annotations, mediaType, propertiesDelegate);
        this.headers = headers;
        this.inputStream = inputStream;
        this.workers = workers;
        this.translateNce = translateNce;
        this.injectionManager = injectionManager;
        List effectiveInterceptors = StreamSupport.stream(readerInterceptors.spliterator(), false).collect(Collectors.toList());
        effectiveInterceptors.add(new TerminalReaderInterceptor());
        this.interceptors = effectiveInterceptors.iterator();
        this.processedCount = 0;
    }

    @Override
    public Object proceed() throws IOException {
        if (!this.interceptors.hasNext()) {
            throw new ProcessingException(LocalizationMessages.ERROR_INTERCEPTOR_READER_PROCEED());
        }
        ReaderInterceptor interceptor = this.interceptors.next();
        this.traceBefore(interceptor, MsgTraceEvent.RI_BEFORE);
        try {
            Object object = interceptor.aroundReadFrom(this);
            return object;
        }
        finally {
            ++this.processedCount;
            this.traceAfter(interceptor, MsgTraceEvent.RI_AFTER);
        }
    }

    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }

    @Override
    public void setInputStream(InputStream is) {
        this.inputStream = is;
    }

    @Override
    public MultivaluedMap<String, String> getHeaders() {
        return this.headers;
    }

    int getProcessedCount() {
        return this.processedCount;
    }

    @Override
    public InjectionManager getInjectionManager() {
        return this.injectionManager;
    }

    public static InputStream closeableInputStream(InputStream inputStream) {
        if (inputStream instanceof UnCloseableInputStream) {
            return ((UnCloseableInputStream)inputStream).unwrap();
        }
        return inputStream;
    }

    private static class UnCloseableInputStream
    extends InputStream {
        private final InputStream original;
        private final MessageBodyReader reader;

        private UnCloseableInputStream(InputStream original, MessageBodyReader reader) {
            this.original = original;
            this.reader = reader;
        }

        @Override
        public int read() throws IOException {
            return this.original.read();
        }

        @Override
        public int read(byte[] b) throws IOException {
            return this.original.read(b);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return this.original.read(b, off, len);
        }

        @Override
        public long skip(long l) throws IOException {
            return this.original.skip(l);
        }

        @Override
        public int available() throws IOException {
            return this.original.available();
        }

        @Override
        public synchronized void mark(int i) {
            this.original.mark(i);
        }

        @Override
        public synchronized void reset() throws IOException {
            this.original.reset();
        }

        @Override
        public boolean markSupported() {
            return this.original.markSupported();
        }

        @Override
        public void close() throws IOException {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, LocalizationMessages.MBR_TRYING_TO_CLOSE_STREAM(this.reader.getClass()));
            }
        }

        private InputStream unwrap() {
            return this.original;
        }
    }

    private class TerminalReaderInterceptor
    implements ReaderInterceptor {
        private TerminalReaderInterceptor() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
            ReaderInterceptorExecutor.this.processedCount--;
            ReaderInterceptorExecutor.this.traceBefore(null, MsgTraceEvent.RI_BEFORE);
            try {
                TracingLogger tracingLogger = ReaderInterceptorExecutor.this.getTracingLogger();
                if (tracingLogger.isLogEnabled(MsgTraceEvent.MBR_FIND)) {
                    tracingLogger.log(MsgTraceEvent.MBR_FIND, context.getType().getName(), context.getGenericType() instanceof Class ? ((Class)context.getGenericType()).getName() : context.getGenericType(), String.valueOf(context.getMediaType()), Arrays.toString(context.getAnnotations()));
                }
                MessageBodyReader<?> bodyReader = ReaderInterceptorExecutor.this.workers.getMessageBodyReader(context.getType(), context.getGenericType(), context.getAnnotations(), context.getMediaType(), ReaderInterceptorExecutor.this);
                EntityInputStream input = new EntityInputStream(context.getInputStream());
                if (bodyReader == null) {
                    if (input.isEmpty() && !context.getHeaders().containsKey("Content-Type")) {
                        Object var5_5 = null;
                        return var5_5;
                    }
                    LOGGER.log(Level.FINE, LocalizationMessages.ERROR_NOTFOUND_MESSAGEBODYREADER(context.getMediaType(), context.getType(), context.getGenericType()));
                    throw new MessageBodyProviderNotFoundException(LocalizationMessages.ERROR_NOTFOUND_MESSAGEBODYREADER(context.getMediaType(), context.getType(), context.getGenericType()));
                }
                Object entity = this.invokeReadFrom(context, bodyReader, input);
                if (bodyReader instanceof CompletableReader) {
                    entity = ((CompletableReader)((Object)bodyReader)).complete(entity);
                }
                Object object = entity;
                return object;
            }
            finally {
                ReaderInterceptorExecutor.this.clearLastTracedInterceptor();
                ReaderInterceptorExecutor.this.traceAfter(null, MsgTraceEvent.RI_AFTER);
            }
        }

        private Object invokeReadFrom(ReaderInterceptorContext context, MessageBodyReader reader, EntityInputStream input) throws WebApplicationException, IOException {
            Object obj;
            TracingLogger tracingLogger = ReaderInterceptorExecutor.this.getTracingLogger();
            long timestamp = tracingLogger.timestamp(MsgTraceEvent.MBR_READ_FROM);
            UnCloseableInputStream stream = new UnCloseableInputStream(input, reader);
            try {
                obj = reader.readFrom(context.getType(), context.getGenericType(), context.getAnnotations(), context.getMediaType(), context.getHeaders(), stream);
            }
            catch (NoContentException ex) {
                try {
                    if (ReaderInterceptorExecutor.this.translateNce) {
                        throw new BadRequestException(ex);
                    }
                    throw ex;
                }
                catch (Throwable throwable) {
                    tracingLogger.logDuration(MsgTraceEvent.MBR_READ_FROM, timestamp, reader);
                    throw throwable;
                }
            }
            tracingLogger.logDuration(MsgTraceEvent.MBR_READ_FROM, timestamp, reader);
            return obj;
        }
    }
}

