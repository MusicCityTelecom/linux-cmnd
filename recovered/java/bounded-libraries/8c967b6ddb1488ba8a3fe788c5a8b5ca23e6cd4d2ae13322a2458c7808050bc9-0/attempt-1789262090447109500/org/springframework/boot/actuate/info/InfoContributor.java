/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.info;

import org.springframework.boot.actuate.info.Info;

@FunctionalInterface
public interface InfoContributor {
    public void contribute(Info.Builder var1);
}

