/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.reflect;

import org.aopalliance.reflect.Class;
import org.aopalliance.reflect.ProgramUnit;

public interface Member
extends ProgramUnit {
    public static final int USER_SIDE = 0;
    public static final int PROVIDER_SIDE = 1;

    public Class getDeclaringClass();

    public String getName();

    public int getModifiers();
}

