/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.source;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class MutuallyExclusiveConfigurationPropertiesException
extends RuntimeException {
    private final Set<String> configuredNames;
    private final Set<String> mutuallyExclusiveNames;

    public MutuallyExclusiveConfigurationPropertiesException(Collection<String> configuredNames, Collection<String> mutuallyExclusiveNames) {
        this(MutuallyExclusiveConfigurationPropertiesException.asSet(configuredNames), MutuallyExclusiveConfigurationPropertiesException.asSet(mutuallyExclusiveNames));
    }

    private MutuallyExclusiveConfigurationPropertiesException(Set<String> configuredNames, Set<String> mutuallyExclusiveNames) {
        super(MutuallyExclusiveConfigurationPropertiesException.buildMessage(mutuallyExclusiveNames, configuredNames));
        this.configuredNames = configuredNames;
        this.mutuallyExclusiveNames = mutuallyExclusiveNames;
    }

    public Set<String> getConfiguredNames() {
        return this.configuredNames;
    }

    public Set<String> getMutuallyExclusiveNames() {
        return this.mutuallyExclusiveNames;
    }

    private static Set<String> asSet(Collection<String> collection) {
        return collection != null ? new LinkedHashSet<String>(collection) : null;
    }

    private static String buildMessage(Set<String> mutuallyExclusiveNames, Set<String> configuredNames) {
        Assert.isTrue((configuredNames != null && configuredNames.size() > 1 ? 1 : 0) != 0, (String)"ConfiguredNames must contain 2 or more names");
        Assert.isTrue((mutuallyExclusiveNames != null && mutuallyExclusiveNames.size() > 1 ? 1 : 0) != 0, (String)"MutuallyExclusiveNames must contain 2 or more names");
        return "The configuration properties '" + String.join((CharSequence)", ", mutuallyExclusiveNames) + "' are mutually exclusive and '" + String.join((CharSequence)", ", configuredNames) + "' have been configured together";
    }

    public static void throwIfMultipleNonNullValuesIn(Consumer<Map<String, Object>> entries) {
        LinkedHashMap map = new LinkedHashMap();
        entries.accept(map);
        Set configuredNames = map.entrySet().stream().filter(entry -> entry.getValue() != null).map(Map.Entry::getKey).collect(Collectors.toCollection(LinkedHashSet::new));
        if (configuredNames.size() > 1) {
            throw new MutuallyExclusiveConfigurationPropertiesException(configuredNames, map.keySet());
        }
    }
}

