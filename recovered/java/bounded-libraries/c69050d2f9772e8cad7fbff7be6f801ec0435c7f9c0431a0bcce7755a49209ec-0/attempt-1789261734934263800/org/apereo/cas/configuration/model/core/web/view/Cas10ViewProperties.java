/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.view;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="Cas10ViewProperties")
public class Cas10ViewProperties
implements Serializable {
    private static final long serialVersionUID = -1154879759474698223L;
    private ValidationAttributesRendererTypes attributeRendererType = ValidationAttributesRendererTypes.DEFAULT;

    @Generated
    public ValidationAttributesRendererTypes getAttributeRendererType() {
        return this.attributeRendererType;
    }

    @Generated
    public Cas10ViewProperties setAttributeRendererType(ValidationAttributesRendererTypes attributeRendererType) {
        this.attributeRendererType = attributeRendererType;
        return this;
    }

    public static enum ValidationAttributesRendererTypes {
        DEFAULT,
        VALUES_PER_LINE;

    }
}

