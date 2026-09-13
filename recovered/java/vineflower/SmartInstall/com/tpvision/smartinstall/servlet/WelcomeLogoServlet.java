package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.Welcome;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.WelcomeManager;
import com.tpvision.smartinstall.japit.WelcomeAppData;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.WelcomeLogoUtils;
import com.tpvision.smartinstall.xml.welcomeappsetting.WelcomeAppSettings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/welcomelogo/*")
public class WelcomeLogoServlet extends BaseHttpServlet {
   private static final String WELCOME_ID = "welcomeId";
   private static final String REASON = "reason";
   private static final Logger LOG = LoggerFactory.getLogger(WelcomeLogoServlet.class);

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String mode = request.getParameter("mode");
      if ("WELCOME_INDEX".equals(mode)) {
         this.welcomeIndex(request, response);
      } else if ("UPLOAD_WELCOMELOGO".equals(mode)) {
         this.modeUpdateWelcomeLogo(request, response);
      } else if ("SAVE_WELCOMELOGO".equals(mode)) {
         this.modeSaveWelcomeLogo(request, response);
      } else if ("DELETE_WELCOMELOGO".equals(mode)) {
         this.modeDeleteWelcomeLogo(request, response);
      } else if ("COPY_WELCOME".equals(mode)) {
         this.copyWelcome(request, response);
      } else if ("DELETE_WELCOME".equals(mode)) {
         this.deleteWelcome(request, response);
      } else if ("RENAME_WELCOME".equals(mode)) {
         this.renameWelcome(request, response);
      } else if ("ASSIGN_WELCOME".equals(mode)) {
         this.assignWelcome(request, response);
      } else if ("GET_ASSIGN_WELCOME_LIST".equals(mode)) {
         this.getAssignWelcomeList(request, response);
      } else if ("LOAD_APP_CONFIG".equals(mode)) {
         this.loadAppConfig(request, response);
      } else if ("LOAD_APP_DATA".equals(mode)) {
         this.loadAppData(request, response);
      } else if ("UPDATE_APP_CONFIG".equals(mode)) {
         this.updateAppConfig(request, response);
      } else if ("UPDATE_APP_MESSAGE".equals(mode)) {
         this.updateAppMessages(request, response);
      } else if ("UPDATE_APP_CONTENTS".equals(mode)) {
         this.updateAppContents(request, response);
      } else if ("UPLOAD_WELCOMEAPP_FILE".equals(mode)) {
         this.uploadAPPFile(request, response);
      } else if ("ADD_WELCOME".equals(mode)) {
         this.addWelcome(request, response);
      } else if ("WELCOME_GALLERY".equals(mode)) {
         this.getWelcomeGallery(request, response);
      }
   }

   private void getWelcomeGallery(HttpServletRequest request, HttpServletResponse response) {
      String status = null;
      String welcomeId = request.getParameter("welcomeId");

      try {
         Welcome welcome = JpaManager.getWelcomeManager().loadByKey(Integer.parseInt(welcomeId));
         String platform = welcome.getPlatform();
         JSONArray array = WelcomeLogoUtils.getWelcomeLogoGallery(platform);
         status = this.successStatus(array);
      } catch (NumberFormatException e) {
         LOG.error(e.getMessage(), e);
         status = this.failedStatus(e.getMessage());
      }

      this.responseJSON(status, response);
   }

   private void addWelcome(HttpServletRequest request, HttpServletResponse response) {
      String platform = request.getParameter("platform");
      Welcome welcome = new Welcome();
      WelcomeManager welMgr = JpaManager.getWelcomeManager();
      welcome.setName("Welcome_" + TpvDateUtils.getCloneDateTimeName());
      welcome.setPlatform(PlatformUtils.getPlatformId(platform));
      welcome.setType(PlatformUtils.getWelcomeType(platform));
      String createBy = Utils.getAuthenticationName();
      welcome.setCreatedBy(createBy);
      welMgr.save(welcome);
      if (welcome.getType() == 1) {
         String welcomeId = String.valueOf(welcome.getId());
         WelcomeAppSettings appSettings = WelcomeLogoUtils.getAppConfig(welcomeId);
         List<String> allProperties = new ArrayList<>();
         allProperties.add("Edit Welcome Screen.Configure.Premises Name");
         allProperties.add("Edit Welcome Screen.Configure.Guest Name");
         allProperties.add("Edit Welcome Screen.Configure.Welcome");
         allProperties.add("Edit Welcome Screen.Configure.Date");
         allProperties.add("Edit Welcome Screen.Configure.Time");
         if (PlatformUtils.isSupportWelcomeAppWeather(welcome.getPlatform())) {
            allProperties.add("Edit Welcome Screen.Configure.Weather");
         }

         for (String property : allProperties) {
            WelcomeLogoUtils.updateAppConfigItem(property, "On", appSettings);
         }

         WelcomeLogoUtils.saveAppConfig(welcomeId, appSettings);
         WelcomeAppData appData = WelcomeLogoUtils.getAppData(welcomeId);
         WelcomeLogoUtils.saveAppData(welcomeId, appData);
      }

      this.responseJSON("{\"status\":\"success\"}", response);
   }

   private void uploadAPPFile(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         String welcomeId = request.getParameter("welcomeId");
         WelcomeAppData appData = WelcomeLogoUtils.getAppData(welcomeId);

         for (File uploadFile : this.extractUploadedFiles(request, false)) {
            WelcomeLogoUtils.addContentItem(uploadFile, welcomeId);
            FileUtils.deleteQuietly(uploadFile);
         }

         WelcomeLogoUtils.saveAppData(welcomeId, appData);
         resObj = new JSONObject("{\"status\":\"success\"}");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         resObj.put("reason", e.getMessage());
      }

      this.responseJSON(resObj.toString(), response);
   }

   private void updateAppContents(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         String welcomeId = request.getParameter("welcomeId");
         WelcomeAppData appData = WelcomeLogoUtils.getAppData(welcomeId);
         String contents = request.getParameter("contents");
         WelcomeAppData.ContentItem[] contentItems = new Gson().fromJson(contents, WelcomeAppData.ContentItem[].class);
         List<WelcomeAppData.ContentItem> contentItemList = Arrays.asList(contentItems);
         appData.CommandDetails.Content.clear();
         appData.CommandDetails.Content.addAll(contentItemList);
         WelcomeLogoUtils.saveAppData(welcomeId, appData);
         resObj = new JSONObject("{\"status\":\"success\"}");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         resObj.put("reason", e.getMessage());
      }

      this.responseJSON(resObj.toString(), response);
   }

   private void updateAppMessages(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         String welcomeId = request.getParameter("welcomeId");
         WelcomeAppData appData = WelcomeLogoUtils.getAppData(welcomeId);
         String messageStr = request.getParameter("message");
         WelcomeAppData.MessageItemContent messageItemContent = new Gson().fromJson(messageStr, WelcomeAppData.MessageItemContent.class);
         appData.updateMessages(messageItemContent);
         WelcomeLogoUtils.saveAppData(welcomeId, appData);
         resObj = new JSONObject("{\"status\":\"success\"}");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         resObj.put("reason", e.getMessage());
      }

      this.responseJSON(resObj.toString(), response);
   }

   private void updateAppConfig(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"success\"}");
      String welcomeId = request.getParameter("welcomeId");
      String itemName = request.getParameter("itemName");
      String itemValue = request.getParameter("itemValue");
      WelcomeAppSettings appSettings = WelcomeLogoUtils.getAppConfig(welcomeId);
      WelcomeLogoUtils.updateAppConfigItem(itemName, itemValue, appSettings);
      WelcomeLogoUtils.saveAppConfig(welcomeId, appSettings);
      this.responseJSON(resObj.toString(), response);
   }

   private void loadAppData(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         String welcomeId = request.getParameter("welcomeId");
         WelcomeAppData appData = WelcomeLogoUtils.getAppData(welcomeId);
         resObj = new JSONObject("{\"status\":\"success\"}");
         resObj.put("data", new Gson().toJson(appData.CommandDetails));
         resObj.put("content", WelcomeLogoUtils.getMediaInfos(welcomeId));
         resObj.put("basePath", "./static/images/welcomethumb//" + welcomeId + "/");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         resObj.put("reason", e.getMessage());
      }

      this.responseJSON(resObj.toString(), response);
   }

   private void loadAppConfig(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"success\"}");

      try {
         String welcomeId = request.getParameter("welcomeId");
         WelcomeAppSettings appSettings = WelcomeLogoUtils.getAppConfig(welcomeId);
         if (null != appSettings) {
            resObj.put("data", new Gson().toJson(appSettings.getItem()));
         } else {
            resObj.put("data", "[]");
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         resObj = new JSONObject("{\"status\":\"fail\"}");
         resObj.put("reason", e.getMessage());
      }

      this.responseJSON(resObj.toString(), response);
   }

   private void getAssignWelcomeList(HttpServletRequest request, HttpServletResponse response) {
      JSONArray ja = new JSONArray();
      String platform = request.getParameter("platform");
      String platformInCondition = PlatformUtils.getPlatformId(platform);

      for (Welcome welcome : JpaManager.getWelcomeManager().findWelcomesByPlatforms(platformInCondition)) {
         JSONObject jo = new JSONObject();
         jo.put("id", welcome.getId());
         jo.put("name", welcome.getName());
         jo.put("thumbnailurl", WelcomeLogoUtils.getInstance().getThumbnailUrl(welcome.getId()));
         ja.put(jo);
      }

      this.responseJSON(ja.toString(), response);
   }

   private void assignWelcome(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         int welcomeId = Integer.parseInt(request.getParameter("welcomeId"));
         Welcome welcome = JpaManager.getWelcomeManager().loadByKey(welcomeId);
         String cloneids = request.getParameter("clone_ids");

         for (Setting setting : JpaManager.getSettingManager().findSettingByIds(TpvStringUtils.splitStringToIntList(cloneids, ",", 0))) {
            if (welcomeId != -1 && null != welcome && !welcome.getPlatform().equalsIgnoreCase(setting.getPlatform())) {
               LOG.warn("clone id {} not compatible with welcome id {}", setting.getId(), welcomeId);
            } else {
               setting.setWelcomeId(welcomeId);
               setting.setLastUpdatedDate(new Date());
               JpaManager.getSettingManager().save(setting);
            }
         }

         resObj = new JSONObject("{\"status\":\"success\"}");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         resObj.put("reason", e.getMessage());
      }

      this.responseJSON(resObj.toString(), response);
   }

   private void renameWelcome(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         String welcomeId = request.getParameter("id");
         String newName = request.getParameter("newName");
         WelcomeLogoUtils.renameWelcome(Integer.valueOf(welcomeId), newName);
         resObj = new JSONObject("{\"status\":\"success\"}");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         resObj.put("reason", e.getMessage());
      }

      this.responseJSON(resObj.toString(), response);
   }

   private void deleteWelcome(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         String welcomeId = request.getParameter("id");
         WelcomeLogoUtils.deleteWelcome(Integer.valueOf(welcomeId));
         resObj = new JSONObject("{\"status\":\"success\"}");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         resObj.put("reason", e.getMessage());
      }

      this.responseJSON(resObj.toString(), response);
   }

   private void copyWelcome(HttpServletRequest request, HttpServletResponse response) {
      JSONObject resObj = new JSONObject("{\"status\":\"fail\"}");

      try {
         String welcomeId = request.getParameter("id");
         WelcomeLogoUtils.getInstance().copyWelcome(Integer.valueOf(welcomeId));
         resObj = new JSONObject("{\"status\":\"success\"}");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         resObj.put("reason", e.getMessage());
      }

      this.responseJSON(resObj.toString(), response);
   }

   protected void welcomeIndex(HttpServletRequest request, HttpServletResponse response) {
      String idStr = request.getParameter("id");

      try {
         Welcome welcome = JpaManager.getWelcomeManager().loadByKey(Integer.parseInt(idStr));
         String jspUrl = null;
         if (null != welcome) {
            request.setAttribute("DisplayName", welcome.getName());
            request.setAttribute("Platform", PlatformUtils.getPlatformName(welcome.getPlatform()));
            request.setAttribute("welcomeId", idStr);
            request.setAttribute("imageMd5", WelcomeLogoUtils.getWelcomeLogoImageMd5(welcome.getId()));
            WelcomeLogoUtils.getInstance().generateWelcomeAppImageThumb(idStr);
            request.setAttribute("supportResolution", PlatformUtils.getPlatformWelcomeLogoResolution(welcome.getPlatform()));
            jspUrl = welcome.getType() == 1 ? "/WelcomeAppEditor.jsp" : "/welcomelogoeditor.jsp";
         }

         request.getRequestDispatcher(jspUrl).forward(request, response);
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         request.setAttribute("config_error", "config_error");

         try {
            request.getRequestDispatcher("/getFile?mode=index").forward(request, response);
         } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   protected void modeUpdateWelcomeLogo(HttpServletRequest request, HttpServletResponse response) {
      String status = this.successStatus();
      String welcomeId = request.getParameter("welcomeId");

      try {
         Welcome welcome = JpaManager.getWelcomeManager().loadByKey(Integer.parseInt(welcomeId));
         String platform = welcome.getPlatform();

         for (File logo : this.extractUploadedFiles(request, false)) {
            if (!WelcomeLogoUtils.checkWelcomeLogoCompatible(logo, platform)) {
               throw new IOException("Image resolution is not supported by current platform");
            }

            WelcomeLogoUtils.getInstance().addWelcomeLogo(logo);
         }

         WelcomeLogoUtils.getInstance().updateWelcomeThumbCache();
      } catch (Exception e2) {
         status = this.failedStatus(e2.getMessage());
      }

      this.responseJSON(status, response);
   }

   protected void modeDeleteWelcomeLogo(HttpServletRequest request, HttpServletResponse response) {
      String id = request.getParameter("imageId");
      String status = this.successStatus();
      WelcomeLogoUtils.getInstance().deleteWelcomLogo(id);
      this.responseJSON(status, response);
   }

   private void modeSaveWelcomeLogo(HttpServletRequest request, HttpServletResponse response) {
      String welcomeId = request.getParameter("welcomeId");
      String imageId = request.getParameter("imageId");
      String json = "";
      File newWelcomeLogo = new File(CommonConstants.WELCOME_LOGO_IMG_LOCATION + imageId);

      try {
         WelcomeLogoUtils.updateWelcomeLogo(Integer.parseInt(welcomeId), newWelcomeLogo);
         json = this.successStatus();
      } catch (NumberFormatException | IOException e) {
         LOG.error(e.getMessage(), e);
         json = this.failedStatus(e.getMessage());
      }

      this.responseJSON(json, response);
   }
}
