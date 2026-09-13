/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.HK2Loader;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;

public interface ActiveDescriptorBuilder {
    public ActiveDescriptorBuilder named(String var1) throws IllegalArgumentException;

    public ActiveDescriptorBuilder to(Type var1) throws IllegalArgumentException;

    public ActiveDescriptorBuilder in(Annotation var1) throws IllegalArgumentException;

    public ActiveDescriptorBuilder in(Class<? extends Annotation> var1) throws IllegalArgumentException;

    public ActiveDescriptorBuilder qualifiedBy(Annotation var1) throws IllegalArgumentException;

    public ActiveDescriptorBuilder has(String var1, String var2) throws IllegalArgumentException;

    public ActiveDescriptorBuilder has(String var1, List<String> var2) throws IllegalArgumentException;

    public ActiveDescriptorBuilder ofRank(int var1);

    public ActiveDescriptorBuilder localOnly();

    public ActiveDescriptorBuilder visibility(DescriptorVisibility var1);

    public ActiveDescriptorBuilder proxy();

    public ActiveDescriptorBuilder proxy(boolean var1);

    public ActiveDescriptorBuilder proxyForSameScope();

    public ActiveDescriptorBuilder proxyForSameScope(boolean var1);

    public ActiveDescriptorBuilder andLoadWith(HK2Loader var1) throws IllegalArgumentException;

    public ActiveDescriptorBuilder analyzeWith(String var1);

    public ActiveDescriptorBuilder asType(Type var1);

    public <T> AbstractActiveDescriptor<T> build() throws IllegalArgumentException;

    @Deprecated
    public <T> AbstractActiveDescriptor<T> buildFactory() throws IllegalArgumentException;

    public <T> AbstractActiveDescriptor<T> buildProvideMethod() throws IllegalArgumentException;
}

