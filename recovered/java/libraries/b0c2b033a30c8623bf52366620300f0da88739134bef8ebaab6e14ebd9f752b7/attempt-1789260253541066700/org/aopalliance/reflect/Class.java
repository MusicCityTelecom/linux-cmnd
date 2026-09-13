/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.reflect;

import org.aopalliance.reflect.ClassLocator;
import org.aopalliance.reflect.Field;
import org.aopalliance.reflect.Method;
import org.aopalliance.reflect.ProgramUnit;

public interface Class
extends ProgramUnit {
    public ClassLocator getClassLocator();

    public String getName();

    public Field[] getFields();

    public Field[] getDeclaredFields();

    public Method[] getMethods();

    public Method[] getDeclaredMethods();

    public Class getSuperclass();

    public Class[] getInterfaces();
}

