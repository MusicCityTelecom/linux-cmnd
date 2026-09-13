/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.text.ParseException;
import java.util.Date;
import javax.inject.Singleton;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.inject.ExtractorException;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.message.internal.HttpDateFormat;

@Singleton
public class ParamConverters {

    @Singleton
    public static class AggregatedProvider
    implements ParamConverterProvider {
        private final ParamConverterProvider[] providers = new ParamConverterProvider[]{new DateProvider(), new TypeFromStringEnum(), new TypeValueOf(), new CharacterProvider(), new TypeFromString(), new StringConstructor()};

        @Override
        public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
            for (ParamConverterProvider p : this.providers) {
                ParamConverter<T> reader = p.getConverter(rawType, genericType, annotations);
                if (reader == null) continue;
                return reader;
            }
            return null;
        }
    }

    @Singleton
    public static class DateProvider
    implements ParamConverterProvider {
        @Override
        public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
            return rawType != Date.class ? null : new ParamConverter<T>(){

                @Override
                public T fromString(String value) {
                    if (value == null) {
                        throw new IllegalArgumentException(LocalizationMessages.METHOD_PARAMETER_CANNOT_BE_NULL("value"));
                    }
                    try {
                        return rawType.cast(HttpDateFormat.readDate(value));
                    }
                    catch (ParseException ex) {
                        throw new ExtractorException(ex);
                    }
                }

                @Override
                public String toString(T value) throws IllegalArgumentException {
                    if (value == null) {
                        throw new IllegalArgumentException(LocalizationMessages.METHOD_PARAMETER_CANNOT_BE_NULL("value"));
                    }
                    return value.toString();
                }
            };
        }
    }

    @Singleton
    public static class CharacterProvider
    implements ParamConverterProvider {
        @Override
        public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
            if (rawType.equals(Character.class)) {
                return new ParamConverter<T>(){

                    @Override
                    public T fromString(String value) {
                        if (value == null || value.isEmpty()) {
                            return null;
                        }
                        if (value.length() == 1) {
                            return rawType.cast(Character.valueOf(value.charAt(0)));
                        }
                        throw new ExtractorException(LocalizationMessages.ERROR_PARAMETER_INVALID_CHAR_VALUE(value));
                    }

                    @Override
                    public String toString(T value) {
                        if (value == null) {
                            throw new IllegalArgumentException(LocalizationMessages.METHOD_PARAMETER_CANNOT_BE_NULL("value"));
                        }
                        return value.toString();
                    }
                };
            }
            return null;
        }
    }

    @Singleton
    public static class TypeFromStringEnum
    extends TypeFromString {
        @Override
        public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
            return !Enum.class.isAssignableFrom(rawType) ? null : super.getConverter(rawType, genericType, annotations);
        }
    }

    @Singleton
    public static class TypeFromString
    implements ParamConverterProvider {
        @Override
        public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
            final Method fromStringMethod = AccessController.doPrivileged(ReflectionHelper.getFromStringStringMethodPA(rawType));
            return fromStringMethod == null ? null : new AbstractStringReader<T>(){

                @Override
                public T _fromString(String value) throws Exception {
                    return rawType.cast(fromStringMethod.invoke(null, value));
                }
            };
        }
    }

    @Singleton
    public static class TypeValueOf
    implements ParamConverterProvider {
        @Override
        public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
            final Method valueOf = AccessController.doPrivileged(ReflectionHelper.getValueOfStringMethodPA(rawType));
            return valueOf == null ? null : new AbstractStringReader<T>(){

                @Override
                public T _fromString(String value) throws Exception {
                    return rawType.cast(valueOf.invoke(null, value));
                }
            };
        }
    }

    @Singleton
    public static class StringConstructor
    implements ParamConverterProvider {
        @Override
        public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
            final Constructor constructor = AccessController.doPrivileged(ReflectionHelper.getStringConstructorPA(rawType));
            return constructor == null ? null : new AbstractStringReader<T>(){

                @Override
                protected T _fromString(String value) throws Exception {
                    return rawType.cast(constructor.newInstance(value));
                }
            };
        }
    }

    private static abstract class AbstractStringReader<T>
    implements ParamConverter<T> {
        private AbstractStringReader() {
        }

        @Override
        public T fromString(String value) {
            if (value == null) {
                throw new IllegalArgumentException(LocalizationMessages.METHOD_PARAMETER_CANNOT_BE_NULL("value"));
            }
            try {
                return this._fromString(value);
            }
            catch (InvocationTargetException ex) {
                if (value.isEmpty()) {
                    return null;
                }
                Throwable cause = ex.getCause();
                if (cause instanceof WebApplicationException) {
                    throw (WebApplicationException)cause;
                }
                throw new ExtractorException(cause);
            }
            catch (Exception ex) {
                throw new ProcessingException(ex);
            }
        }

        protected abstract T _fromString(String var1) throws Exception;

        @Override
        public String toString(T value) throws IllegalArgumentException {
            if (value == null) {
                throw new IllegalArgumentException(LocalizationMessages.METHOD_PARAMETER_CANNOT_BE_NULL("value"));
            }
            return value.toString();
        }
    }
}

