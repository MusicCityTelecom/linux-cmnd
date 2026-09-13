/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Priority;
import javax.ws.rs.core.Configurable;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.config.SystemPropertiesConfigurationProvider;
import org.glassfish.jersey.spi.ExternalConfigurationModel;
import org.glassfish.jersey.spi.ExternalConfigurationProvider;

public class ExternalPropertiesConfigurationFactory {
    private static final List<ExternalConfigurationProvider> EXTERNAL_CONFIGURATION_PROVIDERS = ExternalPropertiesConfigurationFactory.getExternalConfigurations();

    static Map<String, Object> readExternalPropertiesMap() {
        ExternalConfigurationProvider provider = ExternalPropertiesConfigurationFactory.mergeConfigs(EXTERNAL_CONFIGURATION_PROVIDERS);
        return provider == null ? Collections.emptyMap() : provider.getProperties();
    }

    public static boolean configure(Configurable config) {
        if (config instanceof ExternalConfigurationModel) {
            return false;
        }
        Map<String, Object> properties = ExternalPropertiesConfigurationFactory.readExternalPropertiesMap();
        properties.forEach((k, v) -> config.property((String)k, v));
        return true;
    }

    static ExternalConfigurationModel getConfig() {
        ExternalConfigurationProvider provider = ExternalPropertiesConfigurationFactory.mergeConfigs(ExternalPropertiesConfigurationFactory.getExternalConfigurations());
        return provider == null ? null : provider.getConfiguration();
    }

    private static List<ExternalConfigurationProvider> getExternalConfigurations() {
        ArrayList<ExternalConfigurationProvider> providers = new ArrayList<ExternalConfigurationProvider>();
        ServiceFinder<ExternalConfigurationProvider> finder = ServiceFinder.find(ExternalConfigurationProvider.class);
        if (finder.iterator().hasNext()) {
            finder.forEach(providers::add);
        } else {
            providers.add(new SystemPropertiesConfigurationProvider());
        }
        return providers;
    }

    private static ExternalConfigurationProvider mergeConfigs(List<ExternalConfigurationProvider> configurations) {
        Set<ExternalConfigurationProvider> orderedConfigurations = ExternalPropertiesConfigurationFactory.orderConfigs(configurations);
        Iterator<ExternalConfigurationProvider> configurationIterator = orderedConfigurations.iterator();
        if (!configurationIterator.hasNext()) {
            return null;
        }
        ExternalConfigurationProvider firstConfig = configurationIterator.next();
        while (configurationIterator.hasNext()) {
            ExternalConfigurationProvider nextConfig = configurationIterator.next();
            firstConfig.merge(nextConfig.getConfiguration());
        }
        return firstConfig;
    }

    private static Set<ExternalConfigurationProvider> orderConfigs(List<ExternalConfigurationProvider> configurations) {
        TreeSet<ExternalConfigurationProvider> sortedSet = new TreeSet<ExternalConfigurationProvider>(new ConfigComparator());
        sortedSet.addAll(configurations);
        return Collections.unmodifiableSortedSet(sortedSet);
    }

    private static class ConfigComparator
    implements Comparator<ExternalConfigurationProvider> {
        private ConfigComparator() {
        }

        @Override
        public int compare(ExternalConfigurationProvider config1, ExternalConfigurationProvider config2) {
            boolean config1PriorityPresent = config1.getClass().isAnnotationPresent(Priority.class);
            boolean config2PriorityPresent = config2.getClass().isAnnotationPresent(Priority.class);
            int priority1 = 5000;
            int priority2 = 5000;
            if (config1PriorityPresent) {
                priority1 = config1.getClass().getAnnotation(Priority.class).value();
            }
            if (config2PriorityPresent) {
                priority2 = config2.getClass().getAnnotation(Priority.class).value();
            }
            if (priority1 == priority2) {
                return config1.getClass().getName().compareTo(config2.getClass().getName());
            }
            return Integer.compare(priority1, priority2);
        }
    }
}

