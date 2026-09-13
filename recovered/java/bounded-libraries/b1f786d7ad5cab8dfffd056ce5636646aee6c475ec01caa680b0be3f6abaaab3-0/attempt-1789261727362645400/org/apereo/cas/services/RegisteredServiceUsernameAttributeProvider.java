/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;

@FunctionalInterface
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceUsernameAttributeProvider
extends Serializable {
    public String resolveUsername(Principal var1, Service var2, RegisteredService var3);
}

