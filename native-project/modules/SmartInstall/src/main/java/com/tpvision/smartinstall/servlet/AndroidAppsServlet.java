package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tpvision.smartinstall.androidapp.AndroidAppHelper;
import com.tpvision.smartinstall.androidapp.AndroidApplications;
import com.tpvision.smartinstall.androidapp.AppBootgridVO;
import com.tpvision.smartinstall.util.TpvFileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/apps")
public class AndroidAppsServlet extends BaseHttpServlet {
   private static final Logger LOG = LoggerFactory.getLogger(AndroidAppsServlet.class);
   private static final Map<String, String> preInstalledPlayStoreApps = new HashMap<>();
   private AndroidAppHelper helper;
   private String appPackageId = "0";

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) {
      try {
         this.helper = this.getHelper(request);
      } catch (IOException e) {
         LOG.error("get help error,{}", e.getMessage());
         return;
      }

      String mode = request.getParameter("mode");
      switch (mode) {
         case "appsInfo":
            this.getAppInfos(request, response);
            break;
         case "getTotalSize":
            this.getAppsTotalSize(request, response);
            break;
         case "deleteLocalApp":
            this.deleteLocalApp(request, response);
            break;
         case "addLocalApp":
            this.addLocalApp(request, response);
            break;
         case "addAppBundle":
            this.addAppBundle(request, response);
            break;
         case "switchAppNo":
            this.switchAppNo(request, response);
            break;
         case "setApplicationName":
            this.setApplicationName(request, response);
            break;
         case "getAndroidAndProfessionalApp":
         case "getAllAppNames":
            this.getAndroidAppsNames(response);
            break;
         default:
            if (mode.startsWith("change")) {
               this.changeApp(request, response);
            }
      }
   }

   private boolean checkUploadedAppBundle(List<File> files) throws BaseHttpServlet.MessageException {
      if (files != null && !files.isEmpty()) {
         int aabFileCount = 0;
         int apkFileCount = 0;

         for (File file : files) {
            String ext = FilenameUtils.getExtension(file.getName());
            if (ext.equalsIgnoreCase("aab")) {
               aabFileCount++;
            } else if (ext.equalsIgnoreCase("apk")) {
               apkFileCount++;
            }
         }

         if (aabFileCount > 1) {
            throw new BaseHttpServlet.MessageException("uploaded files contains more than 1 aab files");
         } else if (aabFileCount == 0 && apkFileCount == 0) {
            throw new BaseHttpServlet.MessageException("Please upload folder contains valid app bundle");
         } else {
            return aabFileCount == 1;
         }
      } else {
         throw new BaseHttpServlet.MessageException("uploaded files is empty");
      }
   }

   private void addAppBundle(HttpServletRequest request, HttpServletResponse response) {
      try {
         List<File> files = this.extractUploadedFiles(request, true);
         boolean isAabFile = this.checkUploadedAppBundle(files);
         if (isAabFile) {
            File file = files.get(0);
            String apkname = this.helper.extractApksfromAAB(file.getAbsolutePath(), true);
            FileUtils.deleteQuietly(file);
            this.helper.addApp(apkname);
         } else {
            String packageName = files.get(0).getParentFile().getName();
            Iterator apkname = files.iterator();

            while (true) {
               if (apkname.hasNext()) {
                  File file = (File)apkname.next();
                  String ext = FilenameUtils.getExtension(file.getName());
                  if (!ext.equalsIgnoreCase("aab")) {
                     String targetFileName = this.helper.getAndroidAppPath() + packageName + "/" + file.getName();
                     File targetFile = new File(targetFileName);
                     if (targetFile.exists()) {
                        LOG.error("File {} already exists,delete", targetFile.getAbsolutePath());
                        FileUtils.deleteQuietly(targetFile);
                     }

                     FileUtils.moveFile(file, targetFile);
                     continue;
                  }

                  this.helper.extractApksfromAAB(file.getAbsolutePath(), false);
                  FileUtils.deleteQuietly(file);
               }

               this.helper.addAppBundle(packageName);
               break;
            }
         }

         this.responseJSON(this.successStatus(), response);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         this.responseJSON(this.failedStatus(e.getMessage()), response);
      }
   }

   private AndroidAppHelper getHelper(HttpServletRequest request) throws IOException {
      this.appPackageId = request.getParameter("appPackageId");
      if (this.appPackageId != null) {
         return new AndroidAppHelper(Integer.parseInt(this.appPackageId));
      }

      String sname = request.getParameter("sname");
      if (null != sname && !"".equals(sname)) {
         return new AndroidAppHelper(sname);
      }

      String settingId = request.getParameter("settingPackageId");
      return AndroidAppHelper.getHelper(Integer.parseInt(settingId));
   }

   private void setApplicationName(HttpServletRequest request, HttpServletResponse response) {
      String changeAppNo = request.getParameter("changeAppNo");
      String name = request.getParameter("name");

      try {
         this.helper.setApplicationName(changeAppNo, name);
         this.responseJSON(this.successStatus(), response);
      } catch (NumberFormatException | IOException e) {
         LOG.error(e.getMessage(), e);
         this.responseJSON(this.failedStatus(e.getMessage()), response);
      }
   }

   private void switchAppNo(HttpServletRequest request, HttpServletResponse response) {
      String newAppNo = request.getParameter("changeAppNo");
      String oldAppNo = request.getParameter("orgNo");

      try {
         this.helper.changeAppNo(newAppNo, oldAppNo);
         this.responseJSON(this.successStatus(), response);
      } catch (NumberFormatException | IOException e) {
         LOG.error(e.getMessage(), e);
         this.responseJSON(this.failedStatus(e.getMessage()), response);
      }
   }

   private void deleteLocalApp(HttpServletRequest request, HttpServletResponse response) {
      String appNo = request.getParameter("appNo");

      try {
         this.helper.deleteApp(Integer.parseInt(appNo));
         this.responseJSON(this.successStatus(), response);
      } catch (NumberFormatException | IOException e) {
         LOG.error(e.getMessage(), e);
         this.responseJSON(this.failedStatus(e.getMessage()), response);
      }
   }

   private void changeApp(HttpServletRequest request, HttpServletResponse response) {
      String newValue = request.getParameter("newValue");
      String changeAppNo = request.getParameter("changeAppNo");

      try {
         this.helper.changeApp(request.getParameter("mode"), newValue, changeAppNo);
         this.responseJSON(this.successStatus(), response);
      } catch (NumberFormatException | IOException e) {
         LOG.error(e.getMessage(), e);
         this.responseJSON(this.failedStatus(e.getMessage()), response);
      }
   }

   private void getAppsTotalSize(HttpServletRequest request, HttpServletResponse response) {
      String searchPhrase = request.getParameter("searchPhrase");
      String sortColumn = this.getSortColumn(request);
      List<AndroidApplications.AndroidApp> appDetails = this.helper.getApps();
      JsonArray localArray = this.getEachAppArray(appDetails, "local", searchPhrase, sortColumn);
      JsonArray portalArray = this.getEachAppArray(appDetails, "portal", searchPhrase, sortColumn);
      JsonObject data = this.getAppsTotalSize(localArray, portalArray);
      this.responseJSON(this.successStatus(data), response);
   }

   private void getAppInfos(HttpServletRequest request, HttpServletResponse response) {
      String current0 = request.getParameter("current");
      int current = null != current0 ? Integer.valueOf(current0) : 0;
      String rowCount0 = request.getParameter("rowCount");
      int rowCount = null != rowCount0 ? Integer.valueOf(rowCount0) : 0;
      String searchPhrase = request.getParameter("searchPhrase");
      String sortColumn = this.getSortColumn(request);
      List<AndroidApplications.AndroidApp> appDetails = this.helper.getApps();
      JsonArray localArray = this.getEachAppArray(appDetails, null, searchPhrase, sortColumn);
      int localAppShowCount = 0;
      int offset = 0;
      if (current > 0) {
         offset = (current - 1) * rowCount;
      }

      JsonArray localShowArray = new JsonArray();

      for (int i = offset; null != localArray && i < localArray.size(); i++) {
         if (localAppShowCount < rowCount) {
            localShowArray.add(localArray.get(i));
            localAppShowCount++;
         }
      }

      JsonObject data = new JsonObject();
      data.add("rows", localShowArray);
      data.addProperty("current", current);
      data.addProperty("rowCount", rowCount);
      data.addProperty("total", null != localArray ? localArray.size() : 0);
      if (!this.helper.checkAppInfoJSONValid()) {
         data.addProperty("errorMsg", "There's something wrong with apps JSON file");
      }

      this.responseJSON(data.toString(), response);
   }

   private String getSortColumn(HttpServletRequest request) {
      String column = null;
      String appNoSort = request.getParameter("sort[appNo]");
      String appNameSort = request.getParameter("sort[name]");
      String categorySort = request.getParameter("sort[category]");
      String countrySort = request.getParameter("sort[country]");
      String sizeSort = request.getParameter("sort[size]");
      String hideSort = request.getParameter("sort[hide]");
      String typeSort = request.getParameter("sort[type]");
      if (null != appNoSort) {
         column = "appNo," + appNoSort;
      } else if (null != appNameSort) {
         column = "name," + appNameSort;
      } else if (null != categorySort) {
         column = "category," + categorySort;
      } else if (null != countrySort) {
         column = "country," + countrySort;
      } else if (null != sizeSort) {
         column = "size," + sizeSort;
      } else if (null != hideSort) {
         column = "hide," + hideSort;
      } else if (null != typeSort) {
         column = "type," + typeSort;
      }

      if (null == column) {
         column = "appNo,asc";
      }

      return column;
   }

   private void addLocalApp(HttpServletRequest request, HttpServletResponse response) {
      try {
         String newApkFileName = this.copyApkToProcess(request);
         if (null == newApkFileName) {
            String failedResaon = "copy to process workspace failed";
            throw new BaseHttpServlet.MessageException(failedResaon);
         }

         this.helper.addApp(newApkFileName);
         this.responseJSON(this.successStatus(), response);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         this.responseJSON(this.failedStatus(e.getMessage()), response);
      }
   }

   public String arrayToString(String[] strs) {
      if (null == strs) {
         LOG.info(" string is null in arraytostring");
         return "";
      }

      String str = "";

      for (int i = 0; i < strs.length; i++) {
         str = str + strs[i];
         if (i != strs.length - 1) {
            str = str + ",";
         }
      }

      return str;
   }

   public String sizeUnitConverse(long filesize) {
      if (0L == filesize) {
         return "0 KB";
      } else {
         long kb = 1024L;
         long mb = kb * 1024L;
         long gb = mb * 1024L;
         if (filesize >= gb) {
            return String.format(Locale.ENGLISH, "%.1f GB", (float)((double)filesize / gb));
         } else if (filesize >= mb) {
            float f = (float)filesize / (float)mb;
            return String.format(Locale.ENGLISH, f > 100.0F ? "%.0f MB" : "%.1f MB", f);
         } else {
            float f = (float)filesize / (float)kb;
            return String.format(Locale.ENGLISH, f > 100.0F ? "%.0f KB" : "%.1f KB", f);
         }
      }
   }

   public static JsonArray sortAppArray(JsonArray array, String sortColumn) {
      if (null == array) {
         LOG.info(" null inputs in sortJSONArray");
         return null;
      }

      JsonArray result = new JsonArray();
      List<AppBootgridVO> appVO = new ArrayList<>();
      Gson gson = new Gson();

      for (int i = 0; i < array.size(); i++) {
         AppBootgridVO ab = gson.fromJson(array.get(i), AppBootgridVO.class);
         appVO.add(ab);
      }

      String[] sortInfo = sortColumn.split(",");
      String sortColumnName = null;
      String sortRule = null;
      if (null != sortInfo && sortInfo.length > 0) {
         sortColumnName = sortInfo[0];
         sortRule = sortInfo[1];
      }

      final String columnName = sortColumnName;
      final String rule = sortRule;
      Collections.sort(
         appVO,
         new Comparator<AppBootgridVO>() {
            public int compare(AppBootgridVO arg0, AppBootgridVO arg1) {
               int compareResult = -1;
               if ("appNo".equalsIgnoreCase(columnName)) {
                  if (arg0.getAppNo() > arg1.getAppNo() && "asc".equalsIgnoreCase(rule) || arg0.getAppNo() < arg1.getAppNo() && "desc".equalsIgnoreCase(rule)) {
                     compareResult = 1;
                  }

                  if (arg0.getAppNo() == arg1.getAppNo()) {
                     compareResult = 0;
                  }
               } else if ("size".equalsIgnoreCase(columnName)) {
                  if (arg0.getSize() > arg1.getSize() && "asc".equalsIgnoreCase(rule) || arg0.getSize() < arg1.getSize() && "desc".equalsIgnoreCase(rule)) {
                     compareResult = 1;
                  }

                  if (arg0.getAppNo() == arg1.getAppNo()) {
                     compareResult = 0;
                  }
               } else if ("name".equalsIgnoreCase(columnName)) {
                  if (arg0.getName().compareTo(arg1.getName()) > 0 && "asc".equalsIgnoreCase(rule)
                     || arg0.getName().compareTo(arg1.getName()) < 0 && "desc".equalsIgnoreCase(rule)) {
                     compareResult = 1;
                  }

                  if (arg0.getName().compareTo(arg1.getName()) == 0) {
                     compareResult = 0;
                  }
               } else if ("category".equalsIgnoreCase(columnName)) {
                  if (arg0.getCategory().compareTo(arg1.getCategory()) > 0 && "asc".equalsIgnoreCase(rule)
                     || arg0.getCategory().compareTo(arg1.getCategory()) < 0 && "desc".equalsIgnoreCase(rule)) {
                     compareResult = 1;
                  }

                  if (arg0.getCategory().compareTo(arg1.getCategory()) == 0) {
                     compareResult = 0;
                  }
               } else if ("country".equalsIgnoreCase(columnName)) {
                  if (arg0.getCountry().compareTo(arg1.getCountry()) > 0 && "asc".equalsIgnoreCase(rule)
                     || arg0.getCountry().compareTo(arg1.getCountry()) < 0 && "desc".equalsIgnoreCase(rule)) {
                     compareResult = 1;
                  }

                  if (arg0.getCountry().compareTo(arg1.getCountry()) == 0) {
                     compareResult = 0;
                  }
               } else if ("hide".equalsIgnoreCase(columnName)) {
                  if (arg0.getHide().compareTo(arg1.getHide()) > 0 && "asc".equalsIgnoreCase(rule)
                     || arg0.getHide().compareTo(arg1.getHide()) < 0 && "desc".equalsIgnoreCase(rule)) {
                     compareResult = 1;
                  }

                  if (arg0.getHide().compareTo(arg1.getHide()) == 0) {
                     compareResult = 0;
                  }
               } else if ("type".equalsIgnoreCase(columnName)) {
                  if (arg0.getType().compareTo(arg1.getType()) > 0 && "asc".equalsIgnoreCase(rule)
                     || arg0.getType().compareTo(arg1.getType()) < 0 && "desc".equalsIgnoreCase(rule)) {
                     compareResult = 1;
                  }

                  if (arg0.getType().compareTo(arg1.getType()) == 0) {
                     compareResult = 0;
                  }
               }

               return compareResult;
            }
         }
      );
      Iterator<AppBootgridVO> it = appVO.iterator();

      while (it.hasNext()) {
         result.add(it.next().toJsonObject());
      }

      return result;
   }

   public JsonArray getEachAppArray(List<AndroidApplications.AndroidApp> appDetails, String type, String searchPhrase, String sortColumn) {
      if (null == appDetails) {
         LOG.error(" null inputs in getEachAppArray");
         return null;
      }

      JsonArray array = new JsonArray();

      for (AndroidApplications.AndroidApp app : appDetails) {
         if (type == null || type.equalsIgnoreCase(app.getPackageType())) {
            JsonObject object = this.helper.getAppsInfo(app);
            if (object != null && (null == searchPhrase || "".equalsIgnoreCase(searchPhrase))
               || !"".equalsIgnoreCase(searchPhrase) && app.getAllInfoString().contains(searchPhrase)) {
               array.add(object);
            }
         }
      }

      return sortAppArray(array, sortColumn);
   }

   public long getFileSize(String fileName) {
      return TpvFileUtils.getDirSize(new File(this.helper.getAndroidAppPath() + fileName));
   }

   public JsonObject getAppsTotalSize(JsonArray localArray, JsonArray portalArray) {
      JsonObject object = new JsonObject();
      long localSize = 0L;
      long portalSize = 0L;
      Gson gson = new Gson();
      if (null == localArray) {
         localSize = 0L;
      } else {
         for (int i = 0; i < localArray.size(); i++) {
            AppBootgridVO ab = gson.fromJson(localArray.get(i), AppBootgridVO.class);
            localSize += ab.getSize();
         }
      }

      if (null == portalArray) {
         portalSize = 0L;
      } else {
         for (int i = 0; i < portalArray.size(); i++) {
            AppBootgridVO ab = gson.fromJson(portalArray.get(i), AppBootgridVO.class);
            portalSize += ab.getSize();
         }
      }

      object.addProperty("localTotalSize", this.sizeUnitConverse(localSize));
      object.addProperty("portalTotalSize", this.sizeUnitConverse(portalSize));
      return object;
   }

   public String copyApkToProcess(HttpServletRequest request) throws Exception {
      List<File> files = this.extractUploadedFiles(request, true);
      if (files.isEmpty()) {
         throw new IOException("uploaded files not found");
      }

      File apk = files.get(0);
      String ext = FilenameUtils.getExtension(apk.getName());
      if (ext.equalsIgnoreCase("aab")) {
         return this.helper.extractApksfromAAB(apk.getAbsolutePath(), true);
      }

      File targetFile = new File(this.helper.getAndroidAppPath() + apk.getName());
      FileUtils.moveFile(apk, targetFile);
      return targetFile.getName();
   }

   private void getAndroidAppsNames(HttpServletResponse response) {
      List<String> allNamesList = this.helper.getAppNames(null);
      String data = String.join(",", allNamesList);
      this.responseText(data, response);
   }

   static {
      preInstalledPlayStoreApps.put("com.google.android.videos", "Play Movies & TV");
      preInstalledPlayStoreApps.put("com.google.android.play.games", "Play Games");
      preInstalledPlayStoreApps.put("com.google.android.youtube.tv", "YouTube");
      preInstalledPlayStoreApps.put("com.google.android.music", "Play Music");
      preInstalledPlayStoreApps.put("com.android.vending", "Play Store");
      preInstalledPlayStoreApps.put("com.netflix.ninja", "Netflix");
   }
}
