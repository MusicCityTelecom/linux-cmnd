/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.model.TriStateBoolean
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.web.flow;

import org.apereo.cas.util.model.TriStateBoolean;
import org.apereo.cas.web.flow.SingleSignOnParticipationRequest;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface SingleSignOnParticipationStrategy
extends Ordered {
    public static final String BEAN_NAME = "singleSignOnParticipationStrategy";

    public static SingleSignOnParticipationStrategy alwaysParticipating() {
        return context -> true;
    }

    public static SingleSignOnParticipationStrategy neverParticipating() {
        return context -> false;
    }

    public boolean isParticipating(SingleSignOnParticipationRequest var1);

    default public boolean supports(SingleSignOnParticipationRequest ssoRequest) {
        return ssoRequest.getRequestContext().isPresent() || ssoRequest.getHttpServletRequest().isPresent();
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public TriStateBoolean isCreateCookieOnRenewedAuthentication(SingleSignOnParticipationRequest context) {
        return TriStateBoolean.UNDEFINED;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

