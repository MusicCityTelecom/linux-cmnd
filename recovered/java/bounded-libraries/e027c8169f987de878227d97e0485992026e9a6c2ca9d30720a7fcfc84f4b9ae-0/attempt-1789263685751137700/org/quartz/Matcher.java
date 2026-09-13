/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.io.Serializable;
import org.quartz.utils.Key;

public interface Matcher<T extends Key<?>>
extends Serializable {
    public boolean isMatch(T var1);

    public int hashCode();

    public boolean equals(Object var1);
}

