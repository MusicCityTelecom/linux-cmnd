/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.core.Ordered;

@FunctionalInterface
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceAttributeFilter
extends Serializable,
Ordered {
    public Map<String, List<Object>> filter(Map<String, List<Object>> var1);

    default public int getOrder() {
        return Integer.MIN_VALUE;
    }
}

