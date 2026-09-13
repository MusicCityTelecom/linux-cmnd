/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.api.ApiCloneConfig;
import com.tpvision.smartinstall.api.ApiErrorCode;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.SiIdentifiers;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/exapi/"})
public class TvController {
    private static final Logger LOG = LoggerFactory.getLogger(TvController.class);

    @GetMapping(value={"/tvs"})
    public Object query(String id) {
        if (StringUtils.isEmpty(id)) {
            List<Devices> result = JpaManager.getDevicesManager().loadAll();
            JSONArray array = new JSONArray();
            for (Devices tv : result) {
                array.put(this.formatTvJSONObject(tv));
            }
            return array;
        }
        Devices tv = JpaManager.getDevicesManager().loadByKey(id);
        if (tv == null) {
            return ApiErrorCode.TVS_QUERY_TV_NOT_EXIST;
        }
        return new JSONArray().put(this.formatTvJSONObject(tv));
    }

    private JSONObject formatTvJSONObject(Devices tv) {
        JSONObject object = new JSONObject();
        object.put("model", tv.getTvmodelnumber());
        object.put("platform", tv.getType().replace(" ", ""));
        object.put("connection", tv.isRFDevice() ? "rf" : "ip");
        object.put("id", tv.getId());
        object.put("serial_number", tv.getTvserialnumber());
        object.put("room_id", tv.getTvroomid());
        object.put("ip_address", tv.getTvipaddress());
        object.put("power_status", Optional.ofNullable(tv.getPowerstatus()).orElse("").toLowerCase());
        object.put("firmware_upgrade_status", this.formateUpgradeStatus(tv.getFwColor()));
        object.put("clone_upgrade_status", this.formateUpgradeStatus(tv.getCloneColor()));
        object.put("vsecure_id", Optional.ofNullable(tv.getVsecuretvid()).orElse(""));
        object.put("vsecure_certificate", Optional.ofNullable(tv.getVsecureKey()).orElse(""));
        JSONArray cloneItems = new JSONArray();
        SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
        for (Map.Entry<String, String> entry : siIdentifiers.getTvResponseUpgradeVersion().entrySet()) {
            JSONObject clone = new JSONObject();
            clone.put("clone_item_type", CloneItemUtils.convertJapitNameToItem(entry.getKey()));
            clone.put("version", entry.getValue());
            cloneItems.put(clone);
        }
        object.put("clone_items", cloneItems);
        return object;
    }

    @PutMapping(value={"/tvs/{id}"})
    public Object assignOrUnassign(@PathVariable(value="id") String tvid, @RequestBody String assignData) {
        LOG.info("assignData = {}", (Object)assignData);
        if (StringUtils.isEmpty(tvid) || !TpvStringUtils.isJSONString(assignData)) {
            return ApiErrorCode.TVS_CLONE_PARAMETER_ERROR;
        }
        JSONObject cloneInfo = new JSONObject(assignData);
        String packageType = cloneInfo.optString("package_type");
        String cloneName = cloneInfo.optString("name");
        boolean forceUpgrade = cloneInfo.optBoolean("force_upgrade", false);
        if ("NONE".equalsIgnoreCase(cloneName)) {
            return this.doUnassignAction(tvid, packageType);
        }
        return this.doAssignAction(tvid, packageType, cloneName, forceUpgrade);
    }

    @DeleteMapping(value={"/tvs/{id}"})
    public Object unAssign(@PathVariable(value="id") String tvid, @RequestBody String unAssignData) {
        LOG.info("unAssignData = {}", (Object)unAssignData);
        if (StringUtils.isEmpty(tvid) || !TpvStringUtils.isJSONString(unAssignData)) {
            return ApiErrorCode.TVS_UNASSIGN_PARAMETER_INVALID;
        }
        JSONObject cloneInfo = new JSONObject(unAssignData);
        String packageType = cloneInfo.optString("package_type");
        return this.doUnassignAction(tvid, packageType);
    }

    private Object doUnassignAction(String tvid, String packageType) {
        if (!ApiCloneConfig.isValidPackageTypeIgnoreCase(packageType)) {
            return ApiErrorCode.TVS_UNASSIGN_PACKAGE_TYPE_INVALID;
        }
        packageType = ApiCloneConfig.fixPackageTypeCase(packageType);
        Devices devices = JpaManager.getDevicesManager().loadByKey(tvid);
        if (devices == null) {
            return ApiErrorCode.TVS_QUERY_TV_NOT_EXIST;
        }
        if (!"U".equalsIgnoreCase(devices.getProgress())) {
            return ApiErrorCode.TVS_UNASSIGN_TV_NOT_IN_ASSIGNED_STATUS;
        }
        if (packageType.equalsIgnoreCase("Firmware") || packageType.equalsIgnoreCase("Clone")) {
            IPUpgradeManager.handleAssignNone(tvid, "", packageType);
        } else if (devices.getCloneType().equalsIgnoreCase(packageType) || devices.getCloneType().equalsIgnoreCase(IPUpgradeManager.convertUpgradeTypeToCloneItemType(packageType).name())) {
            IPUpgradeManager.handleAssignNone(tvid, "", packageType);
        } else {
            SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(devices.getSiIdentifiers());
            List<String> cloneItemNames = ApiCloneConfig.UPGRADE_PART_CLONE_FILE_MAP.get(packageType);
            if (cloneItemNames == null) {
                return ApiErrorCode.TVS_UNASSIGN_NOT_SUPPORTED;
            }
            cloneItemNames.forEach(siIdentifiers::removeSiItemByItemName);
            devices.setSiIdentifiers(siIdentifiers.toJson());
            JpaManager.getDevicesManager().save(devices);
        }
        return this.getRefreshAssignCloneData(tvid);
    }

    private Object doAssignAction(String tvid, String packageType, String cloneName, Boolean forceUpgrade) {
        if (StringUtils.isEmpty(cloneName) || StringUtils.isEmpty(packageType)) {
            return ApiErrorCode.TVS_ASSIGN_CLONE_DATA_INVALID;
        }
        if (!ApiCloneConfig.isValidPackageTypeIgnoreCase(packageType)) {
            return ApiErrorCode.TVS_UNASSIGN_PACKAGE_TYPE_INVALID;
        }
        packageType = ApiCloneConfig.fixPackageTypeCase(packageType);
        Devices devices = JpaManager.getDevicesManager().loadByKey(tvid);
        if (devices == null) {
            return ApiErrorCode.TVS_QUERY_TV_NOT_EXIST;
        }
        if (IPUpgradeManager.isTVUpgrading(devices)) {
            return ApiErrorCode.TVS_ASSIGN_TV_IN_UPGRADING_STATUS;
        }
        int cloneId = IPUpgradeManager.getCloneIdByCloneTypeAndName(packageType, cloneName);
        if (cloneId <= 0) {
            return ApiErrorCode.TVS_ASSIGN_CLONE_DATA_NOT_EXIST;
        }
        String status = IPUpgradeManager.processCloneUpgradeType(packageType, String.valueOf(cloneId), tvid, "", null);
        if (status.contains("fail")) {
            return ApiErrorCode.TVS_ASSIGN_FAILRE;
        }
        if (forceUpgrade.booleanValue()) {
            IPUpgradeManager.startUpgrades(tvid, "U");
        }
        return this.getRefreshAssignCloneData(tvid);
    }

    private String formateUpgradeStatus(String color) {
        return IPUpgradeManager.getUpgradeStatusByColor(color).replace(" ", "_").toLowerCase();
    }

    private JSONObject getRefreshAssignCloneData(String tvid) {
        Devices devices = JpaManager.getDevicesManager().loadByKey(tvid);
        JSONObject result = new JSONObject();
        result.put("id", tvid);
        JSONArray cloneArray = new JSONArray();
        SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(devices.getSiIdentifiers());
        for (Map.Entry<String, String> entry : siIdentifiers.getSiItemUpgradeVersion().entrySet()) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("clone_item_type", CloneItemUtils.convertJapitNameToItem(entry.getKey()));
            itemObj.put("version", entry.getValue());
            cloneArray.put(itemObj);
        }
        result.put("clone_items", cloneArray);
        return result;
    }
}

