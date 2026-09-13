package com.tpvision.smartinstall.auth;

import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.TVDiscoveryManager;
import com.tpvision.smartinstall.util.TpvRunableTask;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class SIAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
   private static final Logger LOG = LoggerFactory.getLogger(SIAuthSuccessHandler.class);
   private RequestCache requestCache = new HttpSessionRequestCache();

   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
      SavedRequest savedRequest = this.requestCache.getRequest(request, response);
      String directUrl = "";
      if (null != savedRequest) {
         directUrl = savedRequest.getRedirectUrl();
         LOG.info("redirect to:{}", directUrl);
         if (!this.isValidDirectUrl(directUrl)) {
            LOG.info("not valid redirect url:{}", directUrl);
            directUrl = "";
         }
      }

      LOG.info("CMND Version:7.0.1");
      String url = request.getRequestURL().toString();
      int ind = url.indexOf(58, 5);
      String url1 = "";
      String url2 = "";
      if (ind < 0) {
         url2 = request.getScheme() + "://" + request.getHeader("Host") + "/SmartInstall";
         url1 = url2 + "/";
      } else {
         url1 = url.substring(0, ind) + ":8080/SmartInstall/";
         url2 = url.substring(0, ind) + ":8080/SmartInstall";
      }

      LOG.info("url1 = {}", url1);
      if ("".equalsIgnoreCase(directUrl) || url1.equals(directUrl) || url2.equals(directUrl)) {
         String directAddr = "/dev?type=index";
         if (JpaManager.getDevicesManager().loadAll().isEmpty() && !JpaManager.getSettingManager().loadAll().isEmpty()) {
            directAddr = "/getFile?mode=index";
         }

         directUrl = request.getContextPath() + directAddr;
      }

      LOG.info("directUrl = {}", directUrl);
      response.sendRedirect(directUrl);
      new Thread(new TpvRunableTask() {
         @Override
         public void execute() {
            TVDiscoveryManager.refreshAllIpTvStatus();
         }
      }).start();
   }

   private boolean isValidDirectUrl(String url) {
      if (null != url && !url.isEmpty()) {
         String[] validUrls = new String[]{
            "/dev?type=index",
            "/getFile?mode=index",
            "/pms?type=index",
            "/admin.jsp",
            "/settingpackage?mode=",
            "/channel?mode=INDEX",
            "/appPackage?mode=APP_INDEX",
            "/banners?mode=",
            "/welcomelogo?mode=WELCOME_INDEX",
            "/ui?mode=UICUSTOMIZATIONS_INDEX",
            "/schedule?mode=SCHEDULE_INDEX"
         };

         for (String validUrl : validUrls) {
            if (url.contains(validUrl)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
