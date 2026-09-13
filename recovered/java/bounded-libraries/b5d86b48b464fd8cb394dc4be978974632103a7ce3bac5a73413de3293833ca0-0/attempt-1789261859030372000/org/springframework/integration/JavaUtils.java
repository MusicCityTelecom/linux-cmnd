/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public final class JavaUtils {
    public static final JavaUtils INSTANCE = new JavaUtils();

    private JavaUtils() {
    }

    public <T> JavaUtils acceptIfCondition(boolean condition, @Nullable T value, Consumer<T> consumer) {
        if (condition) {
            consumer.accept(value);
        }
        return this;
    }

    public <T> JavaUtils acceptIfNotNull(@Nullable T value, Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
        return this;
    }

    public JavaUtils acceptIfHasText(String value, Consumer<String> consumer) {
        if (StringUtils.hasText((String)value)) {
            consumer.accept(value);
        }
        return this;
    }

    public <T> JavaUtils acceptIfNotEmpty(List<T> value, Consumer<List<T>> consumer) {
        if (!CollectionUtils.isEmpty(value)) {
            consumer.accept(value);
        }
        return this;
    }

    public <T> JavaUtils acceptIfNotEmpty(T[] value, Consumer<T[]> consumer) {
        if (!ObjectUtils.isEmpty((Object[])value)) {
            consumer.accept(value);
        }
        return this;
    }

    public <T1, T2> JavaUtils acceptIfCondition(boolean condition, T1 t1, T2 t2, BiConsumer<T1, T2> consumer) {
        if (condition) {
            consumer.accept(t1, t2);
        }
        return this;
    }

    public <T1, T2> JavaUtils acceptIfNotNull(T1 t1, T2 t2, BiConsumer<T1, T2> consumer) {
        if (t2 != null) {
            consumer.accept(t1, t2);
        }
        return this;
    }

    public <T> JavaUtils acceptIfHasText(T t1, String value, BiConsumer<T, String> consumer) {
        if (StringUtils.hasText((String)value)) {
            consumer.accept(t1, value);
        }
        return this;
    }
}

