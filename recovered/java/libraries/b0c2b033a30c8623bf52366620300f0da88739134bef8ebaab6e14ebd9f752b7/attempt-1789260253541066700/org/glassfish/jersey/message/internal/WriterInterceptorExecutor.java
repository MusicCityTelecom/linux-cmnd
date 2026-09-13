/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionManagerSupplier;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.internal.InterceptorExecutor;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.glassfish.jersey.message.internal.MsgTraceEvent;
import org.glassfish.jersey.message.internal.TracingLogger;

public final class WriterInterceptorExecutor
extends InterceptorExecutor<WriterInterceptor>
implements WriterInterceptorContext,
InjectionManagerSupplier {
    private static final Logger LOGGER = Logger.getLogger(WriterInterceptorExecutor.class.getName());
    private OutputStream outputStream;
    private final MultivaluedMap<String, Object> headers;
    private Object entity;
    private final Iterator<WriterInterceptor> iterator;
    private int processedCount;
    private final InjectionManager injectionManager;

    public WriterInterceptorExecutor(Object entity, Class<?> rawType, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> headers, PropertiesDelegate propertiesDelegate, OutputStream entityStream, MessageBodyWorkers workers, Iterable<WriterInterceptor> writerInterceptors, InjectionManager injectionManager) {
        super(rawType, type, annotations, mediaType, propertiesDelegate);
        this.entity = entity;
        this.headers = headers;
        this.outputStream = entityStream;
        this.injectionManager = injectionManager;
        List effectiveInterceptors = StreamSupport.stream(writerInterceptors.spliterator(), false).collect(Collectors.toList());
        effectiveInterceptors.add(new TerminalWriterInterceptor(workers));
        this.iterator = effectiveInterceptors.iterator();
        this.processedCount = 0;
    }

    private WriterInterceptor getNextInterceptor() {
        if (!this.iterator.hasNext()) {
            return null;
        }
        return this.iterator.next();
    }

    @Override
    public void proceed() throws IOException {
        WriterInterceptor nextInterceptor = this.getNextInterceptor();
        if (nextInterceptor == null) {
            throw new ProcessingException(LocalizationMessages.ERROR_INTERCEPTOR_WRITER_PROCEED());
        }
        this.traceBefore(nextInterceptor, MsgTraceEvent.WI_BEFORE);
        try {
            nextInterceptor.aroundWriteTo(this);
        }
        finally {
            ++this.processedCount;
            this.traceAfter(nextInterceptor, MsgTraceEvent.WI_AFTER);
        }
    }

    @Override
    public Object getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(Object entity) {
        this.entity = entity;
    }

    @Override
    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    @Override
    public void setOutputStream(OutputStream os) {
        this.outputStream = os;
    }

    @Override
    public MultivaluedMap<String, Object> getHeaders() {
        return this.headers;
    }

    int getProcessedCount() {
        return this.processedCount;
    }

    @Override
    public InjectionManager getInjectionManager() {
        return this.injectionManager;
    }

    private static class UnCloseableOutputStream
    extends OutputStream {
        private final OutputStream original;
        private final MessageBodyWriter writer;

        private UnCloseableOutputStream(OutputStream original, MessageBodyWriter writer) {
            this.original = original;
            this.writer = writer;
        }

        @Override
        public void write(int i) throws IOException {
            this.original.write(i);
        }

        @Override
        public void write(byte[] b) throws IOException {
            this.original.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            this.original.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            this.original.flush();
        }

        @Override
        public void close() throws IOException {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, LocalizationMessages.MBW_TRYING_TO_CLOSE_STREAM(this.writer.getClass()));
            }
        }
    }

    private class TerminalWriterInterceptor
    implements WriterInterceptor {
        private final MessageBodyWorkers workers;

        TerminalWriterInterceptor(MessageBodyWorkers workers) {
            this.workers = workers;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void aroundWriteTo(WriterInterceptorContext context) throws WebApplicationException, IOException {
            WriterInterceptorExecutor.this.processedCount--;
            WriterInterceptorExecutor.this.traceBefore(null, MsgTraceEvent.WI_BEFORE);
            try {
                MessageBodyWriter<?> writer;
                TracingLogger tracingLogger = WriterInterceptorExecutor.this.getTracingLogger();
                if (tracingLogger.isLogEnabled(MsgTraceEvent.MBW_FIND)) {
                    tracingLogger.log(MsgTraceEvent.MBW_FIND, context.getType().getName(), context.getGenericType() instanceof Class ? ((Class)context.getGenericType()).getName() : context.getGenericType(), context.getMediaType(), Arrays.toString(context.getAnnotations()));
                }
                if ((writer = this.workers.getMessageBodyWriter(context.getType(), context.getGenericType(), context.getAnnotations(), context.getMediaType(), WriterInterceptorExecutor.this)) == null) {
                    LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_NOTFOUND_MESSAGEBODYWRITER(context.getMediaType(), context.getType(), context.getGenericType()));
                    throw new MessageBodyProviderNotFoundException(LocalizationMessages.ERROR_NOTFOUND_MESSAGEBODYWRITER(context.getMediaType(), context.getType(), context.getGenericType()));
                }
                this.invokeWriteTo(context, writer);
            }
            finally {
                WriterInterceptorExecutor.this.clearLastTracedInterceptor();
                WriterInterceptorExecutor.this.traceAfter(null, MsgTraceEvent.WI_AFTER);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void invokeWriteTo(WriterInterceptorContext context, MessageBodyWriter writer) throws WebApplicationException, IOException {
            TracingLogger tracingLogger = WriterInterceptorExecutor.this.getTracingLogger();
            long timestamp = tracingLogger.timestamp(MsgTraceEvent.MBW_WRITE_TO);
            UnCloseableOutputStream entityStream = new UnCloseableOutputStream(context.getOutputStream(), writer);
            try {
                writer.writeTo(context.getEntity(), context.getType(), context.getGenericType(), context.getAnnotations(), context.getMediaType(), context.getHeaders(), entityStream);
            }
            catch (Throwable throwable) {
                tracingLogger.logDuration(MsgTraceEvent.MBW_WRITE_TO, timestamp, writer);
                throw throwable;
            }
            tracingLogger.logDuration(MsgTraceEvent.MBW_WRITE_TO, timestamp, writer);
        }
    }
}

