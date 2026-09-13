/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.data.mapping;

import java.util.List;
import org.springframework.data.mapping.Parameter;
import org.springframework.data.mapping.PersistentProperty;

public interface InstanceCreatorMetadata<P extends PersistentProperty<P>> {
    public boolean isCreatorParameter(PersistentProperty<?> var1);

    default public boolean isParentParameter(Parameter<?, P> parameter) {
        return false;
    }

    default public int getParameterCount() {
        return this.getParameters().size();
    }

    public List<Parameter<Object, P>> getParameters();

    default public boolean hasParameters() {
        return !this.getParameters().isEmpty();
    }
}

