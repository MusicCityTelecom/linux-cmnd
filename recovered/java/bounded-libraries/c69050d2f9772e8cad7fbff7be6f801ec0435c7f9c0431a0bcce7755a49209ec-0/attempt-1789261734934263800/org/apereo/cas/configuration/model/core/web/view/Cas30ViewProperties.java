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
@JsonFilter(value="Cas30ViewProperties")
public class Cas30ViewProperties
implements Serializable {
    private static final long serialVersionUID = 2345062034300650858L;
    private String success = "protocol/3.0/casServiceValidationSuccess";
    private String failure = "protocol/3.0/casServiceValidationFailure";
    private ValidationAttributesRendererTypes attributeRendererType = ValidationAttributesRendererTypes.DEFAULT;

    @Generated
    public String getSuccess() {
        return this.success;
    }

    @Generated
    public String getFailure() {
        return this.failure;
    }

    @Generated
    public ValidationAttributesRendererTypes getAttributeRendererType() {
        return this.attributeRendererType;
    }

    @Generated
    public Cas30ViewProperties setSuccess(String success) {
        this.success = success;
        return this;
    }

    @Generated
    public Cas30ViewProperties setFailure(String failure) {
        this.failure = failure;
        return this;
    }

    @Generated
    public Cas30ViewProperties setAttributeRendererType(ValidationAttributesRendererTypes attributeRendererType) {
        this.attributeRendererType = attributeRendererType;
        return this;
    }

    public static enum ValidationAttributesRendererTypes {
        DEFAULT,
        INLINE;

    }
}

