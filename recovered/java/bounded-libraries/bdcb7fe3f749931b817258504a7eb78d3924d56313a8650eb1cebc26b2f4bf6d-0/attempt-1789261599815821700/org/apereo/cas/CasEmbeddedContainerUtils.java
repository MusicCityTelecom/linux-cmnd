/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.util.ReflectionUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.logging.LoggingInitialization
 *  org.apereo.cas.util.spring.boot.CasBanner
 *  org.apereo.cas.util.spring.boot.DefaultCasBanner
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup
 *  org.springframework.core.metrics.ApplicationStartup
 *  org.springframework.core.metrics.jfr.FlightRecorderApplicationStartup
 */
package org.apereo.cas;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.ReflectionUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.logging.LoggingInitialization;
import org.apereo.cas.util.spring.boot.CasBanner;
import org.apereo.cas.util.spring.boot.DefaultCasBanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.jfr.FlightRecorderApplicationStartup;

public final class CasEmbeddedContainerUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CasEmbeddedContainerUtils.class);
    private static final int APPLICATION_EVENTS_CAPACITY = 5000;

    public static Optional<LoggingInitialization> getLoggingInitialization() {
        return (Optional)FunctionUtils.doUnchecked(() -> {
            String packageName = CasEmbeddedContainerUtils.class.getPackage().getName();
            Collection subTypes = ReflectionUtils.findSubclassesInPackage(LoggingInitialization.class, (String)packageName);
            return subTypes.isEmpty() ? Optional.empty() : Optional.of((LoggingInitialization)((Class)subTypes.iterator().next()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        });
    }

    public static CasBanner getCasBannerInstance() {
        List subTypes = ServiceLoader.load(CasBanner.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
        return subTypes.isEmpty() ? new DefaultCasBanner() : (CasBanner)subTypes.get(0);
    }

    public static ApplicationStartup getApplicationStartup() {
        String type = (String)StringUtils.defaultIfBlank((CharSequence)System.getProperty("CAS_APP_STARTUP"), (CharSequence)"default");
        if (StringUtils.equalsIgnoreCase((CharSequence)"jfr", (CharSequence)type)) {
            return new FlightRecorderApplicationStartup();
        }
        if (StringUtils.equalsIgnoreCase((CharSequence)"buffering", (CharSequence)type)) {
            return new BufferingApplicationStartup(5000);
        }
        return ApplicationStartup.DEFAULT;
    }

    @Generated
    private CasEmbeddedContainerUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

