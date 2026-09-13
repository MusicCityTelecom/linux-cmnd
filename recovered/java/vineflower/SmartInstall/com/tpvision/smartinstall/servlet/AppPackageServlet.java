package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.mgr.AppPackageManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/appPackage")
public class AppPackageServlet extends HttpServlet {
   private static final Logger LOG = LoggerFactory.getLogger(AppPackageServlet.class);

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String mode = request.getParameter("mode");
      if ("APP_INDEX".equals(mode)) {
         this.appIndex(request, response);
      } else if ("RENAME_APPPACKAGE".equals(mode)) {
         this.renameAppPackage(request, response);
      } else if ("GET_ASSIGN_APPPACKAGE_LIST".equals(mode)) {
         this.getAssignAppPackageList(request, response);
      } else if ("ASSIGN_APPPACKAGE".equals(mode)) {
         this.assignAppPackage(request, response);
      } else if ("COPY_APPPACKAGE".equals(mode)) {
         this.copyAppPackage(request, response);
      } else if ("DELETE_APPPACKAGE".equals(mode)) {
         this.deleteAppPackage(request, response);
      }
   }

   private void deleteAppPackage(HttpServletRequest request, HttpServletResponse response) {
      String id = request.getParameter("id");
      AppPackageManager appPackageManager = JpaManager.getAppPackageManager();
      appPackageManager.deleteByKey(Integer.parseInt(id));
      String deleteDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "AppPackages/" + id;
      File deleteDirectory = new File(deleteDirectoryStr);

      try {
         FileUtils.deleteDirectory(deleteDirectory);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      Utils.renderSuccessJsonData(response);
   }

   private void copyAppPackage(HttpServletRequest request, HttpServletResponse response) {
      String id = request.getParameter("id");
      AppPackageManager appPackageManager = JpaManager.getAppPackageManager();
      AppPackage copyDest = new AppPackage();
      AppPackage copySrc = appPackageManager.loadByKey(Integer.parseInt(id));
      List<String> currentAppPackageNames = appPackageManager.loadAll().stream().map(AppPackage::getName).map(String::trim).collect(Collectors.toList());
      String copyName = CloneItemUtils.getUniqueCloneName(currentAppPackageNames, "Copy of " + copySrc.getName());
      copyDest.setName(copyName);
      copyDest.setPlatform(copySrc.getPlatform());
      copyDest.setValue(copySrc.getValue());
      copyDest.setNumber(copySrc.getNumber());
      copyDest.setSize(copySrc.getSize());
      copyDest.setLastEdit(copySrc.getLastEdit());
      appPackageManager.save(copyDest);
      String copyDestDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "AppPackages/" + copyDest.getId();
      File copyDestDirectory = new File(copyDestDirectoryStr);
      String copySrcDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "AppPackages/" + copySrc.getId() + "/AndroidApps";
      File copySrcDirectory = new File(copySrcDirectoryStr);
      copyDestDirectory.mkdir();

      try {
         FileUtils.copyDirectoryToDirectory(copySrcDirectory, copyDestDirectory);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      Utils.renderSuccessJsonData(response);
   }

   private void appIndex(HttpServletRequest request, HttpServletResponse response) {
      String id = request.getParameter("id");
      AppPackageManager appPackageManager = JpaManager.getAppPackageManager();
      AppPackage appPackage = appPackageManager.loadByKey(Integer.parseInt(id));
      if (null == appPackage) {
         LOG.error("appPackage is null,id is:{}", id);
      } else {
         String appsRef = null;
         String name = appPackage.getName();
         String embed = request.getParameter("embeded");
         if (embed != null && embed.equalsIgnoreCase("true")) {
            appsRef = "/jsp/setting/subsettings/apps.jsp?name=" + name + "&id=" + id;
         } else {
            appsRef = "/appContent.jsp?name=" + name + "&id=" + id;
         }

         request.getSession().setAttribute("appPackageId", id);

         try {
            request.getRequestDispatcher(appsRef).forward(request, response);
         } catch (ServletException | IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private void renameAppPackage(HttpServletRequest request, HttpServletResponse response) {
      String newName = request.getParameter("newName");
      String id = request.getParameter("id");
      int appId = Integer.parseInt(id);
      AppPackageManager appPackageManager = JpaManager.getAppPackageManager();
      AppPackage appPackage = appPackageManager.loadByKey(appId);
      if (appPackage == null) {
         Utils.renderErrorJsonMsg("rename app record not exist", response);
      } else {
         List<AppPackage> appPackages = appPackageManager.findByName(newName);
         if (!appPackages.isEmpty() && (appPackages.size() != 1 || appPackages.get(0).getId() != appId)) {
            Utils.renderErrorJsonMsg("app new name already exist", response);
         } else {
            appPackage.setName(newName);
            appPackageManager.save(appPackage);
            Utils.renderSuccessJsonData(response);
         }
      }
   }

   private void getAssignAppPackageList(HttpServletRequest request, HttpServletResponse response) {
      String platform = request.getParameter("platform");
      LOG.info("assign platform:{}", platform);
      List<AppPackage> appPackageList = null;
      if (StringUtils.isNotBlank(platform)) {
         appPackageList = JpaManager.getAppPackageManager().findAppPackageByPlatforms(platform);
      } else {
         appPackageList = JpaManager.getAppPackageManager().loadAll();
      }

      String jsonStr = new Gson().toJson(appPackageList);

      try {
         IOUtils.write(jsonStr.getBytes(), response.getOutputStream());
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void assignAppPackage(HttpServletRequest request, HttpServletResponse response) {
      String cloneIds = request.getParameter("clone_ids");
      String[] cloneIdsArr = cloneIds.split(",");
      String appPackageId = request.getParameter("appPackage_id");
      String status = "{\"status\":\"success\"}";
      AppPackageManager appPackageManager = JpaManager.getAppPackageManager();
      SettingManager settingManager = JpaManager.getSettingManager();
      Setting setting = null;
      if ("None".equalsIgnoreCase(appPackageId)) {
         appPackageId = String.valueOf(-1);
         status = "{\"status\":\"setNone\"}";
      } else {
         AppPackage appPackage = appPackageManager.loadByKey(Integer.parseInt(appPackageId));
         if (appPackage == null) {
            LOG.error("selected AppPackage:{} not exists", appPackageId);
            Utils.renderErrorJsonMsg("{\"status\":\"fail\"}", response);
            return;
         }
      }

      for (int i = 0; i < cloneIdsArr.length; i++) {
         if (!"null".equals(cloneIdsArr[i])) {
            setting = settingManager.loadByKey(Integer.parseInt(cloneIdsArr[i]));
            String platform = setting.getPlatform();
            if (!PlatformUtils.hasPackageFeature(platform)) {
               status = String.format(Locale.ENGLISH, "{\"status\":\"fail\",\"msg\":\"%s\"}", "Unable Assign App Pacakage to No Android Platform");
               break;
            }

            setting.setAppPackageId(Integer.parseInt(appPackageId));
            settingManager.save(setting);
         }
      }

      try {
         IOUtils.write(status.getBytes(), response.getOutputStream());
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }
}
