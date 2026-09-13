/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.reflect;

import org.aopalliance.reflect.Code;
import org.aopalliance.reflect.CodeLocator;
import org.aopalliance.reflect.Member;

public interface Method
extends Member {
    public CodeLocator getCallLocator();

    public CodeLocator getCallLocator(int var1);

    public Code getBody();
}

