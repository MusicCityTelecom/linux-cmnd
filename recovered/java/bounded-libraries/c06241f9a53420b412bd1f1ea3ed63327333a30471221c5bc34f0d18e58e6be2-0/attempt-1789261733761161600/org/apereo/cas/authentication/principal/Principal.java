/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.authentication.principal;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@FunctionalInterface
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface Principal
extends Serializable {
    public String getId();

    default public Map<String, List<Object>> getAttributes() {
        return new LinkedHashMap<String, List<Object>>(0);
    }
}

