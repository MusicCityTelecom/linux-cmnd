package be.tpvision.smartcontrol.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

public class SmartControlSavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private RequestCache requestCache = new HttpSessionRequestCache();
   private String contextPath;

   @Override
   public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws ServletException, IOException {
      SavedRequest savedRequest = this.requestCache.getRequest(request, response);
      if (savedRequest == null) {
         super.onAuthenticationSuccess(request, response, authentication);
      } else {
         String targetUrlParameter = super.getTargetUrlParameter();
         boolean hasTargetUrl = false;
         if (targetUrlParameter != null) {
            String targetUrl = request.getParameter(targetUrlParameter);
            hasTargetUrl = StringUtils.hasText(targetUrl);
         }

         boolean alwaysUseDefaultTargetUrl = super.isAlwaysUseDefaultTargetUrl();
         if (!alwaysUseDefaultTargetUrl && !hasTargetUrl) {
            this.clearAuthenticationAttributes(request);
            String targetUrl = savedRequest.getRedirectUrl();
            if (this.contextPath != null) {
               int lastSlashIndex = targetUrl.lastIndexOf(47);
               int targetUrlLength = targetUrl.length();
               String lastPart = targetUrl.substring(lastSlashIndex, targetUrlLength);
               if (lastPart.equals(this.contextPath)) {
                  targetUrl = targetUrl + "/";
               }
            }

            this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
            RedirectStrategy redirectStrategy = super.getRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, targetUrl);
         } else {
            this.requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
         }
      }
   }

   public void setRequestCache(final RequestCache requestCache) {
      this.requestCache = requestCache;
   }

   public String getContextPath() {
      return this.contextPath;
   }

   public void setContextPath(final String contextPath) {
      this.contextPath = contextPath;
   }
}
