/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.diagnostics.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.MutuallyExclusiveConfigurationPropertiesException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

class MutuallyExclusiveConfigurationPropertiesFailureAnalyzer
extends AbstractFailureAnalyzer<MutuallyExclusiveConfigurationPropertiesException> {
    private final ConfigurableEnvironment environment;

    MutuallyExclusiveConfigurationPropertiesFailureAnalyzer(Environment environment) {
        this.environment = (ConfigurableEnvironment)environment;
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, MutuallyExclusiveConfigurationPropertiesException cause) {
        ArrayList<Descriptor> descriptors = new ArrayList<Descriptor>();
        for (String name : cause.getConfiguredNames()) {
            List<Descriptor> descriptorsForName = this.getDescriptors(name);
            if (descriptorsForName.isEmpty()) {
                return null;
            }
            descriptors.addAll(descriptorsForName);
        }
        StringBuilder description = new StringBuilder();
        this.appendDetails(description, cause, descriptors);
        return new FailureAnalysis(description.toString(), "Update your configuration so that only one of the mutually exclusive properties is configured.", cause);
    }

    private List<Descriptor> getDescriptors(String propertyName) {
        return this.getPropertySources().filter(source -> source.containsProperty(propertyName)).map(source -> Descriptor.get(source, propertyName)).collect(Collectors.toList());
    }

    private Stream<PropertySource<?>> getPropertySources() {
        if (this.environment == null) {
            return Stream.empty();
        }
        return this.environment.getPropertySources().stream().filter(source -> !ConfigurationPropertySources.isAttachedConfigurationPropertySource(source));
    }

    private void appendDetails(StringBuilder message, MutuallyExclusiveConfigurationPropertiesException cause, List<Descriptor> descriptors) {
        descriptors.sort((d1, d2) -> ((Descriptor)d1).propertyName.compareTo(((Descriptor)d2).propertyName));
        message.append(String.format("The following configuration properties are mutually exclusive:%n%n", new Object[0]));
        this.sortedStrings(cause.getMutuallyExclusiveNames()).forEach(name -> message.append(String.format("\t%s%n", name)));
        message.append(String.format("%n", new Object[0]));
        message.append(String.format("However, more than one of those properties has been configured at the same time:%n%n", new Object[0]));
        Set<String> configuredDescriptions = this.sortedStrings(descriptors, descriptor -> String.format("\t%s%s%n", ((Descriptor)descriptor).propertyName, ((Descriptor)descriptor).origin != null ? " (originating from '" + ((Descriptor)descriptor).origin + "')" : ""));
        configuredDescriptions.forEach(message::append);
    }

    private <S> Set<String> sortedStrings(Collection<String> input) {
        return this.sortedStrings(input, Function.identity());
    }

    private <S> Set<String> sortedStrings(Collection<S> input, Function<S, String> converter) {
        TreeSet<String> results = new TreeSet<String>();
        for (S item : input) {
            results.add(converter.apply(item));
        }
        return results;
    }

    private static final class Descriptor {
        private final String propertyName;
        private final Origin origin;

        private Descriptor(String propertyName, Origin origin) {
            this.propertyName = propertyName;
            this.origin = origin;
        }

        static Descriptor get(PropertySource<?> source, String propertyName) {
            Origin origin = OriginLookup.getOrigin(source, propertyName);
            return new Descriptor(propertyName, origin);
        }
    }
}

