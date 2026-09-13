/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 */
package org.springframework.data.convert;

import java.util.function.BiFunction;
import org.springframework.data.convert.ValueConversionContext;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface PropertyValueConverter<DV, SV, C extends ValueConversionContext<? extends PersistentProperty<?>>> {
    @Nullable
    public DV read(SV var1, C var2);

    @Nullable
    default public DV readNull(C context) {
        return null;
    }

    @Nullable
    public SV write(DV var1, C var2);

    @Nullable
    default public SV writeNull(C context) {
        return null;
    }

    public static class FunctionPropertyValueConverter<DV, SV, P extends PersistentProperty<P>>
    implements PropertyValueConverter<DV, SV, ValueConversionContext<P>> {
        private final BiFunction<DV, ValueConversionContext<P>, SV> writer;
        private final BiFunction<SV, ValueConversionContext<P>, DV> reader;

        public FunctionPropertyValueConverter(@NonNull BiFunction<DV, ValueConversionContext<P>, SV> writer, @NonNull BiFunction<SV, ValueConversionContext<P>, DV> reader) {
            this.writer = writer;
            this.reader = reader;
        }

        @Override
        @Nullable
        public SV write(@Nullable DV value, @NonNull ValueConversionContext<P> context) {
            return this.writer.apply(value, context);
        }

        @Override
        public SV writeNull(@NonNull ValueConversionContext<P> context) {
            return this.writer.apply(null, context);
        }

        @Override
        @Nullable
        public DV read(@Nullable SV value, @NonNull ValueConversionContext<P> context) {
            return this.reader.apply(value, context);
        }

        @Override
        public DV readNull(@NonNull ValueConversionContext<P> context) {
            return this.reader.apply(null, context);
        }
    }

    public static enum ObjectToObjectPropertyValueConverter implements PropertyValueConverter
    {
        INSTANCE;


        @Nullable
        public Object read(@Nullable Object value, ValueConversionContext context) {
            return value;
        }

        @Nullable
        public Object write(@Nullable Object value, ValueConversionContext context) {
            return value;
        }
    }
}

