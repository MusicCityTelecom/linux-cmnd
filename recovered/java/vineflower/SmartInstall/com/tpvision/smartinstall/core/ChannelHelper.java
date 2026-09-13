package com.tpvision.smartinstall.core;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.gateway.ResponsePlayInfo;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.servlet.LastRFConfig;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.xml.channel.v4.Broadcast;
import com.tpvision.smartinstall.xml.channel.v4.Channel;
import com.tpvision.smartinstall.xml.channel.v4.Multicast;
import com.tpvision.smartinstall.xml.channel.v4.Setup;
import com.tpvision.smartinstall.xml.channel.v5.App;
import com.tpvision.smartinstall.xml.channel.v5.Media;
import com.tpvision.smartinstall.xml.channel.v5.Source;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelHelper {
   private static final String LOGO_PREFIX = "file://";
   private static final Logger LOG = LoggerFactory.getLogger(ChannelHelper.class);
   private int channelPackageId;
   protected SettingChannelBean scb;
   protected ChannelPackage cp;
   protected List<?> channelList;
   protected List<?> applicationList;
   protected boolean helperValid = false;
   private String errorMessage = "";
   JSONArray jaChannels = null;
   JSONArray jaApplication = null;

   public ChannelHelper(int channelPackageId) {
      this.channelPackageId = channelPackageId;
      this.loadData();
   }

   private void loadData() {
      try {
         this.cp = JpaManager.getChannelPackageManager().loadByKey(this.channelPackageId);
         if (this.cp == null) {
            throw new BaseHttpServlet.MessageException("cp is null:" + this.channelPackageId);
         }

         this.scb = new Gson().fromJson(this.cp.getValue(), SettingChannelBean.class);
         this.channelList = this.scb.getChannelList();
         this.jaChannels = new JSONArray(new Gson().toJson(this.channelList));
         this.applicationList = this.scb.getApplicationList();
         this.jaApplication = new JSONArray(new Gson().toJson(this.applicationList));
         this.helperValid = true;
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         this.errorMessage = e.getMessage();
         this.helperValid = false;
      }
   }

   private void checkValid() throws BaseHttpServlet.MessageException {
      if (!this.helperValid) {
         throw new BaseHttpServlet.MessageException(this.errorMessage);
      }
   }

   public void addChannel(HttpServletRequest request) throws BaseHttpServlet.MessageException {
      this.checkValid();
   }

   public void deleteChannels(String ids) throws BaseHttpServlet.MessageException, SQLException {
      this.checkValid();
      if (ids == null) {
         throw new BaseHttpServlet.MessageException("channel presetnumber is null");
      }

      String[] idArray = ids.split(",");

      for (String id : idArray) {
         this.deleteChannel(id);
      }

      this.save();
   }

   public void deleteChannel(String idStr) {
      int id = Integer.parseInt(idStr);
      if (id >= 0 && id < this.jaChannels.length()) {
         this.jaChannels.remove(id);
      } else {
         LOG.error("channel id exceed,id=" + idStr);
      }
   }

   public static Double getSafeDouble(JSONObject obj, String key, Double defaultValue) {
      String val = obj.optString(key, "").trim();
      if (val.isEmpty()) {
         return defaultValue;
      }

      try {
         return Double.valueOf(val);
      } catch (NumberFormatException e) {
         return defaultValue;
      }
   }

   public void sortChannels() throws BaseHttpServlet.MessageException {
      this.checkValid();
      List<JSONObject> localchannelList = new ArrayList<>();

      for (int i = 0; i < this.jaChannels.length(); i++) {
         localchannelList.add(this.jaChannels.optJSONObject(i));
      }

      Collections.sort(localchannelList, new Comparator<JSONObject>() {
         public int compare(JSONObject arg0, JSONObject arg1) {
            JSONObject setup0 = arg0.optJSONObject("setup");
            JSONObject setup1 = arg1.optJSONObject("setup");
            Double i0 = ChannelHelper.getSafeDouble(setup0, "presetnumber", 0.0);
            Double i1 = ChannelHelper.getSafeDouble(setup1, "presetnumber", 0.0);
            if (i0 == 0.0 && i1 != 0.0) {
               return 1;
            } else {
               return i0 != 0.0 && i1 == 0.0 ? -1 : i0.compareTo(i1);
            }
         }
      });
      this.jaChannels = new JSONArray();

      for (JSONObject obj : localchannelList) {
         this.jaChannels.put(obj);
      }

      this.save();
   }

   public void resetChannelsNo() throws BaseHttpServlet.MessageException {
      this.checkValid();

      for (int i = 0; i < this.jaChannels.length(); i++) {
         JSONObject setupJson = this.jaChannels.getJSONObject(i).optJSONObject("setup");
         if (setupJson != null) {
            setupJson.put("presetnumber", String.valueOf(i + 1));
         }
      }

      this.save();
   }

   public void swapChannelsNo(String swapIds) throws BaseHttpServlet.MessageException {
      this.checkValid();
      int[] swapIdArray = TpvStringUtils.splitStringToIntArray(swapIds, ",", -1);
      if (swapIdArray.length != 2) {
         LOG.error("swap ids <{}> format error", swapIds);
         throw new BaseHttpServlet.MessageException("swap item id error");
      }

      int id1 = swapIdArray[0];
      int id2 = swapIdArray[1];
      int count = this.jaChannels.length();
      if (id1 >= 0 && id1 < count && id2 >= 0 && id2 < count && id1 != id2) {
         JSONObject channelSetup1 = this.jaChannels.getJSONObject(id1).optJSONObject("setup");
         JSONObject channelSetup2 = this.jaChannels.getJSONObject(id2).optJSONObject("setup");
         if (channelSetup1 != null && channelSetup2 != null) {
            String presetnumber1 = channelSetup1.optString("presetnumber");
            String presetnumber2 = channelSetup2.optString("presetnumber");
            channelSetup1.put("presetnumber", presetnumber2);
            channelSetup2.put("presetnumber", presetnumber1);
            this.sortChannels();
            this.save();
         } else {
            LOG.error("missing channel setup data <{}> and <{}>", channelSetup1, channelSetup2);
            throw new BaseHttpServlet.MessageException("missing channel setup data");
         }
      } else {
         LOG.error("invalid swap ids <{}>", swapIds);
         throw new BaseHttpServlet.MessageException("invalid swap item id!");
      }
   }

   public void updateChannel(int id, String jsChannel) throws BaseHttpServlet.MessageException, SQLException {
      this.checkValid();
      JSONObject obj = new JSONObject(jsChannel);
      this.deleteMediaOriginalUrl(obj);
      JSONObject channelObj = this.jaChannels.getJSONObject(id);
      String[] items = new String[]{"broadcast", "setup", "multicast", "media", "source"};
      boolean isPresetnumberUpdated = false;

      for (String item : items) {
         JSONObject itemObj = obj.optJSONObject(item);
         JSONObject itemObj2 = channelObj.optJSONObject(item);
         if (itemObj != null && itemObj2 == null) {
            LOG.error("channel obj not match");
            throw new BaseHttpServlet.MessageException("channel obj not match");
         }

         if (itemObj != null) {
            for (Object keyStr : itemObj.keySet()) {
               String key = keyStr.toString();
               String keyvalue = itemObj.optString(key, "");
               if (key.equalsIgnoreCase("serviceid")) {
                  key = "serviceID";
               }

               itemObj2.put(key, keyvalue);
               if ("presetnumber".equalsIgnoreCase(key)) {
                  this.handleConflictChannelNo(id, keyvalue);
                  if (!isPresetnumberUpdated) {
                     isPresetnumberUpdated = true;
                  }
               }
            }
         }
      }

      if (isPresetnumberUpdated) {
         this.sortChannels();
      }

      this.save();
   }

   private void deleteMediaOriginalUrl(JSONObject object) {
      try {
         JSONObject media = object.getJSONObject("media");
         if (null == media) {
            return;
         }

         String url = media.getString("url");
         String originalUrl = media.getString("originalUrl");
         if (StringUtils.isBlank(originalUrl)) {
            return;
         }

         if (StringUtils.equalsIgnoreCase(url, originalUrl)) {
            return;
         }

         String srcRootPath = CommonConstants.CLONE_PROCESS_LOCATION
            + "ChannelPackages"
            + File.separator
            + this.channelPackageId
            + File.separator
            + "MediaChannels";
         File processMediaChannel = new File(srcRootPath);
         if (!processMediaChannel.exists()) {
            LOG.error("not find filedir to delete original file");
            return;
         }

         String oldFileName = originalUrl.replace("file://", "");
         String oldFilePath = processMediaChannel.toString() + File.separator + originalUrl.replace("file://", "");
         File oldFile = new File(oldFilePath);
         if (oldFile.exists()) {
            FileUtils.deleteQuietly(oldFile);
            LOG.info("delete file success:{}", oldFileName);
         }
      } catch (Exception e) {
         LOG.error("delete original file fail");
         LOG.error(e.getMessage(), e);
      }
   }

   public void updateApplication(int id, String jsApplication) throws BaseHttpServlet.MessageException, SQLException {
      this.checkValid();
      JSONObject obj = new JSONObject(jsApplication);
      JSONObject applicationObj = this.jaApplication.getJSONObject(id);
      String[] items = new String[]{"setup"};

      for (String item : items) {
         JSONObject itemObj = obj.optJSONObject(item);
         JSONObject itemObj2 = applicationObj.optJSONObject(item);
         if (itemObj != null && itemObj2 == null) {
            LOG.error("application obj not match");
            throw new BaseHttpServlet.MessageException("application obj not match");
         }

         if (itemObj != null) {
            for (Object keyStr : itemObj.keySet()) {
               String key = keyStr.toString();
               String keyvalue = itemObj.optString(key, "");
               itemObj2.put(key, keyvalue);
            }
         }
      }

      this.save();
   }

   private void handleConflictChannelNo(int channelId, String channelNo) {
      for (int i = 0; i < this.jaChannels.length(); i++) {
         if (i != channelId) {
            JSONObject setupJson = this.jaChannels.getJSONObject(i).optJSONObject("setup");
            if (setupJson != null) {
               String presetnumber = setupJson.optString("presetnumber");
               if (String.valueOf(channelNo).equals(presetnumber)) {
                  String assignNewNo = String.valueOf(Integer.parseInt(channelNo) + 1);
                  setupJson.put("presetnumber", assignNewNo);
                  this.handleConflictChannelNo(i, assignNewNo);
               }
            }
         }
      }
   }

   private void fixChannelLogoUrl() {
      String channelLogoPath = CloneItemUtils.getChannelPackageLogoPath(this.channelPackageId) + "custom/";
      List<Integer> requireLogoUrlList = new ArrayList<>();
      Map<String, Integer> currentUsedLogoUrl = new HashMap<>();

      for (int i = 0; i < this.jaChannels.length(); i++) {
         JSONObject obj = this.jaChannels.getJSONObject(i);
         JSONObject setupObj = obj.optJSONObject("setup");
         if (setupObj == null) {
            LOG.warn("invalid channel obj,{}", obj);
         } else {
            String presetNum = setupObj.optString("presetnumber", "0");
            String logoPath = setupObj.optString("logo", "");
            if (logoPath.contains("custom/")) {
               String fileName = logoPath.replace("file://custom/", "");
               String fileNameLowerCase = fileName.toLowerCase();
               if (!fileName.equals(fileNameLowerCase)) {
                  setupObj.put("logo", logoPath.toLowerCase());
                  File logoFile = new File(channelLogoPath + fileName);
                  if (logoFile.exists()) {
                     if (logoFile.renameTo(new File(channelLogoPath + fileNameLowerCase))) {
                        LOG.info("update to lower case name of file <{}>", logoFile.getAbsolutePath());
                     } else {
                        LOG.warn("rename file name failure <{}>", logoFile);
                     }
                  }
               }

               Integer linkCount = currentUsedLogoUrl.get(fileNameLowerCase);
               if (linkCount == null) {
                  linkCount = 0;
               }

               linkCount = linkCount + 1;
               currentUsedLogoUrl.put(fileNameLowerCase, linkCount);
               if (!logoPath.contains("custom/" + presetNum + ".")) {
                  requireLogoUrlList.add(i);
               }
            }
         }
      }

      if (requireLogoUrlList.isEmpty()) {
         LOG.info("no need to fix channel logo url");
      } else {
         File[] listFiles = new File(channelLogoPath).listFiles();
         if (listFiles != null) {
            for (File file : listFiles) {
               if (file.isFile()) {
                  String fileName = file.getName().toLowerCase();
                  if (!currentUsedLogoUrl.containsKey(fileName)) {
                     FileUtils.deleteQuietly(file);
                  }
               }
            }
         }

         for (Integer index : requireLogoUrlList) {
            JSONObject setupObj = this.jaChannels.getJSONObject(index).optJSONObject("setup");
            String oriFileName = setupObj.getString("logo").replace("file://custom/", "").toLowerCase();
            File oriFile = new File(channelLogoPath + oriFileName);
            if (!oriFile.exists()) {
               setupObj.put("logo", "");
            } else {
               String shouldFileName = setupObj.optString("presetnumber", "0") + "." + FilenameUtils.getExtension(oriFileName);
               setupObj.put("logo", "file://custom/" + shouldFileName);
               String tempFileName = shouldFileName + ".tmp";
               File tempFile = new File(channelLogoPath + tempFileName);
               if (!tempFile.exists()) {
                  int leftLinkCount = currentUsedLogoUrl.get(oriFileName) - 1;
                  if (leftLinkCount <= 0) {
                     if (oriFile.renameTo(tempFile)) {
                        LOG.info("rename logo name from <{}> to <{}>", oriFileName, tempFileName);
                     }
                  } else {
                     try {
                        FileUtils.copyFile(oriFile, tempFile);
                        LOG.info("copy logo name from <{}> to <{}>", oriFileName, tempFileName);
                     } catch (IOException e) {
                        LOG.error(e.getMessage(), e);
                     }
                  }

                  currentUsedLogoUrl.put(oriFileName, leftLinkCount);
               }
            }
         }

         File[] currentFiles = new File(channelLogoPath).listFiles();
         if (currentFiles != null) {
            for (File file : currentFiles) {
               if (file.getName().endsWith(".tmp")) {
                  String shouldName = file.getName().replace(".tmp", "");
                  File shouldFile = new File(channelLogoPath + shouldName);
                  if (shouldFile.exists()) {
                     FileUtils.deleteQuietly(file);
                  } else {
                     try {
                        FileUtils.moveFile(file, shouldFile);
                     } catch (IOException e) {
                        LOG.error(e.getMessage(), e);
                     }
                  }
               }
            }
         }
      }
   }

   private void save() {
      this.fixChannelLogoUrl();
      this.scb.updateChannelList(this.jaChannels.toString());
      this.scb.updateApplicationList(this.jaApplication.toString());
      String toDbString = new Gson().toJson(this.scb);
      this.cp.setValue(toDbString);
      int channelCount = 0;
      if (this.scb.getChannelList() != null) {
         channelCount = this.scb.getChannelList().size();
      }

      this.cp.setNumberOfChs(channelCount);
      JpaManager.getChannelPackageManager().save(this.cp);
   }

   public void deleteLogoFile(String logo) {
      String logoPath = CloneItemUtils.getChannelPackageLogoPath(this.channelPackageId);
      String newFileName = null == logo ? "" : logo.replace("file://", "/");
      String logoFileName = logoPath + File.separator + "custom" + File.separator + FilenameUtils.getName(newFileName);
      File logoFile = new File(logoFileName);
      if (logoFile.exists() && !logoFile.isDirectory()) {
         FileUtils.deleteQuietly(logoFile);
      }
   }

   public void updateThemeTvIconMapping(Map<String, String> iconMap) throws BaseHttpServlet.MessageException {
      this.checkValid();
      JSONObject themeTvJson = this.scb.getThemeTvJson();

      for (Entry<String, String> ttv : iconMap.entrySet()) {
         String ttvKeyName = "ttv" + ttv.getKey();
         String updatedPath = ttv.getValue();
         JSONObject themTv = themeTvJson.optJSONObject(ttvKeyName);
         if (themTv == null) {
            themTv = new JSONObject();
            themTv.put("name", "");
         }

         String oldIconPath = themTv.optString("icon", "").replace("file://", "");
         if (!oldIconPath.equalsIgnoreCase(updatedPath)) {
            this.removeExpireThemeTvIcon(oldIconPath);
            themTv.put("icon", updatedPath);
         }
      }

      this.scb.updateThemeTvs(themeTvJson);
      this.save();
   }

   private void removeExpireThemeTvIcon(String oldIconPath) {
      if (oldIconPath.contains("custom")) {
         String themeIconPath = CloneItemUtils.getChannelThemeTvIconPath(this.channelPackageId);
         String fullLogoPath = themeIconPath + File.separator + oldIconPath;
         File oldLogoFile = new File(fullLogoPath);
         if (oldLogoFile.exists()) {
            try {
               FileUtils.forceDelete(oldLogoFile);
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }
   }

   public void updateChannelLogoMapping(Map<String, String> logsMap) throws BaseHttpServlet.MessageException {
      this.checkValid();

      for (Entry<String, String> logo : logsMap.entrySet()) {
         this.updateSingChannelLog(logo.getKey(), logo.getValue());
      }

      this.save();
   }

   public void updateChannelLogo(String presetNum, String newPicName) throws BaseHttpServlet.MessageException {
      this.checkValid();
      this.updateSingChannelLog(presetNum, newPicName);
      this.save();
   }

   private void updateSingChannelLog(String presetNum, String newPicName) {
      JSONArray ja1 = this.jaChannels;
      String packageLogoPath = CloneItemUtils.getChannelPackageLogoPath(this.channelPackageId);

      for (int i = 0; i < ja1.length(); i++) {
         JSONObject obj = ja1.getJSONObject(i);
         JSONObject setupObj = obj.optJSONObject("setup");
         if (setupObj == null) {
            LOG.error("invalid channel obj," + obj.toString());
         } else {
            String loopPresetNum = setupObj.optString("presetnumber", "0");
            if (loopPresetNum.equalsIgnoreCase(presetNum)) {
               String oldLogoPath = setupObj.optString("logo", "");
               String newlogoPath = "";
               if (StringUtils.isNotBlank(newPicName)) {
                  newlogoPath = "file://custom/" + newPicName;
               }

               setupObj.put("logo", newlogoPath);
               if (!StringUtils.equalsIgnoreCase(oldLogoPath, newlogoPath) && oldLogoPath.contains("custom")) {
                  String oldLogoFileName = oldLogoPath.replace("file://", "/");
                  File oldLogoFile = new File(packageLogoPath + File.separator + oldLogoFileName);
                  FileUtils.deleteQuietly(oldLogoFile);
               }
            }
         }
      }
   }

   public boolean isPresetNumberExist(String presetNumber) throws BaseHttpServlet.MessageException {
      this.checkValid();
      List<String> presetNumberList = new ArrayList<>();

      for (int i = 0; i < this.jaChannels.length(); i++) {
         JSONObject obj = this.jaChannels.getJSONObject(i);
         JSONObject setupObj = obj.optJSONObject("setup");
         if (setupObj == null) {
            LOG.error("invalid channel obj,{}", obj);
         } else {
            String presetNum = setupObj.optString("presetnumber", "0");
            presetNumberList.add(presetNum);
         }
      }

      return presetNumberList.contains(presetNumber);
   }

   public JSONArray getChannelLogoMap() throws BaseHttpServlet.MessageException {
      this.checkValid();
      JSONArray ja = new JSONArray();

      for (int i = 0; i < this.jaChannels.length(); i++) {
         JSONObject obj = this.jaChannels.getJSONObject(i);
         JSONObject setupObj = obj.getJSONObject("setup");
         JSONObject jo = new JSONObject();
         String logoPath = setupObj.has("logo") ? setupObj.getString("logo") : "";
         if (null == logoPath || !this.channelLogoExists(logoPath) || logoPath.startsWith("file://default/")) {
            logoPath = "";
         }

         jo.put("presetNumber", setupObj.optString("presetnumber"));
         jo.put("logo", logoPath);
         ja.put(jo);
      }

      return ja;
   }

   private boolean channelLogoExists(String logoPath) {
      String path = null;
      if (null != logoPath) {
         path = logoPath.replaceAll("file://", "");
         if (path.startsWith("default/")) {
            path = "";
         }
      }

      path = CloneItemUtils.getChannelPackageLogoPath(this.channelPackageId) + path;
      File logoFile = new File(path);
      return logoFile.exists();
   }

   public JSONObject getThemeTvJson() throws BaseHttpServlet.MessageException {
      this.checkValid();
      return this.scb.getThemeTvJson();
   }

   public List<String> getThemeTvNameList() throws BaseHttpServlet.MessageException {
      this.checkValid();
      return this.scb.getThemeTvNameList();
   }

   public void updateThemeTvNameList(List<String> nameList) throws BaseHttpServlet.MessageException {
      this.checkValid();
      JSONObject themeTvObj = this.scb.getThemeTvJson();

      for (int i = 1; i <= 10; i++) {
         JSONObject obj = themeTvObj.optJSONObject("ttv" + i);
         if (obj == null) {
            obj = new JSONObject();
            obj.put("icon", "");
            obj.put("name", "");
            themeTvObj.put("ttv" + i, obj);
         }

         String themetvname = nameList.get(i - 1);
         obj.put("name", themetvname == null ? "" : themetvname);
         String oldIconPath = obj.optString("icon");
         if (StringUtils.isBlank(themetvname) && StringUtils.isNotBlank(oldIconPath)) {
            this.removeExpireThemeTvIcon(oldIconPath);
            obj.put("icon", "");
         }
      }

      this.scb.updateThemeTvs(themeTvObj);
      this.save();
   }

   public List<?> getApplicationList() throws BaseHttpServlet.MessageException {
      this.checkValid();
      return this.scb.getApplicationList();
   }

   public List<String> getChannelNames() throws BaseHttpServlet.MessageException {
      this.checkValid();
      List<String> nameList = new ArrayList<>();

      for (int i = 0; i < this.jaChannels.length(); i++) {
         JSONObject channel = this.jaChannels.getJSONObject(i);
         JSONObject setup = channel.optJSONObject("setup");
         nameList.add(setup.optString("presetnumber") + " - " + setup.optString("name"));
      }

      return nameList;
   }

   public List<String> getChannelNamesByTypeAndMediums(String type, String... mediums) throws BaseHttpServlet.MessageException {
      this.checkValid();
      List<String> nameList = new ArrayList<>();
      List<String> mediumList = Arrays.asList(mediums).stream().map(String::toLowerCase).collect(Collectors.toList());

      for (int i = 0; i < this.jaChannels.length(); i++) {
         JSONObject channel = this.jaChannels.getJSONObject(i);
         JSONObject typeObject = channel.optJSONObject(StringUtils.lowerCase(type));
         if (typeObject != null) {
            String medium = typeObject.optString("medium");
            if (mediumList.isEmpty() || medium != null && mediumList.contains(medium.toLowerCase())) {
               JSONObject setup = channel.optJSONObject("setup");
               nameList.add(setup.optString("presetnumber") + " - " + setup.optString("name"));
            }
         }
      }

      return nameList;
   }

   public List<?> getChannelList() throws BaseHttpServlet.MessageException {
      this.checkValid();
      return this.scb.getChannelList();
   }

   public void newChannel(String data) throws BaseHttpServlet.MessageException {
      this.checkValid();
      JSONObject params = new JSONObject(data);
      if ("v5".equalsIgnoreCase(this.scb.getChannelVersion())) {
         this.addV5ChannelObj(params);
      } else if ("v4".equalsIgnoreCase(this.scb.getChannelVersion())) {
         this.addV4ChannelObj(params);
      } else {
         LOG.error("not support channel add except v4 & v5, current version is :{}", this.scb.getChannelVersion());
      }

      this.sortChannels();
   }

   public void addV4ChannelObj(JSONObject obj) {
      List<Channel> channels = new ArrayList<>();
      channels.add(this.v4ChannelFromRequest(obj));

      for (Channel channel : channels) {
         JSONObject channelObj = new JSONObject(new Gson().toJson(channel));
         this.jaChannels.put(channelObj);
         Optional<String> presetNumberOp = Optional.ofNullable(channel.getSetup()).map(Setup::getPresetnumber);
         if (presetNumberOp.isPresent()) {
            int position = this.jaChannels.length() - 1;
            String presetNumber = presetNumberOp.get();
            this.handleConflictChannelNo(position, presetNumber);
         }
      }
   }

   public void addV5ChannelObj(JSONObject obj) {
      List<com.tpvision.smartinstall.xml.channel.v5.Channel> channels = new ArrayList<>();
      String chtype = obj.optString("chtype");
      if ("cmnd_streams".equalsIgnoreCase(chtype)) {
         channels.addAll(this.CMNDStreamChannelFromRequest(obj));
      } else {
         channels.add(this.v5ChannelFromRequest(obj));
      }

      for (com.tpvision.smartinstall.xml.channel.v5.Channel channel : channels) {
         JSONObject channelObj = new JSONObject(new Gson().toJson(channel));
         this.jaChannels.put(channelObj);
         Optional<String> presetNumberOp = Optional.ofNullable(channel.getSetup()).map(com.tpvision.smartinstall.xml.channel.v5.Setup::getPresetnumber);
         if (presetNumberOp.isPresent()) {
            int position = this.jaChannels.length() - 1;
            String presetNumber = presetNumberOp.get();
            this.handleConflictChannelNo(position, presetNumber);
         }
      }
   }

   public Channel v4ChannelFromRequest(JSONObject obj) {
      Channel channel = new Channel();
      Setup setup = new Setup();
      String chtype = obj.optString("chtype", "rf");
      String number = obj.optString("presetnumber", "");
      String frequency = obj.optString("frequency", "0");
      String servicetype = obj.optString("servicetype", "TV");
      String bandwidth = obj.optString("bandwidth", "Auto");
      String medium = obj.optString("medium", "dvbt");
      String modulation = obj.optString("modulation", "16");
      String onid = obj.optString("onid", "0");
      String symrate = obj.optString("symbolrate", "0");
      String serviceId = obj.optString("serviceid", "0");
      String system = obj.optString("system", "");
      String tsid = obj.optString("tsid", "0");
      String name = obj.optString("name");
      String blank = obj.optString("blank", "0");
      String skip = obj.optString("skip", "0");
      String freePKG = "1";
      String payPKG1 = "0";
      String payPKG2 = "0";
      if (chtype.equalsIgnoreCase("broadcast")) {
         Broadcast broadcast = new Broadcast();
         broadcast.setMedium(medium);
         broadcast.setFrequency(frequency);
         broadcast.setSystem(system);
         broadcast.setServiceID(serviceId);
         broadcast.setONID(onid);
         broadcast.setTSID(tsid);
         broadcast.setModulation(modulation);
         broadcast.setSymbolrate(symrate);
         broadcast.setBandwidth(bandwidth);
         broadcast.setServicetype(servicetype);
         channel.setBroadcast(broadcast);
      } else if ("multicast".equalsIgnoreCase(chtype)) {
         Multicast multicast = new Multicast();
         String multicasturl = obj.optString("url", "");
         multicast.setUrl(multicasturl);
         channel.setMulticast(multicast);
      }

      setup.setPresetnumber(number);
      setup.setName(name);
      setup.setBlank(blank);
      setup.setSkip(skip);
      setup.setFreePKG(freePKG);
      setup.setPayPKG1(payPKG1);
      setup.setPayPKG2(payPKG2);
      channel.setSetup(setup);
      return channel;
   }

   public com.tpvision.smartinstall.xml.channel.v5.Channel v5ChannelFromRequest(JSONObject obj) {
      com.tpvision.smartinstall.xml.channel.v5.Channel channel = new com.tpvision.smartinstall.xml.channel.v5.Channel();
      com.tpvision.smartinstall.xml.channel.v5.Setup setup = new com.tpvision.smartinstall.xml.channel.v5.Setup();
      String chtype = obj.optString("chtype", "rf");
      String number = obj.optString("presetnumber", "");
      String frequency = obj.optString("frequency", "0");
      String servicetype = obj.optString("servicetype", "TV");
      String bandwidth = obj.optString("bandwidth", "Auto");
      String medium = obj.optString("medium", "dvbt");
      String modulation = obj.optString("modulation", "16");
      String onid = obj.optString("onid", "0");
      String symrate = obj.optString("symbolrate", "0");
      String serviceId = obj.optString("serviceid", "0");
      String system = obj.optString("system", "");
      String tsid = obj.optString("tsid", "0");
      String name = obj.optString("name");
      String blank = obj.optString("blank", "0");
      String skip = obj.optString("skip", "0");
      String physicalChannel = obj.optString("physicalchannel", "2");
      String programNumber = obj.optString("programnumber", "0");
      String orbitalposition = obj.optString("orbitalposition", "0192E");
      String polarization = obj.optString("polarization", "Horizontal");
      String freePKG = "1";
      String payPKG1 = "0";
      String payPKG2 = "0";
      if (chtype.equalsIgnoreCase("broadcast")) {
         com.tpvision.smartinstall.xml.channel.v5.Broadcast broadcast = new com.tpvision.smartinstall.xml.channel.v5.Broadcast();
         broadcast.setFrequency(frequency);
         broadcast.setServicetype(servicetype);
         broadcast.setBandwidth(bandwidth);
         broadcast.setMedium(medium);
         broadcast.setModulation(modulation);
         broadcast.setONID(onid);
         broadcast.setPhysicalChannel(physicalChannel);
         broadcast.setProgramNumber(programNumber);
         broadcast.setOrbitalposition(orbitalposition);
         broadcast.setPolarization(polarization);
         broadcast.setServiceID(serviceId);
         broadcast.setSymbolrate(symrate);
         broadcast.setSystem(system);
         broadcast.setTSID(tsid);
         channel.setBroadcast(broadcast);
      } else if ("multicast".equalsIgnoreCase(chtype)) {
         com.tpvision.smartinstall.xml.channel.v5.Multicast multicast = new com.tpvision.smartinstall.xml.channel.v5.Multicast();
         String multicasturl = obj.optString("url", "");
         multicast.setUrl(multicasturl);
         channel.setMulticast(multicast);
      } else if ("media".equalsIgnoreCase(chtype) || "hls".equalsIgnoreCase(chtype)) {
         Media media = new Media();
         String mediaurl = obj.optString("url", "");
         media.setUrl(mediaurl);
         channel.setMedia(media);
      } else if ("source".equalsIgnoreCase(chtype)) {
         Source source = new Source();
         String sourceType = obj.optString("type", "");
         source.setType(sourceType);
         channel.setSource(source);
      } else if ("app".equalsIgnoreCase(chtype)) {
         App app = new App();
         app.setAppName(obj.optString("appName", ""));
         app.setType("Native");
         channel.setApp(app);
      }

      setup.setTTV1("0");
      setup.setTTV2("0");
      setup.setTTV3("0");
      setup.setTTV4("0");
      setup.setTTV5("0");
      setup.setTTV6("0");
      setup.setTTV7("0");
      setup.setTTV8("0");
      setup.setTTV9("0");
      setup.setTTV10("0");
      setup.setName(name);
      setup.setPresetnumber(number);
      setup.setSkip(skip);
      setup.setBlank(blank);
      setup.setFreePKG(freePKG);
      setup.setPayPKG1(payPKG1);
      setup.setPayPKG2(payPKG2);
      channel.setSetup(setup);
      return channel;
   }

   public List<com.tpvision.smartinstall.xml.channel.v5.Channel> CMNDStreamChannelFromRequest(JSONObject obj) {
      List<com.tpvision.smartinstall.xml.channel.v5.Channel> channels = new ArrayList<>();

      for (ResponsePlayInfo.AVService avService : GatewayManager.getInstance().getAVServices()) {
         String checkValue = obj.optString("cmnd_streams_" + avService.getSID());
         if (null != checkValue && checkValue.equalsIgnoreCase("on")) {
            com.tpvision.smartinstall.xml.channel.v5.Channel channel = new com.tpvision.smartinstall.xml.channel.v5.Channel();
            com.tpvision.smartinstall.xml.channel.v5.Setup setup = new com.tpvision.smartinstall.xml.channel.v5.Setup();
            if (avService.getFreq() > 0L) {
               com.tpvision.smartinstall.xml.channel.v5.Broadcast broadcast = new com.tpvision.smartinstall.xml.channel.v5.Broadcast();
               String frequency = String.valueOf(avService.getFreq()).substring(0, 6);
               broadcast.setFrequency(frequency);
               broadcast.setServicetype("TV");
               broadcast.setBandwidth("Auto");
               broadcast.setMedium("dvbt");
               LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
               String modulation = lastRFConfig.getModulation();
               if (modulation != null) {
                  modulation = modulation.replace("-QAM", "");
               } else {
                  modulation = "auto";
               }

               broadcast.setModulation(modulation);
               broadcast.setONID(String.valueOf(avService.getONID()));
               broadcast.setServiceID(String.valueOf(avService.getSID()));
               broadcast.setSymbolrate("0");
               broadcast.setSystem("");
               broadcast.setTSID(String.valueOf(avService.getTSID()));
               channel.setBroadcast(broadcast);
            } else if (avService.getUrl() != null) {
               String casttype = "multicast";

               try {
                  InetAddress inetAddress = InetAddress.getByName(avService.getUrl());
                  casttype = inetAddress.isMulticastAddress() ? "multicast" : "unicast";
               } catch (UnknownHostException e) {
                  LOG.error("not a valid url for ip");
               }

               String encoding = "VBR";
               String multicasturl = String.format(
                  "%s://%s:%d/%d/%d/%d/%s",
                  casttype,
                  avService.getUrl(),
                  avService.getPort(),
                  avService.getSID(),
                  avService.getONID(),
                  avService.getTSID(),
                  encoding
               );
               com.tpvision.smartinstall.xml.channel.v5.Multicast multicast = new com.tpvision.smartinstall.xml.channel.v5.Multicast();
               multicast.setUrl(multicasturl);
               channel.setMulticast(multicast);
            }

            setup.setTTV1("0");
            setup.setTTV2("0");
            setup.setTTV3("0");
            setup.setTTV4("0");
            setup.setTTV5("0");
            setup.setTTV6("0");
            setup.setTTV7("0");
            setup.setTTV8("0");
            setup.setTTV9("0");
            setup.setTTV10("0");
            setup.setName(avService.getName());
            setup.setPresetnumber(String.valueOf(avService.getPMTPid()));
            setup.setSkip("0");
            setup.setBlank("0");
            setup.setFreePKG("1");
            setup.setPayPKG1("0");
            setup.setPayPKG2("0");
            channel.setSetup(setup);
            channels.add(channel);
         }
      }

      return channels;
   }
}
