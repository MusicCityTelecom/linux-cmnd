/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 */
package org.springframework.boot.actuate.endpoint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.boot.actuate.endpoint.OperationArgumentResolver;
import org.springframework.boot.actuate.endpoint.Producible;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

public class ProducibleOperationArgumentResolver
implements OperationArgumentResolver {
    private final Supplier<List<String>> accepts;

    public ProducibleOperationArgumentResolver(Supplier<List<String>> accepts) {
        this.accepts = accepts;
    }

    @Override
    public boolean canResolve(Class<?> type) {
        return Producible.class.isAssignableFrom(type) && Enum.class.isAssignableFrom(type);
    }

    @Override
    public <T> T resolve(Class<T> type) {
        return (T)this.resolveProducible(type);
    }

    private Enum<? extends Producible<?>> resolveProducible(Class<Enum<? extends Producible<?>>> type) {
        List<String> accepts = this.accepts.get();
        List<Enum<? extends Producible<?>>> values = this.getValues(type);
        if (CollectionUtils.isEmpty(accepts)) {
            return this.getDefaultValue(values);
        }
        Enum<? extends Producible<?>> result = null;
        for (String accept : accepts) {
            for (String mimeType : MimeTypeUtils.tokenize((String)accept)) {
                result = this.mostRecent(result, this.forMimeType(values, MimeTypeUtils.parseMimeType((String)mimeType)));
            }
        }
        return result;
    }

    private Enum<? extends Producible<?>> mostRecent(Enum<? extends Producible<?>> existing, Enum<? extends Producible<?>> candidate) {
        int existingOrdinal = existing != null ? existing.ordinal() : -1;
        int candidateOrdinal = candidate != null ? candidate.ordinal() : -1;
        return candidateOrdinal > existingOrdinal ? candidate : existing;
    }

    private Enum<? extends Producible<?>> forMimeType(List<Enum<? extends Producible<?>>> values, MimeType mimeType) {
        if (mimeType.isWildcardType() && mimeType.isWildcardSubtype()) {
            return this.getDefaultValue(values);
        }
        for (Enum<Producible<?>> enum_ : values) {
            if (!mimeType.isCompatibleWith(((Producible)((Object)enum_)).getProducedMimeType())) continue;
            return enum_;
        }
        return null;
    }

    private List<Enum<? extends Producible<?>>> getValues(Class<Enum<? extends Producible<?>>> type) {
        List<Enum<Producible<?>>> values = Arrays.asList(type.getEnumConstants());
        Collections.reverse(values);
        Assert.state((values.stream().filter(this::isDefault).count() <= 1L ? 1 : 0) != 0, (String)("Multiple default values declared in " + type.getName()));
        return values;
    }

    private Enum<? extends Producible<?>> getDefaultValue(List<Enum<? extends Producible<?>>> values) {
        return values.stream().filter(this::isDefault).findFirst().orElseGet(() -> (Enum)values.get(0));
    }

    private boolean isDefault(Enum<? extends Producible<?>> value) {
        return ((Producible)((Object)value)).isDefault();
    }
}

