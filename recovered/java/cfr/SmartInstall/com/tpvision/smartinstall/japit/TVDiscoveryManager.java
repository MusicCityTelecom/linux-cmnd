/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.OnlineDevices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.GetVSecureKeyManager;
import com.tpvision.smartinstall.japit.TVDiscoveryCmd;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvCallableTask;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvNamedThreadFactory;
import com.tpvision.smartinstall.util.TpvRunableTask;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TVDiscoveryManager {
    private static final Logger logger = LoggerFactory.getLogger(TVDiscoveryManager.class);
    private static final int THREAD_POOL_SIZE = 30;

    private TVDiscoveryManager() {
    }

    public static OnlineDevices findTvInfoBySingleIp(String ip, DetectTarget detectTarget) {
        List<Integer> ports = detectTarget.getDetectPorts();
        for (Integer port : ports) {
            OnlineDevices onlineTvInfo;
            boolean isValidPort = NetworkUtils.checkSocketPort(ip, port);
            if (!isValidPort || (onlineTvInfo = TVDiscoveryManager.detectTv(ip, port)) == null) continue;
            return onlineTvInfo;
        }
        return null;
    }

    public static List<OnlineDevices> scanTvListByTargetIpList(List<String> checkIpList, DetectTarget detectTarget) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(30, 30, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new TpvNamedThreadFactory("tvdiscovery-pool"));
        List<OnlineDevices> connectAbleDeviceList = TVDiscoveryManager.filterConnectedIpList(executorService, checkIpList, detectTarget);
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        return connectAbleDeviceList;
    }

    public static List<OnlineDevices> scanAllTvListByIpSegment(String lowAddr, String highAddr, DetectTarget detectTarget) {
        List<String> segmentIpList = NetworkUtils.findAllIpListBelongToTheIpSegment(lowAddr, highAddr);
        return TVDiscoveryManager.scanTvListByTargetIpList(segmentIpList, detectTarget);
    }

    public static Devices saveDetectedTvToDevices(OnlineDevices onlineTvInfo, Devices tv, boolean isPollingFromTV) {
        DevicesManager tvManager = JpaManager.getDevicesManager();
        String tvUniqueId = onlineTvInfo.getTvuniqueid();
        String lastUniqueId = onlineTvInfo.getLastUniqueId();
        if (StringUtils.isNotEmpty(lastUniqueId)) {
            tv = tvManager.loadByKey(tvUniqueId);
            Devices lastUniqueIdTV = tvManager.loadByKey(lastUniqueId);
            if (lastUniqueIdTV != null) {
                logger.info("found old device data with last unique id <{}> ,will use the record to hold the device priority", (Object)lastUniqueId);
                if (tv != null) {
                    logger.info("new id record <{}> also existed, delete it", (Object)tvUniqueId);
                    tvManager.deleteByKey(tvUniqueId);
                }
                tvManager.deleteTV(lastUniqueIdTV);
                tv = lastUniqueIdTV;
                tv.setId(tvUniqueId);
            } else {
                logger.info("can't found old device data with last unique id <{}>", (Object)lastUniqueId);
            }
        }
        boolean isNewDevice = false;
        if (tv == null) {
            tv = new Devices(true);
            logger.info("found a new device with unique id <{}>, start to create one", (Object)tvUniqueId);
            tv.setId(tvUniqueId);
            tv.setTvname(TpvDateUtils.getTvNameFormateString());
            tv.setCreateddate(TpvDateUtils.getDateTime());
            tv.setProgress("ST");
            isNewDevice = true;
        }
        tv.setTvuniqueid(tvUniqueId);
        tv.setTvserialnumber(onlineTvInfo.getTvserialnumber());
        tv.setTvmodelnumber(onlineTvInfo.getTvmodelnumber());
        tv.setTvroomid(onlineTvInfo.getTvroomid());
        tv.setTvmacaddress(onlineTvInfo.getTvmacaddress());
        tv.setVsecuretvid(onlineTvInfo.getVsecuretvid());
        tv.setPowerstatus(onlineTvInfo.getPowerstatus());
        tv.setTvipaddress(onlineTvInfo.getTvipaddress());
        if (isPollingFromTV || StringUtils.isEmpty(tv.getLastonline())) {
            tv.setLastonline(TpvDateUtils.getDateTime());
        }
        tv.setSecureCmdSupport(onlineTvInfo.getSecureCmdSupport());
        tv.setType(onlineTvInfo.getType());
        tv.setNetworkInterfaceIp(onlineTvInfo.getNetworkInterfaceIp());
        if (onlineTvInfo.getWlsPort() != 0) {
            tv.setWlsPort(onlineTvInfo.getWlsPort());
        }
        if (onlineTvInfo.getWlsSecurePort() != 0) {
            tv.setWlsSecurePort(onlineTvInfo.getWlsSecurePort());
        }
        tvManager.save(tv);
        PmsUtils.setMessageUpdated();
        if ((StringUtils.isBlank(tv.getVsecureKey()) || StringUtils.isBlank(tv.getVsecuretvid())) && PlatformUtils.isSupportVSecureKeyByPlatformName(tv.getType()) && (tv.isSecureHttpJapit() && tv.getWlsSecurePort() != 0 || !tv.isSecureHttpJapit() && tv.getWlsPort() != 0)) {
            GetVSecureKeyManager.getVSecureKeyFromTv(tv);
        }
        if (isNewDevice && PlatformUtils.isSupportEnablerService(tv.getType())) {
            try {
                PmsUtils.enableApplicationControlService(tv, true, true);
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return tv;
    }

    private static OnlineDevices detectTv(String ip, int port) {
        OnlineDevices onlineTvInfo = null;
        try {
            boolean bHttps = CommonConstants.TV_WIXP_HTTPS_PORTS.contains(port);
            String resData = JAPITUtils.sendJapitCommandToTV(ip, port, bHttps, new TVDiscoveryCmd().generateCommand(), 3000);
            JSONObject jsonObject = TpvStringUtils.getJSONObjectFromString(resData);
            if (jsonObject == null || !jsonObject.has("CommandDetails")) {
                logger.warn("AutoTvDetectServlet:detectTv received from IP {} incorrect formatted message = {}", (Object)ip, (Object)resData);
                return null;
            }
            JSONObject commandDetails = jsonObject.getJSONObject("CommandDetails");
            if (!commandDetails.has("WebListeningServiceParameters")) {
                logger.warn("AutoTvDetectServlet:detectTv received from IP {} incorrect formatted message = {} ", (Object)ip, (Object)resData);
                return null;
            }
            JSONObject webListeningServiceParameters = commandDetails.getJSONObject("WebListeningServiceParameters");
            JSONObject tvDiscoveryParameters = commandDetails.getJSONObject("TVDiscoveryParameters");
            String japtTvUniqueId = webListeningServiceParameters.optString("TVUniqueID");
            boolean isHttpsPort = CommonConstants.TV_WIXP_HTTPS_PORTS.contains(port);
            String tvIpAddress = tvDiscoveryParameters.optString("TVIPAddress");
            Properties prop = Utils.getProductPropties();
            String incomingTvAdr = prop.getProperty("incomingTvAdr");
            if ("on".equalsIgnoreCase(incomingTvAdr)) {
                tvIpAddress = ip;
            }
            if ((onlineTvInfo = TVDiscoveryManager.initOnlineTvInfoDiscoveryParameter(tvIpAddress, port, japtTvUniqueId, tvDiscoveryParameters, isHttpsPort, false)) != null) {
                if (isHttpsPort) {
                    onlineTvInfo.setWlsSecurePort(port);
                } else {
                    onlineTvInfo.setWlsPort(port);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return onlineTvInfo;
    }

    private static boolean sendUpdateTvUniqueIdToTV(String type, String ip, int port, boolean isUsingHttps, String updateUniqueId) {
        try {
            String updateResult = JAPITUtils.sendJapitCommandToTV(ip, port, isUsingHttps, new TVDiscoveryCmd().generateUpdateTVUnqiueIdCommand(type, updateUniqueId), 3000);
            JSONObject jsonResult = new JSONObject(updateResult);
            return jsonResult.getJSONObject("CommandDetails").getJSONObject("WebListeningServiceParameters").getString("TVUniqueID").equalsIgnoreCase(updateUniqueId);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static OnlineDevices initOnlineTvInfoDiscoveryParameter(String ip, int port, String japitTvUniqueId, JSONObject tvDiscoveryParameters, boolean isUsingHttps, boolean isWebServiceTypeJapit) {
        OnlineDevices onlineTvInfo = new OnlineDevices();
        onlineTvInfo.setTvipaddress(ip);
        if (isWebServiceTypeJapit) {
            onlineTvInfo.setNetworkInterfaceIp(NetworkUtils.getCmndIpAddressFromHttpRequest());
        }
        if (onlineTvInfo.getNetworkInterfaceIp() == null) {
            onlineTvInfo.setNetworkInterfaceIp(NetworkUtils.getServerIpFromSameRoute(ip));
        }
        String tvModelNumber = tvDiscoveryParameters.optString("TVModelNumber");
        String type = PlatformUtils.convertTvModelNumberToType(tvModelNumber);
        onlineTvInfo.setTvmodelnumber(tvModelNumber);
        onlineTvInfo.setType(type);
        onlineTvInfo.setPowerstatus(tvDiscoveryParameters.optString("PowerStatus"));
        onlineTvInfo.setTvserialnumber(tvDiscoveryParameters.optString("TVSerialNumber", ""));
        onlineTvInfo.setTvmacaddress(tvDiscoveryParameters.optString("TVMACAddress", ""));
        onlineTvInfo.setTvroomid(tvDiscoveryParameters.optString("TVRoomID", ""));
        onlineTvInfo.setVsecuretvid(tvDiscoveryParameters.optString("VSecureTVID", "NO"));
        onlineTvInfo.setSecureCmdSupport(String.valueOf(isUsingHttps));
        onlineTvInfo.setTvuniqueid(japitTvUniqueId);
        String shouldUniqueId = TVDiscoveryManager.generateTvUniqueId(onlineTvInfo.getTvserialnumber(), onlineTvInfo.getTvmacaddress());
        if (!StringUtils.equalsIgnoreCase(shouldUniqueId, japitTvUniqueId)) {
            logger.warn("TV <{}> current unique <{}> is invalid format, should be <{}>, send Japit to fix the value", ip, japitTvUniqueId, shouldUniqueId);
            if (!TVDiscoveryManager.checkTvInfoValidForAssembleUnqiueId(onlineTvInfo.getTvmacaddress(), ip)) {
                return null;
            }
            boolean fixResult = TVDiscoveryManager.sendUpdateTvUniqueIdToTV(type, ip, port, isUsingHttps, shouldUniqueId);
            if (!fixResult) {
                logger.error("can't reset current tv's unique id, skip save to database : <{}>", (Object)ip);
                return null;
            }
            onlineTvInfo.setTvuniqueid(shouldUniqueId);
            onlineTvInfo.setLastUniqueId(japitTvUniqueId);
        }
        return onlineTvInfo;
    }

    private static boolean checkTvInfoValidForAssembleUnqiueId(String tvMacAddress, String tvIpAdress) {
        if (tvMacAddress.contains(":") && tvMacAddress.length() != 17 || !tvMacAddress.contains(":") && tvMacAddress.length() != 12) {
            logger.error("Invalid MAC address<{}> detected from device on IP address: <{}>", (Object)tvMacAddress, (Object)tvIpAdress);
            return false;
        }
        return true;
    }

    private static List<OnlineDevices> filterConnectedIpList(ExecutorService executorService, List<String> checkIpList, DetectTarget detectTarget) {
        ArrayList<Future<OnlineDevices>> ipFutureList = new ArrayList<Future<OnlineDevices>>();
        for (String ip : checkIpList) {
            Future<OnlineDevices> ipFuture2 = TVDiscoveryManager.submitDetectTask(executorService, ip, detectTarget);
            ipFutureList.add(ipFuture2);
        }
        return ipFutureList.stream().map(ipFuture -> {
            OnlineDevices ip = null;
            try {
                ip = (OnlineDevices)ipFuture.get();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return ip;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static Future<OnlineDevices> submitDetectTask(ExecutorService executorService, final String ip, final DetectTarget detectTarget) {
        return executorService.submit(new TpvCallableTask<OnlineDevices>(){

            @Override
            public OnlineDevices execute() {
                return TVDiscoveryManager.findTvInfoBySingleIp(ip, detectTarget);
            }
        });
    }

    public static void refreshAllIpTvStatus() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        DevicesManager devicesManager = JpaManager.getDevicesManager();
        List<String> ipList = devicesManager.loadAll().stream().filter(e -> !e.isRFDevice()).map(Devices::getTvipaddress).distinct().collect(Collectors.toList());
        logger.info("detectTvStatusAfterLogin,refresh ip count=={} ", (Object)ipList.size());
        List<OnlineDevices> refreshedOnlineTvInfos = TVDiscoveryManager.scanTvListByTargetIpList(ipList, DetectTarget.HTTPSANDHTTP);
        for (OnlineDevices onlineTvInfo : refreshedOnlineTvInfos) {
            Devices tv = devicesManager.loadByKey(onlineTvInfo.getTvuniqueid());
            TVDiscoveryManager.saveDetectedTvToDevices(onlineTvInfo, tv, false);
        }
        stopWatch.stop();
        logger.info("detectTvStatusAfterLogin, time={} ", (Object)(stopWatch.getTime() / 1000L + "s"));
    }

    public static void refreshTVInfo(String clientip) {
        OnlineDevices onlineTvInfo = TVDiscoveryManager.findTvInfoBySingleIp(clientip, DetectTarget.HTTPSANDHTTP);
        if (onlineTvInfo != null) {
            Devices tv = JpaManager.getDevicesManager().loadByKey(onlineTvInfo.getTvuniqueid());
            TVDiscoveryManager.saveDetectedTvToDevices(onlineTvInfo, tv, false);
        }
    }

    public static void getDevices(String clientip) {
        new Thread(new TpvRunableTask(){
            private String tvip;

            public Runnable init(String ip) {
                this.tvip = ip;
                return this;
            }

            @Override
            public void execute() {
                Thread.currentThread().setName("iptv-getdevices-" + RandomUtils.nextInt(1, 100));
                logger.info("get devices data for IP {} started", (Object)this.tvip);
                TVDiscoveryManager.refreshTVInfo(this.tvip);
            }
        }.init(clientip)).start();
    }

    public static String generateTvUniqueId(String serialNo, String macAddress) {
        return (serialNo + macAddress.replace(":", "")).toUpperCase();
    }

    public static enum DetectTarget {
        HTTPS,
        HTTP,
        HTTPSANDHTTP,
        SSDP;


        public static DetectTarget valueOf(int intValue) {
            switch (intValue) {
                case 0: {
                    return HTTP;
                }
                case 1: {
                    return HTTPS;
                }
                case 2: {
                    return SSDP;
                }
            }
            return HTTPSANDHTTP;
        }

        public List<Integer> getDetectPorts() {
            switch (this) {
                case HTTPS: {
                    return CommonConstants.TV_WIXP_HTTPS_PORTS;
                }
                case HTTP: {
                    return CommonConstants.TV_WIXP_HTTP_PORTS;
                }
            }
            return CommonConstants.TV_WIXP_ALL_PORTS;
        }
    }
}

