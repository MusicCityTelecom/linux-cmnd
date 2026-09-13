/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.sse;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.FactoryFinder;
import javax.ws.rs.sse.InboundSseEvent;

public interface SseEventSource
extends AutoCloseable {
    public void register(Consumer<InboundSseEvent> var1);

    public void register(Consumer<InboundSseEvent> var1, Consumer<Throwable> var2);

    public void register(Consumer<InboundSseEvent> var1, Consumer<Throwable> var2, Runnable var3);

    public static Builder target(WebTarget endpoint) {
        return Builder.newBuilder().target(endpoint);
    }

    public void open();

    public boolean isOpen();

    @Override
    default public void close() {
        this.close(5L, TimeUnit.SECONDS);
    }

    public boolean close(long var1, TimeUnit var3);

    public static abstract class Builder {
        public static final String JAXRS_DEFAULT_SSE_BUILDER_PROPERTY = "javax.ws.rs.sse.SseEventSource.Builder";
        private static final String JAXRS_DEFAULT_SSE_BUILDER = "org.glassfish.jersey.media.sse.internal.JerseySseEventSource$Builder";

        protected Builder() {
        }

        static Builder newBuilder() {
            try {
                Object delegate = FactoryFinder.find(JAXRS_DEFAULT_SSE_BUILDER_PROPERTY, JAXRS_DEFAULT_SSE_BUILDER, Builder.class);
                if (!(delegate instanceof Builder)) {
                    Class<Builder> pClass = Builder.class;
                    String classnameAsResource = pClass.getName().replace('.', '/') + ".class";
                    ClassLoader loader = pClass.getClassLoader();
                    if (loader == null) {
                        loader = ClassLoader.getSystemClassLoader();
                    }
                    URL targetTypeURL = loader.getResource(classnameAsResource);
                    throw new LinkageError("ClassCastException: attempting to cast" + delegate.getClass().getClassLoader().getResource(classnameAsResource) + " to " + targetTypeURL);
                }
                return (Builder)delegate;
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        protected abstract Builder target(WebTarget var1);

        public abstract Builder reconnectingEvery(long var1, TimeUnit var3);

        public abstract SseEventSource build();
    }
}

