/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.origin;

import org.springframework.boot.origin.Origin;

@FunctionalInterface
public interface OriginProvider {
    public Origin getOrigin();
}

