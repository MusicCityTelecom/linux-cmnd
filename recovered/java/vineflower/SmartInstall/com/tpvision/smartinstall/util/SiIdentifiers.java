package com.tpvision.smartinstall.util;

import com.google.gson.Gson;
import com.tpvision.smartinstall.japit.IPCloneService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiIdentifiers {
   private static final Logger LOG = LoggerFactory.getLogger(SiIdentifiers.class);
   public List<SiIdentifiers.CloneItem> SiAssignItem = new ArrayList<>();
   public List<SiIdentifiers.ResponseItem> TvResponseItem = new ArrayList<>();
   public List<SiIdentifiers.ResponseItem> TvUpgradeItem = new ArrayList<>();
   public List<SiIdentifiers.ResponseItem> RetryTvUpgradeItem = new ArrayList<>();
   public String partialSelections;

   public static SiIdentifiers fromJson(String data) {
      SiIdentifiers siIdentifiers = null;

      try {
         if (StringUtils.isNotBlank(data)) {
            siIdentifiers = new Gson().fromJson(data, SiIdentifiers.class);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
         LOG.error("convert data <{}> to identifer object fauilure:{}", data, ex);
      }

      if (siIdentifiers == null) {
         siIdentifiers = new SiIdentifiers();
      }

      return siIdentifiers;
   }

   public void updateRetryTvUpgradeItem(List<String> retryTvUpgradeItemList) {
      this.RetryTvUpgradeItem = retryTvUpgradeItemList.stream().map(e -> {
         SiIdentifiers.ResponseItem item = new SiIdentifiers.ResponseItem();
         item.CloneItemName = e;
         return item;
      }).collect(Collectors.toList());
   }

   public Map<String, String> getTvResponseUpgradeVersion() {
      Map<String, String> tvResponseUpgradeVersion = new HashMap<>();

      for (SiIdentifiers.ResponseItem item : this.TvResponseItem) {
         tvResponseUpgradeVersion.put(item.CloneItemName, item.CloneItemVersionNo);
      }

      return tvResponseUpgradeVersion;
   }

   public String getCloneItemVersionFromTvResponseItems(String itemName) {
      for (SiIdentifiers.ResponseItem item : this.TvResponseItem) {
         if (itemName.equalsIgnoreCase(item.CloneItemName)) {
            return item.CloneItemVersionNo;
         }
      }

      return null;
   }

   public Map<String, String> getTvItemUpgradeVersion() {
      Map<String, String> tvItemUpgradeVersion = new HashMap<>();

      for (SiIdentifiers.ResponseItem item : this.TvUpgradeItem) {
         tvItemUpgradeVersion.put(item.CloneItemName, item.CloneItemVersionNo);
      }

      return tvItemUpgradeVersion;
   }

   public Map<String, String> getSiItemUpgradeVersion() {
      Map<String, String> siItemUpgradeVersion = new HashMap<>();

      for (SiIdentifiers.CloneItem item : this.SiAssignItem) {
         siItemUpgradeVersion.put(item.CloneItemName, item.CloneItemVersionNo);
      }

      return siItemUpgradeVersion;
   }

   public void removeSiItemByItemName(String itemName) {
      Iterator<SiIdentifiers.CloneItem> iterator = this.SiAssignItem.iterator();

      while (iterator.hasNext()) {
         SiIdentifiers.CloneItem cloneItem = iterator.next();
         if (cloneItem.CloneItemName.equals(itemName)) {
            iterator.remove();
            break;
         }
      }
   }

   public String getTVCurrentCloneItemIdentifier(String cloneItem) {
      for (SiIdentifiers.ResponseItem responseItem : this.TvResponseItem) {
         if (responseItem.CloneItemName.equalsIgnoreCase(cloneItem)) {
            return responseItem.CloneItemVersionNo;
         }
      }

      return null;
   }

   public void removeAllSiItemNotItemName(String itemName) {
      Iterator<SiIdentifiers.CloneItem> iterator = this.SiAssignItem.iterator();

      while (iterator.hasNext()) {
         SiIdentifiers.CloneItem cloneItem = iterator.next();
         if (!cloneItem.CloneItemName.equals(itemName)) {
            iterator.remove();
         }
      }
   }

   public void updateTvResponseItem(JSONArray ipCloneItemStatus, JSONArray cloneItemsAvailableToServer, JSONArray cloneItemsRequiredForUpgrade) {
      if (ipCloneItemStatus != null && ipCloneItemStatus.length() > 0) {
         List<SiIdentifiers.ResponseItem> tvResponseItemList = new ArrayList<>();
         boolean isContainMainFirmWareInCloneItemStatus = false;

         for (int i = 0; i < ipCloneItemStatus.length(); i++) {
            JSONObject cloneItem = ipCloneItemStatus.getJSONObject(i);
            String cloneStatus = cloneItem.optString("CloneStatus");
            if (!StringUtils.equalsIgnoreCase("Cancelled", cloneStatus)) {
               JSONObject cloneItemDetails = cloneItem.getJSONObject("CloneItemDetails");
               String cloneItemName = cloneItemDetails.getString("CloneItemName");
               String cloneItemVersionNo = cloneItemDetails.optString("CloneItemVersionNo");
               if (StringUtils.isBlank(cloneItemVersionNo) && cloneItemsAvailableToServer != null) {
                  for (int j = 0; j < cloneItemsAvailableToServer.length(); j++) {
                     JSONObject cloneItemsAvailable = cloneItemsAvailableToServer.getJSONObject(j);
                     if (cloneItemName.equalsIgnoreCase(cloneItemsAvailable.getString("CloneItemName"))) {
                        cloneItemVersionNo = cloneItemsAvailable.getString("CloneItemVersionNo");
                        break;
                     }
                  }
               }

               if ("MainFirmware".equalsIgnoreCase(cloneItemName)) {
                  isContainMainFirmWareInCloneItemStatus = true;
               }

               SiIdentifiers.ResponseItem item = new SiIdentifiers.ResponseItem();
               item.CloneItemName = cloneItemName;
               item.CloneItemVersionNo = cloneItemVersionNo;
               tvResponseItemList.add(item);
            }
         }

         if (!isContainMainFirmWareInCloneItemStatus && null != cloneItemsRequiredForUpgrade) {
            for (int j = 0; j < cloneItemsRequiredForUpgrade.length(); j++) {
               JSONObject cloneItemRequired = (JSONObject)cloneItemsRequiredForUpgrade.get(j);
               String cloneItemName = cloneItemRequired.getString("CloneItemName");
               if ("MainFirmware".equalsIgnoreCase(cloneItemName)) {
                  String cloneItemVersionNo = cloneItemRequired.getString("CloneItemVersionNo");
                  SiIdentifiers.ResponseItem item = new SiIdentifiers.ResponseItem();
                  item.CloneItemName = cloneItemName;
                  item.CloneItemVersionNo = cloneItemVersionNo;
                  tvResponseItemList.add(item);
                  break;
               }
            }
         }

         this.TvResponseItem = tvResponseItemList;
      }
   }

   public void updateTvUpgradeItemsByIpCloneService(IPCloneService ipCloneService) {
      this.TvUpgradeItem = ipCloneService.CommandDetails.IPCloneParameters.CloneItemDownloadDetails.stream().map(e -> {
         SiIdentifiers.ResponseItem responseItem = new SiIdentifiers.ResponseItem();
         responseItem.CloneItemName = e.CloneItemDetails.CloneItemName;
         responseItem.CloneItemVersionNo = e.CloneItemDetails.CloneItemVersionNo;
         return responseItem;
      }).collect(Collectors.toList());
   }

   public void updateAssignCloneData(String siCloneJsonData) {
      SiIdentifiers siAssignIdentifiers = new Gson().fromJson(siCloneJsonData, SiIdentifiers.class);

      for (SiIdentifiers.CloneItem cloneItem : this.SiAssignItem) {
         if (cloneItem.CloneItemName.equals("MainFirmware")) {
            siAssignIdentifiers.SiAssignItem.add(cloneItem);
            break;
         }
      }

      this.SiAssignItem = siAssignIdentifiers.SiAssignItem;
      this.TvUpgradeItem.clear();
      this.RetryTvUpgradeItem.clear();
   }

   public void updateAssignFirmwareVersion(String firmWareVersion) {
      SiIdentifiers.CloneItem firmwareItem = null;
      Iterator<SiIdentifiers.CloneItem> iterator = this.SiAssignItem.iterator();

      while (iterator.hasNext()) {
         SiIdentifiers.CloneItem cloneItem = iterator.next();
         if (cloneItem.CloneItemName.equals("MainFirmware")) {
            firmwareItem = cloneItem;
            iterator.remove();
            break;
         }
      }

      if (firmwareItem == null) {
         firmwareItem = new SiIdentifiers.CloneItem();
      }

      firmwareItem.CloneItemName = "MainFirmware";
      firmwareItem.CloneItemVersionNo = firmWareVersion;
      firmwareItem.CloneItemStatus = "No";
      this.SiAssignItem.add(firmwareItem);
   }

   public String toJson() {
      String jsonData = new Gson().toJson(this);
      if (!TpvStringUtils.isJSONString(jsonData)) {
         LOG.error("si identifer json data format error:<{}>", jsonData);
         jsonData = "{}";
      }

      return jsonData;
   }

   public static class CloneItem {
      public String CloneItemName;
      public String CloneItemVersionNo;
      public String CloneItemStatus;
   }

   public static class ResponseItem {
      public String CloneItemName;
      public String CloneItemVersionNo;
   }
}
