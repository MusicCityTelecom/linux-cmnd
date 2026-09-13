/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 */
package org.apereo.cas.util.spring.boot;

import java.util.Formatter;
import org.springframework.core.env.Environment;

@FunctionalInterface
public interface BannerContributor {
    public void contribute(Formatter var1, Environment var2);
}

