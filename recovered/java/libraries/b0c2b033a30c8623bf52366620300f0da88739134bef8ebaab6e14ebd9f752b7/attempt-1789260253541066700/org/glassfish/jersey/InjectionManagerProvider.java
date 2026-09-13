/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey;

import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptorContext;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InjectionManagerSupplier;

public class InjectionManagerProvider {
    public static InjectionManager getInjectionManager(WriterInterceptorContext writerInterceptorContext) {
        if (!(writerInterceptorContext instanceof InjectionManagerSupplier)) {
            throw new IllegalArgumentException(LocalizationMessages.ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_WRITER_INTERCEPTOR_CONTEXT(writerInterceptorContext.getClass().getName()));
        }
        return ((InjectionManagerSupplier)((Object)writerInterceptorContext)).getInjectionManager();
    }

    public static InjectionManager getInjectionManager(ReaderInterceptorContext readerInterceptorContext) {
        if (!(readerInterceptorContext instanceof InjectionManagerSupplier)) {
            throw new IllegalArgumentException(LocalizationMessages.ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_READER_INTERCEPTOR_CONTEXT(readerInterceptorContext.getClass().getName()));
        }
        return ((InjectionManagerSupplier)((Object)readerInterceptorContext)).getInjectionManager();
    }

    public static InjectionManager getInjectionManager(FeatureContext featureContext) {
        if (!(featureContext instanceof InjectionManagerSupplier)) {
            throw new IllegalArgumentException(LocalizationMessages.ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_CONTEXT(featureContext.getClass().getName()));
        }
        return ((InjectionManagerSupplier)((Object)featureContext)).getInjectionManager();
    }
}

