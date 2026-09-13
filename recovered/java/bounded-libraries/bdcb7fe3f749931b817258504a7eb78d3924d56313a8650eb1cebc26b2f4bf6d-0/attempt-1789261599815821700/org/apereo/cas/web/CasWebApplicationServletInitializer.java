/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.spring.boot.AbstractCasSpringBootServletInitializer
 *  org.springframework.boot.Banner
 */
package org.apereo.cas.web;

import java.util.List;
import org.apereo.cas.CasEmbeddedContainerUtils;
import org.apereo.cas.util.spring.boot.AbstractCasSpringBootServletInitializer;
import org.apereo.cas.web.CasWebApplication;
import org.springframework.boot.Banner;

public class CasWebApplicationServletInitializer
extends AbstractCasSpringBootServletInitializer {
    public CasWebApplicationServletInitializer() {
        super(List.of(CasWebApplication.class), (Banner)CasEmbeddedContainerUtils.getCasBannerInstance(), CasEmbeddedContainerUtils.getApplicationStartup());
    }
}

