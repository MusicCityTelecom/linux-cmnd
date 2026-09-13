/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.querydsl.core.types.EntityPath
 */
package org.springframework.data.querydsl;

import com.querydsl.core.types.EntityPath;

public interface EntityPathResolver {
    public <T> EntityPath<T> createPath(Class<T> var1);
}

