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

@FunctionalInterface
public interface SingleValueBinding<T extends Path<? extends S>, S> {
    public Predicate bind(T var1, S var2);
}

