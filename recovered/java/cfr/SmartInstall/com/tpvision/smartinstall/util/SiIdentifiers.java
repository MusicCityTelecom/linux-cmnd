/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.google.gson.Gson;
import com.tpvision.smartinstall.japit.IPCloneService;
import com.tpvision.smartinstall.util.TpvStringUtils;
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
    public List<CloneItem> SiAssignItem = new ArrayList<CloneItem>();
    public List<ResponseItem> TvResponseItem = new ArrayList<ResponseItem>();
    public List<ResponseItem> TvUpgradeItem = new ArrayList<ResponseItem>();
    public List<ResponseItem> RetryTvUpgradeItem = new ArrayList<ResponseItem>();
    public String partialSelections;

    public static SiIdentifiers fromJson(String data) {
        SiIdentifiers siIdentifiers = null;
        try {
            if (StringUtils.isNotBlank(data)) {
                siIdentifiers = new Gson().fromJson(data, SiIdentifiers.class);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            LOG.error("convert data <{}> to identifer object fauilure:{}", (Object)data, (Object)ex);
        }
        if (siIdentifiers == null) {
            siIdentifiers = new SiIdentifiers();
        }
        return siIdentifiers;
    }

    public void updateRetryTvUpgradeItem(List<String> retryTvUpgradeItemList) {
        this.RetryTvUpgradeItem = retryTvUpgradeItemList.stream().map(e -> {
            ResponseItem item = new ResponseItem();
            item.CloneItemName = e;
            return item;
        }).collect(Collectors.toList());
    }

    public Map<String, String> getTvResponseUpgradeVersion() {
        HashMap<String, String> tvResponseUpgradeVersion = new HashMap<String, String>();
        for (ResponseItem item : this.TvResponseItem) {
            tvResponseUpgradeVersion.put(item.CloneItemName, item.CloneItemVersionNo);
        }
        return tvResponseUpgradeVersion;
    }

    public String getCloneItemVersionFromTvResponseItems(String itemName) {
        for (ResponseItem item : this.TvResponseItem) {
            if (!itemName.equalsIgnoreCase(item.CloneItemName)) continue;
            return item.CloneItemVersionNo;
        }
        return null;
    }

    public Map<String, String> getTvItemUpgradeVersion() {
        HashMap<String, String> tvItemUpgradeVersion = new HashMap<String, String>();
        for (ResponseItem item : this.TvUpgradeItem) {
            tvItemUpgradeVersion.put(item.CloneItemName, item.CloneItemVersionNo);
        }
        return tvItemUpgradeVersion;
    }

    public Map<String, String> getSiItemUpgradeVersion() {
        HashMap<String, String> siItemUpgradeVersion = new HashMap<String, String>();
        for (CloneItem item : this.SiAssignItem) {
            siItemUpgradeVersion.put(item.CloneItemName, item.CloneItemVersionNo);
        }
        return siItemUpgradeVersion;
    }

    public void removeSiItemByItemName(String itemName) {
        Iterator<CloneItem> iterator = this.SiAssignItem.iterator();
        while (iterator.hasNext()) {
            CloneItem cloneItem = iterator.next();
            if (!cloneItem.CloneItemName.equals(itemName)) continue;
            iterator.remove();
            break;
        }
    }

    public String getTVCurrentCloneItemIdentifier(String cloneItem) {
        for (ResponseItem responseItem : this.TvResponseItem) {
            if (!responseItem.CloneItemName.equalsIgnoreCase(cloneItem)) continue;
            return responseItem.CloneItemVersionNo;
        }
        return null;
    }

    public void removeAllSiItemNotItemName(String itemName) {
        Iterator<CloneItem> iterator = this.SiAssignItem.iterator();
        while (iterator.hasNext()) {
            CloneItem cloneItem = iterator.next();
            if (cloneItem.CloneItemName.equals(itemName)) continue;
            iterator.remove();
        }
    }

    public void updateTvResponseItem(JSONArray ipCloneItemStatus, JSONArray cloneItemsAvailableToServer, JSONArray cloneItemsRequiredForUpgrade) {
        if (ipCloneItemStatus == null || ipCloneItemStatus.length() <= 0) {
            return;
        }
        ArrayList<ResponseItem> tvResponseItemList = new ArrayList<ResponseItem>();
        boolean isContainMainFirmWareInCloneItemStatus = false;
        for (int i = 0; i < ipCloneItemStatus.length(); ++i) {
            JSONObject cloneItem = ipCloneItemStatus.getJSONObject(i);
            String cloneStatus = cloneItem.optString("CloneStatus");
            if (StringUtils.equalsIgnoreCase("Cancelled", cloneStatus)) continue;
            JSONObject cloneItemDetails = cloneItem.getJSONObject("CloneItemDetails");
            String cloneItemName = cloneItemDetails.getString("CloneItemName");
            String cloneItemVersionNo = cloneItemDetails.optString("CloneItemVersionNo");
            if (StringUtils.isBlank(cloneItemVersionNo) && cloneItemsAvailableToServer != null) {
                for (int j = 0; j < cloneItemsAvailableToServer.length(); ++j) {
                    JSONObject cloneItemsAvailable = cloneItemsAvailableToServer.getJSONObject(j);
                    if (!cloneItemName.equalsIgnoreCase(cloneItemsAvailable.getString("CloneItemName"))) continue;
                    cloneItemVersionNo = cloneItemsAvailable.getString("CloneItemVersionNo");
                    break;
                }
            }
            if ("MainFirmware".equalsIgnoreCase(cloneItemName)) {
                isContainMainFirmWareInCloneItemStatus = true;
            }
            ResponseItem item = new ResponseItem();
            item.CloneItemName = cloneItemName;
            item.CloneItemVersionNo = cloneItemVersionNo;
            tvResponseItemList.add(item);
        }
        if (!isContainMainFirmWareInCloneItemStatus && null != cloneItemsRequiredForUpgrade) {
            for (int j = 0; j < cloneItemsRequiredForUpgrade.length(); ++j) {
                JSONObject cloneItemRequired = (JSONObject)cloneItemsRequiredForUpgrade.get(j);
                String cloneItemName = cloneItemRequired.getString("CloneItemName");
                if (!"MainFirmware".equalsIgnoreCase(cloneItemName)) continue;
                String cloneItemVersionNo = cloneItemRequired.getString("CloneItemVersionNo");
                ResponseItem item = new ResponseItem();
                item.CloneItemName = cloneItemName;
                item.CloneItemVersionNo = cloneItemVersionNo;
                tvResponseItemList.add(item);
                break;
            }
        }
        this.TvResponseItem = tvResponseItemList;
    }

    public void updateTvUpgradeItemsByIpCloneService(IPCloneService ipCloneService) {
        this.TvUpgradeItem = ipCloneService.CommandDetails.IPCloneParameters.CloneItemDownloadDetails.stream().map(e -> {
            ResponseItem responseItem = new ResponseItem();
            responseItem.CloneItemName = e.CloneItemDetails.CloneItemName;
            responseItem.CloneItemVersionNo = e.CloneItemDetails.CloneItemVersionNo;
            return responseItem;
        }).collect(Collectors.toList());
    }

    public void updateAssignCloneData(String siCloneJsonData) {
        SiIdentifiers siAssignIdentifiers = new Gson().fromJson(siCloneJsonData, SiIdentifiers.class);
        for (CloneItem cloneItem : this.SiAssignItem) {
            if (!cloneItem.CloneItemName.equals("MainFirmware")) continue;
            siAssignIdentifiers.SiAssignItem.add(cloneItem);
            break;
        }
        this.SiAssignItem = siAssignIdentifiers.SiAssignItem;
        this.TvUpgradeItem.clear();
        this.RetryTvUpgradeItem.clear();
    }

    public void updateAssignFirmwareVersion(String firmWareVersion) {
        CloneItem firmwareItem = null;
        Iterator<CloneItem> iterator = this.SiAssignItem.iterator();
        while (iterator.hasNext()) {
            CloneItem cloneItem = iterator.next();
            if (!cloneItem.CloneItemName.equals("MainFirmware")) continue;
            firmwareItem = cloneItem;
            iterator.remove();
            break;
        }
        if (firmwareItem == null) {
            firmwareItem = new CloneItem();
        }
        firmwareItem.CloneItemName = "MainFirmware";
        firmwareItem.CloneItemVersionNo = firmWareVersion;
        firmwareItem.CloneItemStatus = "No";
        this.SiAssignItem.add(firmwareItem);
    }

    public String toJson() {
        String jsonData = new Gson().toJson(this);
        if (!TpvStringUtils.isJSONString(jsonData)) {
            LOG.error("si identifer json data format error:<{}>", (Object)jsonData);
            jsonData = "{}";
        }
        return jsonData;
    }

    public static class ResponseItem {
        public String CloneItemName;
        public String CloneItemVersionNo;
    }

    public static class CloneItem {
        public String CloneItemName;
        public String CloneItemVersionNo;
        public String CloneItemStatus;
    }
}

