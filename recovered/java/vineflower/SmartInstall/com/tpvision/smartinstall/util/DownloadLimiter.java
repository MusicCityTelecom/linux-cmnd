package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.servlet.IPProfile;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.util.FormatUtil;

public class DownloadLimiter {
   private static final Logger LOG = LoggerFactory.getLogger(DownloadLimiter.class);
   private static final long RESERVE_BYTES_FOR_DOWNLOAD = 104857600L;
   private static final int NETWORK_COLLECT_INTERVAL_IN_SECONDS = 60;
   private static final int NETWORK_SHARE_URL_FREEZE_SECONDS = 15;
   private static final int TV_WILL_DOWNLOAD_EXPIRE_SECONDS = 30;
   private static DownloadLimiter singleInstance;
   private Map<String, AtomicInteger> downloadingCount = new ConcurrentHashMap<>();
   private Map<String, Map<String, LocalDateTime>> willDownloadTVsInfo = new ConcurrentHashMap<>();
   private Map<String, DownloadLimiter.LastNetworkIFData> lastNetworkIFDataMap = new HashMap<>();
   private Map<String, LocalDateTime> networkFreezeMap = new ConcurrentHashMap<>();
   private ScheduledExecutorService service;
   private boolean manuallyLimitUpdate;
   private int maxSimUpdates;
   private int timeOut;
   private Map<String, LocalDateTime> activeUpgradeTVs = new ConcurrentHashMap<>();

   private DownloadLimiter() {
   }

   public static void init() {
      getInstance();
   }

   public static synchronized DownloadLimiter getInstance() {
      if (singleInstance == null) {
         singleInstance = new DownloadLimiter();
         IPProfile lastConfig = IPProfile.loadIPProfile();
         singleInstance.loadProfileToLimiter(lastConfig);
      }

      return singleInstance;
   }

   public void increaseIpDownloadCount(String tvIp, String serverIp) {
      AtomicInteger count = this.downloadingCount.get(serverIp);
      if (count == null) {
         count = new AtomicInteger(1);
      } else {
         count.incrementAndGet();
      }

      LOG.info("increase server ip:{} actual downloading count to {}", serverIp, count);
      this.downloadingCount.put(serverIp, count);
      Map<String, LocalDateTime> willDownloadData = this.willDownloadTVsInfo.get(serverIp);
      if (willDownloadData != null) {
         willDownloadData.remove(tvIp);
      }
   }

   public void decreaseIpDownloadCount(String serverIp) {
      AtomicInteger count = this.downloadingCount.get(serverIp);
      if (count != null && count.intValue() > 0) {
         count.decrementAndGet();
         LOG.info("decrease server ip:{},actual downloading count to {}", serverIp, count);
         this.downloadingCount.put(serverIp, count);
      }
   }

   private int getIpDownloadCount(String serverIp) {
      int actualDownloadingCount = 0;
      AtomicInteger count = this.downloadingCount.get(serverIp);
      if (count != null) {
         actualDownloadingCount = count.intValue();
      }

      int willDownloadCount = 0;
      Map<String, LocalDateTime> willDownloadData = this.willDownloadTVsInfo.get(serverIp);
      if (willDownloadData != null) {
         Iterator<Entry<String, LocalDateTime>> it = willDownloadData.entrySet().iterator();

         while (it.hasNext()) {
            Entry<String, LocalDateTime> entry = it.next();
            if (entry.getValue().isAfter(LocalDateTime.now())) {
               willDownloadCount++;
            } else {
               it.remove();
            }
         }
      }

      int totalDownloadCount = actualDownloadingCount + willDownloadCount;
      LOG.info(
         "download data for network <{}>, actual downloading count is <{}> ,will download count <{}> ,total is <{}> ",
         serverIp,
         actualDownloadingCount,
         willDownloadCount,
         totalDownloadCount
      );
      return totalDownloadCount;
   }

   public void loadProfileToLimiter(IPProfile lastConfig) {
      int maxUpdateTVNum = TpvStringUtils.tryParseInt(lastConfig.getMaxTVUpdate(), -1);
      if (maxUpdateTVNum <= 0) {
         maxUpdateTVNum = Integer.MAX_VALUE;
      }

      int updateTimeout = Integer.parseInt(lastConfig.getUpdateTimeout());
      this.maxSimUpdates = maxUpdateTVNum;
      this.timeOut = updateTimeout;
      this.manuallyLimitUpdate = StringUtils.containsIgnoreCase(lastConfig.getManuallyLimitUpdate(), "true");
      if (this.manuallyLimitUpdate) {
         this.stopNetworkUsageSchedule();
      } else {
         this.startNetworkUsageSchedule();
      }
   }

   private void startNetworkUsageSchedule() {
      if (this.service == null) {
         this.service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("NetworkUsageCollectTask"));
         this.service.scheduleWithFixedDelay(new TpvTimerTask() {
            @Override
            public void tryRun() {
               DownloadLimiter.singleInstance.collectNetworkUsage();
            }
         }, 1L, 60L, TimeUnit.SECONDS);
      }
   }

   private void stopNetworkUsageSchedule() {
      if (this.service != null) {
         this.service.shutdownNow();
         this.lastNetworkIFDataMap.clear();
         this.service = null;
      }
   }

   public JSONObject checkDownloadAllowedWhenForceUpgradeDevices(List<Devices> checkDevices) {
      List<NetworkInterfaceInfo> netSegmentArray = NetworkUtils.getNetSegmentJsonArray();
      Map<NetworkInterfaceInfo, Set<String>> groupDevicesByNetworkSegment = new HashMap<>();

      for (Devices tv : checkDevices) {
         NetworkInterfaceInfo netSegment = NetworkUtils.getCmndIpSegmentFromNetworkInterface(tv.getTvipaddress(), netSegmentArray);
         if (netSegment == null) {
            LOG.warn("unable to locate network interface for client ip :<{}> during check devices ", tv.getTvipaddress());
         } else {
            Set<String> tvUniqueIds = groupDevicesByNetworkSegment.computeIfAbsent(netSegment, k -> new HashSet<>());
            tvUniqueIds.add(tv.getTvuniqueid());
         }
      }

      Map<String, Set<String>> validIntefaceMap = new HashMap<>();
      Map<String, Set<String>> inValidIntefaceMap = new HashMap<>();

      for (Entry<NetworkInterfaceInfo, Set<String>> netSegmentEntry : groupDevicesByNetworkSegment.entrySet()) {
         NetworkInterfaceInfo netSegment = netSegmentEntry.getKey();
         String displayName = netSegment.getDisplayName();
         if (this.manuallyLimitUpdate) {
            validIntefaceMap.put(displayName, groupDevicesByNetworkSegment.get(netSegment));
         } else if (this.isNetworkDownloadAvailableForAutoCheck(netSegment)) {
            validIntefaceMap.put(displayName, groupDevicesByNetworkSegment.get(netSegment));
         } else {
            inValidIntefaceMap.put(displayName, groupDevicesByNetworkSegment.get(netSegment));
         }
      }

      JSONObject resultJson = new JSONObject();
      resultJson.put("validIntefaceMap", validIntefaceMap);
      resultJson.put("inValidIntefaceMap", inValidIntefaceMap);
      return resultJson;
   }

   public boolean isDownloadAllowed(Devices tv) {
      LOG.info("CMND current use {} mode", this.manuallyLimitUpdate ? "manually download check limiter" : "auto download check limiter");
      String tvUniqueId = tv.getTvuniqueid();
      boolean isAllowDownload = false;
      if (this.manuallyLimitUpdate) {
         isAllowDownload = this.manualLimitCheck(tvUniqueId);
      } else {
         isAllowDownload = this.autoLimiterCheck(tv);
      }

      LOG.info("Download is {} for TVUniqueID = {}, ip = {}", isAllowDownload ? "allowed" : "not allowed", tvUniqueId, tv.getTvipaddress());
      return isAllowDownload;
   }

   private boolean autoLimiterCheck(Devices tv) {
      NetworkInterfaceInfo networkSegment = this.findNetworkInterfaceInfoByDevices(tv);
      if (networkSegment == null) {
         LOG.warn("unable to locate network interface for client ip :<{}> ", tv.getTvipaddress());
         return true;
      } else {
         return this.isNetworkDownloadAvailableForAutoCheck(networkSegment);
      }
   }

   private NetworkInterfaceInfo findNetworkInterfaceInfoByDevices(Devices device) {
      String clientIp = device.getTvipaddress();
      return NetworkUtils.getCmndIpSegmentFromNetworkInterface(clientIp, NetworkUtils.getNetSegmentJsonArray());
   }

   private boolean isNetworkDownloadAvailableForAutoCheck(NetworkInterfaceInfo networkSegment) {
      String serverIp = networkSegment.getHostAddress();
      String displayName = networkSegment.getDisplayName();
      DownloadLimiter.LastNetworkIFData lastNetworkIFData = this.lastNetworkIFDataMap.get(displayName);
      if (lastNetworkIFData == null) {
         LOG.warn("unable to get network usage data for interface: <{}> ,ip: <{}> ", displayName, serverIp);
         return true;
      } else {
         int downloadCount = this.getIpDownloadCount(serverIp);
         this.printNetworkUsageLog(lastNetworkIFData, downloadCount);
         return !this.isReachMaxAllowedDownloadingCount(serverIp, lastNetworkIFData, downloadCount);
      }
   }

   private boolean isReachMaxAllowedDownloadingCount(String serverIp, DownloadLimiter.LastNetworkIFData lastNetworkIFData, int downloadCount) {
      long networkSpeed = lastNetworkIFData.getNetworkIF().getSpeed();
      int maxDownloadAllowedCount = new BigDecimal(networkSpeed).divide(new BigDecimal(104857600L)).setScale(0, 2).intValue();
      if (downloadCount >= maxDownloadAllowedCount) {
         LOG.warn(
            "network interface <{}> downloading count is <{}> ,max allowed count is <{}>, max reached,share rejected",
            serverIp,
            downloadCount,
            maxDownloadAllowedCount
         );
         return true;
      } else {
         LOG.info("network interface <{}> downloading count is <{}> ,max allowed count is <{}>", serverIp, downloadCount, maxDownloadAllowedCount);
         return false;
      }
   }

   private boolean manualLimitCheck(String tvUniqueId) {
      int activeDownloadsNum = this.getNumberOfActiveUpgradeTVs();
      LOG.info("manualLimitCheck downloadLimiter Current Download Size={} ,MaxUpdateTVNum={}", activeDownloadsNum, this.maxSimUpdates);
      if (this.activeUpgradeTVs.containsKey(tvUniqueId)) {
         LOG.info("manualLimitCheck activeUpgradeTVs already have the device, return true");
         return true;
      } else {
         return activeDownloadsNum < this.maxSimUpdates;
      }
   }

   public void startUpgrade(Devices device) {
      if (!this.manuallyLimitUpdate) {
         this.addUpgradeStartedTvToInterface(device);
      }

      this.activeUpgradeTVs.put(device.getTvuniqueid(), LocalDateTime.now());
   }

   public void addUpgradeStartedTvToInterface(Devices device) {
      NetworkInterfaceInfo networkInterfaceInfo = this.findNetworkInterfaceInfoByDevices(device);
      if (networkInterfaceInfo == null) {
         LOG.warn("cant locate interface for ip : {} ", device.getTvipaddress());
      } else {
         String serverIP = networkInterfaceInfo.getHostAddress();
         Map<String, LocalDateTime> willDownloadData = this.willDownloadTVsInfo.computeIfAbsent(serverIP, k -> new ConcurrentHashMap<>());
         willDownloadData.put(device.getTvipaddress(), LocalDateTime.now().plusSeconds(30L));
         String displayName = networkInterfaceInfo.getDisplayName();
         this.networkFreezeMap.put(displayName, LocalDateTime.now().plusSeconds(15L));
         DownloadLimiter.LastNetworkIFData lastNetworkIFData = this.lastNetworkIFDataMap.get(displayName);
         if (lastNetworkIFData != null) {
            lastNetworkIFData.addUpgradeStartedTv(device.getTvuniqueid());
            LOG.info(
               "add shared url count for network interface:<{}> serverIP:<{}>, new shared count is {} ",
               displayName,
               networkInterfaceInfo.getHostAddress(),
               lastNetworkIFData.getUpgradeStartedTVs().size()
            );
         } else {
            LOG.warn("cant find cached network interface to add share count for name {} ", displayName);
         }
      }
   }

   public void upgradeComplete(Devices device) {
      this.activeUpgradeTVs.remove(device.getTvuniqueid());
   }

   private int getNumberOfActiveUpgradeTVs() {
      ArrayList<String> keysToRemove = new ArrayList<>();
      LocalDateTime now = LocalDateTime.now();

      for (Entry<String, LocalDateTime> activeDownload : this.activeUpgradeTVs.entrySet()) {
         LocalDateTime downloadStart = activeDownload.getValue();
         if (downloadStart.plusSeconds(this.timeOut).compareTo(now) <= 0) {
            keysToRemove.add(activeDownload.getKey());
         }
      }

      for (String tvUniqueId : keysToRemove) {
         this.activeUpgradeTVs.remove(tvUniqueId);
      }

      return this.activeUpgradeTVs.size();
   }

   private void printNetworkUsageLog(DownloadLimiter.LastNetworkIFData lastNetworkIFData, int downloadCount) {
      NetworkIF net = lastNetworkIFData.getNetworkIF();
      JSONObject networkDataJson = new JSONObject();
      networkDataJson.put("displayName", net.getDisplayName());
      networkDataJson.put("ipv4", Arrays.toString(net.getIPv4addr()));
      networkDataJson.put("packetsRecv", net.getPacketsRecv());
      networkDataJson.put("bytesRecv", FormatUtil.formatBytes(net.getBytesRecv()));
      networkDataJson.put("inErrors", net.getInErrors());
      networkDataJson.put("packetsSent", net.getPacketsSent());
      networkDataJson.put("bytesSent", FormatUtil.formatBytes(net.getBytesSent()));
      networkDataJson.put("outErrors", net.getOutErrors());
      networkDataJson.put("currentDownload", downloadCount);
      networkDataJson.put("mtu", net.getMTU());
      networkDataJson.put("speed", FormatUtil.formatValue(net.getSpeed(), "bps"));
      networkDataJson.put("sentSpeed", FormatUtil.formatBytes(lastNetworkIFData.getSendSpeedBps()) + "/s");
      networkDataJson.put("recvSpeed", FormatUtil.formatBytes(lastNetworkIFData.getRecvSpeedBps()) + "/s");
      networkDataJson.put("upgradeStartedTVCount", lastNetworkIFData.getUpgradeStartedTVs().size());
      LOG.info("download network usage:{}", networkDataJson);
   }

   private void collectNetworkUsage() {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      SystemInfo si = new SystemInfo();
      HardwareAbstractionLayer hal = si.getHardware();
      Map<String, DownloadLimiter.LastNetworkIFData> currentNetworkIFDataMap = new HashMap<>();

      for (NetworkIF net : hal.getNetworkIFs()) {
         boolean hasData = net.getBytesRecv() > 0L || net.getBytesSent() > 0L || net.getPacketsRecv() > 0L || net.getPacketsSent() > 0L;
         if (hasData) {
            DownloadLimiter.LastNetworkIFData currentData = new DownloadLimiter.LastNetworkIFData();
            long currentTime = System.currentTimeMillis();
            currentData.setTime(currentTime);
            currentData.setNetworkIF(net);
            String displayName = net.getDisplayName();
            DownloadLimiter.LastNetworkIFData lastNetworkIFData = this.lastNetworkIFDataMap.get(displayName);
            if (lastNetworkIFData != null && Arrays.equals(lastNetworkIFData.getNetworkIF().getIPv4addr(), net.getIPv4addr())) {
               long intervalSec = (currentTime - lastNetworkIFData.getTime()) / 1000L;
               long bytesSentInterval = net.getBytesSent() - lastNetworkIFData.getNetworkIF().getBytesSent();
               long bytesRecInterval = net.getBytesRecv() - lastNetworkIFData.getNetworkIF().getBytesRecv();
               currentData.setSendSpeedBps(bytesSentInterval / intervalSec);
               currentData.setRecvSpeedBps(bytesRecInterval / intervalSec);
            }

            currentNetworkIFDataMap.put(displayName, currentData);
         }
      }

      this.lastNetworkIFDataMap = currentNetworkIFDataMap;
      stopWatch.stop();
      LOG.info("do network usage collect use time: {} ms ", stopWatch.getTime());
   }

   private static class LastNetworkIFData {
      long time;
      long sendSpeedBps;
      long recvSpeedBps;
      NetworkIF networkIF;
      Set<String> upgradeStartedTVs = new HashSet<>();

      private LastNetworkIFData() {
      }

      public long getTime() {
         return this.time;
      }

      public void setTime(long time) {
         this.time = time;
      }

      public long getSendSpeedBps() {
         return this.sendSpeedBps;
      }

      public void setSendSpeedBps(long sendSpeedBps) {
         this.sendSpeedBps = sendSpeedBps;
      }

      public long getRecvSpeedBps() {
         return this.recvSpeedBps;
      }

      public void setRecvSpeedBps(long recvSpeedBps) {
         this.recvSpeedBps = recvSpeedBps;
      }

      public NetworkIF getNetworkIF() {
         return this.networkIF;
      }

      public void setNetworkIF(NetworkIF networkIF) {
         this.networkIF = networkIF;
      }

      public Set<String> getUpgradeStartedTVs() {
         return this.upgradeStartedTVs;
      }

      public void addUpgradeStartedTv(String tvUniqueId) {
         this.upgradeStartedTVs.add(tvUniqueId);
      }
   }
}
