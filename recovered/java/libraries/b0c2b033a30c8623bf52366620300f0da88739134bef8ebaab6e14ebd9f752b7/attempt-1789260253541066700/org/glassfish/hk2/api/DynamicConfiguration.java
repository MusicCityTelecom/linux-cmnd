/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.FactoryDescriptors;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.TwoPhaseResource;

public interface DynamicConfiguration {
    public <T> ActiveDescriptor<T> bind(Descriptor var1);

    public <T> ActiveDescriptor<T> bind(Descriptor var1, boolean var2);

    public FactoryDescriptors bind(FactoryDescriptors var1);

    public FactoryDescriptors bind(FactoryDescriptors var1, boolean var2);

    public <T> ActiveDescriptor<T> addActiveDescriptor(ActiveDescriptor<T> var1) throws IllegalArgumentException;

    public <T> ActiveDescriptor<T> addActiveDescriptor(ActiveDescriptor<T> var1, boolean var2) throws IllegalArgumentException;

    public <T> ActiveDescriptor<T> addActiveDescriptor(Class<T> var1) throws MultiException, IllegalArgumentException;

    public <T> FactoryDescriptors addActiveFactoryDescriptor(Class<? extends Factory<T>> var1) throws MultiException, IllegalArgumentException;

    public void addUnbindFilter(Filter var1) throws IllegalArgumentException;

    public void addIdempotentFilter(Filter ... var1) throws IllegalArgumentException;

    public void registerTwoPhaseResources(TwoPhaseResource ... var1);

    public void commit() throws MultiException;
}

