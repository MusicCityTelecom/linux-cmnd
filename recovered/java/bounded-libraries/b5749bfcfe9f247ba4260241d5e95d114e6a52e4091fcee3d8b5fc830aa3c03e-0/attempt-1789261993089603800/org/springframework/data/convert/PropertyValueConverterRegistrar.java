/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.NonNull
 *  org.springframework.util.Assert
 */
package org.springframework.data.convert;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.SimplePropertyValueConverterRegistry;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.convert.ValueConverterRegistry;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.util.MethodInvocationRecorder;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

public class PropertyValueConverterRegistrar<P extends PersistentProperty<P>> {
    private final SimplePropertyValueConverterRegistry<P> registry = new SimplePropertyValueConverterRegistry();

    public <T, S> WritingConverterRegistrationBuilder<T, S, P> registerConverter(Class<T> type, Function<T, S> property) {
        String propertyName = MethodInvocationRecorder.forProxyOf(type).record(property).getPropertyPath().orElseThrow(() -> new IllegalArgumentException("Cannot obtain property name"));
        return new WritingConverterRegistrationBuilder(type, propertyName, this);
    }

    public <T, S> WritingConverterRegistrationBuilder<T, S, P> registerConverter(Class<T> type, String propertyName, Class<S> propertyType) {
        return new WritingConverterRegistrationBuilder(type, propertyName, this);
    }

    public PropertyValueConverterRegistrar<P> registerConverter(Class<?> type, String path, PropertyValueConverter<?, ?, ? extends ValueConversionContext<?>> converter) {
        this.registry.registerConverter(type, path, converter);
        return this;
    }

    public void registerConvertersIn(@NonNull ValueConverterRegistry<P> target) {
        Assert.notNull(target, (String)"Target registry must not be null");
        this.registry.getConverterRegistrationMap().forEach((key, value) -> target.registerConverter(key.type, key.path, (PropertyValueConverter)value));
    }

    @NonNull
    public ValueConverterRegistry<P> buildRegistry() {
        return new SimplePropertyValueConverterRegistry<P>(this.registry);
    }

    public static class ReadingConverterRegistrationBuilder<T, S, R, P extends PersistentProperty<P>> {
        private final WritingConverterRegistrationBuilder<T, S, P> origin;
        private final BiFunction<S, ValueConversionContext<P>, R> writer;

        ReadingConverterRegistrationBuilder(@NonNull WritingConverterRegistrationBuilder<T, S, P> origin, @NonNull BiFunction<S, ValueConversionContext<P>, R> writer) {
            this.origin = origin;
            this.writer = writer;
        }

        public PropertyValueConverterRegistrar<P> readingAsIs() {
            return this.reading((R source, ValueConversionContext<P> context) -> source);
        }

        public PropertyValueConverterRegistrar<P> reading(Function<R, S> reader) {
            return this.reading((R source, ValueConversionContext<P> context) -> reader.apply(source));
        }

        public PropertyValueConverterRegistrar<P> reading(BiFunction<R, ValueConversionContext<P>, S> reader) {
            ((WritingConverterRegistrationBuilder)this.origin).registration.accept(new PropertyValueConverter.FunctionPropertyValueConverter<S, R, P>(this.writer, reader));
            return ((WritingConverterRegistrationBuilder)this.origin).config;
        }
    }

    public static class WritingConverterRegistrationBuilder<T, S, P extends PersistentProperty<P>> {
        private final Consumer<PropertyValueConverter<T, S, ValueConversionContext<P>>> registration;
        private final PropertyValueConverterRegistrar<P> config;

        WritingConverterRegistrationBuilder(Class<T> type, String property, @NonNull PropertyValueConverterRegistrar<P> config) {
            this.config = config;
            this.registration = converter -> config.registerConverter((Class<?>)type, property, (PropertyValueConverter<?, ?, ValueConversionContext<?>>)converter);
        }

        public ReadingConverterRegistrationBuilder<T, S, S, P> writingAsIs() {
            return this.writing((S source, ValueConversionContext<P> context) -> source);
        }

        public <R> ReadingConverterRegistrationBuilder<T, S, R, P> writing(Function<S, R> writer) {
            return this.writing((S source, ValueConversionContext<P> context) -> writer.apply(source));
        }

        public <R> ReadingConverterRegistrationBuilder<T, S, R, P> writing(BiFunction<S, ValueConversionContext<P>, R> writer) {
            return new ReadingConverterRegistrationBuilder(this, writer);
        }
    }
}

