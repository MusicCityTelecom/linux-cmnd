package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.core.SettingChannelBean;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Schedule;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.UploadCloneUtils;
import com.tpvision.smartinstall.util.UploadException;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.WelcomeLogoUtils;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(UploadServlet.class);
   private static final String UPLOAD_TYPE_FIELD_NAME = "uploadType";

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      CommonConstants.CloneItemType targetUploadedCloneItemType = CommonConstants.CloneItemType.Clone;

      try {
         Map<String, FileItem> uploadCloneFileItemMap = this.extractFileItemFromHttpRequest(request);
         String userName = Utils.getAuthenticationName();
         FileItem uploadCloneFileItem = uploadCloneFileItemMap.get("uploadFile");
         File storeZipFile = this.saveUploadCloneFileToZipTempLocation(userName, uploadCloneFileItem);
         String unZipCloneLocationStr = this.unzipUploadedClonePackage(storeZipFile);
         String uploadType = uploadCloneFileItemMap.get("uploadType").getString();
         targetUploadedCloneItemType = this.convertUploadTypeToCloneItemTypes(uploadType);
         String platformId = "";
         String processPlatformDir = "";
         if (targetUploadedCloneItemType != CommonConstants.CloneItemType.Banner) {
            platformId = this.getPlatformIdFromUnZipFolder(unZipCloneLocationStr);
            processPlatformDir = UploadCloneUtils.getPlatformHandleFolderDir(unZipCloneLocationStr, platformId).getAbsolutePath();
         }

         String redirectUrl = "";
         switch (targetUploadedCloneItemType) {
            case Clone:
               redirectUrl = this.uploadFullClonePackage(request, userName, uploadCloneFileItem, unZipCloneLocationStr, platformId);
               break;
            case TVSettings:
               redirectUrl = this.uploadTVSettingCloneItem(processPlatformDir, platformId);
               break;
            case ChannelList:
               redirectUrl = this.uploadChannelsCloneItem(processPlatformDir, userName, platformId);
               break;
            case AndroidApps:
               redirectUrl = this.uploadAppsCloneItem(processPlatformDir, platformId);
               break;
            case Banner:
               redirectUrl = this.uploadBannersCloneItem(unZipCloneLocationStr, request);
               break;
            case WelcomeLogo:
               redirectUrl = this.uploadWelcomeCloneItem(unZipCloneLocationStr, platformId, userName, request);
               break;
            case UiCustomizations:
               redirectUrl = this.uploadUICloneItem(processPlatformDir, platformId);
               break;
            case Schedules:
               redirectUrl = this.uploadScheduleCloneItem(processPlatformDir, platformId);
         }

         response.sendRedirect(redirectUrl);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         String errorInfo = "error:" + e.getMessage();
         this.showErrorMsg(errorInfo, targetUploadedCloneItemType, request, response);
      }
   }

   private String uploadScheduleCloneItem(String processPlatformDir, String platformId) throws UploadException {
      String scheduleCloneName = "Schedule_" + TpvDateUtils.getCloneDateTimeName();
      Schedule schedule = UploadCloneUtils.saveScheduleToDB(processPlatformDir, scheduleCloneName, platformId);
      if (schedule != null) {
         return "schedule?mode=SCHEDULE_INDEX&cloneId=-1&id=" + schedule.getId();
      } else {
         throw new UploadException(UploadException.ExceptionType.UPLOAD_SCHEDULES_FAIL);
      }
   }

   private String uploadUICloneItem(String processPlatformDir, String platformId) throws UploadException {
      String uICloneName = "UI_" + TpvDateUtils.getCloneDateTimeName();
      UiCustomizations uiCustomizations = UploadCloneUtils.loadConfToUiCustomizationsDb(processPlatformDir, uICloneName, platformId);
      if (uiCustomizations != null) {
         return "ui?mode=UICUSTOMIZATIONS_INDEX&id=" + uiCustomizations.getId();
      } else {
         throw new UploadException(UploadException.ExceptionType.UploadUIFail);
      }
   }

   private String uploadWelcomeCloneItem(String unZipCloneLocationStr, String platformId, String userName, HttpServletRequest request) throws Exception {
      String welcomeCloneName = "Welcome_" + TpvDateUtils.getCloneDateTimeName();
      int welcomeId = WelcomeLogoUtils.getInstance().saveWelcomeToDb(platformId, unZipCloneLocationStr, welcomeCloneName, userName);
      if (welcomeId > 0) {
         this.setSuccessMsg("upload welcome logo success", request);
         return "getFile?mode=index#tabs_welcome";
      } else {
         throw new UploadException(UploadException.ExceptionType.UploadWelcomeFail);
      }
   }

   private String uploadBannersCloneItem(String unZipCloneLocationStr, HttpServletRequest request) throws UploadException {
      String appsCloneName = "Banners_" + TpvDateUtils.getCloneDateTimeName();
      Banners banners = UploadCloneUtils.loadConfToBanners(unZipCloneLocationStr, appsCloneName);
      if (banners != null) {
         this.setSuccessMsg("upload bannner success", request);
         return "getFile?mode=index#tabs_banners";
      } else {
         throw new UploadException(UploadException.ExceptionType.UploadBannerZipInvalid);
      }
   }

   private String uploadAppsCloneItem(String processPlatformDir, String platformId) throws UploadException {
      String appsCloneName = "Apps_" + TpvDateUtils.getCloneDateTimeName();
      AppPackage appPackage = UploadCloneUtils.loadConfToAppPackageDb(processPlatformDir, appsCloneName, platformId);
      if (appPackage != null) {
         return "appPackage?mode=APP_INDEX&id=" + appPackage.getId();
      } else {
         throw new UploadException(UploadException.ExceptionType.UploadAppsFail);
      }
   }

   private String uploadChannelsCloneItem(String processPlatformDir, String userName, String platformId) throws IOException, JAXBException, UploadException {
      String channelCloneName = "Channels_" + TpvDateUtils.getCloneDateTimeName();
      SettingChannelBean settingChannelBean = UploadCloneUtils.getSettingChannelBean(processPlatformDir, platformId);
      ChannelPackage channelPackage = UploadCloneUtils.saveChannelPackageToDB(processPlatformDir, userName, channelCloneName, settingChannelBean, platformId);
      if (channelPackage != null) {
         return "channel?mode=INDEX&channelPackageId=" + channelPackage.getId();
      } else {
         throw new UploadException(UploadException.ExceptionType.UploadChannelsFail);
      }
   }

   private String uploadTVSettingCloneItem(String processPlatformDir, String platformId) throws IOException, JAXBException, UploadException {
      String cloneName = "TVSetting_" + TpvDateUtils.getCloneDateTimeName();
      SettingChannelBean settingChannelBean = UploadCloneUtils.getSettingChannelBean(processPlatformDir, platformId);
      if (settingChannelBean.getSetttings() != null) {
         SettingPackage settingPackage = UploadCloneUtils.loadConfToSettingPackageDb(processPlatformDir, cloneName, settingChannelBean, platformId);
         return "settingpackage?mode=SETTING_INDEX&id=" + settingPackage.getId();
      } else {
         throw new UploadException(UploadException.ExceptionType.UploadTvSettingFail);
      }
   }

   private String uploadFullClonePackage(
      HttpServletRequest request, String userName, FileItem uploadCloneFileItem, String unZipCloneLocationStr, String platformId
   ) throws Exception {
      String configName = UploadCloneUtils.generateSaveCloneName(FilenameUtils.getBaseName(uploadCloneFileItem.getName()));
      Setting setting = UploadCloneUtils.loadAllConfToDb(unZipCloneLocationStr, userName, configName, "USER_DEFINED", platformId);
      if (setting.getSettingPackageId() > 0) {
         return "settingpackage?mode=SETTING_INDEX&sid=" + setting.getId();
      }

      this.setSuccessMsg("upload part clone package success", request);
      return "getFile?mode=index";
   }

   private void setSuccessMsg(String successMsg, HttpServletRequest request) {
      request.getSession().setAttribute("UPLOADSUCCESS", successMsg);
   }

   private void showErrorMsg(
      String errorMsg, CommonConstants.CloneItemType targetUploadedCloneItemType, HttpServletRequest request, HttpServletResponse response
   ) {
      request.getSession().setAttribute("UPLOADERROR", errorMsg);

      try {
         String tabLoc = "";
         switch (targetUploadedCloneItemType) {
            case TVSettings:
               tabLoc = "#tabs_settingPackage";
               break;
            case ChannelList:
               tabLoc = "#tabs_channelPackage";
               break;
            case AndroidApps:
               tabLoc = "#tabs_app";
               break;
            case Banner:
               tabLoc = "#tabs_banners";
               break;
            case WelcomeLogo:
               tabLoc = "#tabs_welcome";
               break;
            case UiCustomizations:
               tabLoc = "#tabs_uiCustomizations";
               break;
            case Schedules:
               tabLoc = "#tabs_schedule";
         }

         String forwardUrl = "getFile?mode=index" + tabLoc;
         response.sendRedirect(forwardUrl);
      } catch (Exception e1) {
         LOG.error(e1.getMessage(), e1);
      }
   }

   private CommonConstants.CloneItemType convertUploadTypeToCloneItemTypes(String uploadType) throws UploadException {
      switch (uploadType) {
         case "uploadedClone":
            return CommonConstants.CloneItemType.Clone;
         case "uploadedClone_settingPackage":
            return CommonConstants.CloneItemType.TVSettings;
         case "uploadedClone_channelPackage":
            return CommonConstants.CloneItemType.ChannelList;
         case "uploadedClone_appPackage":
            return CommonConstants.CloneItemType.AndroidApps;
         case "uploadedClone_banners":
            return CommonConstants.CloneItemType.Banner;
         case "uploadedClone_welcome":
            return CommonConstants.CloneItemType.WelcomeLogo;
         case "uploadedClone_ui":
            return CommonConstants.CloneItemType.UiCustomizations;
         case "uploadedClone_schedule":
            return CommonConstants.CloneItemType.Schedules;
         default:
            throw new UploadException(UploadException.ExceptionType.InvalidZipFile);
      }
   }

   private String unzipUploadedClonePackage(File storeZipFile) throws UploadException {
      try {
         TpvFileUtils.checkDiskSpaceFull(storeZipFile);
      } catch (IOException e) {
         throw new UploadException(UploadException.ExceptionType.NOT_ENGOUTH_DISK_SPACE);
      }

      File unZipLocation = new File(CommonConstants.USER_ZIP_TEMP_LOCATION + FilenameUtils.getBaseName(storeZipFile.getName()));
      if (unZipLocation.exists()) {
         try {
            FileUtils.deleteDirectory(unZipLocation);
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }

      String unZipCloneLocationStr = unZipLocation.getPath() + "/";
      ZipCommonUtils.unZipFiles(storeZipFile, unZipCloneLocationStr);
      if (!new File(unZipCloneLocationStr).exists()) {
         throw new UploadException(UploadException.ExceptionType.InvalidZipFile);
      } else {
         return unZipCloneLocationStr;
      }
   }

   private Map<String, FileItem> extractFileItemFromHttpRequest(HttpServletRequest request) throws FileUploadException, UploadException {
      ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
      List<FileItem> items = fileUpload.parseRequest(request);
      Map<String, FileItem> uploadFileItems = new HashMap<>();

      for (FileItem item : items) {
         if (!item.isFormField()) {
            uploadFileItems.put("uploadFile", item);
         } else if ("uploadType".equals(item.getFieldName())) {
            uploadFileItems.put("uploadType", item);
         }
      }

      if (uploadFileItems.size() != 2) {
         throw new UploadException(UploadException.ExceptionType.UploadFileIsNull);
      } else {
         return uploadFileItems;
      }
   }

   private File saveUploadCloneFileToZipTempLocation(String userName, FileItem uploadFileItem) throws IOException {
      String storedZipFileName = this.generateStoredZipName(userName);
      TpvFileUtils.checkDir(CommonConstants.USER_ZIP_TEMP_LOCATION);
      File storeZipFile = new File(CommonConstants.USER_ZIP_TEMP_LOCATION + storedZipFileName);

      try (FileOutputStream fos = new FileOutputStream(storeZipFile)) {
         IOUtils.copy(uploadFileItem.getInputStream(), fos);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return storeZipFile;
   }

   private String generateStoredZipName(String userName) {
      String storedZipFileName = DigestUtils.md5Hex(userName + UUID.randomUUID().toString());
      LOG.info("generate uploaded clone filename: {}", storedZipFileName);
      return storedZipFileName + ".zip";
   }

   private String getPlatformIdFromUnZipFolder(String unZipCloneLocationStr) throws UploadException {
      File unzipFolder = new File(unZipCloneLocationStr);
      if (unzipFolder.exists() && unzipFolder.listFiles().length == 1) {
         String platformId = unzipFolder.listFiles()[0].getName();
         if (PlatformUtils.isValidUploadedPlatform(platformId)) {
            return platformId;
         }
      }

      throw new UploadException(UploadException.ExceptionType.InvalidCloneFolderName);
   }
}
