/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 */
package org.springframework.data.convert;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.data.convert.TypeInformationMapper;
import org.springframework.data.mapping.Alias;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

public class SimpleTypeInformationMapper
implements TypeInformationMapper,
BeanClassLoaderAware {
    private final Map<String, Optional<ClassTypeInformation<?>>> cache = new ConcurrentHashMap();
    @Nullable
    private ClassLoader classLoader;

    @Override
    @Nullable
    public TypeInformation<?> resolveTypeFrom(Alias alias) {
        String stringAlias = alias.mapTyped(String.class);
        if (stringAlias != null) {
            return this.cache.computeIfAbsent(stringAlias, this::loadClass).orElse(null);
        }
        return null;
    }

    @Override
    public Alias createAliasFor(TypeInformation<?> type) {
        return Alias.of(type.getType().getName());
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private Optional<ClassTypeInformation<?>> loadClass(String typeName) {
        try {
            return Optional.of(ClassTypeInformation.from(ClassUtils.forName((String)typeName, (ClassLoader)this.classLoader)));
        }
        catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}

