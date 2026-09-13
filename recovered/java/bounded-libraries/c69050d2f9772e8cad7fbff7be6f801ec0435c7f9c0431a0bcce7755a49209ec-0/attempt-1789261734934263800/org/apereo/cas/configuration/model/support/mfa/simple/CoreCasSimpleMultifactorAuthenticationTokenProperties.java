/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.mfa.simple;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-simple-mfa")
@JsonFilter(value="CasSimpleMultifactorAuthenticationTokenDefaultProperties")
public class CoreCasSimpleMultifactorAuthenticationTokenProperties
implements Serializable {
    private static final long serialVersionUID = -6333748853833491119L;
    private long timeToKillInSeconds = 30L;
    private int tokenLength = 6;

    @Generated
    public long getTimeToKillInSeconds() {
        return this.timeToKillInSeconds;
    }

    @Generated
    public int getTokenLength() {
        return this.tokenLength;
    }

    @Generated
    public CoreCasSimpleMultifactorAuthenticationTokenProperties setTimeToKillInSeconds(long timeToKillInSeconds) {
        this.timeToKillInSeconds = timeToKillInSeconds;
        return this;
    }

    @Generated
    public CoreCasSimpleMultifactorAuthenticationTokenProperties setTokenLength(int tokenLength) {
        this.tokenLength = tokenLength;
        return this;
    }
}

