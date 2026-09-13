/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.reflect.KFunction
 *  kotlin.reflect.KParameter
 *  kotlin.reflect.KType
 *  kotlin.reflect.jvm.ReflectJvmMapping
 *  org.springframework.beans.BeanUtils
 *  org.springframework.core.DefaultParameterNameDiscoverer
 *  org.springframework.core.KotlinDetector
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.ParameterNameDiscoverer
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.convert.ConversionException
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.bind;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.jvm.ReflectJvmMapping;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.bind.BindConstructorProvider;
import org.springframework.boot.context.properties.bind.BindConverter;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.DataObjectBinder;
import org.springframework.boot.context.properties.bind.DataObjectPropertyBinder;
import org.springframework.boot.context.properties.bind.DataObjectPropertyName;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.KotlinDetector;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.convert.ConversionException;
import org.springframework.util.Assert;

class ValueObjectBinder
implements DataObjectBinder {
    private final BindConstructorProvider constructorProvider;

    ValueObjectBinder(BindConstructorProvider constructorProvider) {
        this.constructorProvider = constructorProvider;
    }

    @Override
    public <T> T bind(ConfigurationPropertyName name, Bindable<T> target, Binder.Context context, DataObjectPropertyBinder propertyBinder) {
        ValueObject<T> valueObject = ValueObject.get(target, this.constructorProvider, context);
        if (valueObject == null) {
            return null;
        }
        context.pushConstructorBoundTypes(target.getType().resolve());
        List<ConstructorParameter> parameters = valueObject.getConstructorParameters();
        ArrayList<Object> args = new ArrayList<Object>(parameters.size());
        boolean bound = false;
        for (ConstructorParameter parameter : parameters) {
            Object arg = parameter.bind(propertyBinder);
            bound = bound || arg != null;
            arg = arg != null ? arg : this.getDefaultValue(context, parameter);
            args.add(arg);
        }
        context.clearConfigurationProperty();
        context.popConstructorBoundTypes();
        return bound ? (T)valueObject.instantiate(args) : null;
    }

    @Override
    public <T> T create(Bindable<T> target, Binder.Context context) {
        ValueObject<T> valueObject = ValueObject.get(target, this.constructorProvider, context);
        if (valueObject == null) {
            return null;
        }
        List<ConstructorParameter> parameters = valueObject.getConstructorParameters();
        ArrayList<Object> args = new ArrayList<Object>(parameters.size());
        for (ConstructorParameter parameter : parameters) {
            args.add(this.getDefaultValue(context, parameter));
        }
        return valueObject.instantiate(args);
    }

    private <T> T getDefaultValue(Binder.Context context, ConstructorParameter parameter) {
        Annotation[] annotations;
        ResolvableType type = parameter.getType();
        for (Annotation annotation : annotations = parameter.getAnnotations()) {
            if (!(annotation instanceof DefaultValue)) continue;
            String[] defaultValue = ((DefaultValue)annotation).value();
            if (defaultValue.length == 0) {
                return this.getNewInstanceIfPossible(context, type);
            }
            return this.convertDefaultValue(context.getConverter(), defaultValue, type, annotations);
        }
        return null;
    }

    private <T> T convertDefaultValue(BindConverter converter, String[] defaultValue, ResolvableType type, Annotation[] annotations) {
        try {
            return converter.convert((Object)defaultValue, type, annotations);
        }
        catch (ConversionException ex) {
            if (defaultValue.length == 1) {
                return converter.convert((Object)defaultValue[0], type, annotations);
            }
            throw ex;
        }
    }

    private <T> T getNewInstanceIfPossible(Binder.Context context, ResolvableType type) {
        Class resolved = type.resolve();
        Assert.state((resolved == null || this.isEmptyDefaultValueAllowed(resolved) ? 1 : 0) != 0, () -> "Parameter of type " + type + " must have a non-empty default value.");
        Object instance = this.create(Bindable.of(type), context);
        if (instance != null) {
            return instance;
        }
        return (T)(resolved != null ? BeanUtils.instantiateClass((Class)resolved) : null);
    }

    private boolean isEmptyDefaultValueAllowed(Class<?> type) {
        return !type.isPrimitive() && !type.isEnum() && !this.isAggregate(type) && !type.getName().startsWith("java.lang");
    }

    private boolean isAggregate(Class<?> type) {
        return type.isArray() || Map.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type);
    }

    private static class ConstructorParameter {
        private final String name;
        private final ResolvableType type;
        private final Annotation[] annotations;

        ConstructorParameter(String name, ResolvableType type, Annotation[] annotations) {
            this.name = DataObjectPropertyName.toDashedForm(name);
            this.type = type;
            this.annotations = annotations;
        }

        Object bind(DataObjectPropertyBinder propertyBinder) {
            return propertyBinder.bindProperty(this.name, Bindable.of(this.type).withAnnotations(this.annotations));
        }

        Annotation[] getAnnotations() {
            return this.annotations;
        }

        ResolvableType getType() {
            return this.type;
        }
    }

    private static final class DefaultValueObject<T>
    extends ValueObject<T> {
        private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
        private final List<ConstructorParameter> constructorParameters;

        private DefaultValueObject(Constructor<T> constructor, ResolvableType type) {
            super(constructor);
            this.constructorParameters = DefaultValueObject.parseConstructorParameters(constructor, type);
        }

        private static List<ConstructorParameter> parseConstructorParameters(Constructor<?> constructor, ResolvableType type) {
            String[] names = PARAMETER_NAME_DISCOVERER.getParameterNames(constructor);
            Assert.state((names != null ? 1 : 0) != 0, () -> "Failed to extract parameter names for " + constructor);
            Parameter[] parameters = constructor.getParameters();
            ArrayList<ConstructorParameter> result = new ArrayList<ConstructorParameter>(parameters.length);
            for (int i = 0; i < parameters.length; ++i) {
                String name = MergedAnnotations.from((AnnotatedElement)parameters[i]).get(Name.class).getValue("value", String.class).orElse(names[i]);
                ResolvableType parameterType = ResolvableType.forMethodParameter((MethodParameter)new MethodParameter(constructor, i), (ResolvableType)type);
                Annotation[] annotations = parameters[i].getDeclaredAnnotations();
                result.add(new ConstructorParameter(name, parameterType, annotations));
            }
            return Collections.unmodifiableList(result);
        }

        @Override
        List<ConstructorParameter> getConstructorParameters() {
            return this.constructorParameters;
        }

        static <T> ValueObject<T> get(Constructor<?> bindConstructor, ResolvableType type) {
            return new DefaultValueObject(bindConstructor, type);
        }
    }

    private static final class KotlinValueObject<T>
    extends ValueObject<T> {
        private static final Annotation[] ANNOTATION_ARRAY = new Annotation[0];
        private final List<ConstructorParameter> constructorParameters;

        private KotlinValueObject(Constructor<T> primaryConstructor, KFunction<T> kotlinConstructor, ResolvableType type) {
            super(primaryConstructor);
            this.constructorParameters = this.parseConstructorParameters(kotlinConstructor, type);
        }

        private List<ConstructorParameter> parseConstructorParameters(KFunction<T> kotlinConstructor, ResolvableType type) {
            List parameters = kotlinConstructor.getParameters();
            ArrayList<ConstructorParameter> result = new ArrayList<ConstructorParameter>(parameters.size());
            for (KParameter parameter : parameters) {
                String name = this.getParameterName(parameter);
                ResolvableType parameterType = ResolvableType.forType((Type)ReflectJvmMapping.getJavaType((KType)parameter.getType()), (ResolvableType)type);
                Annotation[] annotations = parameter.getAnnotations().toArray(ANNOTATION_ARRAY);
                result.add(new ConstructorParameter(name, parameterType, annotations));
            }
            return Collections.unmodifiableList(result);
        }

        private String getParameterName(KParameter parameter) {
            return MergedAnnotations.from((Object)parameter, (Annotation[])parameter.getAnnotations().toArray(ANNOTATION_ARRAY)).get(Name.class).getValue("value", String.class).orElseGet(() -> ((KParameter)parameter).getName());
        }

        @Override
        List<ConstructorParameter> getConstructorParameters() {
            return this.constructorParameters;
        }

        static <T> ValueObject<T> get(Constructor<T> bindConstructor, ResolvableType type) {
            KFunction kotlinConstructor = ReflectJvmMapping.getKotlinFunction(bindConstructor);
            if (kotlinConstructor != null) {
                return new KotlinValueObject<T>(bindConstructor, kotlinConstructor, type);
            }
            return DefaultValueObject.get(bindConstructor, type);
        }
    }

    private static abstract class ValueObject<T> {
        private final Constructor<T> constructor;

        protected ValueObject(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        T instantiate(List<Object> args) {
            return (T)BeanUtils.instantiateClass(this.constructor, (Object[])args.toArray());
        }

        abstract List<ConstructorParameter> getConstructorParameters();

        static <T> ValueObject<T> get(Bindable<T> bindable, BindConstructorProvider constructorProvider, Binder.Context context) {
            Class type = bindable.getType().resolve();
            if (type == null || type.isEnum() || Modifier.isAbstract(type.getModifiers())) {
                return null;
            }
            Constructor<?> bindConstructor = constructorProvider.getBindConstructor(bindable, context.isNestedConstructorBinding());
            if (bindConstructor == null) {
                return null;
            }
            if (KotlinDetector.isKotlinType((Class)type)) {
                return KotlinValueObject.get(bindConstructor, bindable.getType());
            }
            return DefaultValueObject.get(bindConstructor, bindable.getType());
        }
    }
}

