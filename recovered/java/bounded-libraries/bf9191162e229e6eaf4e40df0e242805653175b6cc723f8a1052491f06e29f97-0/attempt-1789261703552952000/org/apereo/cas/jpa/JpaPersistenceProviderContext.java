/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.jpa;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Generated;

public class JpaPersistenceProviderContext {
    private Set<String> includeEntityClasses = new LinkedHashSet<String>();

    @Generated
    public Set<String> getIncludeEntityClasses() {
        return this.includeEntityClasses;
    }

    @Generated
    public JpaPersistenceProviderContext setIncludeEntityClasses(Set<String> includeEntityClasses) {
        this.includeEntityClasses = includeEntityClasses;
        return this;
    }
}

