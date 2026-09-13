/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.source;

import java.util.List;
import java.util.function.BiPredicate;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;

interface PropertyMapper {
    public static final BiPredicate<ConfigurationPropertyName, ConfigurationPropertyName> DEFAULT_ANCESTOR_OF_CHECK = ConfigurationPropertyName::isAncestorOf;

    public List<String> map(ConfigurationPropertyName var1);

    public ConfigurationPropertyName map(String var1);

    default public BiPredicate<ConfigurationPropertyName, ConfigurationPropertyName> getAncestorOfCheck() {
        return DEFAULT_ANCESTOR_OF_CHECK;
    }
}

