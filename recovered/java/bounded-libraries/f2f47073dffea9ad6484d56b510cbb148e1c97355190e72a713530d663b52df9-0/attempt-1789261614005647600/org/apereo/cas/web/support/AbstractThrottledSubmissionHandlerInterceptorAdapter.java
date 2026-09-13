/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.model.support.throttle.ThrottleCoreProperties
 *  org.apereo.cas.configuration.model.support.throttle.ThrottleFailureProperties
 *  org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan
 *  org.apereo.cas.throttle.ThrottledRequestExecutor
 *  org.apereo.cas.util.DateTimeUtils
 *  org.apereo.cas.web.support.ThrottledSubmission
 *  org.apereo.cas.web.support.ThrottledSubmissionHandlerInterceptor
 *  org.apereo.inspektr.audit.AuditActionContext
 *  org.apereo.inspektr.common.web.ClientInfo
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.servlet.ModelAndView
 */
package org.apereo.cas.web.support;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.model.support.throttle.ThrottleCoreProperties;
import org.apereo.cas.configuration.model.support.throttle.ThrottleFailureProperties;
import org.apereo.cas.throttle.AuthenticationThrottlingExecutionPlan;
import org.apereo.cas.throttle.ThrottledRequestExecutor;
import org.apereo.cas.util.DateTimeUtils;
import org.apereo.cas.web.support.ThrottledSubmission;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerConfigurationContext;
import org.apereo.cas.web.support.ThrottledSubmissionHandlerInterceptor;
import org.apereo.inspektr.audit.AuditActionContext;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

public abstract class AbstractThrottledSubmissionHandlerInterceptorAdapter
implements ThrottledSubmissionHandlerInterceptor,
InitializingBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractThrottledSubmissionHandlerInterceptorAdapter.class);
    public static final String ACTION_THROTTLED_LOGIN_ATTEMPT = "THROTTLED_LOGIN_ATTEMPT";
    private static final double NUMBER_OF_MILLISECONDS_IN_SECOND = 1000.0;
    private final ThrottledSubmissionHandlerConfigurationContext configurationContext;
    private double thresholdRate = -1.0;

    public void afterPropertiesSet() {
        ThrottleFailureProperties throttle = this.getConfigurationContext().getCasProperties().getAuthn().getThrottle().getFailure();
        this.thresholdRate = (double)throttle.getThreshold() / (double)throttle.getRangeSeconds();
        LOGGER.trace("Calculated threshold rate as [{}]", (Object)this.thresholdRate);
    }

    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean throttled;
        if (this.isRequestIgnoredForThrottling(request, response)) {
            LOGGER.trace("Letting the request through without throttling; No request filters support it");
            return true;
        }
        boolean bl = throttled = this.throttleRequest(request, response) || this.exceedsThreshold(request);
        if (throttled) {
            ThrottleFailureProperties throttle = this.getConfigurationContext().getCasProperties().getAuthn().getThrottle().getFailure();
            LOGGER.warn("Throttling submission from [{}]. More than [{}] failed login attempts within [{}] seconds. Authentication attempt exceeds the failure threshold [{}]", new Object[]{request.getRemoteAddr(), this.thresholdRate, throttle.getRangeSeconds(), throttle.getThreshold()});
            this.recordThrottle(request);
            return this.configurationContext.getThrottledRequestResponseHandler().handle(request, response);
        }
        return true;
    }

    public final void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (this.isRequestIgnoredForThrottling(request, response)) {
            LOGGER.trace("Skipping authentication throttling for requests; no filters support it.");
            return;
        }
        boolean recordEvent = this.shouldResponseBeRecordedAsFailure(response);
        if (recordEvent) {
            LOGGER.debug("Recording submission failure for [{}]", (Object)request.getRequestURI());
            this.recordSubmissionFailure(request);
        } else {
            LOGGER.trace("Skipping to record submission failure for [{}] with response status [{}]", (Object)request.getRequestURI(), (Object)response.getStatus());
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        if (!this.isRequestIgnoredForThrottling(request, response) && this.shouldResponseBeRecordedAsFailure(response)) {
            this.recordSubmissionFailure(request);
        }
    }

    protected boolean throttleRequest(HttpServletRequest request, HttpServletResponse response) {
        ThrottledRequestExecutor executor = this.configurationContext.getThrottledRequestExecutor();
        return executor != null && executor.throttle(request, response);
    }

    protected boolean shouldResponseBeRecordedAsFailure(HttpServletResponse response) {
        int status = response.getStatus();
        return status != HttpStatus.CREATED.value() && status != HttpStatus.OK.value() && status != HttpStatus.FOUND.value();
    }

    protected void recordThrottle(HttpServletRequest request) {
    }

    protected boolean calculateFailureThresholdRateAndCompare(List<? extends ThrottledSubmission> failures) {
        if (failures.size() >= 2) {
            long lastTime = DateTimeUtils.dateOf((ChronoZonedDateTime)failures.get(0).getValue()).getTime();
            long secondToLastTime = DateTimeUtils.dateOf((ChronoZonedDateTime)failures.get(1).getValue()).getTime();
            long difference = lastTime - secondToLastTime;
            double rate = 1000.0 / (double)difference;
            LOGGER.debug("Last attempt was at [{}] and the one before that was at [{}]. Difference is [{}] calculated as rate of [{}]", new Object[]{lastTime, secondToLastTime, difference, rate});
            if (rate > this.getThresholdRate()) {
                LOGGER.warn("Authentication throttling rate [{}] exceeds the defined threshold [{}]", (Object)rate, (Object)this.getThresholdRate());
                return true;
            }
        }
        return false;
    }

    protected String getUsernameParameterFromRequest(HttpServletRequest request) {
        ThrottleCoreProperties throttle = this.getConfigurationContext().getCasProperties().getAuthn().getThrottle().getCore();
        return request.getParameter(StringUtils.defaultString((String)throttle.getUsernameParameter(), (String)"username"));
    }

    protected Date getFailureInRangeCutOffDate() {
        ThrottleFailureProperties throttle = this.getConfigurationContext().getCasProperties().getAuthn().getThrottle().getFailure();
        ZonedDateTime cutoff = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(throttle.getRangeSeconds());
        return DateTimeUtils.timestampOf((ChronoZonedDateTime)cutoff);
    }

    protected void recordAuditAction(HttpServletRequest request, String actionName) {
        String userToUse = this.getUsernameParameterFromRequest(request);
        ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
        ThrottleCoreProperties throttle = this.getConfigurationContext().getCasProperties().getAuthn().getThrottle().getCore();
        String resource = StringUtils.defaultString((String)request.getParameter("service"), (String)"N/A");
        AuditActionContext context = new AuditActionContext(userToUse, resource, actionName, throttle.getAppCode(), DateTimeUtils.dateOf((ChronoZonedDateTime)ZonedDateTime.now(ZoneOffset.UTC)), clientInfo.getClientIpAddress(), clientInfo.getServerIpAddress(), clientInfo.getUserAgent());
        LOGGER.debug("Recording throttled audit action [{}]", (Object)context);
        this.configurationContext.getAuditTrailExecutionPlan().record(context);
    }

    private boolean isRequestIgnoredForThrottling(HttpServletRequest request, HttpServletResponse response) {
        AuthenticationThrottlingExecutionPlan plan = (AuthenticationThrottlingExecutionPlan)this.configurationContext.getApplicationContext().getBean(AuthenticationThrottlingExecutionPlan.class);
        return !plan.getAuthenticationThrottleFilter().supports(request, response);
    }

    protected ThrottledSubmission toThrottledSubmission(AuditActionContext context) {
        return ThrottledSubmission.builder().key(UUID.randomUUID().toString()).value(DateTimeUtils.zonedDateTimeOf((Date)context.getWhenActionWasPerformed())).build();
    }

    @Generated
    public String toString() {
        return "AbstractThrottledSubmissionHandlerInterceptorAdapter(configurationContext=" + this.configurationContext + ", thresholdRate=" + this.thresholdRate + ")";
    }

    @Generated
    public ThrottledSubmissionHandlerConfigurationContext getConfigurationContext() {
        return this.configurationContext;
    }

    @Generated
    public double getThresholdRate() {
        return this.thresholdRate;
    }

    @Generated
    protected AbstractThrottledSubmissionHandlerInterceptorAdapter(ThrottledSubmissionHandlerConfigurationContext configurationContext) {
        this.configurationContext = configurationContext;
    }
}

