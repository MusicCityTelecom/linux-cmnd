/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.mfa.simple;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-simple-mfa")
@JsonFilter(value="CasSimpleMultifactorAuthenticationTokenDefaultProperties")
public class RestfulCasSimpleMultifactorAuthenticationTokenProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = -6333748853833491119L;
}

