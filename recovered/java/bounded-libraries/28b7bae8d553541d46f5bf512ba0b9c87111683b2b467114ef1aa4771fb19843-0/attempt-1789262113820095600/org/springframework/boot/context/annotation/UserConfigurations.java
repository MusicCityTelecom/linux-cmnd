/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.PriorityOrdered
 */
package org.springframework.boot.context.annotation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.springframework.boot.context.annotation.Configurations;
import org.springframework.core.PriorityOrdered;

public class UserConfigurations
extends Configurations
implements PriorityOrdered {
    protected UserConfigurations(Collection<Class<?>> classes) {
        super(classes);
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected UserConfigurations merge(Set<Class<?>> mergedClasses) {
        return new UserConfigurations(mergedClasses);
    }

    public static UserConfigurations of(Class<?> ... classes) {
        return new UserConfigurations(Arrays.asList(classes));
    }
}

