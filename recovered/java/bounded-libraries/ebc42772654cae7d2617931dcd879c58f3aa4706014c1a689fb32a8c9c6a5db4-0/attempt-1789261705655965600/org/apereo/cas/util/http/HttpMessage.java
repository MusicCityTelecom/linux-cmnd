/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.http;

import java.io.Serializable;
import java.net.URL;
import lombok.Generated;
import org.apereo.cas.util.EncodingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpMessage
implements Serializable {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpMessage.class);
    private static final long serialVersionUID = 2015460875654586133L;
    private static final boolean DEFAULT_ASYNCHRONOUS_CALLBACKS_ENABLED = true;
    private final URL url;
    private final String message;
    private final boolean asynchronous;
    private int responseCode;
    private String contentType = "application/x-www-form-urlencoded";

    public HttpMessage(URL url, String message) {
        this(url, message, true);
    }

    public HttpMessage(URL url, String message, boolean async) {
        this.url = url;
        this.message = this.formatOutputMessageInternal(message);
        this.asynchronous = async;
    }

    protected String formatOutputMessageInternal(String message) {
        try {
            return EncodingUtils.urlEncode(message);
        }
        catch (Exception e) {
            LOGGER.warn("Unable to encode URL " + message, (Throwable)e);
            return message;
        }
    }

    @Generated
    public String toString() {
        return "HttpMessage(url=" + this.url + ", message=" + this.message + ", asynchronous=" + this.asynchronous + ", responseCode=" + this.responseCode + ", contentType=" + this.contentType + ")";
    }

    @Generated
    public URL getUrl() {
        return this.url;
    }

    @Generated
    public String getMessage() {
        return this.message;
    }

    @Generated
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    @Generated
    public int getResponseCode() {
        return this.responseCode;
    }

    @Generated
    public String getContentType() {
        return this.contentType;
    }

    @Generated
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Generated
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}

