/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 */
package org.springframework.boot.actuate.web.mappings;

import org.springframework.context.ApplicationContext;

public interface MappingDescriptionProvider {
    public String getMappingName();

    public Object describeMappings(ApplicationContext var1);
}

