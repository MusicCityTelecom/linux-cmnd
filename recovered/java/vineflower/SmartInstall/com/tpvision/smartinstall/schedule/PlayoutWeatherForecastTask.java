package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.core.SettingCreator;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.PlayoutInfoManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvTimerTask;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayoutWeatherForecastTask extends TpvTimerTask {
   private static final Logger LOG = LoggerFactory.getLogger(PlayoutWeatherForecastTask.class);
   private static final String PLAYOUT_INFO_SOURCE = "autoSchedule";

   @Override
   public void tryRun() {
      LOG.info("PlayoutWeatherForecastTask schedule run");
      if (PlayoutUtils.isOldGatewayPlayoutExist()) {
         LOG.warn("old gateway still exist, we don't support playout by gateway, exit weather playout");
      } else {
         String tempSavePath = this.initWeatherForecastCloneFile();
         if (tempSavePath == null) {
            LOG.warn("clone file created filed playout fail");
         } else {
            JSONArray jsonArray = JpaManager.getDevicesManager().findPlayoutTvGroupsData();
            if (jsonArray != null && jsonArray.length() != 0) {
               this.clearOldPlayoutInfoData();
               this.saveCurrentWeatherDataPlayData(tempSavePath, jsonArray);
               PlayoutUtils.getInstance().onPlayoutChanged();
               LOG.info("PlayoutWeatherForecastTask schedule finish.");
            } else {
               LOG.warn("there not exists TV to play");
            }
         }
      }
   }

   private void saveCurrentWeatherDataPlayData(String tempSavePath, JSONArray jsonArray) {
      int i = 0;

      for (int j = jsonArray.length(); i < j; i++) {
         JSONObject jsonObject = jsonArray.getJSONObject(i);
         String type = jsonObject.optString("type");
         boolean isSupportWeatherForecast = PlatformUtils.getSupportCloneItemNames(type).contains(CommonConstants.CloneItemType.WeatherForecast.name());
         if (isSupportWeatherForecast) {
            CloneItemUtils.CloneItemInfo cloneItemInfo = new CloneItemUtils.CloneItemInfo();
            cloneItemInfo.setName("WeatherForcast");
            cloneItemInfo.setClone(false);
            cloneItemInfo.setId(0);
            cloneItemInfo.setItemType(CommonConstants.CloneItemType.WeatherForecast);
            cloneItemInfo.setPlatform(PlatformUtils.getPlatformId(type));
            cloneItemInfo.setVersion(TpvDateUtils.getCurrentIndentifierFormatTime());
            cloneItemInfo.setLastUpdated(new Date());
            PlayoutInfo savedPlayoutInfo = PlayoutUtils.savePlayout(cloneItemInfo, jsonObject.getString("roomIds"), "autoSchedule");

            try {
               FileUtils.copyFile(new File(tempSavePath), new File(PlayoutUtils.getRFZipFullPath(savedPlayoutInfo)));
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }
   }

   private void clearOldPlayoutInfoData() {
      PlayoutInfoManager playoutInfoManager = JpaManager.getPlayoutInfoManager();

      for (PlayoutInfo info : playoutInfoManager.findPlayoutInfoByTypeAndSource(CommonConstants.CloneItemType.WeatherForecast.name(), "autoSchedule")) {
         playoutInfoManager.deleteByKey(info.getPlayoutId());
         FileUtils.deleteQuietly(new File(PlayoutUtils.getRFZipFullPath(info)));
      }
   }

   private String initWeatherForecastCloneFile() {
      try {
         String tempSavePath = File.createTempFile("weatherforcast", ".zip").getAbsolutePath();
         boolean createResult = SettingCreator.createWeatherForecastClonePackage(tempSavePath);
         if (createResult) {
            return tempSavePath;
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return null;
   }

   public static void startPlay() {
      new Thread(new PlayoutWeatherForecastTask()).start();
   }
}
