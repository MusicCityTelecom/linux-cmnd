/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 */
package org.springframework.data.convert;

import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface ValueConversionContext<P extends PersistentProperty<P>> {
    public P getProperty();

    @Nullable
    default public Object write(@Nullable Object value) {
        return this.write(value, this.getProperty().getTypeInformation());
    }

    @Nullable
    default public <T> T write(@Nullable Object value, @NonNull Class<T> target) {
        return this.write(value, ClassTypeInformation.from(target));
    }

    @Nullable
    default public <T> T write(@Nullable Object value, @NonNull TypeInformation<T> target) {
        if (value == null || target.getType().isInstance(value)) {
            return target.getType().cast(value);
        }
        throw new IllegalStateException(String.format("%s does not provide write function that allows value conversion to target type (%s)", this.getClass(), target));
    }

    @Nullable
    default public Object read(@Nullable Object value) {
        return this.read(value, this.getProperty().getTypeInformation());
    }

    @Nullable
    default public <T> T read(@Nullable Object value, @NonNull Class<T> target) {
        return this.read(value, ClassTypeInformation.from(target));
    }

    @Nullable
    default public <T> T read(@Nullable Object value, @NonNull TypeInformation<T> target) {
        if (value == null || target.getType().isInstance(value)) {
            return target.getType().cast(value);
        }
        throw new IllegalStateException(String.format("%s does not provide read function that allows value conversion to target type (%s)", this.getClass(), target));
    }
}

