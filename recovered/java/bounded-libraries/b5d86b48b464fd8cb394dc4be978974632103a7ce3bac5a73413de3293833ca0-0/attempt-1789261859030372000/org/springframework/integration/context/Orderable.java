/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.springframework.integration.context;

import org.springframework.core.Ordered;

public interface Orderable
extends Ordered {
    public void setOrder(int var1);
}

