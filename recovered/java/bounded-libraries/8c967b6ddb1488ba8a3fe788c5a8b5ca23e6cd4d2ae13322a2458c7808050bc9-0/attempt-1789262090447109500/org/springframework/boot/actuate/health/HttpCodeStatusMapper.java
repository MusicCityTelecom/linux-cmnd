/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.health;

import org.springframework.boot.actuate.health.SimpleHttpCodeStatusMapper;
import org.springframework.boot.actuate.health.Status;

@FunctionalInterface
public interface HttpCodeStatusMapper {
    public static final HttpCodeStatusMapper DEFAULT = new SimpleHttpCodeStatusMapper();

    public int getStatusCode(Status var1);
}

