package com.tpvision.smartinstall.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tpvision.smartinstall.xml.Settings;
import com.tpvision.smartinstall.xml.channel.v4.Application;
import com.tpvision.smartinstall.xml.channel.v4.Channel;
import com.tpvision.smartinstall.xml.channel.v4.TvContents;
import com.tpvision.smartinstall.xml.channel.v5.ThemeTV;
import com.tpvision.smartinstall.xml.remotediagnose.DIAGNOSTICANALYTIC;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.RoomSpecificSettings;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingChannelBean {
   private static final Logger LOG = LoggerFactory.getLogger(SettingChannelBean.class);
   private Settings setttings;
   private String channelVersion;
   private TvContents v4Channel;
   private com.tpvision.smartinstall.xml.channel.v5.TvContents v5Channel;
   private com.tpvision.smartinstall.xml.channel.v6.TvContents v6Channel;
   private String hotelImageName;
   private String welcomelogoImageName;
   private List<String> themetvImageName;
   private DIAGNOSTICANALYTIC diagnosticAnalytic;
   private RoomSpecificSettings roomSpecificSettings;

   public SettingChannelBean() {
   }

   public SettingChannelBean(
      Settings setttings,
      TvContents v4Channel,
      com.tpvision.smartinstall.xml.channel.v5.TvContents v5Channel,
      String channelVersion,
      DIAGNOSTICANALYTIC diagnosticAnalytic,
      RoomSpecificSettings roomSpecificSettings
   ) {
      this.setttings = setttings;
      this.v4Channel = v4Channel;
      this.v5Channel = v5Channel;
      this.channelVersion = channelVersion;
      this.diagnosticAnalytic = diagnosticAnalytic;
      this.roomSpecificSettings = roomSpecificSettings;
   }

   public String exportSettingSaveJson() {
      SettingChannelBean jsonBean = new SettingChannelBean();
      jsonBean.setHotelImageName(this.hotelImageName);
      jsonBean.setWelcomelogoImageName(this.welcomelogoImageName);
      jsonBean.setThemetvImageName(this.themetvImageName);
      jsonBean.setDiagnosticAnalytic(this.diagnosticAnalytic);
      jsonBean.setRoomSpecificSettings(this.roomSpecificSettings);
      return new Gson().toJson(jsonBean);
   }

   public String exportSettingPackageSaveJson() {
      SettingChannelBean jsonBean = new SettingChannelBean();
      jsonBean.setSetttings(this.setttings);
      return new Gson().toJson(jsonBean);
   }

   public SettingChannelBean mergeWithSettingPackageBean(SettingChannelBean settingPackageBean) {
      this.setSetttings(settingPackageBean.getSetttings());
      return this;
   }

   public String exportChannelPackageJson() {
      SettingChannelBean jsonBean = new SettingChannelBean();
      jsonBean.setChannelVersion(this.channelVersion);
      jsonBean.setV4Channel(this.v4Channel);
      jsonBean.setV5Channel(this.v5Channel);
      jsonBean.setV6Channel(this.v6Channel);
      return new Gson().toJson(jsonBean);
   }

   public SettingChannelBean mergeWithChanelPackageBean(SettingChannelBean channelPackageBean) {
      this.setChannelVersion(channelPackageBean.getChannelVersion());
      this.setV4Channel(channelPackageBean.getV4Channel());
      this.setV5Channel(channelPackageBean.getV5Channel());
      this.setV6Channel(channelPackageBean.getV6Channel());
      return this;
   }

   public List getChannelList() {
      List channelList = null;
      switch (this.channelVersion.toLowerCase()) {
         case "v4":
            channelList = this.getV4Channel().getChannelMap().getChannel();
            break;
         case "v5":
            channelList = this.getV5Channel().getChannelMap().getChannel();
            break;
         case "v6":
            channelList = this.getV6Channel().getChannelMap().getChannel();
      }

      return channelList;
   }

   public List getApplicationList() {
      List appList = null;

      try {
         switch (this.channelVersion.toLowerCase()) {
            case "v4":
               appList = this.getV4Channel().getApplicationMap().getApplication();
               break;
            case "v5":
               appList = this.getV5Channel().getApplicationMap().getApplication();
               break;
            case "v6":
               appList = this.getV6Channel().getApplicationMap().getApplication();
         }

         return appList;
      } catch (NullPointerException e) {
         LOG.error("Application not exist");
         return new ArrayList();
      }
   }

   public void updateThemeTvs(JSONObject themeTvObj) {
      switch (this.channelVersion.toLowerCase()) {
         case "v5":
            ThemeTV v5themetv = new Gson().fromJson(themeTvObj.toString(), ThemeTV.class);
            this.getV5Channel().setThemeTV(v5themetv);
            break;
         case "v6":
            com.tpvision.smartinstall.xml.channel.v6.TvContents.ThemeTV v6themetv = new Gson()
               .fromJson(themeTvObj.toString(), com.tpvision.smartinstall.xml.channel.v6.TvContents.ThemeTV.class);
            this.getV6Channel().setThemeTV(v6themetv);
      }
   }

   public JSONObject getThemeTvJson() {
      String json = null;
      switch (this.channelVersion.toLowerCase()) {
         case "v5":
            ThemeTV v5themetv = this.getV5Channel().getThemeTV();
            json = new Gson().toJson(v5themetv);
            break;
         case "v6":
            com.tpvision.smartinstall.xml.channel.v6.TvContents.ThemeTV v6themetv = this.getV6Channel().getThemeTV();
            json = new Gson().toJson(v6themetv);
      }

      if (json == null || json.equalsIgnoreCase("null")) {
         json = "{}";
      }

      return new JSONObject(json);
   }

   public List<String> getThemeTvNameList() {
      List<String> appList = new ArrayList<>();
      JSONObject obj = this.getThemeTvJson();

      for (int i = 1; i <= 10; i++) {
         JSONObject obj1 = obj.optJSONObject("ttv" + i);
         String name = obj1 == null ? "" : obj1.optString("name", "");
         appList.add(name);
      }

      return appList;
   }

   public String getChannelClassName() {
      String channelClass = "";
      switch (this.channelVersion.toLowerCase()) {
         case "v4":
            channelClass = Channel.class.getName();
            break;
         case "v5":
            channelClass = com.tpvision.smartinstall.xml.channel.v5.Channel.class.getName();
            break;
         case "v6":
            channelClass = com.tpvision.smartinstall.xml.channel.v6.TvContents.ChannelMap.Channel.class.getName();
      }

      return channelClass;
   }

   public List channelListFromJson(String json) {
      List newList = null;
      switch (this.channelVersion.toLowerCase()) {
         case "v4":
            newList = new Gson().fromJson(json, (new TypeToken<List<Channel>>() {}).getType());
            break;
         case "v5":
            newList = new Gson().fromJson(json, (new TypeToken<List<com.tpvision.smartinstall.xml.channel.v5.Channel>>() {}).getType());
            break;
         case "v6":
            newList = new Gson().fromJson(json, (new TypeToken<List<com.tpvision.smartinstall.xml.channel.v6.TvContents.ChannelMap.Channel>>() {}).getType());
      }

      return newList;
   }

   public List applicationListFromJson(String json) {
      List newList = null;
      switch (this.channelVersion.toLowerCase()) {
         case "v4":
            newList = new Gson().fromJson(json, (new TypeToken<List<Application>>() {}).getType());
            break;
         case "v5":
            newList = new Gson().fromJson(json, (new TypeToken<List<com.tpvision.smartinstall.xml.channel.v5.Application>>() {}).getType());
            break;
         case "v6":
            newList = new Gson()
               .fromJson(json, (new TypeToken<List<com.tpvision.smartinstall.xml.channel.v6.TvContents.ApplicationMap.Application>>() {}).getType());
      }

      return newList;
   }

   public void updateChannelList(String jsChannels) {
      List<?> channelList = this.getChannelList();
      channelList.clear();
      channelList.addAll(this.channelListFromJson(jsChannels));
   }

   public void updateApplicationList(String jsApplications) {
      List<?> applicationList = this.getApplicationList();
      applicationList.clear();
      applicationList.addAll(this.applicationListFromJson(jsApplications));
   }

   public DIAGNOSTICANALYTIC getDiagnosticAnalytic() {
      return this.diagnosticAnalytic;
   }

   public void setDiagnosticAnalytic(DIAGNOSTICANALYTIC diagnosticAnalytic) {
      this.diagnosticAnalytic = diagnosticAnalytic;
   }

   public String getHotelImageName() {
      return this.hotelImageName;
   }

   public void setHotelImageName(String hotelImageName) {
      this.hotelImageName = hotelImageName;
   }

   public String getWelcomelogoImageName() {
      return this.welcomelogoImageName;
   }

   public void setWelcomelogoImageName(String welcomelogoImageName) {
      this.welcomelogoImageName = welcomelogoImageName;
   }

   public List<String> getThemetvImageName() {
      return this.themetvImageName;
   }

   public void setThemetvImageName(List<String> themetvImageName) {
      this.themetvImageName = themetvImageName;
   }

   public Settings getSetttings() {
      return this.setttings;
   }

   public void setSetttings(Settings setttings) {
      this.setttings = setttings;
   }

   public String getChannelVersion() {
      return this.channelVersion;
   }

   public void setChannelVersion(String channelVersion) {
      this.channelVersion = channelVersion;
   }

   public RoomSpecificSettings getRoomSpecificSettings() {
      return this.roomSpecificSettings;
   }

   public void setRoomSpecificSettings(RoomSpecificSettings roomSpecificSettings) {
      this.roomSpecificSettings = roomSpecificSettings;
   }

   public com.tpvision.smartinstall.xml.channel.v6.TvContents getV6Channel() {
      return this.v6Channel;
   }

   public void setV6Channel(com.tpvision.smartinstall.xml.channel.v6.TvContents v6Channel) {
      this.v6Channel = v6Channel;
   }

   public com.tpvision.smartinstall.xml.channel.v5.TvContents getV5Channel() {
      return this.v5Channel;
   }

   public void setV5Channel(com.tpvision.smartinstall.xml.channel.v5.TvContents vChannel) {
      this.v5Channel = vChannel;
   }

   public TvContents getV4Channel() {
      return this.v4Channel;
   }

   public void setV4Channel(TvContents v4Channel) {
      this.v4Channel = v4Channel;
   }
}
