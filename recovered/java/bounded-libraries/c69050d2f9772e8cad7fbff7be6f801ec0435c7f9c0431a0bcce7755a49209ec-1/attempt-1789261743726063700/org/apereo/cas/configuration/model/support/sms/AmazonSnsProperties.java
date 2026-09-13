/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.sms;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.aws.BaseAmazonWebServicesProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-sms-aws-sns")
@JsonFilter(value="AmazonSnsProperties")
public class AmazonSnsProperties
extends BaseAmazonWebServicesProperties {
    private static final long serialVersionUID = -3366665169030844517L;
    private String senderId;
    private String maxPrice;
    private String smsType = "Transactional";

    @Generated
    public String getSenderId() {
        return this.senderId;
    }

    @Generated
    public String getMaxPrice() {
        return this.maxPrice;
    }

    @Generated
    public String getSmsType() {
        return this.smsType;
    }

    @Generated
    public AmazonSnsProperties setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    @Generated
    public AmazonSnsProperties setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    @Generated
    public AmazonSnsProperties setSmsType(String smsType) {
        this.smsType = smsType;
        return this;
    }
}

