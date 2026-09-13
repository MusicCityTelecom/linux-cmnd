/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.intercept;

import java.lang.reflect.Field;
import org.aopalliance.intercept.Joinpoint;

public interface FieldAccess
extends Joinpoint {
    public static final int READ = 0;
    public static final int WRITE = 1;

    public Field getField();

    public Object getValueToSet();

    public int getAccessType();
}

