/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.properties.source;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.BiPredicate;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.PropertyMapper;

final class SystemEnvironmentPropertyMapper
implements PropertyMapper {
    public static final PropertyMapper INSTANCE = new SystemEnvironmentPropertyMapper();

    SystemEnvironmentPropertyMapper() {
    }

    @Override
    public List<String> map(ConfigurationPropertyName configurationPropertyName) {
        String legacyName;
        String name = this.convertName(configurationPropertyName);
        if (name.equals(legacyName = this.convertLegacyName(configurationPropertyName))) {
            return Collections.singletonList(name);
        }
        return Arrays.asList(name, legacyName);
    }

    private String convertName(ConfigurationPropertyName name) {
        return this.convertName(name, name.getNumberOfElements());
    }

    private String convertName(ConfigurationPropertyName name, int numberOfElements) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numberOfElements; ++i) {
            if (result.length() > 0) {
                result.append('_');
            }
            result.append(name.getElement(i, ConfigurationPropertyName.Form.UNIFORM).toUpperCase(Locale.ENGLISH));
        }
        return result.toString();
    }

    private String convertLegacyName(ConfigurationPropertyName name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.getNumberOfElements(); ++i) {
            if (result.length() > 0) {
                result.append('_');
            }
            result.append(this.convertLegacyNameElement(name.getElement(i, ConfigurationPropertyName.Form.ORIGINAL)));
        }
        return result.toString();
    }

    private Object convertLegacyNameElement(String element) {
        return element.replace('-', '_').toUpperCase(Locale.ENGLISH);
    }

    @Override
    public ConfigurationPropertyName map(String propertySourceName) {
        return this.convertName(propertySourceName);
    }

    private ConfigurationPropertyName convertName(String propertySourceName) {
        try {
            return ConfigurationPropertyName.adapt(propertySourceName, '_', this::processElementValue);
        }
        catch (Exception ex) {
            return ConfigurationPropertyName.EMPTY;
        }
    }

    private CharSequence processElementValue(CharSequence value) {
        String result = value.toString().toLowerCase(Locale.ENGLISH);
        return SystemEnvironmentPropertyMapper.isNumber(result) ? "[" + result + "]" : result;
    }

    private static boolean isNumber(String string) {
        return string.chars().allMatch(Character::isDigit);
    }

    @Override
    public BiPredicate<ConfigurationPropertyName, ConfigurationPropertyName> getAncestorOfCheck() {
        return this::isAncestorOf;
    }

    private boolean isAncestorOf(ConfigurationPropertyName name, ConfigurationPropertyName candidate) {
        return name.isAncestorOf(candidate) || this.isLegacyAncestorOf(name, candidate);
    }

    private boolean isLegacyAncestorOf(ConfigurationPropertyName name, ConfigurationPropertyName candidate) {
        if (!this.hasDashedEntries(name)) {
            return false;
        }
        ConfigurationPropertyName legacyCompatibleName = this.buildLegacyCompatibleName(name);
        return legacyCompatibleName != null && legacyCompatibleName.isAncestorOf(candidate);
    }

    private ConfigurationPropertyName buildLegacyCompatibleName(ConfigurationPropertyName name) {
        StringBuilder legacyCompatibleName = new StringBuilder();
        for (int i = 0; i < name.getNumberOfElements(); ++i) {
            if (i != 0) {
                legacyCompatibleName.append('.');
            }
            legacyCompatibleName.append(name.getElement(i, ConfigurationPropertyName.Form.DASHED).replace('-', '.'));
        }
        return ConfigurationPropertyName.ofIfValid(legacyCompatibleName);
    }

    boolean hasDashedEntries(ConfigurationPropertyName name) {
        for (int i = 0; i < name.getNumberOfElements(); ++i) {
            if (name.getElement(i, ConfigurationPropertyName.Form.DASHED).indexOf(45) == -1) continue;
            return true;
        }
        return false;
    }
}

