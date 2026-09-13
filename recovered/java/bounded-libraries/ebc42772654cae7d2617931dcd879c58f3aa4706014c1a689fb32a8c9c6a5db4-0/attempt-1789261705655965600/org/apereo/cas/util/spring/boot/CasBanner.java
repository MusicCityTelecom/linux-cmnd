/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.Banner
 *  org.springframework.core.env.Environment
 */
package org.apereo.cas.util.spring.boot;

import java.util.Formatter;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public interface CasBanner
extends Banner {
    public String getTitle();

    default public void injectEnvironmentInfo(Formatter formatter, Environment environment, Class<?> sourceClass) {
    }
}

