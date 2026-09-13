/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.AuthenticationPolicy
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import org.apereo.cas.authentication.AuthenticationPolicy;
import org.apereo.cas.services.RegisteredService;

@FunctionalInterface
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceAuthenticationPolicyCriteria
extends Serializable {
    @JsonIgnore
    public AuthenticationPolicy toAuthenticationPolicy(RegisteredService var1);
}

