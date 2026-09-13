/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;

public final class ConfigData {
    private final List<PropertySource<?>> propertySources;
    private final PropertySourceOptions propertySourceOptions;
    public static final ConfigData EMPTY = new ConfigData(Collections.emptySet(), new Option[0]);

    public ConfigData(Collection<? extends PropertySource<?>> propertySources, Option ... options) {
        this(propertySources, PropertySourceOptions.always(Options.of(options)));
    }

    public ConfigData(Collection<? extends PropertySource<?>> propertySources, PropertySourceOptions propertySourceOptions) {
        Assert.notNull(propertySources, (String)"PropertySources must not be null");
        Assert.notNull((Object)propertySourceOptions, (String)"PropertySourceOptions must not be null");
        this.propertySources = Collections.unmodifiableList(new ArrayList(propertySources));
        this.propertySourceOptions = propertySourceOptions;
    }

    public List<PropertySource<?>> getPropertySources() {
        return this.propertySources;
    }

    @Deprecated
    public Set<Option> getOptions() {
        Assert.state((boolean)(this.propertySourceOptions instanceof AlwaysPropertySourceOptions), (String)"No global options defined");
        return this.propertySourceOptions.get(null).asSet();
    }

    public Options getOptions(PropertySource<?> propertySource) {
        Options options = this.propertySourceOptions.get(propertySource);
        return options != null ? options : Options.NONE;
    }

    public static enum Option {
        IGNORE_IMPORTS,
        IGNORE_PROFILES,
        PROFILE_SPECIFIC;

    }

    public static final class Options {
        public static final Options NONE = new Options(Collections.emptySet());
        private final Set<Option> options;

        private Options(Set<Option> options) {
            this.options = Collections.unmodifiableSet(options);
        }

        Set<Option> asSet() {
            return this.options;
        }

        public boolean contains(Option option) {
            return this.options.contains((Object)option);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            Options other = (Options)obj;
            return this.options.equals(other.options);
        }

        public int hashCode() {
            return this.options.hashCode();
        }

        public String toString() {
            return this.options.toString();
        }

        public Options without(Option option) {
            return this.copy(options -> options.remove((Object)option));
        }

        public Options with(Option option) {
            return this.copy(options -> options.add(option));
        }

        private Options copy(Consumer<EnumSet<Option>> processor) {
            EnumSet<Option> options = EnumSet.noneOf(Option.class);
            options.addAll(this.options);
            processor.accept(options);
            return new Options(options);
        }

        public static Options of(Option ... options) {
            Assert.notNull((Object)options, (String)"Options must not be null");
            if (options.length == 0) {
                return NONE;
            }
            return new Options(EnumSet.copyOf(Arrays.asList(options)));
        }
    }

    private static class AlwaysPropertySourceOptions
    implements PropertySourceOptions {
        private final Options options;

        AlwaysPropertySourceOptions(Options options) {
            this.options = options;
        }

        @Override
        public Options get(PropertySource<?> propertySource) {
            return this.options;
        }
    }

    @FunctionalInterface
    public static interface PropertySourceOptions {
        public static final PropertySourceOptions ALWAYS_NONE = new AlwaysPropertySourceOptions(Options.NONE);

        public Options get(PropertySource<?> var1);

        public static PropertySourceOptions always(Option ... options) {
            return PropertySourceOptions.always(Options.of(options));
        }

        public static PropertySourceOptions always(Options options) {
            if (options == Options.NONE) {
                return ALWAYS_NONE;
            }
            return new AlwaysPropertySourceOptions(options);
        }
    }
}

