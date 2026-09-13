/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  lombok.NonNull
 *  org.apereo.cas.authentication.ContextualAuthenticationPolicy
 *  org.apereo.cas.ticket.AbstractTicketException
 */
package org.apereo.cas.ticket;

import lombok.Generated;
import lombok.NonNull;
import org.apereo.cas.authentication.ContextualAuthenticationPolicy;
import org.apereo.cas.ticket.AbstractTicketException;

public class UnsatisfiedAuthenticationPolicyException
extends AbstractTicketException {
    private static final long serialVersionUID = -827432780367197133L;
    private static final String CODE = "UNSATISFIED_AUTHN_POLICY";
    private final ContextualAuthenticationPolicy<?> policy;

    public UnsatisfiedAuthenticationPolicyException(@NonNull ContextualAuthenticationPolicy<?> policy) {
        super(policy.getCode().orElse(CODE));
        if (policy == null) {
            throw new NullPointerException("policy is marked non-null but is null");
        }
        this.policy = policy;
    }

    @Generated
    public ContextualAuthenticationPolicy<?> getPolicy() {
        return this.policy;
    }
}

