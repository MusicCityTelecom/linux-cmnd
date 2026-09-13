package com.tpvision.smartinstall.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentUtils {
   private static final Logger LOG = LoggerFactory.getLogger(ContentUtils.class);
   private static final String GET_LIST_URL = CommonConstants.CMS_URL + "/rest/api/website/v1/getList";
   private static final String GET_ZIP_URL_PREFIX = CommonConstants.CMS_URL + "/rest/api/website/v1/";

   private ContentUtils() {
   }

   public static String getContentList() {
      String contentList = getDataFromUrl(GET_LIST_URL);
      if (!TpvStringUtils.isValidJson(contentList)) {
         contentList = "[]";
      }

      return contentList;
   }

   public static List<Integer> findContentIdByTitle(String title) {
      List<Integer> idList = new ArrayList<>();
      String data = getContentList();
      if (null != data) {
         JSONArray jsonArr = new JSONArray(data);

         for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject obj = jsonArr.optJSONObject(i);
            if (StringUtils.equalsIgnoreCase(title, obj.optString("title"))) {
               idList.add(Integer.parseInt(obj.optString("id")));
            }
         }
      }

      return idList;
   }

   public static ContentUtils.Content getContent(String id) {
      String data = getContentList();
      if (null != data) {
         JSONArray jsonArr = new JSONArray(data);

         for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject obj = jsonArr.optJSONObject(i);
            if (id.equals(obj.optString("id"))) {
               ContentUtils.Content content = new ContentUtils.Content();
               content.id = obj.optString("id");
               content.title = obj.optString("title");
               content.created = TpvDateUtils.getContentFormatedChangedTime(obj.optString("created"));
               content.changed = TpvDateUtils.getContentFormatedChangedTime(obj.optString("changed"));
               content.thumbnail = obj.optString("thumbnail");
               content.orientation = obj.optString("orientation");
               return content;
            }
         }
      }

      return null;
   }

   public static String getContentTitle(int websiteId) {
      ContentUtils.Content content = getContent(String.valueOf(websiteId));
      return content == null ? null : content.title;
   }

   public static String getContentEditUrl(int contentId) {
      return contentId > 0 ? CommonConstants.CMS_URL + "/website/edit/" + contentId : CommonConstants.CMS_URL;
   }

   private static String getDataFromUrl(String urlStr) {
      URL url = null;
      URLConnection conn = null;
      StringBuilder result = new StringBuilder();

      try {
         url = new URL(urlStr);
         conn = url.openConnection();
      } catch (MalformedURLException e2) {
         LOG.error(e2.getMessage(), e2);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      if (null != conn) {
         try (
            InputStreamReader input = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader r = new BufferedReader(input);
         ) {
            String str = "";

            do {
               str = r.readLine();
               if (null != str && !"null".equals(str)) {
                  result.append(str);
               }
            } while (null != str && !"null".equals(str));
         } catch (IOException e1) {
            LOG.error(e1.getMessage(), e1);
         } catch (NullPointerException e2) {
            LOG.error(e2.getMessage(), e2);
         }
      }

      if ("".equals(result.toString())) {
         result.append("[]");
      }

      return result.toString();
   }

   public static String downloadContentZip(String websiteId) throws IOException {
      LOG.info("download content:{}", websiteId);
      String getzipUrl = GET_ZIP_URL_PREFIX + websiteId;
      File storeZipFile = File.createTempFile("assignContent-", ".zip", new File(CommonConstants.USER_ZIP_TEMP_LOCATION));
      getZipFromUrl(getzipUrl, storeZipFile.getAbsolutePath());
      return storeZipFile.getAbsolutePath();
   }

   public static String copyWebsiteToAssembly(String websiteId, String platform, String outputPath) throws IOException {
      LOG.info("copyWebsiteToAssembly,websiteId={}", websiteId);
      String tempZipFile = downloadContentZip(websiteId);
      String unZipSmartInfoBrowserLocationStr = outputPath + "/" + PlatformUtils.getSmartinfoDirctoryByPlatform(platform) + "/";
      File existfile = new File(unZipSmartInfoBrowserLocationStr);
      existfile.mkdirs();
      if (existfile.exists() || existfile.isDirectory()) {
         FileUtils.forceDelete(existfile);
      }

      ZipCommonUtils.unzip(new File(tempZipFile), unZipSmartInfoBrowserLocationStr);
      String txtName = PlatformUtils.getSmartinfoIdentifierTxtName(platform);
      if (StringUtils.isNotBlank(txtName)) {
         String changedDate = getSmartInfoChangedTime(String.valueOf(websiteId));
         File identifierFile = new File(unZipSmartInfoBrowserLocationStr + txtName);
         FileUtils.writeStringToFile(identifierFile, changedDate, StandardCharsets.UTF_8);
      }

      FileUtils.deleteQuietly(new File(tempZipFile));
      LOG.info("AssginContentServlet:delete temp file {} successfully!", tempZipFile);
      return unZipSmartInfoBrowserLocationStr;
   }

   public static String getSmartInfoChangedTime(String id) {
      ContentUtils.Content content = getContent(id);
      String identifier = content == null ? null : content.changed + " " + content.title;
      return TpvStringUtils.limitStringLength(identifier, 32);
   }

   public static void getZipFromUrl(String url, String destZipFileStr) throws IOException {
      LOG.info("download content from:{}", url);
      URL url1 = new URL(url);
      URLConnection conn = url1.openConnection();

      try (
         InputStream inStream = conn.getInputStream();
         FileOutputStream fs = new FileOutputStream(destZipFileStr);
      ) {
         byte[] buffer = new byte[1024];
         int byteread = 0;

         while ((byteread = inStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
         }
      }

      LOG.info("download content finished");
   }

   public static class Content {
      String id;
      String title;
      String created;
      String changed;
      String thumbnail;
      String orientation;
   }
}
