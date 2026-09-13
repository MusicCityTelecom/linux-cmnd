/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.reflect;

import org.aopalliance.reflect.Class;
import org.aopalliance.reflect.CodeLocator;
import org.aopalliance.reflect.Field;
import org.aopalliance.reflect.Method;

public interface Code {
    public CodeLocator getLocator();

    public CodeLocator getCallLocator(Method var1);

    public CodeLocator getReadLocator(Field var1);

    public CodeLocator getWriteLocator(Field var1);

    public CodeLocator getThrowLocator(Class var1);

    public CodeLocator getCatchLocator(Class var1);
}

