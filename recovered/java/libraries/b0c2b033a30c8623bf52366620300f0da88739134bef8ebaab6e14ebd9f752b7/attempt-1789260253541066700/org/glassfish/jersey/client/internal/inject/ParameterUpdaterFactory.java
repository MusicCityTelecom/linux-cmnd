/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import javax.inject.Singleton;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.ParamConverter;
import org.glassfish.jersey.client.inject.ParameterUpdater;
import org.glassfish.jersey.client.inject.ParameterUpdaterProvider;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.internal.inject.CollectionUpdater;
import org.glassfish.jersey.client.internal.inject.PrimitiveCharacterUpdater;
import org.glassfish.jersey.client.internal.inject.PrimitiveValueOfUpdater;
import org.glassfish.jersey.client.internal.inject.SingleStringValueUpdater;
import org.glassfish.jersey.client.internal.inject.SingleValueUpdater;
import org.glassfish.jersey.internal.inject.ParamConverterFactory;
import org.glassfish.jersey.internal.inject.PrimitiveMapper;
import org.glassfish.jersey.internal.inject.UpdaterException;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.ClassTypePair;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.model.Parameter;

@Singleton
final class ParameterUpdaterFactory
implements ParameterUpdaterProvider {
    private final LazyValue<ParamConverterFactory> paramConverterFactory;

    public ParameterUpdaterFactory(LazyValue<ParamConverterFactory> paramConverterFactory) {
        this.paramConverterFactory = paramConverterFactory;
    }

    @Override
    public ParameterUpdater<?, ?> get(Parameter p) {
        return this.process((ParamConverterFactory)this.paramConverterFactory.get(), p.getDefaultValue(), p.getRawType(), p.getType(), p.getAnnotations(), p.getSourceName());
    }

    private ParameterUpdater<?, ?> process(ParamConverterFactory paramConverterFactory, String defaultValue, Class<?> rawType, Type type, Annotation[] annotations, String parameterName) {
        ParamConverter<?> converter = paramConverterFactory.getConverter(rawType, type, annotations);
        if (converter != null) {
            try {
                return new SingleValueUpdater(converter, parameterName, defaultValue);
            }
            catch (UpdaterException e) {
                throw e;
            }
            catch (Exception e) {
                throw new ProcessingException(LocalizationMessages.ERROR_PARAMETER_TYPE_PROCESSING(rawType), e);
            }
        }
        if (rawType == List.class || rawType == Set.class || rawType == SortedSet.class) {
            ClassTypePair typePair;
            List<ClassTypePair> typePairs = ReflectionHelper.getTypeArgumentAndClass(type);
            ClassTypePair classTypePair = typePair = typePairs.size() == 1 ? typePairs.get(0) : null;
            if (typePair != null) {
                converter = paramConverterFactory.getConverter(typePair.rawClass(), typePair.type(), annotations);
            }
            if (converter != null) {
                try {
                    return CollectionUpdater.getInstance(rawType, converter, parameterName, defaultValue);
                }
                catch (UpdaterException e) {
                    throw e;
                }
                catch (Exception e) {
                    throw new ProcessingException(LocalizationMessages.ERROR_PARAMETER_TYPE_PROCESSING(rawType), e);
                }
            }
        }
        if (rawType == String.class) {
            return new SingleStringValueUpdater(parameterName, defaultValue);
        }
        if (rawType == Character.class) {
            return new PrimitiveCharacterUpdater(parameterName, defaultValue, PrimitiveMapper.primitiveToDefaultValueMap.get(rawType));
        }
        if (rawType.isPrimitive()) {
            Class wrappedRaw = PrimitiveMapper.primitiveToClassMap.get(rawType);
            if (wrappedRaw == null) {
                return null;
            }
            if (wrappedRaw == Character.class) {
                return new PrimitiveCharacterUpdater(parameterName, defaultValue, PrimitiveMapper.primitiveToDefaultValueMap.get(wrappedRaw));
            }
            return new PrimitiveValueOfUpdater(parameterName, defaultValue, PrimitiveMapper.primitiveToDefaultValueMap.get(wrappedRaw));
        }
        return null;
    }
}

