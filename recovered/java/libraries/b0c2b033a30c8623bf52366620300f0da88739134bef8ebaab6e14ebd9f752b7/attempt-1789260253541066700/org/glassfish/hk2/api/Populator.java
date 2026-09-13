/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.io.IOException;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorFileFinder;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.PopulatorPostProcessor;

public interface Populator {
    public List<ActiveDescriptor<?>> populate(DescriptorFileFinder var1, PopulatorPostProcessor ... var2) throws IOException, MultiException;

    public List<ActiveDescriptor<?>> populate() throws IOException, MultiException;
}

