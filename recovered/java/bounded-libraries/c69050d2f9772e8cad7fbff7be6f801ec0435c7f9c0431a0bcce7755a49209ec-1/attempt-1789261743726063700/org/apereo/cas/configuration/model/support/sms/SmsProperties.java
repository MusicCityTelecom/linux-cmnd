/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.configuration.model.support.sms;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiresModule(name="cas-server-core-util", automated=true)
@JsonFilter(value="SmsProperties")
public class SmsProperties
implements Serializable {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsProperties.class);
    private static final long serialVersionUID = -3713886839517507306L;
    @RequiredProperty
    private String text;
    @RequiredProperty
    private String from;
    @RequiredProperty
    private String attributeName = "phone";

    @JsonIgnore
    public boolean isDefined() {
        return StringUtils.isNotBlank((CharSequence)this.getText()) && StringUtils.isNotBlank((CharSequence)this.getFrom());
    }

    @Generated
    public String getText() {
        return this.text;
    }

    @Generated
    public String getFrom() {
        return this.from;
    }

    @Generated
    public String getAttributeName() {
        return this.attributeName;
    }

    @Generated
    public SmsProperties setText(String text) {
        this.text = text;
        return this;
    }

    @Generated
    public SmsProperties setFrom(String from) {
        this.from = from;
        return this;
    }

    @Generated
    public SmsProperties setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }
}

