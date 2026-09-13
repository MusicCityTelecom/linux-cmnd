package org.apereo.cas.web.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

public class TpvHttpsLoginCheckHandlerInterceptorAdapter implements AsyncHandlerInterceptor {
   protected final Logger logger = LoggerFactory.getLogger(this.getClass());
   private boolean forceHttps = false;
   private Map<String, String> portRedirectMap = new HashMap<>();

   @Override
   public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object o) throws Exception {
      if (!this.isForceHttps()) {
         return true;
      }

      if (!"GET".equalsIgnoreCase(request.getMethod())) {
         return true;
      }

      if ("https".equalsIgnoreCase(request.getScheme())) {
         return true;
      }

      String requestUrl = request.getRequestURL().toString();
      if (!requestUrl.endsWith("/cas/login")) {
         return true;
      }

      String queryString = request.getQueryString();
      if (queryString != null && !queryString.startsWith("service=")) {
         return true;
      }

      String userAgent = request.getHeader("user-agent");
      if (!StringUtils.isBlank(userAgent) && !userAgent.contains("Apache-HttpClient")) {
         String fullUrl = requestUrl;
         if (queryString != null) {
            fullUrl = fullUrl + "?" + queryString;
         }

         String redirectHttpsUrl = this.simpleReplaceLoginURLUsingHttps(fullUrl);
         response.sendRedirect(redirectHttpsUrl);
         return false;
      } else {
         return true;
      }
   }

   private String simpleReplaceLoginURLUsingHttps(String requestUrl) {
      String redirecttUrl = requestUrl;

      for (Entry<String, String> entry : this.portRedirectMap.entrySet()) {
         String key = entry.getKey();
         String value = entry.getValue();
         redirecttUrl = redirecttUrl.replace(value, key).replace(key, value);
      }

      return redirecttUrl;
   }

   public Map<String, String> getPortRedirectMap() {
      return this.portRedirectMap;
   }

   public void setPortRedirectMap(Map<String, String> portRedirectMap) {
      this.portRedirectMap = portRedirectMap;
   }

   public boolean isForceHttps() {
      return this.forceHttps;
   }

   public void setForceHttps(boolean forceHttps) {
      this.forceHttps = forceHttps;
   }
}
