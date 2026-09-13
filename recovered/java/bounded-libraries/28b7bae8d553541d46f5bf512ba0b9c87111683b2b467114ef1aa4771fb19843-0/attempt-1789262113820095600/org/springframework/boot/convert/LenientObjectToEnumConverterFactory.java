/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.convert.converter.ConverterFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.LinkedMultiValueMap
 */
package org.springframework.boot.convert;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;

abstract class LenientObjectToEnumConverterFactory<T>
implements ConverterFactory<T, Enum<?>> {
    private static Map<String, List<String>> ALIASES;

    LenientObjectToEnumConverterFactory() {
    }

    public <E extends Enum<?>> Converter<T, E> getConverter(Class<E> targetType) {
        Class enumType;
        for (enumType = targetType; enumType != null && !enumType.isEnum(); enumType = enumType.getSuperclass()) {
        }
        Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
        return new LenientToEnumConverter<E>(enumType);
    }

    static {
        LinkedMultiValueMap aliases = new LinkedMultiValueMap();
        aliases.add((Object)"true", (Object)"on");
        aliases.add((Object)"false", (Object)"off");
        ALIASES = Collections.unmodifiableMap(aliases);
    }

    private class LenientToEnumConverter<E extends Enum>
    implements Converter<T, E> {
        private final Class<E> enumType;

        LenientToEnumConverter(Class<E> enumType) {
            this.enumType = enumType;
        }

        public E convert(T source) {
            String value = source.toString().trim();
            if (value.isEmpty()) {
                return null;
            }
            try {
                return Enum.valueOf(this.enumType, value);
            }
            catch (Exception ex) {
                return this.findEnum(value);
            }
        }

        private E findEnum(String value) {
            String name = this.getCanonicalName(value);
            List aliases = ALIASES.getOrDefault(name, Collections.emptyList());
            for (Enum candidate : EnumSet.allOf(this.enumType)) {
                String candidateName = this.getCanonicalName(candidate.name());
                if (!name.equals(candidateName) && !aliases.contains(candidateName)) continue;
                return (E)candidate;
            }
            throw new IllegalArgumentException("No enum constant " + this.enumType.getCanonicalName() + "." + value);
        }

        private String getCanonicalName(String name) {
            StringBuilder canonicalName = new StringBuilder(name.length());
            name.chars().filter(Character::isLetterOrDigit).map(Character::toLowerCase).forEach(c -> canonicalName.append((char)c));
            return canonicalName.toString();
        }
    }
}

