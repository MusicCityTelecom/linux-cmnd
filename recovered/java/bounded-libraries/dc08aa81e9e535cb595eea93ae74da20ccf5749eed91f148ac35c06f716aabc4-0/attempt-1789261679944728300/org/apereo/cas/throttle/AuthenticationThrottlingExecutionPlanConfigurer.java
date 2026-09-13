/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.throttle;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan;

@FunctionalInterface
public interface AuthenticationThrottlingExecutionPlanConfigurer {
    public void configureAuthenticationThrottlingExecutionPlan(AuthenticationThrottlingExecutionPlan var1);

    default public String getName() {
        return (String)StringUtils.defaultIfBlank((CharSequence)this.getClass().getSimpleName(), (CharSequence)"Default");
    }
}

