/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.sms;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-sms-smsmode")
@JsonFilter(value="SmsModeProperties")
public class SmsModeProperties
implements Serializable {
    private static final long serialVersionUID = -4185702036613030013L;
    @RequiredProperty
    private String accessToken;
    private String messageAttribute = "message";
    private String toAttribute = "numero";
    @RequiredProperty
    private String url = "https://api.smsmode.com/http/1.6/sendSMS.do";
    private Map<String, String> headers = new HashMap<String, String>();
    private String proxyUrl;

    @Generated
    public String getAccessToken() {
        return this.accessToken;
    }

    @Generated
    public String getMessageAttribute() {
        return this.messageAttribute;
    }

    @Generated
    public String getToAttribute() {
        return this.toAttribute;
    }

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Generated
    public String getProxyUrl() {
        return this.proxyUrl;
    }

    @Generated
    public SmsModeProperties setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Generated
    public SmsModeProperties setMessageAttribute(String messageAttribute) {
        this.messageAttribute = messageAttribute;
        return this;
    }

    @Generated
    public SmsModeProperties setToAttribute(String toAttribute) {
        this.toAttribute = toAttribute;
        return this;
    }

    @Generated
    public SmsModeProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    @Generated
    public SmsModeProperties setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Generated
    public SmsModeProperties setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
        return this;
    }
}

