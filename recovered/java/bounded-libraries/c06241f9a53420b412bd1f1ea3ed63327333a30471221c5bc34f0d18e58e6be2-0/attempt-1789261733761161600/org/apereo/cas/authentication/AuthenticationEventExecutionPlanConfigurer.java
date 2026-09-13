/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface AuthenticationEventExecutionPlanConfigurer
extends Ordered {
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan var1) throws Exception;

    default public String getName() {
        return (String)StringUtils.defaultIfBlank((CharSequence)this.getClass().getSimpleName(), (CharSequence)"Default");
    }

    default public int getOrder() {
        return 0;
    }
}

