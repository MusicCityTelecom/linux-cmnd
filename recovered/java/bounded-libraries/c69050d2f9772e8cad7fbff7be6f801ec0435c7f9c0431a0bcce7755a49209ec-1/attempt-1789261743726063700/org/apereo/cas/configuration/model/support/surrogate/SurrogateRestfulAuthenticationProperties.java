/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.surrogate;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-surrogate-authentication-rest")
@JsonFilter(value="SurrogateRestfulAuthenticationProperties")
public class SurrogateRestfulAuthenticationProperties
extends RestEndpointProperties {
    private static final long serialVersionUID = 8152273816132989085L;
}

