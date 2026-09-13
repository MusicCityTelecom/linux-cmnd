/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.BeanParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.internal.guava.Lists;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.ClassTypePair;
import org.glassfish.jersey.model.AnnotatedMethod;
import org.glassfish.jersey.model.internal.spi.ParameterServiceProvider;

public class Parameter
implements AnnotatedElement {
    private static final Logger LOGGER = Logger.getLogger(Parameter.class.getName());
    private static final List<ParamCreationFactory> PARAM_CREATION_FACTORIES;
    private static final Map<Class, ParamAnnotationHelper> ANNOTATION_HELPER_MAP;
    private final Annotation[] annotations;
    private final Annotation sourceAnnotation;
    private final Source source;
    private final String sourceName;
    private final boolean encoded;
    private final String defaultValue;
    private final Class<?> rawType;
    private final Type type;

    protected Parameter(Annotation[] markers, Annotation marker, Source source, String sourceName, Class<?> rawType, Type type, boolean encoded, String defaultValue) {
        this.annotations = markers;
        this.sourceAnnotation = marker;
        this.source = source;
        this.sourceName = sourceName;
        this.rawType = rawType;
        this.type = type;
        this.encoded = encoded;
        this.defaultValue = defaultValue;
    }

    public static <PARAMETER extends Parameter> PARAMETER create(Class concreteClass, Class declaringClass, boolean encodeByDefault, Class<?> rawType, Type type, Annotation[] annotations) {
        return Parameter.create(concreteClass, declaringClass, encodeByDefault, rawType, type, annotations, Parameter.class);
    }

    protected static <PARAMETER extends Parameter> PARAMETER create(Class concreteClass, Class declaringClass, boolean encodeByDefault, Class<?> rawType, Type type, Annotation[] annotations, Class<?> parameterClass) {
        if (null == annotations) {
            return null;
        }
        Annotation paramAnnotation = null;
        Source paramSource = null;
        String paramName = null;
        boolean paramEncoded = encodeByDefault;
        String paramDefault = null;
        for (Annotation annotation : annotations) {
            if (ANNOTATION_HELPER_MAP.containsKey(annotation.annotationType())) {
                ParamAnnotationHelper helper = ANNOTATION_HELPER_MAP.get(annotation.annotationType());
                paramAnnotation = annotation;
                paramSource = helper.getSource();
                paramName = helper.getValueOf(annotation);
                continue;
            }
            if (Encoded.class == annotation.annotationType()) {
                paramEncoded = true;
                continue;
            }
            if (DefaultValue.class == annotation.annotationType()) {
                paramDefault = ((DefaultValue)annotation).value();
                continue;
            }
            if (paramAnnotation != null && paramSource != Source.UNKNOWN) continue;
            paramAnnotation = annotation;
            paramSource = Source.UNKNOWN;
            paramName = Parameter.getValue(annotation);
        }
        if (paramAnnotation == null) {
            paramSource = Source.ENTITY;
        }
        ClassTypePair ct = ReflectionHelper.resolveGenericType(concreteClass, declaringClass, rawType, type);
        if (paramSource == Source.BEAN_PARAM) {
            return Parameter.createBeanParameter(annotations, paramAnnotation, paramSource, paramName, ct.rawClass(), ct.type(), paramEncoded, paramDefault, parameterClass);
        }
        return Parameter.createParameter(annotations, paramAnnotation, paramSource, paramName, ct.rawClass(), ct.type(), paramEncoded, paramDefault, parameterClass);
    }

    private static <PARAMETER extends Parameter> PARAMETER createBeanParameter(Annotation[] markers, Annotation marker, Source source, String sourceName, Class<?> rawType, Type type, boolean encoded, String defaultValue, Class<?> parameterClass) {
        for (ParamCreationFactory factory : PARAM_CREATION_FACTORIES) {
            if (!factory.isFor(parameterClass)) continue;
            return factory.createBeanParameter(markers, marker, source, sourceName, rawType, type, encoded, defaultValue);
        }
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, LocalizationMessages.PARAM_CREATION_FACTORY_NOT_FOUND(parameterClass.getName()));
        }
        throw new IllegalStateException(LocalizationMessages.PARAM_CREATION_FACTORY_NOT_FOUND(parameterClass.getName()));
    }

    private static <PARAMETER extends Parameter> PARAMETER createParameter(Annotation[] markers, Annotation marker, Source source, String sourceName, Class<?> rawType, Type type, boolean encoded, String defaultValue, Class<?> parameterClass) {
        for (ParamCreationFactory factory : PARAM_CREATION_FACTORIES) {
            if (!factory.isFor(parameterClass)) continue;
            return factory.createParameter(markers, marker, source, sourceName, rawType, type, encoded, defaultValue);
        }
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, LocalizationMessages.PARAM_CREATION_FACTORY_NOT_FOUND(parameterClass.getName()));
        }
        throw new IllegalStateException(LocalizationMessages.PARAM_CREATION_FACTORY_NOT_FOUND(parameterClass.getName()));
    }

    public static <PARAMETER extends Parameter> List<PARAMETER> create(Class concreteClass, Class declaringClass, Method javaMethod, boolean keepEncoded) {
        return Parameter.createList(concreteClass, declaringClass, javaMethod, keepEncoded, Parameter.class);
    }

    protected static <PARAMETER extends Parameter> List<PARAMETER> createList(Class concreteClass, Class declaringClass, Method javaMethod, boolean keepEncoded, Class parameterClass) {
        AnnotatedMethod method = new AnnotatedMethod(javaMethod);
        return Parameter.createList(concreteClass, declaringClass, null != method.getAnnotation(Encoded.class) || keepEncoded, method.getParameterTypes(), method.getGenericParameterTypes(), method.getParameterAnnotations(), parameterClass);
    }

    private static <PARAMETER extends Parameter> List<PARAMETER> createList(Class concreteClass, Class declaringClass, boolean keepEncoded, Class[] parameterTypes, Type[] genericParameterTypes, Annotation[][] parameterAnnotations, Class<?> parameterClass) {
        ArrayList<PARAMETER> parameters = new ArrayList<PARAMETER>(parameterTypes.length);
        for (int i = 0; i < parameterTypes.length; ++i) {
            PARAMETER parameter = Parameter.create(concreteClass, declaringClass, keepEncoded, parameterTypes[i], genericParameterTypes[i], parameterAnnotations[i], parameterClass);
            if (null == parameter) {
                return Collections.emptyList();
            }
            parameters.add(parameter);
        }
        return parameters;
    }

    protected static <PARAMETER extends Parameter> List<PARAMETER> createList(Class concreteClass, Class declaringClass, Constructor<?> ctor, boolean keepEncoded, Class<?> parameterClass) {
        Type[] genericParameterTypes;
        Class[] parameterTypes = ctor.getParameterTypes();
        if (parameterTypes.length != (genericParameterTypes = ctor.getGenericParameterTypes()).length) {
            Type[] _genericParameterTypes = new Type[parameterTypes.length];
            _genericParameterTypes[0] = parameterTypes[0];
            System.arraycopy(genericParameterTypes, 0, _genericParameterTypes, 1, genericParameterTypes.length);
            genericParameterTypes = _genericParameterTypes;
        }
        return Parameter.createList(concreteClass, declaringClass, null != ctor.getAnnotation(Encoded.class) || keepEncoded, parameterTypes, genericParameterTypes, ctor.getParameterAnnotations(), parameterClass);
    }

    private static String getValue(Annotation a) {
        try {
            Method m = a.annotationType().getMethod("value", new Class[0]);
            if (m.getReturnType() != String.class) {
                return null;
            }
            return (String)m.invoke(a, new Object[0]);
        }
        catch (Exception ex) {
            if (LOGGER.isLoggable(Level.FINER)) {
                LOGGER.log(Level.FINER, String.format("Unable to get the %s annotation value property", a.getClass().getName()), ex);
            }
            return null;
        }
    }

    public Annotation getSourceAnnotation() {
        return this.sourceAnnotation;
    }

    public Source getSource() {
        return this.source;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public boolean isEncoded() {
        return this.encoded;
    }

    public boolean hasDefaultValue() {
        return this.defaultValue != null;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public Class<?> getRawType() {
        return this.rawType;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isQualified() {
        return false;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return this.getAnnotation(annotationClass) != null;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        if (annotationClass == null) {
            return null;
        }
        for (Annotation a : this.annotations) {
            if (a.annotationType() != annotationClass) continue;
            return (T)((Annotation)annotationClass.cast(a));
        }
        return null;
    }

    @Override
    public Annotation[] getAnnotations() {
        return (Annotation[])this.annotations.clone();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return (Annotation[])this.annotations.clone();
    }

    public String toString() {
        return String.format("Parameter [type=%s, source=%s, defaultValue=%s]", this.getRawType(), this.getSourceName(), this.getDefaultValue());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Parameter parameter = (Parameter)o;
        if (this.encoded != parameter.encoded) {
            return false;
        }
        if (!Arrays.equals(this.annotations, parameter.annotations)) {
            return false;
        }
        if (this.defaultValue != null ? !this.defaultValue.equals(parameter.defaultValue) : parameter.defaultValue != null) {
            return false;
        }
        if (this.rawType != null ? !this.rawType.equals(parameter.rawType) : parameter.rawType != null) {
            return false;
        }
        if (this.source != parameter.source) {
            return false;
        }
        if (this.sourceAnnotation != null ? !this.sourceAnnotation.equals(parameter.sourceAnnotation) : parameter.sourceAnnotation != null) {
            return false;
        }
        if (this.sourceName != null ? !this.sourceName.equals(parameter.sourceName) : parameter.sourceName != null) {
            return false;
        }
        return !(this.type != null ? !this.type.equals(parameter.type) : parameter.type != null);
    }

    public int hashCode() {
        int result = this.annotations != null ? Arrays.hashCode(this.annotations) : 0;
        result = 31 * result + (this.sourceAnnotation != null ? this.sourceAnnotation.hashCode() : 0);
        result = 31 * result + (this.source != null ? this.source.hashCode() : 0);
        result = 31 * result + (this.sourceName != null ? this.sourceName.hashCode() : 0);
        result = 31 * result + (this.encoded ? 1 : 0);
        result = 31 * result + (this.defaultValue != null ? this.defaultValue.hashCode() : 0);
        result = 31 * result + (this.rawType != null ? this.rawType.hashCode() : 0);
        result = 31 * result + (this.type != null ? this.type.hashCode() : 0);
        return result;
    }

    static {
        ArrayList<ParameterServiceProvider> PARAMETER_SERVICE_PROVIDERS = Lists.newArrayList(ServiceFinder.find(ParameterServiceProvider.class));
        PARAMETER_SERVICE_PROVIDERS.add(new ParameterService());
        PARAM_CREATION_FACTORIES = Collections.unmodifiableList(PARAMETER_SERVICE_PROVIDERS.stream().map(a -> a.getParameterCreationFactory()).collect(Collectors.toList()));
        ANNOTATION_HELPER_MAP = Collections.unmodifiableMap(PARAMETER_SERVICE_PROVIDERS.stream().map(a -> a.getParameterAnnotationHelperMap()).collect(WeakHashMap::new, Map::putAll, Map::putAll));
    }

    public static class ParameterService
    implements ParameterServiceProvider {
        @Override
        public Map<Class, ParamAnnotationHelper> getParameterAnnotationHelperMap() {
            WeakHashMap<Class, ParamAnnotationHelper> m = new WeakHashMap<Class, ParamAnnotationHelper>();
            m.put(Context.class, new ParamAnnotationHelper<Context>(){

                @Override
                public String getValueOf(Context a) {
                    return null;
                }

                @Override
                public Source getSource() {
                    return Source.CONTEXT;
                }
            });
            m.put(CookieParam.class, new ParamAnnotationHelper<CookieParam>(){

                @Override
                public String getValueOf(CookieParam a) {
                    return a.value();
                }

                @Override
                public Source getSource() {
                    return Source.COOKIE;
                }
            });
            m.put(FormParam.class, new ParamAnnotationHelper<FormParam>(){

                @Override
                public String getValueOf(FormParam a) {
                    return a.value();
                }

                @Override
                public Source getSource() {
                    return Source.FORM;
                }
            });
            m.put(HeaderParam.class, new ParamAnnotationHelper<HeaderParam>(){

                @Override
                public String getValueOf(HeaderParam a) {
                    return a.value();
                }

                @Override
                public Source getSource() {
                    return Source.HEADER;
                }
            });
            m.put(MatrixParam.class, new ParamAnnotationHelper<MatrixParam>(){

                @Override
                public String getValueOf(MatrixParam a) {
                    return a.value();
                }

                @Override
                public Source getSource() {
                    return Source.MATRIX;
                }
            });
            m.put(PathParam.class, new ParamAnnotationHelper<PathParam>(){

                @Override
                public String getValueOf(PathParam a) {
                    return a.value();
                }

                @Override
                public Source getSource() {
                    return Source.PATH;
                }
            });
            m.put(QueryParam.class, new ParamAnnotationHelper<QueryParam>(){

                @Override
                public String getValueOf(QueryParam a) {
                    return a.value();
                }

                @Override
                public Source getSource() {
                    return Source.QUERY;
                }
            });
            m.put(Suspended.class, new ParamAnnotationHelper<Suspended>(){

                @Override
                public String getValueOf(Suspended a) {
                    return Suspended.class.getName();
                }

                @Override
                public Source getSource() {
                    return Source.SUSPENDED;
                }
            });
            m.put(BeanParam.class, new ParamAnnotationHelper<BeanParam>(){

                @Override
                public String getValueOf(BeanParam a) {
                    return null;
                }

                @Override
                public Source getSource() {
                    return Source.BEAN_PARAM;
                }
            });
            return m;
        }

        public ParamCreationFactory<Parameter> getParameterCreationFactory() {
            return new ParamCreationFactory<Parameter>(){

                @Override
                public boolean isFor(Class<?> clazz) {
                    return clazz == Parameter.class;
                }

                @Override
                public Parameter createParameter(Annotation[] markers, Annotation marker, Source source, String sourceName, Class<?> rawType, Type type, boolean encoded, String defaultValue) {
                    return new Parameter(markers, marker, source, sourceName, rawType, type, encoded, defaultValue);
                }

                @Override
                public Parameter createBeanParameter(Annotation[] markers, Annotation marker, Source source, String sourceName, Class<?> rawType, Type type, boolean encoded, String defaultValue) {
                    return this.createParameter(markers, marker, source, sourceName, rawType, type, encoded, defaultValue);
                }
            };
        }
    }

    public static interface ParamCreationFactory<PARAMETER extends Parameter> {
        public boolean isFor(Class<?> var1);

        public PARAMETER createParameter(Annotation[] var1, Annotation var2, Source var3, String var4, Class<?> var5, Type var6, boolean var7, String var8);

        public PARAMETER createBeanParameter(Annotation[] var1, Annotation var2, Source var3, String var4, Class<?> var5, Type var6, boolean var7, String var8);
    }

    public static interface ParamAnnotationHelper<T extends Annotation> {
        public String getValueOf(T var1);

        public Source getSource();
    }

    public static enum Source {
        CONTEXT,
        COOKIE,
        ENTITY,
        FORM,
        HEADER,
        URI,
        MATRIX,
        PATH,
        QUERY,
        SUSPENDED,
        BEAN_PARAM,
        UNKNOWN;

    }
}

