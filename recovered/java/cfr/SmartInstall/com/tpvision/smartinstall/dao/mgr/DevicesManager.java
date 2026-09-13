/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tpvision.smartinstall.VersionChecker;
import com.tpvision.smartinstall.api.ApiLicenseChecker;
import com.tpvision.smartinstall.dao.DevicesRepository;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.servlet.IPProfile;
import com.tpvision.smartinstall.servlet.IPTVPooling;
import com.tpvision.smartinstall.trigger.TriggerUtils;
import com.tpvision.smartinstall.util.CastServerUtils;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevicesManager {
    private static final Logger logger = LoggerFactory.getLogger(DevicesManager.class);
    @Autowired
    private DevicesRepository devicesRepository;

    public Devices loadByKey(String id) {
        return this.devicesRepository.findById(id).orElse(null);
    }

    public boolean isExistForUnqiueId(String tvUnqiueId) {
        return this.devicesRepository.existsById(tvUnqiueId);
    }

    public void deleteByKey(String id) {
        Devices tv = this.devicesRepository.findById(id).orElse(null);
        if (tv != null) {
            IPUpgradeManager.cleanExpiredCloneFiles(tv, null);
            this.devicesRepository.deleteById(id);
            ApiLicenseChecker.getInstance().submitDeviceCountToLicenseServer();
            IPTVPooling.notifyDeviceDataChange();
            CastServerUtils.forwardDeviceRoomListToPpdsCastServer();
        }
    }

    public void deleteTV(Devices tv) {
        this.devicesRepository.delete(tv);
    }

    public Devices findDeviceByTVUniqueID(String tvUniqueId) {
        return this.devicesRepository.findByTvuniqueid(tvUniqueId);
    }

    public List<Devices> findDevicesByCloneId(int cloneId, String cloneType) {
        return this.devicesRepository.findByCloneidAndCloneType(cloneId, cloneType);
    }

    public List<Devices> findDevicesByType(String type) {
        return this.devicesRepository.findByType(type);
    }

    public List<Devices> findDevicesByTvipaddress(String tvipaddress) {
        return this.devicesRepository.findByTvipaddress(tvipaddress);
    }

    public List<Devices> findDevicesByTvipaddressAndCloneMode(String tvipaddress, String cloneMode) {
        return this.devicesRepository.findByTvipaddressAndCloneMode(tvipaddress, cloneMode);
    }

    public List<Devices> findDevicesByTvipaddressAndPowerStatus(String tvipaddress, String powerStatus) {
        return this.devicesRepository.findByTvipaddressAndPowerstatus(tvipaddress, powerStatus);
    }

    public List<Devices> findDevicesByTvname(String tvname) {
        return this.devicesRepository.findByTvname(tvname);
    }

    public List<Devices> findDevicesByClonePath(String clonePath) {
        return this.devicesRepository.findByClonePath(clonePath);
    }

    public List<Devices> findDevicesByRoomIdAndType(String tvroomid, String type) {
        return this.devicesRepository.findByTvroomidAndType(tvroomid, type);
    }

    public List<Devices> findDevicesByRoomId(String tvroomid) {
        return this.devicesRepository.findByTvroomid(tvroomid);
    }

    public List<Devices> findDevicesByFirmwareId(Integer firmwareId) {
        return this.devicesRepository.findByFirmwareid(firmwareId);
    }

    public List<Devices> loadAll() {
        return this.devicesRepository.findAll();
    }

    public List<Devices> findDevicesByGroupName(String groupName) {
        return this.devicesRepository.findDevicesByGroupName(groupName);
    }

    public List<Map<String, Object>> findDeviceTypeAndFirmwareInfoByRooms(Set<String> roomIds) {
        return this.devicesRepository.findDeviceTypeAndFirmwareInfoByRooms(roomIds);
    }

    public void save(Devices obj) {
        boolean isCreateNewDevice = obj.isCreateNewDevice();
        this.devicesRepository.save(obj);
        if (isCreateNewDevice || obj.isNeedRefresh()) {
            IPTVPooling.notifyDeviceDataChange();
        }
        if (isCreateNewDevice || obj.isNeedWriteMetricsLog()) {
            CmndMetricsTask.writeIptvInfoToMetricsLog(obj);
        }
        if (isCreateNewDevice) {
            TriggerUtils.executeNewDeviceTrigger(obj);
            ApiLicenseChecker.getInstance().submitDeviceCountToLicenseServer();
            CastServerUtils.forwardDeviceRoomListToPpdsCastServer();
        }
        if (!isCreateNewDevice && obj.isForwardToCastServer()) {
            CastServerUtils.forwardDeviceRoomListToPpdsCastServer();
        }
    }

    @Transactional
    public void updatePmsSyncNewStatus(Devices tv) {
        this.devicesRepository.updateDevicePmsSyncStatus(tv.getPmsSyncStatus(), tv.getId());
    }

    public String getDevicesChartOverviewData(String mode) {
        String field = "";
        switch (mode.toUpperCase(Locale.ROOT)) {
            case "GETTVMODELSOVERVIEW": {
                field = "TVModelNumber";
                break;
            }
            case "GETCURRENTPOWERSTATUSOVERVIEW": {
                field = "PowerStatus";
                break;
            }
            case "GETCURRENTSWOVERVIEW": {
                field = "tv_firmware_Identifier";
                break;
            }
            case "GETCURRENTCLONEDATAOVERVIEW": {
                field = "tv_clone_Identifiers";
                break;
            }
            case "GETCURRENTUPGRADESTATUSOVERVIEW": {
                field = "Progress";
                break;
            }
            default: {
                logger.info("unsupport case for Overview filter");
            }
        }
        String rowData = "";
        String sql = "select " + field + ", count(1) as counts from devices group by " + field;
        boolean cloneDataFlag = false;
        if ("tv_clone_Identifiers".equals(field)) {
            cloneDataFlag = true;
            sql = "select tv_clone_Identifiers, count(tv_clone_Identifiers) as knownclonedatecount from devices where Status = 'Successful' group by tv_clone_Identifiers";
            sql = sql + " union all select 'Unknown', count(*) as knownclonedatecount from devices where Status = 'Failure' or Status is null or tv_clone_Identifiers is null ";
        } else if ("Progress".equals(field)) {
            field = "colors";
            sql = "select count(colors) counts, colors from  (select fw_color colors from  smartinstall.devices Union all select clone_color from  smartinstall.devices) t group by colors order by  case when colors='black' then 1 when colors='blue' then 2 when colors='#FFBF00' then 3\twhen colors='#01DF01' then 4 when colors='red' then 5 end;";
        } else if ("PowerStatus".equals(field)) {
            sql = "select " + field + ", count(1) as counts, (select count(*) from devices where TVIPAddress = 'RF') as RFcounts from devices where TVIPAddress <> 'RF' group by " + field;
        }
        ArrayList<String> array = new ArrayList<String>();
        try (Connection conn = JpaManager.getConnection();
             Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery(sql);){
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (cloneDataFlag) {
                String temp_rowData = "";
                String lastclonerename = "";
                int knownCloneDataCount = 0;
                while (rs.next()) {
                    lastclonerename = rs.getString(metaData.getColumnLabel(1));
                    knownCloneDataCount = rs.getInt(metaData.getColumnLabel(2));
                    if (knownCloneDataCount == 0) continue;
                    logger.debug("knownCloneDataCount={},lastclonerename={}", (Object)knownCloneDataCount, (Object)lastclonerename);
                    if (!StringUtils.isNotBlank(lastclonerename)) continue;
                    temp_rowData = temp_rowData + "{\"c\":[{\"v\":\"" + lastclonerename + "(" + knownCloneDataCount + ")\",\"f\":null},{\"v\":" + knownCloneDataCount + ",\"f\":null}]}\r\n,";
                }
                if (!"".equals(temp_rowData)) {
                    array.add(temp_rowData.substring(0, temp_rowData.length() - 1));
                }
            } else {
                while (rs.next()) {
                    String result = null;
                    String count = null;
                    for (int i = 1; i <= columnCount; ++i) {
                        String columnName = metaData.getColumnLabel(i);
                        if (field.equalsIgnoreCase(columnName)) {
                            result = this.fixShowValue(mode, rs.getString(columnName));
                            continue;
                        }
                        if (!"counts".equalsIgnoreCase(columnName)) continue;
                        count = rs.getString(columnName);
                    }
                    rowData = "{\"c\":[{\"v\":\"" + result + "(" + count + ")\",\"f\":null},{\"v\":" + count + ",\"f\":null}]}\r\n";
                    array.add(rowData);
                }
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        String status = "{\r\n  \"cols\": [\r\n        {\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},\r\n        {\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}\r\n      ],\r\n  \"rows\": " + ((Object)array).toString() + " }";
        return status;
    }

    private String fixShowValue(String mode, String result) {
        if (mode.equalsIgnoreCase("getCurrentPowerStatusOverview")) {
            if (result.equalsIgnoreCase("offline")) {
                return "Offline";
            }
            if (result.equalsIgnoreCase("ON")) {
                return "On";
            }
        } else if (mode.equalsIgnoreCase("getCurrentUpgradeStatusOverview")) {
            return IPUpgradeManager.getUpgradeStatusByColor(result);
        }
        return result;
    }

    public JSONArray findPlayoutTvGroupsData() {
        String sql = "select group_concat(distinct tvroomid) as roomIds,type from devices group by type";
        JSONArray jsonArray = null;
        try {
            jsonArray = JpaManager.getResultsetAsArray(sql, new Object[0]);
        }
        catch (SQLException e) {
            logger.error("select playout tv failed", e);
        }
        return jsonArray;
    }

    /*
     * Exception decompiling
     */
    public String findDeviceIdsByRoomId(String roomId, String tvgroups) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean getPowerStatus(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        Devices device = this.loadByKey(id);
        return null != device && "offline".equalsIgnoreCase(device.getPowerstatus());
    }

    public JSONArray findDeviceInfoViewDataByTvId(String tvId) throws SQLException {
        String sql = "select * from deviceinfo_view where Id=?";
        return JpaManager.getResultsetAsArray(sql, tvId);
    }

    /*
     * Exception decompiling
     */
    public JSONArray getDeviceJsonData(String sql, List<Object> paramsList, String filter) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public JsonArray getDeviceJsonDataByTvGroup(String groupIds) {
        String[] groupIdArray = groupIds.split(",");
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < groupIdArray.length; ++i) {
            if (i > 0) {
                placeholders.append(",");
            }
            placeholders.append("?");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tv_firmware_Identifier, type FROM deviceinfo_view v WHERE TVGroups IN (").append((CharSequence)placeholders).append(")");
        JsonArray result = new JsonArray();
        try (Connection conn = JpaManager.getConnection();
             PreparedStatement stat = conn.prepareStatement(sql.toString());){
            for (int i = 0; i < groupIdArray.length; ++i) {
                stat.setString(i + 1, groupIdArray[i]);
            }
            try (ResultSet rs = stat.executeQuery();){
                while (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("version", rs.getString("tv_firmware_Identifier"));
                    jsonObject.addProperty("platform", rs.getString("type"));
                    result.add(jsonObject);
                }
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return result;
    }

    private boolean checkWarningsStatus(ResultSet rs, long maxOnlineExpireMilSecs) throws SQLException {
        String modelName;
        String firmwareVersion;
        String siIdentifiers;
        String lastSuccessSettingPackageId;
        boolean isWebServiceUrlError;
        String type = rs.getString("Type");
        if (!PlatformUtils.isSupportShowWarnings(type)) {
            return false;
        }
        IPProfile profile = IPProfile.loadIPProfile();
        String powerStatus = rs.getString("PowerStatus");
        String tvIpAddress = rs.getString("TVIPAddress");
        if (!StringUtils.equalsIgnoreCase(profile.getWebserviceURLIncorrectWarning(), "false") && (isWebServiceUrlError = Utils.isWebServiceUrlError(maxOnlineExpireMilSecs, powerStatus, tvIpAddress, rs.getString("Lastonline"), type))) {
            return true;
        }
        if (!StringUtils.equalsIgnoreCase(profile.getTvSettingChangeWarning(), "false") && Utils.isTvSettingNotMatchBetweenTvAndSIServer(powerStatus, lastSuccessSettingPackageId = rs.getString("success_siclone_Identifier"), siIdentifiers = rs.getString("si_Identifiers"), tvIpAddress, type)) {
            return true;
        }
        return !StringUtils.equalsIgnoreCase(profile.getFirmwareOutOfDateWarning(), "false") && StringUtils.isNoneBlank(firmwareVersion = rs.getString("tv_firmware_Identifier")) && VersionChecker.getFirmwareNewVersion(type, modelName = rs.getString("TVModelNumber"), firmwareVersion) != null;
    }

    private String maxTvCloneIdent(String tvCloneIdent, String type) {
        if (StringUtils.isEmpty(tvCloneIdent)) {
            return "None";
        }
        String ret = null;
        String[] tvCloneIdentifiers = tvCloneIdent.split(",");
        String[] tvCloneIdentifiers_Android = tvCloneIdent.split(",");
        String platform = PlatformUtils.getPlatformId(type);
        if (PlatformUtils.hasPackageFeature(platform)) {
            SimpleDateFormat format = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd/MM/yyyy:HH:mm");
            SimpleDateFormat formatTmp = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("yyyy/MM/dd:HH:mm");
            SimpleDateFormat formatSmartInfo = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd-MM-yyyy-'T'HHmmss");
            String dateStr = "";
            for (int i = 0; i < tvCloneIdentifiers.length; ++i) {
                dateStr = tvCloneIdentifiers[i].trim();
                try {
                    dateStr = "00/00/0000:--:--".equals(dateStr) ? "01/01/1000:00:00" : (tvCloneIdentifiers[i].contains("T") ? formatTmp.format(formatSmartInfo.parse(dateStr.substring(0, 18))) : formatTmp.format(format.parse(dateStr)));
                }
                catch (Exception e) {
                    dateStr = tvCloneIdentifiers[i].trim();
                }
                tvCloneIdentifiers_Android[i] = dateStr;
            }
            int maxPos = 0;
            if (null != tvCloneIdentifiers_Android && tvCloneIdentifiers_Android.length > 0) {
                ret = tvCloneIdentifiers_Android[0].trim();
                for (int i = 1; i < tvCloneIdentifiers_Android.length; ++i) {
                    if (ret.compareToIgnoreCase(tvCloneIdentifiers_Android[i].trim()) >= 0) continue;
                    ret = tvCloneIdentifiers_Android[i].trim();
                    maxPos = i;
                }
            }
            if ("01/01/1000:00:00".equals(ret)) {
                return "00/00/0000:--:--";
            }
            return tvCloneIdentifiers[maxPos];
        }
        if (null != tvCloneIdentifiers && tvCloneIdentifiers.length > 0) {
            ret = tvCloneIdentifiers[0].trim();
            for (int i = 1; i < tvCloneIdentifiers.length; ++i) {
                if (ret.compareToIgnoreCase(tvCloneIdentifiers[i].trim()) >= 0) continue;
                ret = tvCloneIdentifiers[i].trim();
            }
        }
        return ret;
    }

    public List<Devices> getDevicesListByTvUniqueIds(List<String> idList) {
        return this.devicesRepository.findByTvuniqueidIn(idList);
    }

    public List<String> getRfDevicePlatformsByRoomId(String roomIdstr) {
        ArrayList<Devices> tvs = new ArrayList<Devices>();
        for (String roomId : roomIdstr.split(",")) {
            tvs.addAll(this.devicesRepository.findByTvroomid(roomId));
        }
        return tvs.stream().filter(Devices::isRFDevice).map(Devices::getType).distinct().collect(Collectors.toList());
    }

    public boolean hasAssignedDevices(CloneItemUtils.CloneItemInfo info) {
        if (info.getItemType() == CommonConstants.CloneItemType.Firmware) {
            return !this.devicesRepository.findByFirmwareid(info.getId()).isEmpty();
        }
        return !this.findDevicesByClonePath(info.getClonePath()).isEmpty();
    }

    public int findAllCount() {
        return (int)this.devicesRepository.count();
    }
}

