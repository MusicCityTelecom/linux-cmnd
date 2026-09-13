/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.Configuration
 *  javax.validation.Validation
 *  org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
 *  org.springframework.boot.context.event.ApplicationFailedEvent
 *  org.springframework.boot.context.event.ApplicationReadyEvent
 *  org.springframework.boot.context.event.SpringApplicationEvent
 *  org.springframework.context.ApplicationListener
 *  org.springframework.core.NativeDetector
 *  org.springframework.core.annotation.Order
 *  org.springframework.format.support.DefaultFormattingConversionService
 *  org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
 *  org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
 */
package org.springframework.boot.autoconfigure;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.validation.Configuration;
import javax.validation.Validation;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.NativeDetector;
import org.springframework.core.annotation.Order;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;

@Order(value=-2147483627)
public class BackgroundPreinitializer
implements ApplicationListener<SpringApplicationEvent> {
    public static final String IGNORE_BACKGROUNDPREINITIALIZER_PROPERTY_NAME = "spring.backgroundpreinitializer.ignore";
    private static final AtomicBoolean preinitializationStarted = new AtomicBoolean();
    private static final CountDownLatch preinitializationComplete = new CountDownLatch(1);
    private static final boolean ENABLED = !Boolean.getBoolean("spring.backgroundpreinitializer.ignore") && !NativeDetector.inNativeImage() && Runtime.getRuntime().availableProcessors() > 1;

    public void onApplicationEvent(SpringApplicationEvent event) {
        if (!ENABLED) {
            return;
        }
        if (event instanceof ApplicationEnvironmentPreparedEvent && preinitializationStarted.compareAndSet(false, true)) {
            this.performPreinitialization();
        }
        if ((event instanceof ApplicationReadyEvent || event instanceof ApplicationFailedEvent) && preinitializationStarted.get()) {
            try {
                preinitializationComplete.await();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void performPreinitialization() {
        try {
            Thread thread = new Thread(new Runnable(){

                @Override
                public void run() {
                    this.runSafely(new ConversionServiceInitializer());
                    this.runSafely(new ValidationInitializer());
                    this.runSafely(new MessageConverterInitializer());
                    this.runSafely(new JacksonInitializer());
                    this.runSafely(new CharsetInitializer());
                    preinitializationComplete.countDown();
                }

                public void runSafely(Runnable runnable) {
                    try {
                        runnable.run();
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
            }, "background-preinit");
            thread.start();
        }
        catch (Exception ex) {
            preinitializationComplete.countDown();
        }
    }

    private static class CharsetInitializer
    implements Runnable {
        private CharsetInitializer() {
        }

        @Override
        public void run() {
            StandardCharsets.UTF_8.name();
        }
    }

    private static class ConversionServiceInitializer
    implements Runnable {
        private ConversionServiceInitializer() {
        }

        @Override
        public void run() {
            new DefaultFormattingConversionService();
        }
    }

    private static class JacksonInitializer
    implements Runnable {
        private JacksonInitializer() {
        }

        @Override
        public void run() {
            Jackson2ObjectMapperBuilder.json().build();
        }
    }

    private static class ValidationInitializer
    implements Runnable {
        private ValidationInitializer() {
        }

        @Override
        public void run() {
            Configuration configuration = Validation.byDefaultProvider().configure();
            configuration.buildValidatorFactory().getValidator();
        }
    }

    private static class MessageConverterInitializer
    implements Runnable {
        private MessageConverterInitializer() {
        }

        @Override
        public void run() {
            new AllEncompassingFormHttpMessageConverter();
        }
    }
}

