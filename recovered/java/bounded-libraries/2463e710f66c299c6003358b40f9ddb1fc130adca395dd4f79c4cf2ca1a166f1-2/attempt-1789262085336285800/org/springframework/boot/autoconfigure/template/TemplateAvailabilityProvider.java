/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 */
package org.springframework.boot.autoconfigure.template;

import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

@FunctionalInterface
public interface TemplateAvailabilityProvider {
    public boolean isTemplateAvailable(String var1, Environment var2, ClassLoader var3, ResourceLoader var4);
}

