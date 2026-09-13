/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.data.repository.core;

import java.lang.reflect.Method;
import java.util.Set;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.data.util.TypeInformation;

public interface RepositoryMetadata {
    default public Class<?> getIdType() {
        return this.getIdTypeInformation().getType();
    }

    default public Class<?> getDomainType() {
        return this.getDomainTypeInformation().getType();
    }

    public TypeInformation<?> getIdTypeInformation();

    public TypeInformation<?> getDomainTypeInformation();

    public Class<?> getRepositoryInterface();

    public TypeInformation<?> getReturnType(Method var1);

    public Class<?> getReturnedDomainClass(Method var1);

    public CrudMethods getCrudMethods();

    public boolean isPagingRepository();

    public Set<Class<?>> getAlternativeDomainTypes();

    public boolean isReactiveRepository();
}

