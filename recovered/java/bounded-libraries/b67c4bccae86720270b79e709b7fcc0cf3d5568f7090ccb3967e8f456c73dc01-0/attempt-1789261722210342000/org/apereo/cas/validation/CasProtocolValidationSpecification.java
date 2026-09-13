/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.validation;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.validation.Assertion;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface CasProtocolValidationSpecification
extends Ordered {
    public boolean isSatisfiedBy(Assertion var1, HttpServletRequest var2);

    default public void reset() {
    }

    default public int getOrder() {
        return 0;
    }

    default public void setRenew(boolean value) {
    }

    public static enum CasProtocolVersions {
        CAS10,
        CAS20,
        CAS30,
        SAML1;

    }
}

