/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.InQueryParameter;
import com.tpvision.smartinstall.VersionChecker;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Groups;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.TriggerInfo;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.GroupsManager;
import com.tpvision.smartinstall.dao.mgr.GuestInfoManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.japit.IPCloneServiceManager;
import com.tpvision.smartinstall.japit.TVPowerManager;
import com.tpvision.smartinstall.japit.TVSIServiceManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.servlet.IPProfile;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.DownloadLimiter;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.SiIdentifiers;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/IPTVServlet"})
public class IPTVServlet
extends BaseHttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(IPTVServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        String status = Utils.buildFailReturnJson();
        if ("startTVs".equalsIgnoreCase(mode)) {
            status = this.startTvUpgrade(request);
        } else if ("startGroups".equalsIgnoreCase(mode)) {
            status = this.startGroupUpgrade(request);
        } else if ("COPY_SW".equalsIgnoreCase(mode)) {
            status = this.assignFirmware(request);
        } else if ("GetPlatform".equalsIgnoreCase(mode)) {
            status = this.getSettingCloneStatus(request);
        } else if ("GetGroupTvType".equalsIgnoreCase(mode)) {
            status = this.getGroupTvType(request);
        } else if ("GetIPProfile".equalsIgnoreCase(mode)) {
            status = this.getIpProfile();
        } else if ("IPProfile".equalsIgnoreCase(mode)) {
            IPProfile ip = this.updateIPProfile(request);
            status = "{\"profile\":" + new Gson().toJson(ip) + "}";
        } else if ("mainText".equalsIgnoreCase(mode)) {
            status = this.modifyTvName(request);
        } else if ("power".equalsIgnoreCase(mode)) {
            String tvId = request.getParameter("id");
            String type = request.getParameter("type");
            String power = request.getParameter("power");
            status = TVPowerManager.modifyTVPowerState(tvId, type, power);
        } else if ("info".equalsIgnoreCase(mode)) {
            status = this.getTvInfo(request);
        } else if ("download".equalsIgnoreCase(mode)) {
            status = this.modeDownload(request);
        } else if ("delete".equalsIgnoreCase(mode)) {
            String id = request.getParameter("id");
            status = this.deleteOneTv(id);
        } else if ("deleteSelectedTV".equalsIgnoreCase(mode)) {
            status = this.deleteMutipleTv(request);
        } else if ("updating".equalsIgnoreCase(mode)) {
            String process = request.getParameter("progress");
            String id = request.getParameter("id");
            String selectType = request.getParameter("selectType");
            status = IPUpgradeManager.modeUpdatingProgress(process, Arrays.asList(id.split(",")), selectType);
        } else if ("START".equalsIgnoreCase(mode)) {
            this.startAssignClones(request);
        } else if ("changeTvGroup".equalsIgnoreCase(mode)) {
            status = this.changeTvGroup(request);
        } else if ("groupRename".equalsIgnoreCase(mode)) {
            status = this.modifyGroupName(request);
        } else if ("groupEdit".equalsIgnoreCase(mode)) {
            status = this.toModifyGroupTvData(request);
        } else if ("tvEdit".equalsIgnoreCase(mode)) {
            status = this.getTvDataByType(request);
        } else if ("groupUpdate".equalsIgnoreCase(mode)) {
            status = this.updateGroupTvData(request);
        } else if ("groupDelete".equalsIgnoreCase(mode)) {
            status = this.deleteGroup(request);
        } else if ("groupUpdating".equalsIgnoreCase(mode)) {
            status = this.modifyGroupStatus(request);
        } else if (mode.equals("checkNetworkDownloadLimiter")) {
            String upgradeTvUniqueIds = request.getParameter("tvUniqueIds");
            JSONObject checkResult = this.getDownloadLimterCheckResult(upgradeTvUniqueIds);
            status = checkResult.toString();
        } else if (mode.contains("Overview")) {
            status = JpaManager.getDevicesManager().getDevicesChartOverviewData(mode);
        } else if ("resendAssignTvSettings".equalsIgnoreCase(mode)) {
            String tvUniqueId = request.getParameter("tvUniqueId");
            status = this.resendAssignTvSettings(tvUniqueId);
        } else if ("wakeupOnLan".equalsIgnoreCase(mode)) {
            status = this.wakeupOnLan(request);
        } else if ("refreshWifiStrength".equalsIgnoreCase(mode)) {
            status = this.refreshWifiStrength(request);
        } else if ("getAllGroupsName".equalsIgnoreCase(mode)) {
            status = this.getAllGroupsName();
        }
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private String getAllGroupsName() {
        GroupsManager groupManager = JpaManager.getGroupsManager();
        List<String> groupLists = groupManager.findAllGroupNames();
        return Utils.buildSuccessReturnJson("groupNamesList", groupLists);
    }

    private String refreshWifiStrength(HttpServletRequest request) {
        String id = request.getParameter("id");
        Devices tv = JpaManager.getDevicesManager().loadByKey(id);
        if (tv == null) {
            return this.failedStatus("refresh tv not exist");
        }
        if (!tv.isOnline()) {
            return this.failedStatus("tv is offline");
        }
        String refreshSuccess = TVSIServiceManager.sendRequestSIServiceToTV(tv);
        if (refreshSuccess != null) {
            return this.successStatus();
        }
        return this.failedStatus("refresh wifi signal failure");
    }

    private String wakeupOnLan(HttpServletRequest request) {
        String id = request.getParameter("id");
        Devices tv = JpaManager.getDevicesManager().loadByKey(id);
        if (null == tv) {
            return this.failedStatus("wakeup tv not exist");
        }
        boolean isWakeupSuccess = TVPowerManager.wakeupTvOnLan(tv);
        if (isWakeupSuccess) {
            return this.successStatus();
        }
        return this.failedStatus("wakeup failure");
    }

    private JSONObject getDownloadLimterCheckResult(String upgradeTvUniqueIds) {
        List<Devices> checkDevices;
        JSONObject checkResult = new JSONObject();
        List<String> idList = Arrays.asList(upgradeTvUniqueIds.split(","));
        if (!idList.isEmpty() && !(checkDevices = JpaManager.getDevicesManager().getDevicesListByTvUniqueIds(idList)).isEmpty()) {
            checkResult = DownloadLimiter.getInstance().checkDownloadAllowedWhenForceUpgradeDevices(checkDevices);
        }
        return checkResult;
    }

    private String modifyGroupStatus(HttpServletRequest request) {
        String process = request.getParameter("progress");
        String groupname = request.getParameter("name");
        ArrayList<String> groupTvUniqueList = new ArrayList<String>();
        GroupsManager groupsManager = JpaManager.getGroupsManager();
        List<Groups> iptvgroup = groupsManager.findGroupsByGroupName(groupname);
        for (Groups grouptv : iptvgroup) {
            groupTvUniqueList.add(grouptv.getTvid());
        }
        return IPUpgradeManager.modeUpdatingProgress(process, groupTvUniqueList, "");
    }

    private String deleteGroup(HttpServletRequest request) {
        String deleteGroupNames = request.getParameter("name");
        for (String deleteGroupName : deleteGroupNames.split(",")) {
            List<TriggerInfo> linkedGroupTriggers = JpaManager.getTriggerInfoManager().findTriggerInfosByTarget(deleteGroupName);
            if (!linkedGroupTriggers.isEmpty()) {
                List linkedTriggerNames = linkedGroupTriggers.stream().map(e -> e.getName()).collect(Collectors.toList());
                String triggerTag = linkedTriggerNames.size() == 1 ? "Trigger" : "Triggers";
                String triggerNameInfo = linkedTriggerNames.size() == 1 ? (String)linkedTriggerNames.get(0) : String.join((CharSequence)",", linkedTriggerNames);
                String errorMsg = "The to be deleted group is used in the following Triggers: " + triggerNameInfo + " . Please change the To parameter of the " + triggerTag + " or delete the " + triggerTag + " before deleting the group";
                return Utils.buildFailReturnJson(errorMsg);
            }
            GroupsManager groupManager = JpaManager.getGroupsManager();
            List<Groups> iptvgroup = groupManager.findGroupsByGroupName(deleteGroupName);
            for (Groups grouptv : iptvgroup) {
                groupManager.deleteByKey(grouptv.getId());
            }
        }
        return "{\"status\":\"success\"}";
    }

    private String updateGroupTvData(HttpServletRequest request) {
        String status;
        String name = request.getParameter("name");
        String ids = request.getParameter("tvids");
        String[] listOfTVs = ids.split(",");
        GroupsManager groupManager = JpaManager.getGroupsManager();
        List<Groups> list = groupManager.findGroupsByGroupName(name);
        for (Groups grouptv : list) {
            groupManager.deleteByKey(grouptv.getId());
        }
        if ("".equals(listOfTVs[0])) {
            status = "{\"status\":\"success\"}";
        } else {
            Groups iptvgroup = null;
            String date = TpvDateUtils.getDateTime();
            for (String tvid : listOfTVs) {
                iptvgroup = new Groups();
                iptvgroup.setGroupname(name);
                iptvgroup.setTvid(tvid);
                iptvgroup.setCreateddate(date);
                groupManager.save(iptvgroup);
            }
            status = "{\"status\":\"success\"}";
        }
        return status;
    }

    private String getTvDataByType(HttpServletRequest request) {
        String tvList = request.getParameter("data");
        String tvType = request.getParameter("tvType");
        String[] tvArray = tvList.split(",");
        ArrayList<String> body = new ArrayList<String>();
        DevicesManager iptvmanager = JpaManager.getDevicesManager();
        List<Devices> list = iptvmanager.findDevicesByType(tvType);
        for (int i = 0; i < list.size(); ++i) {
            boolean isfound = false;
            String id = list.get(i).getId();
            for (int j = 0; j < tvArray.length; ++j) {
                if (tvArray[j].equalsIgnoreCase(null) || !tvArray[j].equalsIgnoreCase(id)) continue;
                isfound = true;
                break;
            }
            if (isfound) continue;
            body.add(this.gets(list.get(i)));
        }
        Gson gson = new Gson();
        return "{\"mainBody\":" + gson.toJson(body) + "}";
    }

    private String gets(Devices devices) {
        return "{\"id\":\"" + devices.getId() + "\", \"name\":\"" + devices.getTvname() + "\", \"sn\":\"" + devices.getTvserialnumber() + "\", \"room\":\"" + devices.getTvroomid() + "\", \"type\":\"" + devices.getType() + "\", \"ip\":\"" + devices.getTvipaddress() + "\"}";
    }

    private String toModifyGroupTvData(HttpServletRequest request) {
        String sql;
        String name = request.getParameter("name");
        String tvids = request.getParameter("tvids");
        ArrayList<Object> paramList = new ArrayList<Object>();
        if (StringUtils.isEmpty(tvids)) {
            sql = "select d.* from devices d, `groups` g where d.id = g.tvid and g.groupname = ?";
            paramList.add(name);
        } else {
            InQueryParameter filter = InQueryParameter.getInstance(tvids);
            sql = "select d.* from devices d where d.id in (" + filter.getPreparedInReplaceHolder() + ")";
            paramList.addAll(filter.getParamList());
        }
        JSONArray deviceData = JpaManager.getDevicesManager().getDeviceJsonData(sql, paramList, null);
        String status = "{\"mainBody\":" + deviceData.toString() + "}";
        return status;
    }

    private String modifyGroupName(HttpServletRequest request) {
        String status;
        String newValue = request.getParameter("name");
        String oldValue = request.getParameter("oldname");
        GroupsManager iptvmanager = JpaManager.getGroupsManager();
        List<Groups> iptvgroup = iptvmanager.findGroupsByGroupName(newValue);
        if (!iptvgroup.isEmpty()) {
            status = this.failedStatus("Group name is already used by an existing group!");
        } else {
            iptvgroup = iptvmanager.findGroupsByGroupName(oldValue);
            for (Groups grouptv : iptvgroup) {
                grouptv.setGroupname(newValue);
                grouptv.setModifieddate(TpvDateUtils.getDateTime());
                iptvmanager.save(grouptv);
            }
            status = "{\"status\":\"success\", \"name\":\"" + newValue + "\"}";
        }
        return status;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String changeTvGroup(HttpServletRequest request) {
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        GroupsManager groupManager = JpaManager.getGroupsManager();
        String ids = request.getParameter("tvids");
        String[] listOfTVs = ids.split(",");
        DevicesManager devicesManager = JpaManager.getDevicesManager();
        StringBuilder reasonBld = new StringBuilder();
        for (String tvid : listOfTVs) {
            Groups iptvgroup;
            Devices tv;
            block5: {
                tv = devicesManager.loadByKey(tvid);
                iptvgroup = new Groups();
                String tvType = tv.getType();
                if ("update".equalsIgnoreCase(type)) {
                    if (name.contains(tvType)) {
                        if (groupManager.findGroupsByTvIdAndGroupName(tvid, name) != null) continue;
                        iptvgroup.setGroupname(name);
                        break block5;
                    } else {
                        reasonBld.append(tv.getTvroomid() + "&emsp;" + tvType + "<br>");
                        continue;
                    }
                }
                String groupName = name + "_" + tvType;
                if (groupManager.findGroupsByTvIdAndGroupName(tvid, groupName) != null) continue;
                iptvgroup.setGroupname(groupName);
            }
            iptvgroup.setTvid(tvid);
            iptvgroup.setCreateddate(TpvDateUtils.getDateTime());
            iptvgroup.setPowerstatus(tv.getPowerstatus());
            groupManager.save(iptvgroup);
        }
        if (reasonBld.length() == 0) {
            return "{\"status\":\"success\"}";
        }
        reasonBld.insert(0, "The following rooms failed to be added to the group (" + name + ") due to incompatible TV types : <br>RoomId&emsp;Type<br>");
        return Utils.buildFailReturnJson(reasonBld.toString());
    }

    private String deleteMutipleTv(HttpServletRequest request) {
        String status;
        String ids = request.getParameter("tvids");
        String[] listOfTVs = ids.split(",");
        if (null == listOfTVs || listOfTVs.length == 0) {
            status = Utils.buildFailReturnJson();
        } else {
            for (String tvid : listOfTVs) {
                this.deleteOneTv(tvid);
            }
            IPProfile.updateIPProfile(listOfTVs);
            status = "{\"status\":\"success\"}";
        }
        return status;
    }

    private String deleteOneTv(String tvId) {
        DevicesManager iptvmanager = JpaManager.getDevicesManager();
        Devices iptv2 = iptvmanager.loadByKey(tvId);
        if (null != iptv2) {
            GuestInfoManager gim = JpaManager.getGuestInfoManager();
            List<GuestInfo> gis = gim.findGuestInfosByRoomid(iptv2.getTvroomid());
            for (GuestInfo gi : gis) {
                gim.deleteByKey(gi.getGuestId());
            }
            PmsUtils.setMessageUpdated();
        }
        GroupsManager iptvgroupManager = JpaManager.getGroupsManager();
        List<Groups> iptvgroup = iptvgroupManager.findGroupsByTvid(tvId);
        for (Groups grouptv : iptvgroup) {
            iptvgroupManager.deleteByKey(grouptv.getId());
        }
        iptvmanager.deleteByKey(tvId);
        IPProfile.updateIPProfile(tvId);
        LOG.info("remove tv: {} by {}", (Object)tvId, (Object)Utils.getAuthenticationName());
        return "{\"status\":\"success\"}";
    }

    private String modifyTvName(HttpServletRequest request) {
        String status;
        DevicesManager iptvmanager = JpaManager.getDevicesManager();
        Devices ipTV = iptvmanager.loadByKey(request.getParameter("id"));
        if (null != ipTV) {
            String newName = request.getParameter("name");
            List<Devices> iptv1 = iptvmanager.findDevicesByTvname(newName);
            if (!iptv1.isEmpty()) {
                status = this.failedStatus("TV name is already used by an existing tv!");
            } else {
                ipTV.setTvname(newName);
                iptvmanager.save(ipTV);
                status = Utils.buildSuccessReturnJson("name", newName);
            }
        } else {
            status = this.failedStatus("tv not existed!");
        }
        return status;
    }

    private String getGroupTvType(HttpServletRequest request) {
        String status;
        String cloneItemString;
        String[] tvid;
        String tvids = request.getParameter("tvids");
        int settingId = Integer.parseInt(request.getParameter("id"));
        String type = "";
        if (!"".equals(tvids) && null != (tvid = tvids.split(",")) && tvid.length > 0) {
            DevicesManager devicesManager = JpaManager.getDevicesManager();
            Devices devices = null;
            for (int i = 0; i < tvid.length; ++i) {
                devices = devicesManager.loadByKey(tvid[i]);
                if (null == devices) continue;
                type = type + devices.getType();
            }
        }
        SettingManager settingManager = JpaManager.getSettingManager();
        Setting setting = null;
        String cloneItemStatus = "";
        StringBuilder cloneItem = new StringBuilder();
        setting = settingManager.loadByKey(settingId);
        if (null != setting && !"".equals(cloneItemStatus = setting.getCloneItemStatus()) && null != cloneItemStatus) {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(cloneItemStatus);
            JSONArray cloneItemStatusDetail = (JSONArray)jsonObject.get("cloneItemStatus");
            int cloneItemCount = cloneItemStatusDetail.length();
            for (int i = 0; i < cloneItemCount; ++i) {
                JSONObject item = (JSONObject)cloneItemStatusDetail.get(i);
                String cloneItemName = item.get("CloneItemName").toString();
                String cloneStatus = item.get("Status").toString();
                cloneItem.append("\"" + cloneItemName + "\":\"" + cloneStatus + "\",");
            }
        }
        if ((cloneItemString = cloneItem.toString()).length() > 0) {
            cloneItemString = cloneItemString.substring(0, cloneItemString.length() - 1);
            LOG.info("cloneItemString={}", (Object)cloneItemString);
            status = "{\"type\":\"" + type + "\"," + cloneItemString + "}";
        } else {
            status = "{\"type\":\"" + type + "\"}";
        }
        return status;
    }

    private String getSettingCloneStatus(HttpServletRequest request) {
        String status;
        String cloneItemString;
        int settingId = Integer.valueOf(request.getParameter("id"));
        SettingManager settingManager = JpaManager.getSettingManager();
        String platform = "None";
        String cloneItemStatus = "";
        StringBuilder cloneItem = new StringBuilder();
        Setting setting = settingManager.loadByKey(settingId);
        if (null != setting) {
            platform = setting.getPlatform();
            cloneItemStatus = setting.getCloneItemStatus();
            if (!"".equals(cloneItemStatus) && null != cloneItemStatus) {
                JSONObject jsonObject = null;
                jsonObject = new JSONObject(cloneItemStatus);
                JSONArray cloneItemStatusDetail = jsonObject.getJSONArray("cloneItemStatus");
                int cloneItemCount = cloneItemStatusDetail.length();
                for (int i = 0; i < cloneItemCount; ++i) {
                    JSONObject item = (JSONObject)cloneItemStatusDetail.get(i);
                    String cloneItemName = item.optString("CloneItemName");
                    String cloneStatus = item.optString("Status");
                    cloneItem.append("\"" + cloneItemName + "\":\"" + cloneStatus + "\",");
                }
            }
        }
        if ((cloneItemString = cloneItem.toString()).length() > 0) {
            cloneItemString = cloneItemString.substring(0, cloneItemString.length() - 1);
            LOG.info("cloneItemString={}", (Object)cloneItemString);
            status = "{\"platform\":\"" + platform + "\"," + cloneItemString + "}";
        } else {
            status = "{\"platform\":\"" + platform + "\"}";
        }
        return status;
    }

    private String getTvInfo(HttpServletRequest request) {
        JSONObject resultJSON = new JSONObject();
        Devices ipTV = JpaManager.getDevicesManager().loadByKey(request.getParameter("id"));
        resultJSON.put("Name", ipTV.getTvname());
        resultJSON.put("ModelNumber", ipTV.getTvmodelnumber());
        resultJSON.put("SerialNumber", ipTV.getTvserialnumber());
        resultJSON.put("RoomID", ipTV.getTvroomid());
        resultJSON.put("MACAddress", ipTV.getTvmacaddress());
        resultJSON.put("IPAddress", ipTV.getTvipaddress());
        resultJSON.put("VSecureTVID", ipTV.getVsecuretvid());
        resultJSON.put("PowerStatus", ipTV.getPowerstatus());
        resultJSON.put("TVUniqueID", ipTV.getTvuniqueid());
        resultJSON.put("Status", ipTV.getStatus());
        resultJSON.put("Type", ipTV.getType());
        resultJSON.put("CreatedDate", ipTV.getCreateddate());
        resultJSON.put("Https", ipTV.getSecureCmdSupport());
        resultJSON.put("CertIP", JpaManager.getSIConfigManager().getCmndIp());
        resultJSON.put("Lastonline", ipTV.getLastonline());
        resultJSON.put("supportUpgradeItems", PlatformUtils.getIpUpgradeItems(ipTV.getType()));
        this.assembleCloneItemShowHtml(resultJSON, ipTV);
        if (PlatformUtils.isSupportShowWarnings(ipTV.getType())) {
            resultJSON.put("warnings", this.getDeviceWarningsData(ipTV));
        } else {
            resultJSON.put("warnings", false);
        }
        return resultJSON.toString();
    }

    public Map<String, String> getDeviceWarningsData(Devices device) {
        String firmwareVersion;
        boolean isTvSettingDifferentBetweenTvAndSIServer;
        HashMap<String, String> warnings = new HashMap<String, String>();
        IPProfile profile = IPProfile.loadIPProfile();
        boolean isWebServiceUrlError = false;
        if (!StringUtils.equalsIgnoreCase(profile.getWebserviceURLIncorrectWarning(), "false") || !StringUtils.equalsIgnoreCase(profile.getTvSettingChangeWarning(), "false")) {
            long maxOnlineExpireMilSecs = Utils.getMaxOnlineExpireMilSecs();
            isWebServiceUrlError = Utils.isWebServiceUrlError(maxOnlineExpireMilSecs, device.getPowerstatus(), device.getTvipaddress(), device.getLastonline(), device.getType());
        }
        if (!StringUtils.equalsIgnoreCase(profile.getWebserviceURLIncorrectWarning(), "false") && isWebServiceUrlError) {
            warnings.put("WEB_SERVICE_URL_ERROR", "");
        }
        if (!StringUtils.equalsIgnoreCase(profile.getTvSettingChangeWarning(), "false") && !isWebServiceUrlError && (isTvSettingDifferentBetweenTvAndSIServer = Utils.isTvSettingNotMatchBetweenTvAndSIServer(device.getPowerstatus(), device.getLastSuccessSettingPackageId(), device.getSiIdentifiers(), device.getTvipaddress(), device.getType()))) {
            SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(Integer.parseInt(device.getLastSuccessSettingPackageId()));
            String showVersionTips = "last assigned tv settings version: " + settingPackage.getName() + " " + settingPackage.getLastEdit();
            warnings.put("TV_SETTING_NOT_MATCH_ERROR", showVersionTips);
        }
        if (!StringUtils.equalsIgnoreCase(profile.getFirmwareOutOfDateWarning(), "false") && StringUtils.isNoneBlank(firmwareVersion = device.getTvFirmwareIdentifier())) {
            String modelName = device.getTvmodelnumber();
            VersionChecker.VersionDetail newerVersionDetail = VersionChecker.getFirmwareNewVersion(device.getType(), modelName, firmwareVersion);
            if (newerVersionDetail != null) {
                String tips = "Firmware is out of date, latest firmware version is " + newerVersionDetail.getVersion() + "!";
                if (StringUtils.isNoneBlank(newerVersionDetail.getUrl())) {
                    tips = tips + "$$" + newerVersionDetail.getUrl();
                }
                warnings.put("TV_FIRMWARE_OUT_OF_DATE", tips);
            }
        }
        return warnings;
    }

    private void assembleCloneItemShowHtml(JSONObject resultJSON, Devices ipTV) {
        SiIdentifiers siIdentifiersObj = SiIdentifiers.fromJson(ipTV.getSiIdentifiers());
        Map<String, String> siAssignItem = siIdentifiersObj.getSiItemUpgradeVersion();
        Map<String, String> tvResponseItem = siIdentifiersObj.getTvResponseUpgradeVersion();
        List<String> upgradeStartedColors = Arrays.asList("#01DF01", "red", "#FFBF00");
        boolean isFWUpgradeStarted = upgradeStartedColors.contains(ipTV.getFwColor());
        boolean isCloneUpgradeStarted = upgradeStartedColors.contains(ipTV.getCloneColor());
        for (Map.Entry<String, String> tvResponse : tvResponseItem.entrySet()) {
            String itemName = tvResponse.getKey();
            String tvVersionNo = tvResponse.getValue();
            String siVersionNo = siAssignItem.get(itemName);
            if ("MainFirmware".equalsIgnoreCase(itemName)) {
                if (isFWUpgradeStarted) {
                    if (tvVersionNo.equalsIgnoreCase(siVersionNo)) {
                        resultJSON.put(itemName, this.getColorHtml("#01DF01", tvVersionNo));
                        continue;
                    }
                    resultJSON.put(itemName, this.getColorHtml("red", tvVersionNo));
                    continue;
                }
                resultJSON.put(itemName, this.getColorHtml("black", tvVersionNo));
                continue;
            }
            if (siVersionNo != null && isCloneUpgradeStarted) {
                if (StringUtils.equalsIgnoreCase(tvVersionNo, siVersionNo)) {
                    resultJSON.put(itemName, this.getColorHtml("#01DF01", tvVersionNo));
                    continue;
                }
                if (StringUtils.isBlank(tvVersionNo)) {
                    resultJSON.put(itemName, "<span style='padding:0 75px 0 75px;background-color:red'></span>");
                    continue;
                }
                resultJSON.put(itemName, this.getColorHtml("red", tvVersionNo));
                continue;
            }
            resultJSON.put(itemName, this.getColorHtml("black", tvVersionNo));
        }
    }

    private String getColorHtml(String color, String tvVersionNo) {
        return "<span style='color:" + color + "'>" + tvVersionNo + "</span>";
    }

    private String getIpProfile() {
        IPProfile ip = IPProfile.loadIPProfile();
        Gson gson = new Gson();
        return "{\"profile\":" + gson.toJson(ip) + "}";
    }

    private String assignFirmware(HttpServletRequest request) {
        String id = request.getParameter("id");
        String tvIds = request.getParameter("tvids");
        String groupIds = request.getParameter("groupIds");
        String selectCloneType = request.getParameter("selectType");
        String status = IPUpgradeManager.processCloneUpgradeType(selectCloneType, id, tvIds, groupIds, null);
        CommonConstants.CloneItemType cloneType1 = IPUpgradeManager.convertUpgradeTypeToCloneItemType(selectCloneType);
        IPUpgradeManager.assignRFPlayouts(tvIds, cloneType1, Integer.parseInt(id), "UI");
        return status;
    }

    private String startTvUpgrade(HttpServletRequest request) {
        String tvids = request.getParameter("tvs");
        String process = request.getParameter("progress");
        IPUpgradeManager.startUpgrades(tvids, process);
        return "{\"status\":\"success\"}";
    }

    private String startGroupUpgrade(HttpServletRequest request) {
        String groupIds = request.getParameter("groups");
        String process = request.getParameter("progress");
        String tvs = IPUpgradeManager.getDeviceList(null, groupIds);
        IPUpgradeManager.startUpgrades(tvs, process);
        return "{\"status\":\"success\"}";
    }

    private String startAssignClones(HttpServletRequest request) {
        String cloneIdStr = request.getParameter("id");
        String tvIds = request.getParameter("tvids");
        String groupIds = request.getParameter("groupIds");
        String cloneType = request.getParameter("cloneType");
        CommonConstants.CloneItemType upgradeType = IPUpgradeManager.convertUpgradeTypeToCloneItemType(cloneType);
        String tvs = IPUpgradeManager.getDeviceList(tvIds, groupIds);
        int cloneId = Integer.parseInt(cloneIdStr);
        try {
            IPUpgradeManager.UpgradeResult upgradeResult = this.assignClones(tvs, upgradeType, cloneId);
            return upgradeResult.toStatusJson();
        }
        catch (IPUpgradeManager.UpgradeException e) {
            LOG.error(e.getMessage(), e);
            return this.failedStatus(e.getMessage());
        }
    }

    private String resendAssignTvSettings(String tvUniqueId) {
        Devices devices = JpaManager.getDevicesManager().loadByKey(tvUniqueId);
        if (devices == null) {
            return new JSONObject("{\"status\":\"fail\"}").put("errorMsg", "upgraded TV not exists").toString();
        }
        if (StringUtils.equalsIgnoreCase(devices.getProgress(), "U")) {
            return new JSONObject("{\"status\":\"fail\"}").put("errorMsg", "can't resend tv settings as the TV already assigned clone data").toString();
        }
        if (IPUpgradeManager.isTVUpgrading(devices)) {
            return new JSONObject("{\"status\":\"fail\"}").put("errorMsg", "can't resend tv settings as the TV is in upgrading status, Please wait until the upgrade completed").toString();
        }
        SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(TpvStringUtils.tryParseInt(devices.getLastSuccessSettingPackageId(), -1));
        if (settingPackage == null) {
            return new JSONObject("{\"status\":\"fail\"}").put("errorMsg", "can't find the resend assigned tv settings").toString();
        }
        String result = IPUpgradeManager.sendTVSettingsRequestToTV(devices, settingPackage);
        if (StringUtils.equals("success", result)) {
            return "{\"status\":\"success\"}";
        }
        return new JSONObject("{\"status\":\"fail\"}").put("errorMsg", result).toString();
    }

    private IPUpgradeManager.UpgradeResult assignClones(String tvs, CommonConstants.CloneItemType cloneType, int cloneId) throws IPUpgradeManager.UpgradeException {
        if (tvs.isEmpty()) {
            LOG.error("AssignClones: tv list is empty");
            return new IPUpgradeManager.UpgradeResult();
        }
        List<Devices> tvList = IPUpgradeManager.getDevicesList(tvs);
        return IPUpgradeManager.assignClones(tvList, cloneType, cloneId);
    }

    private String modeDownload(HttpServletRequest request) {
        String progress = request.getParameter("progress");
        String id = request.getParameter("id");
        Devices tv = JpaManager.getDevicesManager().loadByKey(id);
        if (null == tv) {
            return this.failedStatus("download tv not exists");
        }
        if (!JAPITUtils.isJapitListening(tv)) {
            String power = "offline";
            String reason = "TV is not connected.";
            return "{\"status\":\"fail\", \"reason\" :\"" + reason + "\", \"power\" :\"" + power + "\"}";
        }
        try {
            String downloadedPath = CommonConstants.SISERVER_UPLOAD_DIR + id;
            TpvFileUtils.clearFiles(downloadedPath);
            IPCloneServiceManager.sendDLCommandToTV(tv, progress);
            return "{\"status\":\"success\", \"reason\" :\"\", \"power\" :\"" + tv.getPowerstatus() + "\"}";
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return this.failedStatus(e.getMessage());
        }
    }

    private IPProfile updateIPProfile(HttpServletRequest request) {
        IPProfile lastConfig = IPProfile.loadIPProfile();
        lastConfig.setTvDiscovery(request.getParameter("tvDiscovery"));
        lastConfig.setGreenMode(request.getParameter("greenMode"));
        lastConfig.setFastMode(request.getParameter("fastMode"));
        lastConfig.setProfessionalService(request.getParameter("professionalService"));
        lastConfig.setMaxTVUpdate(request.getParameter("maxUpdate"));
        lastConfig.setUpdateTimeout(request.getParameter("updateTimeout"));
        lastConfig.setURL(request.getParameter("URL"));
        lastConfig.setManuallyLimitUpdate(request.getParameter("manuallyLimitUpdate"));
        lastConfig.setWebserviceURLIncorrectWarning(request.getParameter("webserviceURLIncorrectWarning"));
        lastConfig.setTvSettingChangeWarning(request.getParameter("tvSettingChangeWarning"));
        lastConfig.setFirmwareOutOfDateWarning(request.getParameter("firmwareOutOfDateWarning"));
        lastConfig.saveToFile();
        DownloadLimiter.getInstance().loadProfileToLimiter(lastConfig);
        return lastConfig;
    }
}

