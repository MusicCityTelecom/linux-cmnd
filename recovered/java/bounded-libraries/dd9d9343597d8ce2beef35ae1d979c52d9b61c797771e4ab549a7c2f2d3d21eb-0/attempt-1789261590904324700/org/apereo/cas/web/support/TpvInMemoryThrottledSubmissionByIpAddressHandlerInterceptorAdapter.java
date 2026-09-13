/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.text.StringEscapeUtils
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.servlet.AsyncHandlerInterceptor
 *  org.springframework.web.servlet.ModelAndView
 */
package org.apereo.cas.web.support;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter
implements AsyncHandlerInterceptor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConcurrentMap<String, Date> ipMap = new ConcurrentHashMap<String, Date>();
    private final ConcurrentMap<String, Integer> countMap = new ConcurrentHashMap<String, Integer>();
    private int lockMinutes = 15;
    private int errorMaxCount = 5;
    private String usernameParameter = "username";
    public static final String FAILURE_LOGIN_REQUEST_TAG = "request_failure_caused_by_error_username_or_password";

    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (this.isLoginSubmitRequest(request) && this.exceedsThreshold(request)) {
            this.recordThrottle(request);
            String username = StringUtils.isNotBlank((CharSequence)this.usernameParameter) ? StringUtils.defaultString((String)request.getParameter(this.usernameParameter), (String)"N/A") : "N/A";
            String msg = "Access Denied for user [" + StringEscapeUtils.escapeHtml4((String)username) + "] from IP Address [" + request.getRemoteAddr() + "]";
            response.sendError(HttpStatus.LOCKED.value(), msg);
            return false;
        }
        return true;
    }

    public final void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        if (this.isLoginSubmitRequest(request)) {
            if (this.shouldResponseBeRecordedAsFailure(response)) {
                this.recordSubmissionFailure(request);
            } else {
                this.decrementCounts();
            }
        }
    }

    private boolean isLoginSubmitRequest(HttpServletRequest request) {
        if (!"POST".equals(request.getMethod())) {
            return false;
        }
        String url = request.getRequestURI();
        return url != null && url.toLowerCase().contains("login") && request.getParameter(this.usernameParameter) != null;
    }

    protected boolean shouldResponseBeRecordedAsFailure(HttpServletResponse response) {
        int status = response.getStatus();
        return status != HttpStatus.CREATED.value() && status != HttpStatus.OK.value() && status != HttpStatus.FOUND.value();
    }

    private void recordThrottle(HttpServletRequest request) {
        this.logger.warn("Throttling submission from {}. Authentication attempt exceeds the failure threshold {}, the lock interval is {} minutes", new Object[]{request.getRemoteAddr(), this.errorMaxCount, this.lockMinutes});
    }

    private final boolean exceedsThreshold(HttpServletRequest request) {
        String key = this.constructKey();
        Integer errorCount = (Integer)this.countMap.get(key);
        if (errorCount != null && errorCount >= this.errorMaxCount) {
            boolean isExceedLockTime;
            Date last = (Date)this.ipMap.get(key);
            if (last == null) {
                return false;
            }
            boolean bl = isExceedLockTime = System.currentTimeMillis() - last.getTime() > (long)(this.lockMinutes * 60 * 1000);
            if (isExceedLockTime) {
                this.decrementCounts();
                return false;
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void recordSubmissionFailure(HttpServletRequest request) {
        String key = this.constructKey();
        this.ipMap.put(key, new Date());
        TpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter tpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter = this;
        synchronized (tpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter) {
            if (this.countMap.get(key) != null) {
                this.countMap.put(key, (Integer)this.countMap.get(key) + 1);
            } else {
                this.countMap.put(key, 1);
            }
        }
    }

    public final void decrementCounts() {
        String key = this.constructKey();
        this.countMap.remove(key);
        this.ipMap.remove(key);
    }

    private String constructKey() {
        return ClientInfoHolder.getClientInfo().getClientIpAddress();
    }

    public void setLockMinutes(int lockMinutes) {
        this.lockMinutes = lockMinutes;
    }

    public int getLockMinutes() {
        return this.lockMinutes;
    }

    public int getErrorMaxCount() {
        return this.errorMaxCount;
    }

    public void setErrorMaxCount(int errorMaxCount) {
        this.errorMaxCount = errorMaxCount;
    }
}

