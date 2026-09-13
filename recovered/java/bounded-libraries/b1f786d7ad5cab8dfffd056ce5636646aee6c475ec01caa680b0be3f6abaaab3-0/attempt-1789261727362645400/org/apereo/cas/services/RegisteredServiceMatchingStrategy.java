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
import org.apereo.cas.services.RegisteredService;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
@FunctionalInterface
public interface RegisteredServiceMatchingStrategy
extends Serializable {
    public boolean matches(RegisteredService var1, String var2);
}

