/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.core.ResolvableType
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.support.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.core.ResolvableType;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.util.ClassUtils;

public abstract class AbstractJacksonJsonObjectMapper<N, P, J>
implements JsonObjectMapper<N, P>,
BeanClassLoaderAware {
    protected static final Collection<Class<?>> SUPPORTED_JSON_TYPES = Arrays.asList(String.class, byte[].class, File.class, URL.class, InputStream.class, Reader.class);
    private volatile ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    protected ClassLoader getClassLoader() {
        return this.classLoader;
    }

    @Override
    public <T> T fromJson(Object json, Class<T> valueType) throws IOException {
        return this.fromJson(json, this.constructType(valueType));
    }

    @Override
    public <T> T fromJson(Object json, ResolvableType valueType) throws IOException {
        return this.fromJson(json, this.constructType(valueType.getType()));
    }

    @Override
    public <T> T fromJson(Object json, Map<String, Object> javaTypes) throws IOException {
        J javaType = this.extractJavaType(javaTypes);
        return this.fromJson(json, javaType);
    }

    protected J createJavaType(Map<String, Object> javaTypes, String javaTypeKey) {
        Object classValue = javaTypes.get(javaTypeKey);
        if (classValue == null) {
            throw new IllegalArgumentException("Could not resolve '" + javaTypeKey + "' in 'javaTypes'.");
        }
        Class aClass = null;
        if (classValue instanceof Class) {
            aClass = (Class)classValue;
        } else {
            try {
                aClass = ClassUtils.forName((String)classValue.toString(), (ClassLoader)this.classLoader);
            }
            catch (ClassNotFoundException | LinkageError e) {
                throw new IllegalStateException(e);
            }
        }
        return this.constructType(aClass);
    }

    protected abstract <T> T fromJson(Object var1, J var2) throws IOException;

    protected abstract J extractJavaType(Map<String, Object> var1);

    protected abstract J constructType(Type var1);
}

