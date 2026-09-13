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
@JsonFilter(value="CustomLoginFieldViewProperties")
public class CustomLoginFieldViewProperties
implements Serializable {
    private static final long serialVersionUID = -7122345678378395582L;
    private String messageBundleKey;
    private boolean required;
    private String converter;

    @Generated
    public String getMessageBundleKey() {
        return this.messageBundleKey;
    }

    @Generated
    public boolean isRequired() {
        return this.required;
    }

    @Generated
    public String getConverter() {
        return this.converter;
    }

    @Generated
    public void setMessageBundleKey(String messageBundleKey) {
        this.messageBundleKey = messageBundleKey;
    }

    @Generated
    public void setRequired(boolean required) {
        this.required = required;
    }

    @Generated
    public void setConverter(String converter) {
        this.converter = converter;
    }
}

