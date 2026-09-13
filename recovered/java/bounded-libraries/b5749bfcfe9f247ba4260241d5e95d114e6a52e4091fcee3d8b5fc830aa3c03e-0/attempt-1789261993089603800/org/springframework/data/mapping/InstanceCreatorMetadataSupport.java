/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.data.mapping;

import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.data.mapping.InstanceCreatorMetadata;
import org.springframework.data.mapping.Parameter;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.util.Assert;

class InstanceCreatorMetadataSupport<T, P extends PersistentProperty<P>>
implements InstanceCreatorMetadata<P> {
    private final Executable executable;
    private final List<Parameter<Object, P>> parameters;
    private final Map<PersistentProperty<?>, Boolean> isPropertyParameterCache = new ConcurrentHashMap();

    @SafeVarargs
    public InstanceCreatorMetadataSupport(Executable executable, Parameter<Object, P> ... parameters) {
        Assert.notNull((Object)executable, (String)"Executable must not be null");
        Assert.notNull(parameters, (String)"Parameters must not be null");
        this.executable = executable;
        this.parameters = Arrays.asList(parameters);
    }

    Executable getExecutable() {
        return this.executable;
    }

    @Override
    public List<Parameter<Object, P>> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean isCreatorParameter(PersistentProperty<?> property) {
        Assert.notNull(property, (String)"Property must not be null");
        Boolean cached = this.isPropertyParameterCache.get(property);
        if (cached != null) {
            return cached;
        }
        boolean result = this.doGetIsCreatorParameter(property);
        this.isPropertyParameterCache.put(property, result);
        return result;
    }

    public String toString() {
        return this.executable.toString();
    }

    private boolean doGetIsCreatorParameter(PersistentProperty<?> property) {
        for (Parameter<Object, P> parameter : this.parameters) {
            if (!parameter.maps(property)) continue;
            return true;
        }
        return false;
    }
}

