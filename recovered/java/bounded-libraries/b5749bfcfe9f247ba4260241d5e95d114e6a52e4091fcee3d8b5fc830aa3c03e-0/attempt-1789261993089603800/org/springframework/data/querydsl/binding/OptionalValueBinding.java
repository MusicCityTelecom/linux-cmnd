/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.querydsl.core.types.Path
 *  com.querydsl.core.types.Predicate
 */
package org.springframework.data.querydsl.binding;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import java.util.Optional;

@FunctionalInterface
public interface OptionalValueBinding<T extends Path<? extends S>, S> {
    public Optional<Predicate> bind(T var1, Optional<? extends S> var2);
}

