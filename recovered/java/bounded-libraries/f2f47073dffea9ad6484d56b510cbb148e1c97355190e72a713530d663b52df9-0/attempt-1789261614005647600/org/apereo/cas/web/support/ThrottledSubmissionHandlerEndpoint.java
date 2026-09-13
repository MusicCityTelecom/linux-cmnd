/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.v3.oas.annotations.Operation
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan
 *  org.apereo.cas.web.BaseCasActuatorEndpoint
 *  org.apereo.cas.web.support.ThrottledSubmissionHandlerInterceptor
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.actuate.endpoint.annotation.Endpoint
 *  org.springframework.boot.actuate.endpoint.annotation.ReadOperation
 */
package org.apereo.cas.web.support;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan;
import org.apereo.cas.web.BaseCasActuatorEndpoint;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

@Endpoint(id="throttles", enableByDefault=false)
public class ThrottledSubmissionHandlerEndpoint
extends BaseCasActuatorEndpoint {
    private final ObjectProvider<AuthenticationThrottlingExecutionPlan> authenticationThrottlingExecutionPlan;

    public ThrottledSubmissionHandlerEndpoint(CasConfigurationProperties casProperties, ObjectProvider<AuthenticationThrottlingExecutionPlan> executionPlan) {
        super(casProperties);
        this.authenticationThrottlingExecutionPlan = executionPlan;
    }

    @ReadOperation
    @Operation(summary="Get throttled authentication records")
    public List getRecords() {
        return ((AuthenticationThrottlingExecutionPlan)this.authenticationThrottlingExecutionPlan.getObject()).getAuthenticationThrottleInterceptors().stream().map(entry -> (ThrottledSubmissionHandlerInterceptor)entry).filter(Objects::nonNull).map(ThrottledSubmissionHandlerInterceptor::getRecords).flatMap(Collection::stream).collect(Collectors.toList());
    }
}

