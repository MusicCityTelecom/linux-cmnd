/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.principal;

import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.principal.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Service
extends Principal {
    public static final Logger LOGGER = LoggerFactory.getLogger(Service.class);

    default public void setPrincipal(String principal) {
    }

    public void setAttributes(Map<String, List<Object>> var1);

    public String getOriginalUrl();
}

