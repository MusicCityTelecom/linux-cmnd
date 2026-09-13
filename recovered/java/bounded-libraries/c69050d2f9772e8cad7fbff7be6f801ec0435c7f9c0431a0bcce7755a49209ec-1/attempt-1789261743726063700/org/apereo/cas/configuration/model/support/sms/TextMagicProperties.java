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
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-sms-textmagic")
@JsonFilter(value="TextMagicProperties")
public class TextMagicProperties
implements Serializable {
    private static final long serialVersionUID = 5645993472155203013L;
    @RequiredProperty
    private String token;
    @RequiredProperty
    private String username;
    private boolean debugging;
    private String password;
    private int readTimeout = 5000;
    private int connectTimeout = 5000;
    private String userAgent;
    private boolean verifyingSsl = true;
    private int writeTimeout;
    private String apiKey;
    private String apiKeyPrefix;

    @Generated
    public String getToken() {
        return this.token;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public boolean isDebugging() {
        return this.debugging;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public int getReadTimeout() {
        return this.readTimeout;
    }

    @Generated
    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    @Generated
    public String getUserAgent() {
        return this.userAgent;
    }

    @Generated
    public boolean isVerifyingSsl() {
        return this.verifyingSsl;
    }

    @Generated
    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    @Generated
    public String getApiKey() {
        return this.apiKey;
    }

    @Generated
    public String getApiKeyPrefix() {
        return this.apiKeyPrefix;
    }

    @Generated
    public TextMagicProperties setToken(String token) {
        this.token = token;
        return this;
    }

    @Generated
    public TextMagicProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    @Generated
    public TextMagicProperties setDebugging(boolean debugging) {
        this.debugging = debugging;
        return this;
    }

    @Generated
    public TextMagicProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    @Generated
    public TextMagicProperties setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    @Generated
    public TextMagicProperties setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    @Generated
    public TextMagicProperties setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Generated
    public TextMagicProperties setVerifyingSsl(boolean verifyingSsl) {
        this.verifyingSsl = verifyingSsl;
        return this;
    }

    @Generated
    public TextMagicProperties setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    @Generated
    public TextMagicProperties setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    @Generated
    public TextMagicProperties setApiKeyPrefix(String apiKeyPrefix) {
        this.apiKeyPrefix = apiKeyPrefix;
        return this;
    }
}

