/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.interrupt;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-interrupt-webflow")
@JsonFilter(value="RegexInterruptProperties")
public class RegexInterruptProperties
implements Serializable {
    private static final long serialVersionUID = 2169027840047126083L;
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
    public RegexInterruptProperties setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @Generated
    public RegexInterruptProperties setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }
}

