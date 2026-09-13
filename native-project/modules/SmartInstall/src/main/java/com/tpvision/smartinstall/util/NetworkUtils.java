package com.tpvision.smartinstall.util;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class NetworkUtils {
   private static final Pattern IP_V4_FORMAT = Pattern.compile("^(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$");
   private static final Logger LOG = LoggerFactory.getLogger(NetworkUtils.class);
   private static final int SOCKET_TEST_TIME_OUT = 1000;
   private static List<NetworkInterfaceInfo> networkArrayCache;

   private NetworkUtils() {
   }

   public static boolean isListeningOnPort(String ip, int port, int timeout) {
      LOG.debug("checking isListeningOnPort:{}", port);

      try (Socket socket = new Socket()) {
         socket.connect(new InetSocketAddress(ip, port), timeout);
         LOG.info("socket connect success ip={}", ip);
         return socket.isConnected();
      } catch (Exception e) {
         LOG.error("failed socket connect ip={}", ip);
         return false;
      }
   }

   public static String getServerIpFromSameRoute(String clientip) {
      LOG.info("get server ip for client ip:{}", clientip);
      String cmndIp = null;
      List<NetworkInterfaceInfo> netSegmentArray = getNetSegmentJsonArray();
      NetworkInterfaceInfo segment = getCmndIpSegmentFromNetworkInterface(clientip, netSegmentArray);
      if (segment != null) {
         cmndIp = segment.getHostAddress();
         LOG.info("success get server ip from networkinteface, server ip: {} ", cmndIp);
         return cmndIp;
      } else {
         cmndIp = getCmndIpAddressFromDb(clientip);
         if (cmndIp != null) {
            LOG.info("success get server ip from database, server ip: {} ", cmndIp);
            return cmndIp;
         } else if (netSegmentArray.size() == 1) {
            cmndIp = netSegmentArray.get(0).getHostAddress();
            LOG.info("detected ipv4 address network interface count is 1, use this IP directly, server ip: {} ", cmndIp);
            return cmndIp;
         } else {
            cmndIp = getCmndIpAddressFromHttpRequest();
            if (cmndIp != null) {
               LOG.info("success get server ip from http request, server ip: {} ", cmndIp);
               return cmndIp;
            } else {
               throw new RuntimeException("cant get cmnd server ip,client ip is <" + clientip + ">");
            }
         }
      }
   }

   public static String getCmndIpAddressFromHttpRequest() {
      ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
      if (servletRequestAttributes != null) {
         String serverIp = servletRequestAttributes.getRequest().getLocalAddr();
         if (IP_V4_FORMAT.matcher(serverIp).matches() && !"127.0.0.1".equals(serverIp)) {
            return serverIp;
         }
      }

      return null;
   }

   private static String getCmndIpAddressFromDb(String clientip) {
      List<Devices> tvs = JpaManager.getDevicesManager().findDevicesByTvipaddress(clientip);
      return tvs.isEmpty() ? null : tvs.get(0).getNetworkInterfaceIp();
   }

   public static NetworkInterfaceInfo getCmndIpSegmentFromNetworkInterface(String clientip, List<NetworkInterfaceInfo> netSegmentArray) {
      int i = 0;

      for (int j = netSegmentArray.size(); i < j; i++) {
         NetworkInterfaceInfo segment = netSegmentArray.get(i);
         String hostAddress = segment.getHostAddress();
         SubnetUtils utils = new SubnetUtils(hostAddress + "/" + segment.getNetworkprefixlength());
         if (utils.getInfo().isInRange(clientip)) {
            return segment;
         }
      }

      return null;
   }

   public static List<String> getSubnetList() {
      List<String> subnetList = new ArrayList<>();

      for (NetworkInterfaceInfo network : getNetSegmentJsonArray()) {
         subnetList.add(network.getHostAddress() + "/" + network.getNetworkprefixlength());
      }

      return subnetList;
   }

   public static List<NetworkInterfaceInfo> getNetSegmentJsonArray() {
      if (networkArrayCache == null) {
         refreshNetSegmentJsonArray();
      }

      return networkArrayCache;
   }

   public static void refreshNetSegmentJsonArray() {
      List<NetworkInterfaceInfo> networkArray = new ArrayList<>();

      try {
         Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

         while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.isUp()) {
               LOG.info(
                  "ip type is {} ip displayname is {} ip MTU is {}", networkInterface.getName(), networkInterface.getDisplayName(), networkInterface.getMTU()
               );

               for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                  InetAddress inetAddress = interfaceAddress.getAddress();
                  String hostAddress = inetAddress.getHostAddress();
                  if (!"127.0.0.1".equals(hostAddress) && IP_V4_FORMAT.matcher(hostAddress).matches()) {
                     short networkPrefixLength = interfaceAddress.getNetworkPrefixLength();
                     if (networkPrefixLength >= 0) {
                        String subnet = hostAddress + "/" + networkPrefixLength;
                        SubnetInfo subnetInfo = new SubnetUtils(subnet).getInfo();
                        NetworkInterfaceInfo networkInfo = new NetworkInterfaceInfo();
                        networkInfo.setNetworksegment(subnetInfo.getNetworkAddress());
                        networkInfo.setFirstaddress(subnetInfo.getLowAddress());
                        networkInfo.setLastaddress(subnetInfo.getHighAddress());
                        networkInfo.setNetworkprefixlength(networkPrefixLength);
                        networkInfo.setDisplayName(networkInterface.getDisplayName());
                        networkInfo.setHostAddress(hostAddress);
                        networkArray.add(networkInfo);
                     }
                  }
               }
            }
         }
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }

      String arrayJson = new Gson().toJson(networkArray);
      LOG.info("network info:{}", arrayJson);
      networkArrayCache = networkArray;
   }

   public static List<String> getServerIpList() {
      List<String> ipList = new ArrayList<>();
      List<NetworkInterfaceInfo> netSegmentArray = getNetSegmentJsonArray();
      int i = 0;

      for (int j = netSegmentArray.size(); i < j; i++) {
         NetworkInterfaceInfo segment = netSegmentArray.get(i);
         ipList.add(segment.getHostAddress());
      }

      return ipList;
   }

   public static int findFirstListenPortOnTargetIp(String ip, List<Integer> ports) {
      for (int port : ports) {
         if (checkSocketPort(ip, port)) {
            return port;
         }
      }

      return -1;
   }

   public static List<Integer> filterSocketPorts(String ip, List<Integer> ports) {
      return ports.parallelStream().filter(port -> checkSocketPort(ip, port)).collect(Collectors.toList());
   }

   public static boolean checkSocketPort(String ip, int port) {
      try (Socket socket = new Socket()) {
         socket.connect(new InetSocketAddress(ip, port), 1000);
         LOG.info("socket connect success ip={} port={}", ip, port);
         return true;
      } catch (Exception ex) {
         LOG.warn("socket connect fail ip={} port={}", ip, port);
         return false;
      }
   }

   public static List<String> findAllIpListBelongToTheIpSegment(String lowAddr, String highAddr) {
      List<String> ipList = new ArrayList<>();
      String[] subnetArr = lowAddr.split("\\.");
      String[] firstArray = lowAddr.split("\\.");
      String[] endArray = highAddr.split("\\.");
      int firstAddr3 = Integer.parseInt(firstArray[2]);
      int endAddr3 = Integer.parseInt(endArray[2]);

      for (int i = firstAddr3; i <= endAddr3; i++) {
         int startIp = 1;
         if (i == firstAddr3) {
            startIp = Integer.parseInt(firstArray[3]);
         }

         int endIp = 254;
         if (i == endAddr3) {
            endIp = Integer.parseInt(endArray[3]);
         }

         String subnetPrefix = subnetArr[0] + "." + subnetArr[1] + "." + String.format(Locale.ENGLISH, "%d", i);

         for (int hostId = startIp; hostId <= endIp; hostId++) {
            String ip = subnetPrefix + "." + hostId;
            ipList.add(ip);
         }
      }

      return ipList;
   }

   public static String getServerOneMacAddress() {
      try {
         Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();

         while (el.hasMoreElements()) {
            byte[] mac = el.nextElement().getHardwareAddress();
            if (mac != null) {
               return hexByte(mac[0]) + "-" + hexByte(mac[1]) + "-" + hexByte(mac[2]) + "-" + hexByte(mac[3]) + "-" + hexByte(mac[4]) + "-" + hexByte(mac[5]);
            }
         }
      } catch (SocketException e1) {
         LOG.error(e1.getMessage(), e1);
      }

      return null;
   }

   private static String hexByte(byte b) {
      String s = "000000" + Integer.toHexString(b);
      return s.substring(s.length() - 2);
   }

   public static NetworkInterfaceInfo getFirstMatchNetworkInterfaceJsonInfoByServerIp(String serverIp) {
      List<NetworkInterfaceInfo> netSegmentArray = getNetSegmentJsonArray();
      int i = 0;

      for (int j = netSegmentArray.size(); i < j; i++) {
         NetworkInterfaceInfo segment = netSegmentArray.get(i);
         String hostAddress = segment.getHostAddress();
         if (hostAddress.equals(serverIp)) {
            return segment;
         }
      }

      return null;
   }

   public static NetworkInterface findNetworkInterfaceByTargetIp(String ipAddress) {
      try {
         Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

         while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.isUp()) {
               for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                  InetAddress inetAddress = interfaceAddress.getAddress();
                  String hostAddress = inetAddress.getHostAddress();
                  short networkPrefixLength = interfaceAddress.getNetworkPrefixLength();
                  if (!"127.0.0.1".equals(hostAddress) && IP_V4_FORMAT.matcher(hostAddress).matches() && networkPrefixLength >= 0) {
                     String subnet = hostAddress + "/" + networkPrefixLength;
                     SubnetInfo subnetInfo = new SubnetUtils(subnet).getInfo();
                     if (subnetInfo.isInRange(ipAddress)) {
                        return networkInterface;
                     }
                  }
               }
            }
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return null;
   }

   public static boolean isUrlAvailable(String urlString, int timeOutMillSeconds) {
      try {
         URL url = new URL(urlString);
         URLConnection co = url.openConnection();
         co.setConnectTimeout(timeOutMillSeconds);
         co.connect();
         return true;
      } catch (Exception e1) {
         return false;
      }
   }

   public static boolean isHttpUrlAvailable(String urlStr) {
      try {
         URL url = new URL(urlStr);
         HttpURLConnection co = (HttpURLConnection)url.openConnection();
         co.setConnectTimeout(3000);
         co.connect();
         if (co.getResponseCode() == 200) {
            return true;
         }
      } catch (Exception e1) {
         LOG.error(e1.getMessage());
      }

      return false;
   }
}
