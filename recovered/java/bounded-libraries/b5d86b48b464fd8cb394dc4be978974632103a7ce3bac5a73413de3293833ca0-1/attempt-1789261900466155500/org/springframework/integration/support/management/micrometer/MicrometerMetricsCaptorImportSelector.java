/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.ImportSelector
 *  org.springframework.core.type.AnnotationMetadata
 */
package org.springframework.integration.support.management.micrometer;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.integration.support.management.micrometer.MicrometerMetricsCaptorConfiguration;

public class MicrometerMetricsCaptorImportSelector
implements ImportSelector {
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        if (MicrometerMetricsCaptorConfiguration.METER_REGISTRY_PRESENT) {
            return new String[]{MicrometerMetricsCaptorConfiguration.class.getName()};
        }
        return new String[0];
    }
}

