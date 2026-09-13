/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.simple;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.bucket4j.BaseBucket4jProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-simple-mfa")
@JsonFilter(value="CasSimpleMultifactorAuthenticationBucket4jProperties")
public class CasSimpleMultifactorAuthenticationBucket4jProperties
extends BaseBucket4jProperties {
    private static final long serialVersionUID = -2432886337199727140L;

    public CasSimpleMultifactorAuthenticationBucket4jProperties() {
        this.setEnabled(false);
    }
}

