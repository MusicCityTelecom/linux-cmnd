package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.core.SettingCreator;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Schedule;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.dao.core.Welcome;
import com.tpvision.smartinstall.dao.mgr.AppPackageManager;
import com.tpvision.smartinstall.dao.mgr.BannersManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ScheduleManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.dao.mgr.UpgSettingManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/download")
public class DownloadCloneDataServlet extends BaseHttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(DownloadCloneDataServlet.class);
   public static final String FILE_SEPARATOR = System.getProperty("file.separator");

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String mode = request.getParameter("mode");
      if ("downloadUpg".equalsIgnoreCase(mode)) {
         this.downloadFirmware(request, response);
      } else if ("downloadClone".equalsIgnoreCase(mode)) {
         this.downloadCloneData(request, response);
      } else if ("downloadChannelPackage".equalsIgnoreCase(mode)) {
         this.downloadChannelPackage(request, response);
      } else if ("downloadAppPackage".equalsIgnoreCase(mode)) {
         this.downloadAppPackage(request, response);
      } else if ("downloadSettingPackage".equalsIgnoreCase(mode)) {
         this.downloadSettingPackage(request, response);
      } else if ("downloadBanners".equalsIgnoreCase(mode)) {
         this.downloadBanners(request, response);
      } else if ("downloadWelcome".equalsIgnoreCase(mode)) {
         this.downloadWelcome(request, response);
      } else if ("downloadUiCustomizations".equalsIgnoreCase(mode)) {
         this.downloadUiCustomizations(request, response);
      } else if ("downloadSchedule".equalsIgnoreCase(mode)) {
         this.downloadSchedule(request, response);
      } else if ("downloadZip".equalsIgnoreCase(mode)) {
         this.downloadZip(request, response);
      }
   }

   private void downloadZip(HttpServletRequest request, HttpServletResponse response) {
      String zipFileName = request.getParameter("zipName");
      String displayName = request.getParameter("displayName");
      File zipFile = new File(CommonConstants.DOWNLOAD_LOCATION + zipFileName);
      if (zipFile.exists() && zipFile.isFile()) {
         Utils.responseZipFile(zipFile, displayName, response);
      } else {
         LOG.error("zipFile:{} not exist", zipFile);
      }
   }

   public static void responseZipFileDownloadInfo(File zipFile, String displayName, HttpServletResponse response) {
      JSONObject data = new JSONObject();
      data.put("zipFileName", zipFile.getName());
      data.put("displayName", displayName);
      Utils.renderSuccessJsonData(data, response);
   }

   private void downloadUiCustomizations(HttpServletRequest request, HttpServletResponse response) {
      String uiCustomizationsId = request.getParameter("UiCustomizationsId");
      UiCustomizations ui = JpaManager.getUiCustomizationsManager().loadByKey(Integer.parseInt(uiCustomizationsId));
      if (null != ui) {
         try {
            String platform = ui.getPlatform();
            String settingName = ui.getName();

            try (SettingCreator sc = new SettingCreator(platform)) {
               sc.processUiCustomizations(ui);
               File f = this.createCloneZip(DigestUtils.md5Hex(settingName), PlatformUtils.getPlatformId(platform), sc.getOutputPath());
               responseZipFileDownloadInfo(f, settingName, response);
            }
         } catch (IOException e1) {
            LOG.error(e1.getMessage(), e1);
         }
      }
   }

   private void downloadSchedule(HttpServletRequest request, HttpServletResponse response) {
      String scheduleId = request.getParameter("scheduleId");

      try {
         ScheduleManager scheMgr = JpaManager.getScheduleManager();
         Schedule schedule = scheMgr.loadByKey(Integer.parseInt(scheduleId));
         if (schedule == null) {
            LOG.error("schedule is null,scheduleId={}", scheduleId);
            return;
         }

         String displayName = schedule.getName();
         String platformId = PlatformUtils.getPlatformId(schedule.getPlatform());

         try (SettingCreator settingCreator = new SettingCreator(platformId)) {
            settingCreator.processSchedules(schedule);
            File f = this.createCloneZip(DigestUtils.md5Hex(displayName), platformId, settingCreator.getOutputPath());
            responseZipFileDownloadInfo(f, displayName, response);
         }
      } catch (Exception e2) {
         LOG.error(e2.getMessage(), e2);
      }
   }

   private void downloadWelcome(HttpServletRequest request, HttpServletResponse response) {
      String welcomeId = request.getParameter("welcomeId");

      try {
         Welcome welcome = JpaManager.getWelcomeManager().loadByKey(Integer.parseInt(welcomeId));
         if (null == welcome) {
            LOG.error("welcome is null,welcomeid={}", welcomeId);
            return;
         }

         String displayName = welcome.getName();
         String platformId = PlatformUtils.getPlatformId(welcome.getPlatform());

         try (SettingCreator sc = new SettingCreator(platformId)) {
            sc.processWelcome(welcome.getId());
            File f = this.createCloneZip(DigestUtils.md5Hex(displayName), platformId, sc.getOutputPath());
            responseZipFileDownloadInfo(f, displayName, response);
         }
      } catch (IOException e2) {
         LOG.error(e2.getMessage(), e2);
      }
   }

   private void downloadBanners(HttpServletRequest request, HttpServletResponse response) {
      String bannersId = request.getParameter("bannersId");

      try {
         BannersManager bannersm = JpaManager.getBannersManager();
         Banners banners = bannersm.loadByKey(Integer.parseInt(bannersId));
         if (null != banners) {
            String platform = PlatformUtils.getPlatformName(banners.getPlatform());
            String settingName = banners.getName();

            try (SettingCreator sc = new SettingCreator(platform)) {
               sc.processBanner(banners);
               File f = this.createCloneZip(DigestUtils.md5Hex(settingName), PlatformUtils.getPlatformId(platform), sc.getOutputPath());
               responseZipFileDownloadInfo(f, settingName, response);
            }
         }
      } catch (IOException e2) {
         LOG.error(e2.getMessage(), e2);
      }
   }

   private void downloadSettingPackage(HttpServletRequest request, HttpServletResponse response) {
      String settingPackageId = request.getParameter("settingPackageId");

      try {
         SettingPackageManager settingm = JpaManager.getSettingPackageManager();
         SettingPackage setting = settingm.loadByKey(Integer.parseInt(settingPackageId));
         if (null != setting) {
            String platform = setting.getPlatform();
            String settingName = setting.getName();

            try (SettingCreator sc = new SettingCreator(platform)) {
               sc.processTVSettings(setting);
               File f = this.createCloneZip(DigestUtils.md5Hex(settingName), PlatformUtils.getPlatformId(platform), sc.getOutputPath());
               responseZipFileDownloadInfo(f, settingName, response);
            }
         }
      } catch (IOException e2) {
         LOG.error(e2.getMessage(), e2);
      }
   }

   private void downloadAppPackage(HttpServletRequest request, HttpServletResponse response) {
      String appPackageId = request.getParameter("appPackageId");
      AppPackageManager apm = JpaManager.getAppPackageManager();
      AppPackage ap = apm.loadByKey(Integer.parseInt(appPackageId));
      if (null != ap) {
         try {
            String platform = ap.getPlatform();
            String settingName = ap.getName();

            try (SettingCreator sc = new SettingCreator(platform)) {
               sc.processAppPackage(ap);
               File f = this.createCloneZip(DigestUtils.md5Hex(settingName), PlatformUtils.getPlatformId(platform), sc.getOutputPath());
               responseZipFileDownloadInfo(f, settingName, response);
            }
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private void downloadFirmware(HttpServletRequest request, HttpServletResponse response) {
      String upgDownload = request.getParameter("upgDownload");
      String downloadName = request.getParameter("downloadName");
      String id = request.getParameter("id");
      if (null != upgDownload) {
         this.downloadUPGFiles(upgDownload, downloadName, id, response);
      }
   }

   private void downloadChannelPackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String channelPackageId = request.getParameter("channelPackageId");

      try {
         ChannelPackage channelPackage = JpaManager.getChannelPackageManager().loadByKey(Integer.parseInt(channelPackageId));
         if (null == channelPackage) {
            LOG.error("channelPackage not existsed,id={}", channelPackageId);
            throw new BaseHttpServlet.MessageException("channelPackage not existsed");
         }

         String platform = channelPackage.getPlatform();
         String displayName = channelPackage.getName();

         try (SettingCreator settingCreator = new SettingCreator(platform)) {
            settingCreator.processChannelPackage(channelPackage);
            File f2 = this.createCloneZip(DigestUtils.md5Hex(displayName), PlatformUtils.getPlatformId(platform), settingCreator.getOutputPath());
            responseZipFileDownloadInfo(f2, displayName, response);
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void downloadCloneData(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String settingId = request.getParameter("settingId");
      String cloneDisplayName = null;
      SettingManager sm = JpaManager.getSettingManager();
      Setting settings = null;
      String platform = null;

      try {
         settings = sm.loadByKey(Integer.parseInt(settingId));
         if (settings != null) {
            platform = settings.getPlatform();
            cloneDisplayName = settings.getClonerename();

            try (SettingCreator settingCreator = new SettingCreator(platform, true)) {
               settingCreator.processSettings(settings);
               File f = this.createCloneZip(DigestUtils.md5Hex(cloneDisplayName), PlatformUtils.getPlatformId(platform), settingCreator.getOutputPath());
               responseZipFileDownloadInfo(f, cloneDisplayName, response);
            }
         }
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }
   }

   private void downloadUPGFiles(String settingName, String downloadName, String id, HttpServletResponse response) {
      UpgSettingManager smgr = JpaManager.getUpgSettingManager();
      String filePath = "";
      String headerValue = "";
      String headerKey = "Content-Disposition";
      if (StringUtils.isNotBlank(id)) {
         int fwid = Integer.parseInt(id);
         UpgSetting setting = smgr.loadByKey(fwid);
         if (null != setting) {
            filePath = CommonConstants.SISERVER_UPG_DIR + File.separator + settingName + File.separator + "Autorun.upg";
            headerValue = String.format(Locale.ENGLISH, "attachment; filename=\"%s\"", downloadName + ".upg");
         }
      }

      File downloadFile = new File(filePath);

      try (
         FileInputStream inStream = new FileInputStream(downloadFile);
         OutputStream outStream = response.getOutputStream();
      ) {
         ServletContext context = this.getServletContext();
         String mimeType = context.getMimeType(filePath);
         if (null == mimeType) {
            mimeType = "application/octet-stream";
         }

         response.setContentType(mimeType);
         response.setContentLength((int)downloadFile.length());
         response.setHeader(headerKey, headerValue);
         byte[] buffer = new byte[4096];
         int bytesRead = -1;

         while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private File createCloneZip(String zipFileName, String platformId, String srcPath) throws IOException {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      String directoryPath = CommonConstants.USER_ZIP_TEMP_LOCATION + UUID.randomUUID().toString();
      FileUtils.deleteQuietly(new File(directoryPath));
      LOG.info("srcPath = {},zipname = {},platform = {}, tempPath= {}", srcPath, zipFileName, platformId, directoryPath);
      String destPath = directoryPath + "/" + platformId + PlatformUtils.getDownloadCloneProcessPath(platformId);
      File csmFolder2 = new File(srcPath + "/DataDump");
      if (csmFolder2.exists()) {
         FileUtils.copyDirectory(csmFolder2, new File(directoryPath + "/" + platformId + "/DataDump"));
         FileUtils.deleteDirectory(csmFolder2);
      }

      File csmFolder = new File(srcPath + "/CSMDump");
      if (csmFolder.exists()) {
         File file = new File(destPath + "/CSMDump");
         if (file.exists()) {
            FileUtils.deleteQuietly(file);
         }

         FileUtils.moveDirectory(csmFolder, new File(directoryPath + "/" + platformId + "/CSMDump"));
      }

      FileUtils.copyDirectory(new File(srcPath), new File(destPath));
      String zipFilePath = CommonConstants.DOWNLOAD_LOCATION + zipFileName + ".zip";
      File zipFile = new File(zipFilePath);
      if (zipFile.exists()) {
         FileUtils.deleteQuietly(zipFile);
      }

      FileUtils.touch(zipFile);
      ZipCommonUtils.createZip(directoryPath, zipFilePath);
      FileUtils.deleteQuietly(new File(directoryPath));
      stopWatch.stop();
      LOG.info("generate zip file :{}, use :{} ms", zipFilePath, stopWatch.getTime());
      return zipFile;
   }
}
