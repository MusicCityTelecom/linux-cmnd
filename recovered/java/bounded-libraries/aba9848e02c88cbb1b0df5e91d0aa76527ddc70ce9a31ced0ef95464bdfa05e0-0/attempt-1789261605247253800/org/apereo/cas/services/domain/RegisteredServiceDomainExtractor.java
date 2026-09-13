/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.services.domain;

@FunctionalInterface
public interface RegisteredServiceDomainExtractor {
    public static final String DOMAIN_DEFAULT = "default";

    public String extract(String var1);
}

