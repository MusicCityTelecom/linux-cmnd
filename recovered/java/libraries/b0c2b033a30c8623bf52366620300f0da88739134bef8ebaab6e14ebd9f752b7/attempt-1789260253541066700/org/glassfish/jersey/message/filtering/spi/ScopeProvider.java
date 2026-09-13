/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.glassfish.jersey.message.filtering.EntityFiltering;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface ScopeProvider {
    public static final String DEFAULT_SCOPE = EntityFiltering.class.getName();

    public Set<String> getFilteringScopes(Annotation[] var1, boolean var2);
}

