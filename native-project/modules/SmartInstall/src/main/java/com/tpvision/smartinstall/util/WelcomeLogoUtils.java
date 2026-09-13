package com.tpvision.smartinstall.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tpvision.smartinstall.dao.core.Welcome;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.WelcomeManager;
import com.tpvision.smartinstall.japit.WelcomeAppData;
import com.tpvision.smartinstall.xml.welcomeappsetting.WelcomeAppSettings;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.cxf.common.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WelcomeLogoUtils {
   private static final String THUMBNAIL_EXT = ".jpg";
   public static final String WELCOME_APP_PATHNAME = "org.droidtv.welcome";
   public static final int WELCOME_APP = 1;
   public static final int WELCOME_LOGO_2K14 = 2;
   public static final String WELCOME_APP_CONTENT_PREFIX = "/data/data/org.droidtv.welcome/files/";
   private static final Logger LOG = LoggerFactory.getLogger(WelcomeLogoUtils.class);
   private static HashMap<String, WelcomeAppData> welcomeAppDataMap = new HashMap<>();
   private static HashMap<String, WelcomeAppSettings> welcomeAppSettingMap = new HashMap<>();
   private static WelcomeLogoUtils global = new WelcomeLogoUtils();

   public static WelcomeLogoUtils getInstance() {
      return global;
   }

   public String getWebCachePath() {
      return CommonConstants.servletContextPath + "/static/images/welcomethumb/";
   }

   private WelcomeLogoUtils() {
   }

   public void addWelcomeLogo(File newWelcomeLogo) throws IOException {
      String fileExtension = FilenameUtils.getExtension(newWelcomeLogo.getName());
      String imageName = TpvFileUtils.getImageMd5(newWelcomeLogo);
      imageName = imageName + "." + fileExtension;
      File f = new File(CommonConstants.WELCOME_LOGO_IMG_LOCATION + imageName);
      if (!f.exists()) {
         FileUtils.copyFile(newWelcomeLogo, f);
      }

      File file = new File(CommonConstants.WELCOME_LOGO_THUMB_IMG_LOCATION + imageName);
      if (!file.exists()) {
         TpvFileUtils.exportThumbnailFile(f, file);
      }
   }

   public void deleteWelcomLogo(String id) {
      File fileToDelMd5 = new File(CommonConstants.WELCOME_LOGO_IMG_LOCATION + id);
      FileUtils.deleteQuietly(fileToDelMd5);
      FileUtils.deleteQuietly(new File(CommonConstants.WELCOME_LOGO_THUMB_IMG_LOCATION + fileToDelMd5.getName()));
      this.updateWelcomeThumbCache();
   }

   public void updateWelcomeThumbCache() {
      LOG.info("update welcome thumb cache");
      File welcomethumbfile = new File(this.getWebCachePath());
      if (!welcomethumbfile.exists()) {
         welcomethumbfile.mkdirs();
      }

      for (Welcome welcome : JpaManager.getWelcomeManager().loadAll()) {
         this.generateWelcomeThumb(welcome.getId());
      }

      File file = new File(CommonConstants.WELCOME_LOGO_THUMB_IMG_LOCATION);
      File[] thumbs = file.listFiles();
      if (thumbs != null) {
         for (File thumb : thumbs) {
            File cached = new File(this.getWebCachePath() + thumb.getName());
            if (!cached.exists()) {
               try {
                  FileUtils.copyFile(thumb, cached);
               } catch (IOException e) {
                  LOG.error(e.getMessage(), e);
               }
            }
         }
      }
   }

   public static JSONArray getWelcomeLogoGallery(String platform) {
      JSONArray array = new JSONArray();
      File file = new File(CommonConstants.WELCOME_LOGO_IMG_LOCATION);
      File[] logos = file.listFiles();
      if (logos != null) {
         for (File logo : logos) {
            if (!logo.isDirectory()) {
               if (!checkWelcomeLogoCompatible(logo, platform)) {
                  LOG.debug("logo {} is not compatible with platform:{}", logo.getName(), platform);
               } else {
                  JSONObject obj = new JSONObject();
                  obj.put("fileName", logo.getName());
                  obj.put("imgSize", logo.length());
                  obj.put("imgResolution", TpvFileUtils.getImageResolution(logo));
                  array.put(obj);
               }
            }
         }
      }

      return array;
   }

   public static boolean checkWelcomeLogoCompatible(File logoFile, String platform) {
      String resolution = TpvFileUtils.getImageResolution(logoFile);
      String supportResolution = PlatformUtils.getPlatformWelcomeLogoResolution(platform);
      return supportResolution.equals(resolution);
   }

   private static File getWelcomeLogoFile(int welcomeId) {
      String welcomePath = getWelcomePath(String.valueOf(welcomeId));
      return TpvFileUtils.getFileByName(new File(welcomePath), "", "jpg,png,jpeg".split(","));
   }

   public static String getWelcomeLogoImageMd5(int welcomeId) {
      File welcomeLogoFile = getWelcomeLogoFile(welcomeId);
      if (null != welcomeLogoFile) {
         String ext = FilenameUtils.getExtension(welcomeLogoFile.getName());
         return TpvFileUtils.getImageMd5(welcomeLogoFile) + '.' + ext;
      } else {
         return "";
      }
   }

   public String getWelcomeLogoListByJson(String platform) {
      JSONArray ja = new JSONArray();
      int type = PlatformUtils.getWelcomeType(platform);

      for (Welcome welcome : JpaManager.getWelcomeManager().findWelcomesByType(type)) {
         JSONObject obj = new JSONObject();
         obj.put("id", welcome.getId());
         obj.put("name", welcome.getName());
         obj.put("thumbnail", this.getThumbnailUrl(welcome.getId()));
         ja.put(obj);
      }

      return ja.toString();
   }

   public String getThumbnailUrl(int id) {
      File cached = new File(this.getWebCachePath() + id + ".jpg");
      return cached.exists() ? "/SmartInstall//static/images/welcomethumb/" + id + ".jpg" : "";
   }

   public static JSONArray getMediaInfos(String welcomeId) {
      JSONArray arr = new JSONArray();
      WelcomeAppData appData = getAppData(welcomeId);
      String welcomePath = getWelcomePath(welcomeId);

      for (WelcomeAppData.ContentItem item : appData.getContentItemList()) {
         JSONObject obj = new JSONObject();
         obj.put("content", item.Content);
         obj.put("fileExt", FilenameUtils.getExtension(item.Content).toUpperCase());
         String contentPath = item.Content.replace("/data/data/org.droidtv.welcome", welcomePath);
         File mediaFile = new File(contentPath);
         obj.put("fileName", mediaFile.getName());
         obj.put("imgSize", mediaFile.length());
         obj.put("imgResolution", TpvFileUtils.getImageResolution(mediaFile));
         arr.put(obj);
      }

      return arr;
   }

   public static String getWelcomePath(String welcomeId) {
      return CommonConstants.CLONE_PROCESS_LOCATION + "Welcome/" + welcomeId;
   }

   private static File getWelcomeThumbFile(int welcomeId) {
      return new File(CommonConstants.WELCOME_LOGO_THUMB_IMG_LOCATION + welcomeId + ".jpg");
   }

   public void copyWelcome(int id) throws IOException {
      if (id > 0) {
         Welcome welcome = JpaManager.getWelcomeManager().loadByKey(id);
         if (null == welcome) {
            throw new IOException("welcome not existsed");
         }

         Welcome newWelcome = new Welcome();
         List<String> currentWelcomeNames = JpaManager.getWelcomeManager()
            .loadAll()
            .stream()
            .map(Welcome::getName)
            .map(String::trim)
            .collect(Collectors.toList());
         String copyName = CloneItemUtils.getUniqueCloneName(currentWelcomeNames, "Copy of " + welcome.getName());
         newWelcome.setName(copyName);
         newWelcome.setPlatform(welcome.getPlatform());
         newWelcome.setCreatedBy(Utils.getAuthenticationName());
         newWelcome.setType(welcome.getType());
         newWelcome.setLastEdit(welcome.getLastEdit());
         JpaManager.getWelcomeManager().save(newWelcome);
         int newId = newWelcome.getId();
         String origPath = getWelcomePath(String.valueOf(id));
         String newPath = getWelcomePath(String.valueOf(newId));
         FileUtils.copyDirectory(new File(origPath), new File(newPath));
         this.generateWelcomeThumb(newId);
      }
   }

   public static void deleteWelcome(int id) throws IOException {
      if (id > 0) {
         JpaManager.getWelcomeManager().deleteByKey(id);
         String welcomepath = getWelcomePath(String.valueOf(id));
         File welcomePathFile = new File(welcomepath);
         if (welcomePathFile.exists()) {
            FileUtils.deleteQuietly(welcomePathFile);
         }

         File thumb = getWelcomeThumbFile(id);
         if (thumb.exists()) {
            FileUtils.deleteQuietly(thumb);
         }
      }
   }

   private static File getWelcomeAppPath(File clonePath) {
      File professionalApps = TpvFileUtils.getFolderByName(clonePath, "ProfessionalAppsData");
      return professionalApps != null && professionalApps.exists() ? TpvFileUtils.getFolderByName(professionalApps, "org.droidtv.welcome") : null;
   }

   public static void updateWelcomeLogo(int welcomeId, File welcomeLogo) throws IOException {
      if (!welcomeLogo.exists()) {
         throw new IOException("welcome logo file not exists," + welcomeLogo.getName());
      }

      Welcome welcome = JpaManager.getWelcomeManager().loadByKey(welcomeId);
      int welcomeType = welcome.getType();
      if (welcomeType == 1) {
         LOG.error("not support update welcome app logo,id={}", welcomeId);
      } else {
         String targetFileName = "WelcomeLogo." + FilenameUtils.getExtension(welcomeLogo.getName());
         if (welcomeType == 2) {
            String platform = PlatformUtils.getRootFolderName(welcome.getPlatform());
            targetFileName = platform + "_" + targetFileName;
         }

         File oldWelcomelogo = getWelcomeLogoFile(welcomeId);
         if (oldWelcomelogo != null && oldWelcomelogo.exists()) {
            FileUtils.deleteQuietly(oldWelcomelogo);
         }

         getInstance().removeThumbnailFile(welcomeId);
         String welcomePath = getWelcomePath(String.valueOf(welcomeId));
         FileUtils.copyFile(welcomeLogo, new File(welcomePath + "/" + targetFileName));
         refreshWelcomeLastEditTime(welcomeId);
         getInstance().generateWelcomeThumb(welcomeId);
         getInstance().updateWelcomeThumbCache();
      }
   }

   public int saveWelcomeToDb(String platform, String clonePath, String configName, String userName) throws Exception {
      int welcometype = PlatformUtils.getWelcomeType(platform);
      Welcome welcome = new Welcome();
      welcome.setName(configName);
      welcome.setPlatform(platform);
      welcome.setType(welcometype);
      welcome.setCreatedBy(userName);

      try {
         JpaManager.getWelcomeManager().save(welcome);
         String welcomeId = String.valueOf(welcome.getId());
         String welcomeRootPath = CommonConstants.CLONE_PROCESS_LOCATION + "Welcome/";
         String welcomePath = welcomeRootPath + welcomeId;
         File welcomePathFile = new File(welcomePath);
         welcomePathFile.mkdirs();
         File clonePathFile = new File(clonePath);
         switch (welcometype) {
            case 0: {
               File welcomeLogo = TpvFileUtils.getFolderByName(clonePathFile, "WelcomeLogo");
               if (null == welcomeLogo) {
                  throw new UploadException(UploadException.ExceptionType.WelcomeLogoNotFound);
               }

               FileUtils.copyDirectory(welcomeLogo, welcomePathFile);
               File welcomeLogoFile = TpvFileUtils.getFileByName(clonePathFile, "WelcomeLogo", "jpg,png,jpeg".split(","));
               if (welcomeLogoFile == null) {
                  throw new UploadException(UploadException.ExceptionType.WelcomeLogoNotFound);
               }

               this.addWelcomeLogo(welcomeLogoFile);
               String var20 = welcomeLogo.getAbsolutePath() + "/WelcomeLogo_Identifier.txt";
               File fileIdentifier = new File(var20);
               if (fileIdentifier.exists()) {
                  String idendifier = FileUtils.readFileToString(fileIdentifier, StandardCharsets.UTF_8);
                  welcome.setLastEdit(idendifier);
               }
               break;
            }
            case 1:
               File welcomeApp = getWelcomeAppPath(clonePathFile);
               if (null == welcomeApp) {
                  throw new UploadException(UploadException.ExceptionType.WelcomeLogoNotFound);
               }

               FileUtils.copyDirectory(welcomeApp, welcomePathFile);
               break;
            case 2: {
               File welcomeLogo = TpvFileUtils.getFileByName(clonePathFile, "WelcomeLogo", "jpg,png,jpeg".split(","));
               if (null == welcomeLogo) {
                  throw new UploadException(UploadException.ExceptionType.WelcomeLogoNotFound);
               }

               File targetFile = new File(welcomePathFile.getAbsolutePath() + "/" + welcomeLogo.getName());
               FileUtils.copyFile(welcomeLogo, targetFile);
               this.addWelcomeLogo(welcomeLogo);
            }
         }

         this.generateWelcomeThumb(welcome.getId());
         return welcome.getId();
      } catch (UploadException e) {
         LOG.warn(e.getMessage());
         this.removeUploadFailureWelcome(welcome);
         throw e;
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         this.removeUploadFailureWelcome(welcome);
         throw e;
      }
   }

   private void removeUploadFailureWelcome(Welcome welcome) {
      JpaManager.getWelcomeManager().deleteByKey(welcome.getId());
   }

   public static void renameWelcome(int id, String newName) throws IOException {
      Welcome welcome = JpaManager.getWelcomeManager().loadByKey(id);
      if (null == welcome) {
         throw new IOException("welcome not existsed");
      }

      if (null != newName && !newName.isEmpty()) {
         List<Welcome> welcomes = JpaManager.getWelcomeManager().findWelcomesByName(newName);
         if (!welcomes.isEmpty() && (welcomes.size() != 1 || welcomes.get(0).getId() != id)) {
            throw new IOException("new Welcome Name already exist");
         }

         welcome.setName(newName);
         JpaManager.getWelcomeManager().save(welcome);
      } else {
         throw new IOException("invalid new Welcome Name");
      }
   }

   private void generateWelcomeThumb(int welcomeId) {
      String welcomePath = getWelcomePath(String.valueOf(welcomeId));
      File welcomeLogoFile = TpvFileUtils.getFileByName(new File(welcomePath), "", "jpg,png,jpeg".split(","));
      if (null != welcomeLogoFile) {
         generateThumb(welcomeLogoFile, welcomeId);

         try {
            this.addWelcomeLogo(welcomeLogoFile);
         } catch (IOException e) {
            LOG.error(e.getMessage());
         }
      } else {
         LOG.info("no image found for thumbnail:{}", welcomeId);
         this.removeThumbnailFile(welcomeId);
      }
   }

   private void removeThumbnailFile(int welcomeId) {
      File file = getWelcomeThumbFile(welcomeId);
      if (file.exists()) {
         FileUtils.deleteQuietly(file);
      }

      File cached = new File(this.getWebCachePath() + welcomeId + ".jpg");
      if (cached.exists()) {
         FileUtils.deleteQuietly(cached);
      }
   }

   private static void generateThumb(File srcFile, int welcomeId) {
      File file = getWelcomeThumbFile(welcomeId);
      if (!file.exists()) {
         TpvFileUtils.exportThumbnailFile(srcFile, file);
      }
   }

   public void generateWelcomeAppImageThumb(String welcomeId) {
      String welcomePath = getWelcomePath(welcomeId);
      String[] extFilters = "jpg,png,jpeg".split(",");
      File imagesFile = new File(welcomePath + "/files/");
      if (imagesFile.exists() && imagesFile.isDirectory()) {
         File[] images = imagesFile.listFiles();

         for (File image : images) {
            String ext = FilenameUtils.getExtension(image.getName());
            ext = ext.toLowerCase();
            String thumbPath = this.getWebCachePath() + welcomeId + "/" + image.getName();
            if (Arrays.asList(extFilters).contains(ext)) {
               File thumbFile = new File(thumbPath);
               if (!thumbFile.exists()) {
                  TpvFileUtils.exportThumbnailFile(image, thumbFile);
               }
            }
         }
      }
   }

   public static void saveAppConfig(String welcomeId, WelcomeAppSettings appSettings) {
      String welcomePath = getWelcomePath(welcomeId);
      File configFile = new File(welcomePath + "/WelcomeAppSettings.xml");
      JaxbReadXml.convertToXml(appSettings, "UTF-8", configFile.getAbsolutePath());
      refreshWelcomeLastEditTime(Integer.parseInt(welcomeId));
   }

   public static WelcomeAppSettings getAppConfig(String welcomeId) {
      if (welcomeAppSettingMap.containsKey(welcomeId)) {
         return welcomeAppSettingMap.get(welcomeId);
      }

      String welcomePath = getWelcomePath(welcomeId);
      File configFile = new File(welcomePath + "/WelcomeAppSettings.xml");
      WelcomeAppSettings welcomeAppSettings = null;
      if (configFile.exists()) {
         try {
            welcomeAppSettings = JaxbReadXml.readString(WelcomeAppSettings.class, configFile.getAbsolutePath());
         } catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      if (welcomeAppSettings == null) {
         welcomeAppSettings = new WelcomeAppSettings();
      }

      welcomeAppSettingMap.put(welcomeId, welcomeAppSettings);
      return welcomeAppSettings;
   }

   public static void updateAppConfigItem(String itemName, String itemValue, WelcomeAppSettings appSettings) {
      if (null != appSettings) {
         WelcomeAppSettings.Item item = appSettings.getItemByName(itemName);
         item.setValue(itemValue);
      }
   }

   public static void addContentItem(File uploaded, String welcomeId) throws IOException {
      String welcomePath = getWelcomePath(welcomeId);
      String contentName = uploaded.getName();
      File destFile = new File(welcomePath + "/files/" + contentName);
      if (destFile.exists()) {
         throw new IOException("uploaded File had existed");
      }

      FileUtils.copyFile(uploaded, destFile);
      WelcomeAppData appData = getAppData(welcomeId);
      WelcomeAppData.ContentItem contentItem = new WelcomeAppData.ContentItem();
      contentItem.Content = "/data/data/org.droidtv.welcome/files/" + contentName;
      contentItem.Sequence = String.valueOf(appData.CommandDetails.Content.size() + 1);
      appData.CommandDetails.Content.add(contentItem);
      refreshWelcomeLastEditTime(Integer.parseInt(welcomeId));
   }

   public static void saveAppData(String welcomeId, WelcomeAppData appData) {
      String welcomePath = getWelcomePath(welcomeId);
      File dataFile = new File(welcomePath + "/WelcomeApp.json");
      Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
      String dataContent = gson.toJson(appData);

      try {
         FileUtils.write(dataFile, dataContent, StandardCharsets.UTF_8);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      File filesPath = new File(welcomePath + "/files/");
      File[] contentsFile = filesPath.listFiles();
      if (contentsFile != null) {
         for (File contentFile : contentsFile) {
            String contentName = contentFile.getName();
            if (appData.getContentItemByName(contentName) == null) {
               FileUtils.deleteQuietly(contentFile);
            }
         }
      }

      WelcomeAppSettings appSettings = getAppConfig(welcomeId);
      String contentValue = appData.CommandDetails.Content != null && !appData.CommandDetails.Content.isEmpty() ? "Custom" : "Philips slideshow";
      updateAppConfigItem("Edit Welcome Screen.Set Background.Background Content", contentValue, appSettings);
      saveAppConfig(welcomeId, appSettings);
      getInstance().generateWelcomeAppImageThumb(welcomeId);
      getInstance().generateWelcomeThumb(Integer.parseInt(welcomeId));
      getInstance().updateWelcomeThumbCache();
      refreshWelcomeLastEditTime(Integer.parseInt(welcomeId));
   }

   public static WelcomeAppData getAppData(String welcomeId) {
      if (welcomeAppDataMap.containsKey(welcomeId)) {
         return welcomeAppDataMap.get(welcomeId);
      }

      String welcomePath = getWelcomePath(welcomeId);
      WelcomeAppData welcomeAppData = null;
      File dataFile = new File(welcomePath + "/WelcomeApp.json");
      if (dataFile.exists()) {
         try {
            String dataContent = FileUtils.readFileToString(dataFile, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            welcomeAppData = gson.fromJson(dataContent, WelcomeAppData.class);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      if (welcomeAppData == null) {
         welcomeAppData = new WelcomeAppData();
      }

      Iterator<WelcomeAppData.ContentItem> iterator = welcomeAppData.getContentItemList().iterator();

      while (iterator.hasNext()) {
         WelcomeAppData.ContentItem item = iterator.next();
         if (StringUtils.isEmpty(item.Content)) {
            iterator.remove();
         }
      }

      welcomeAppDataMap.put(welcomeId, welcomeAppData);
      return welcomeAppData;
   }

   private static void refreshWelcomeLastEditTime(int uiId) {
      WelcomeManager welcomeManager = JpaManager.getWelcomeManager();
      Welcome welcome = welcomeManager.loadByKey(uiId);
      if (welcome != null) {
         welcome.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
         welcomeManager.save(welcome);
      }
   }

   public static String getWelcomeEditPath(int welcomeId) {
      String path = "/getFile?mode=index#tabs_welcome";
      Welcome welcome = JpaManager.getWelcomeManager().loadByKey(welcomeId);
      if (null != welcome) {
         path = "/welcomelogo?mode=WELCOME_INDEX&&id=" + welcomeId;
      }

      return path;
   }
}
