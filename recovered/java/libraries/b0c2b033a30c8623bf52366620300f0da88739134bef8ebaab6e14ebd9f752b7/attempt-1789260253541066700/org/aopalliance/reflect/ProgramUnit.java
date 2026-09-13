/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.reflect;

import org.aopalliance.reflect.Metadata;
import org.aopalliance.reflect.UnitLocator;

public interface ProgramUnit {
    public UnitLocator getLocator();

    public Metadata getMetadata(Object var1);

    public Metadata[] getMetadatas();

    public void addMetadata(Metadata var1);

    public void removeMetadata(Object var1);
}

