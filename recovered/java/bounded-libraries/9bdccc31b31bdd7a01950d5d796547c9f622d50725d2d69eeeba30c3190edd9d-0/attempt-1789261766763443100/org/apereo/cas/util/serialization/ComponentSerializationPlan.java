/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.serialization;

import java.util.Collection;

public interface ComponentSerializationPlan {
    public void registerSerializableClass(Class var1);

    public void registerSerializableClass(Class var1, Integer var2);

    public Collection<Class> getRegisteredClasses();
}

