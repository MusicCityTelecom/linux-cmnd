/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.lang.annotation.Annotation;
import java.util.List;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.FactoryDescriptors;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.utilities.DescriptorImpl;

public interface DescriptorBuilder {
    public DescriptorBuilder named(String var1) throws IllegalArgumentException;

    public DescriptorBuilder to(Class<?> var1) throws IllegalArgumentException;

    public DescriptorBuilder to(String var1) throws IllegalArgumentException;

    public DescriptorBuilder in(Class<? extends Annotation> var1) throws IllegalArgumentException;

    public DescriptorBuilder in(String var1) throws IllegalArgumentException;

    public DescriptorBuilder qualifiedBy(Annotation var1) throws IllegalArgumentException;

    public DescriptorBuilder qualifiedBy(String var1) throws IllegalArgumentException;

    public DescriptorBuilder has(String var1, String var2) throws IllegalArgumentException;

    public DescriptorBuilder has(String var1, List<String> var2) throws IllegalArgumentException;

    public DescriptorBuilder ofRank(int var1);

    public DescriptorBuilder proxy();

    public DescriptorBuilder proxy(boolean var1);

    public DescriptorBuilder proxyForSameScope();

    public DescriptorBuilder proxyForSameScope(boolean var1);

    public DescriptorBuilder localOnly();

    public DescriptorBuilder visibility(DescriptorVisibility var1);

    public DescriptorBuilder andLoadWith(HK2Loader var1) throws IllegalArgumentException;

    public DescriptorBuilder analyzeWith(String var1);

    public DescriptorImpl build() throws IllegalArgumentException;

    public FactoryDescriptors buildFactory() throws IllegalArgumentException;

    public FactoryDescriptors buildFactory(String var1) throws IllegalArgumentException;

    public FactoryDescriptors buildFactory(Class<? extends Annotation> var1) throws IllegalArgumentException;
}

