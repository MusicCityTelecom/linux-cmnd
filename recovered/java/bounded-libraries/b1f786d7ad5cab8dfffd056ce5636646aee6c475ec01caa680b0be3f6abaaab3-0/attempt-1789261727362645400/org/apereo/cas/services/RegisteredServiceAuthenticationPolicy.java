/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Set;
import org.apereo.cas.services.RegisteredServiceAuthenticationPolicyCriteria;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceAuthenticationPolicy
extends Serializable {
    public Set<String> getRequiredAuthenticationHandlers();

    public Set<String> getExcludedAuthenticationHandlers();

    public RegisteredServiceAuthenticationPolicyCriteria getCriteria();
}

