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

public class TpvInMemoryThrottledSubmissionByIpAddressHandlerInterceptorAdapter implements AsyncHandlerInterceptor {
   protected final Logger logger = LoggerFactory.getLogger(this.getClass());
   private final ConcurrentMap<String, Date> ipMap = new ConcurrentHashMap<>();
   private final ConcurrentMap<String, Integer> countMap = new ConcurrentHashMap<>();
   private int lockMinutes = 15;
   private int errorMaxCount = 5;
   private String usernameParameter = "username";
   public static final String FAILURE_LOGIN_REQUEST_TAG = "request_failure_caused_by_error_username_or_password";

   @Override
   public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object o) throws Exception {
      if (this.isLoginSubmitRequest(request) && this.exceedsThreshold(request)) {
         this.recordThrottle(request);
         String username = StringUtils.isNotBlank(this.usernameParameter)
            ? StringUtils.defaultString(request.getParameter(this.usernameParameter), "N/A")
            : "N/A";
         String msg = "Access Denied for user [" + StringEscapeUtils.escapeHtml4(username) + "] from IP Address [" + request.getRemoteAddr() + "]";
         response.sendError(HttpStatus.LOCKED.value(), msg);
         return false;
      } else {
         return true;
      }
   }

   @Override
   public final void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object o, final ModelAndView modelAndView) throws Exception {
      if (this.isLoginSubmitRequest(request)) {
         if (this.shouldResponseBeRecordedAsFailure(response)) {
            this.recordSubmissionFailure(request);
         } else {
            this.decrementCounts();
         }
      }
   }

   private boolean isLoginSubmitRequest(final HttpServletRequest request) {
      if (!"POST".equals(request.getMethod())) {
         return false;
      }

      String url = request.getRequestURI();
      return url != null && url.toLowerCase().contains("login") && request.getParameter(this.usernameParameter) != null;
   }

   protected boolean shouldResponseBeRecordedAsFailure(final HttpServletResponse response) {
      int status = response.getStatus();
      return status != HttpStatus.CREATED.value() && status != HttpStatus.OK.value() && status != HttpStatus.FOUND.value();
   }

   private void recordThrottle(final HttpServletRequest request) {
      this.logger
         .warn(
            "Throttling submission from {}. Authentication attempt exceeds the failure threshold {}, the lock interval is {} minutes",
            request.getRemoteAddr(),
            this.errorMaxCount,
            this.lockMinutes
         );
   }

   private final boolean exceedsThreshold(final HttpServletRequest request) {
      String key = this.constructKey();
      Integer errorCount = this.countMap.get(key);
      if (errorCount != null && errorCount >= this.errorMaxCount) {
         Date last = this.ipMap.get(key);
         if (last == null) {
            return false;
         } else {
            boolean isExceedLockTime = System.currentTimeMillis() - last.getTime() > this.lockMinutes * 60 * 1000;
            if (isExceedLockTime) {
               this.decrementCounts();
               return false;
            } else {
               return true;
            }
         }
      } else {
         return false;
      }
   }

   private final void recordSubmissionFailure(final HttpServletRequest request) {
      String key = this.constructKey();
      this.ipMap.put(key, new Date());
      synchronized (this) {
         if (this.countMap.get(key) != null) {
            this.countMap.put(key, this.countMap.get(key) + 1);
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
