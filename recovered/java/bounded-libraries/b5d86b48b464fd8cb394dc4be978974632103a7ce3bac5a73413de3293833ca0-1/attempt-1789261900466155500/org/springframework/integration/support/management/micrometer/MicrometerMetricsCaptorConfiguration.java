/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Role
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.support.management.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.integration.support.management.micrometer.MicrometerMetricsCaptor;
import org.springframework.util.ClassUtils;

@Configuration(proxyBeanMethods=false)
public class MicrometerMetricsCaptorConfiguration {
    public static final boolean METER_REGISTRY_PRESENT = ClassUtils.isPresent((String)"io.micrometer.core.instrument.MeterRegistry", null);

    @Bean(name={"integrationMicrometerMetricsCaptor"})
    @Role(value=2)
    public MicrometerMetricsCaptor micrometerMetricsCaptor(ObjectProvider<MeterRegistry> meterRegistries) {
        if (meterRegistries.stream().findAny().isPresent()) {
            return new MicrometerMetricsCaptor(meterRegistries);
        }
        return null;
    }
}

