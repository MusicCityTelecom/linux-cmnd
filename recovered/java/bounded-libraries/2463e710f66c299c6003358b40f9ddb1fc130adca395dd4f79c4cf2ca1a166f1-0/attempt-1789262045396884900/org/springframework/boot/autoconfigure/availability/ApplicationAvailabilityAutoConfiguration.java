/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.availability.ApplicationAvailabilityBean
 *  org.springframework.context.annotation.Bean
 */
package org.springframework.boot.autoconfigure.availability;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.availability.ApplicationAvailabilityBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ApplicationAvailabilityAutoConfiguration {
    @Bean
    public ApplicationAvailabilityBean applicationAvailability() {
        return new ApplicationAvailabilityBean();
    }
}

