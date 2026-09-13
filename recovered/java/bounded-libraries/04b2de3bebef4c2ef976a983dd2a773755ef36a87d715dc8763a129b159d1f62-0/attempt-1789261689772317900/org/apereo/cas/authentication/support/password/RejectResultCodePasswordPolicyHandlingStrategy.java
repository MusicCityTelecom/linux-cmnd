/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.support.password;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.support.password.DefaultPasswordPolicyHandlingStrategy;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RejectResultCodePasswordPolicyHandlingStrategy<AuthnResponse>
extends DefaultPasswordPolicyHandlingStrategy<AuthnResponse> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RejectResultCodePasswordPolicyHandlingStrategy.class);
    private static final String DEFAULT_REJECTED_RESULT_CODE = "INVALID_CREDENTIAL";
    private final Set<String> resultCodes;

    public RejectResultCodePasswordPolicyHandlingStrategy() {
        this(CollectionUtils.wrapSet((Object)DEFAULT_REJECTED_RESULT_CODE));
    }

    public boolean supports(AuthnResponse response) {
        if (response == null) {
            LOGGER.debug("Unable to support authentication response given none is provided");
            return false;
        }
        if (!this.isAuthenticationResponseWithResult(response)) {
            LOGGER.debug("Unable to support authentication response [{}] with a negative/false result", response);
            return false;
        }
        Collection<String> currentCodes = this.getAuthenticationResponseResultCodes(response);
        Optional<String> result = this.resultCodes.stream().filter(currentCodes::contains).findAny();
        if (result.isPresent()) {
            LOGGER.debug("Unable to support authentication response [{}] with a blocked authentication result code [{}]", response, (Object)result.get());
            return false;
        }
        LOGGER.debug("Authentication response [{}] is supported by password policy handling strategy [{}]", response, (Object)this.getClass().getSimpleName());
        return true;
    }

    protected boolean isAuthenticationResponseWithResult(AuthnResponse response) {
        return false;
    }

    protected Collection<String> getAuthenticationResponseResultCodes(AuthnResponse response) {
        return new ArrayList<String>(0);
    }

    @Generated
    public RejectResultCodePasswordPolicyHandlingStrategy(Set<String> resultCodes) {
        this.resultCodes = resultCodes;
    }
}

