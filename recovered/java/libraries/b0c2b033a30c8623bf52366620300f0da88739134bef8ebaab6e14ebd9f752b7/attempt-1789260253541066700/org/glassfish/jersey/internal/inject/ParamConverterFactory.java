/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Singleton;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

@Singleton
public class ParamConverterFactory
implements ParamConverterProvider {
    private final List<ParamConverterProvider> converterProviders;

    public ParamConverterFactory(Set<ParamConverterProvider> providers, Set<ParamConverterProvider> customProviders) {
        LinkedHashSet<ParamConverterProvider> copyProviders = new LinkedHashSet<ParamConverterProvider>(providers);
        this.converterProviders = new ArrayList<ParamConverterProvider>();
        this.converterProviders.addAll(customProviders);
        copyProviders.removeAll(customProviders);
        this.converterProviders.addAll(copyProviders);
    }

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        for (ParamConverterProvider provider : this.converterProviders) {
            ParamConverter<T> converter = provider.getConverter(rawType, genericType, annotations);
            if (converter == null) continue;
            return converter;
        }
        return null;
    }
}

