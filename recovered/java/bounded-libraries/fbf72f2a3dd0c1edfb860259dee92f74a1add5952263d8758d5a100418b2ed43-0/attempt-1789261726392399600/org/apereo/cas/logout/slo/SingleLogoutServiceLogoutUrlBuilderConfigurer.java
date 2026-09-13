/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.logout.slo;

import org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface SingleLogoutServiceLogoutUrlBuilderConfigurer
extends Ordered {
    public SingleLogoutServiceLogoutUrlBuilder configureBuilder();

    default public int getOrder() {
        return 0;
    }
}

