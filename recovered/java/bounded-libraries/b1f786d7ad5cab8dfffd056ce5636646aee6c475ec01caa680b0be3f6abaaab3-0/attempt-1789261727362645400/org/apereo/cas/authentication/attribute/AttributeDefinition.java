/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.authentication.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.apereo.cas.services.RegisteredService;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public interface AttributeDefinition
extends Serializable,
Comparable<AttributeDefinition> {
    public String getKey();

    public String getName();

    public boolean isScoped();

    public boolean isEncrypted();

    public String getAttribute();

    public String getPatternFormat();

    public String getScript();

    public String getCanonicalizationMode();

    public List<Object> resolveAttributeValues(List<Object> var1, String var2, RegisteredService var3, Map<String, List<Object>> var4);
}

