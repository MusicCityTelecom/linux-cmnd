/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.rest;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-rest")
@JsonFilter(value="RestRegisteredServicesProperties")
public class RestRegisteredServicesProperties
implements Serializable {
    private static final long serialVersionUID = -1822107478273171342L;
    @RequiredProperty
    private String attributeName;
    @RequiredProperty
    private String attributeValue;

    @Generated
    public String getAttributeName() {
        return this.attributeName;
    }

    @Generated
    public String getAttributeValue() {
        return this.attributeValue;
    }

    @Generated
    public RestRegisteredServicesProperties setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @Generated
    public RestRegisteredServicesProperties setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }
}

