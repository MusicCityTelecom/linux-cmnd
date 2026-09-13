/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.reflect;

import org.aopalliance.reflect.CodeLocator;
import org.aopalliance.reflect.Member;

public interface Field
extends Member {
    public CodeLocator getReadLocator();

    public CodeLocator getReadLocator(int var1);

    public CodeLocator getWriteLocator();

    public CodeLocator getWriteLocator(int var1);
}

