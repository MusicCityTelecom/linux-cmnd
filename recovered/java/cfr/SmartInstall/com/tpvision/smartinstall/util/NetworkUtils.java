/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.NetworkInterfaceInfo;
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isListeningOnPort(String ip, int port, int timeout) {
        LOG.debug("checking isListeningOnPort:{}", (Object)port);
        try (Socket socket = new Socket();){
            socket.connect(new InetSocketAddress(ip, port), timeout);
            LOG.info("socket connect success ip={}", (Object)ip);
            boolean bl = socket.isConnected();
            return bl;
        }
        catch (Exception e) {
            LOG.error("failed socket connect ip={}", (Object)ip);
            return false;
        }
    }

    public static String getServerIpFromSameRoute(String clientip) {
        LOG.info("get server ip for client ip:{}", (Object)clientip);
        String cmndIp = null;
        List<NetworkInterfaceInfo> netSegmentArray = NetworkUtils.getNetSegmentJsonArray();
        NetworkInterfaceInfo segment = NetworkUtils.getCmndIpSegmentFromNetworkInterface(clientip, netSegmentArray);
        if (segment != null) {
            cmndIp = segment.getHostAddress();
            LOG.info("success get server ip from networkinteface, server ip: {} ", (Object)cmndIp);
            return cmndIp;
        }
        cmndIp = NetworkUtils.getCmndIpAddressFromDb(clientip);
        if (cmndIp != null) {
            LOG.info("success get server ip from database, server ip: {} ", (Object)cmndIp);
            return cmndIp;
        }
        if (netSegmentArray.size() == 1) {
            cmndIp = netSegmentArray.get(0).getHostAddress();
            LOG.info("detected ipv4 address network interface count is 1, use this IP directly, server ip: {} ", (Object)cmndIp);
            return cmndIp;
        }
        cmndIp = NetworkUtils.getCmndIpAddressFromHttpRequest();
        if (cmndIp != null) {
            LOG.info("success get server ip from http request, server ip: {} ", (Object)cmndIp);
            return cmndIp;
        }
        throw new RuntimeException("cant get cmnd server ip,client ip is <" + clientip + ">");
    }

    public static String getCmndIpAddressFromHttpRequest() {
        String serverIp;
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null && IP_V4_FORMAT.matcher(serverIp = servletRequestAttributes.getRequest().getLocalAddr()).matches() && !"127.0.0.1".equals(serverIp)) {
            return serverIp;
        }
        return null;
    }

    private static String getCmndIpAddressFromDb(String clientip) {
        List<Devices> tvs = JpaManager.getDevicesManager().findDevicesByTvipaddress(clientip);
        if (tvs.isEmpty()) {
            return null;
        }
        return tvs.get(0).getNetworkInterfaceIp();
    }

    public static NetworkInterfaceInfo getCmndIpSegmentFromNetworkInterface(String clientip, List<NetworkInterfaceInfo> netSegmentArray) {
        int j = netSegmentArray.size();
        for (int i = 0; i < j; ++i) {
            NetworkInterfaceInfo segment = netSegmentArray.get(i);
            String hostAddress = segment.getHostAddress();
            SubnetUtils utils = new SubnetUtils(hostAddress + "/" + segment.getNetworkprefixlength());
            if (!utils.getInfo().isInRange(clientip)) continue;
            return segment;
        }
        return null;
    }

    public static List<String> getSubnetList() {
        ArrayList<String> subnetList = new ArrayList<String>();
        List<NetworkInterfaceInfo> networkArray = NetworkUtils.getNetSegmentJsonArray();
        for (NetworkInterfaceInfo network : networkArray) {
            subnetList.add(network.getHostAddress() + "/" + network.getNetworkprefixlength());
        }
        return subnetList;
    }

    public static List<NetworkInterfaceInfo> getNetSegmentJsonArray() {
        if (networkArrayCache == null) {
            NetworkUtils.refreshNetSegmentJsonArray();
        }
        return networkArrayCache;
    }

    public static void refreshNetSegmentJsonArray() {
        ArrayList<NetworkInterfaceInfo> networkArray = new ArrayList<NetworkInterfaceInfo>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (!networkInterface.isUp()) continue;
                LOG.info("ip type is {} ip displayname is {} ip MTU is {}", networkInterface.getName(), networkInterface.getDisplayName(), networkInterface.getMTU());
                List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                    short networkPrefixLength;
                    InetAddress inetAddress = interfaceAddress.getAddress();
                    String hostAddress = inetAddress.getHostAddress();
                    if ("127.0.0.1".equals(hostAddress) || !IP_V4_FORMAT.matcher(hostAddress).matches() || (networkPrefixLength = interfaceAddress.getNetworkPrefixLength()) < 0) continue;
                    String subnet = hostAddress + "/" + networkPrefixLength;
                    SubnetUtils.SubnetInfo subnetInfo = new SubnetUtils(subnet).getInfo();
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
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        String arrayJson = new Gson().toJson(networkArray);
        LOG.info("network info:{}", (Object)arrayJson);
        networkArrayCache = networkArray;
    }

    public static List<String> getServerIpList() {
        ArrayList<String> ipList = new ArrayList<String>();
        List<NetworkInterfaceInfo> netSegmentArray = NetworkUtils.getNetSegmentJsonArray();
        int j = netSegmentArray.size();
        for (int i = 0; i < j; ++i) {
            NetworkInterfaceInfo segment = netSegmentArray.get(i);
            ipList.add(segment.getHostAddress());
        }
        return ipList;
    }

    public static int findFirstListenPortOnTargetIp(String ip, List<Integer> ports) {
        for (int port : ports) {
            if (!NetworkUtils.checkSocketPort(ip, port)) continue;
            return port;
        }
        return -1;
    }

    public static List<Integer> filterSocketPorts(String ip, List<Integer> ports) {
        return ports.parallelStream().filter(port -> NetworkUtils.checkSocketPort(ip, port)).collect(Collectors.toList());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean checkSocketPort(String ip, int port) {
        try (Socket socket = new Socket();){
            socket.connect(new InetSocketAddress(ip, port), 1000);
            LOG.info("socket connect success ip={} port={}", (Object)ip, (Object)port);
            boolean bl = true;
            return bl;
        }
        catch (Exception ex) {
            LOG.warn("socket connect fail ip={} port={}", (Object)ip, (Object)port);
            return false;
        }
    }

    public static List<String> findAllIpListBelongToTheIpSegment(String lowAddr, String highAddr) {
        ArrayList<String> ipList = new ArrayList<String>();
        String[] subnetArr = lowAddr.split("\\.");
        String[] firstArray = lowAddr.split("\\.");
        String[] endArray = highAddr.split("\\.");
        int firstAddr3 = Integer.parseInt(firstArray[2]);
        int endAddr3 = Integer.parseInt(endArray[2]);
        for (int i = firstAddr3; i <= endAddr3; ++i) {
            int startIp = 1;
            if (i == firstAddr3) {
                startIp = Integer.parseInt(firstArray[3]);
            }
            int endIp = 254;
            if (i == endAddr3) {
                endIp = Integer.parseInt(endArray[3]);
            }
            String subnetPrefix = subnetArr[0] + "." + subnetArr[1] + "." + String.format(Locale.ENGLISH, "%d", i);
            for (int hostId = startIp; hostId <= endIp; ++hostId) {
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
                if (mac == null) continue;
                return NetworkUtils.hexByte(mac[0]) + "-" + NetworkUtils.hexByte(mac[1]) + "-" + NetworkUtils.hexByte(mac[2]) + "-" + NetworkUtils.hexByte(mac[3]) + "-" + NetworkUtils.hexByte(mac[4]) + "-" + NetworkUtils.hexByte(mac[5]);
            }
        }
        catch (SocketException e1) {
            LOG.error(e1.getMessage(), e1);
        }
        return null;
    }

    private static String hexByte(byte b) {
        String s = "000000" + Integer.toHexString(b);
        return s.substring(s.length() - 2);
    }

    public static NetworkInterfaceInfo getFirstMatchNetworkInterfaceJsonInfoByServerIp(String serverIp) {
        List<NetworkInterfaceInfo> netSegmentArray = NetworkUtils.getNetSegmentJsonArray();
        int j = netSegmentArray.size();
        for (int i = 0; i < j; ++i) {
            NetworkInterfaceInfo segment = netSegmentArray.get(i);
            String hostAddress = segment.getHostAddress();
            if (!hostAddress.equals(serverIp)) continue;
            return segment;
        }
        return null;
    }

    public static NetworkInterface findNetworkInterfaceByTargetIp(String ipAddress) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (!networkInterface.isUp()) continue;
                List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                    String subnet;
                    SubnetUtils.SubnetInfo subnetInfo;
                    InetAddress inetAddress = interfaceAddress.getAddress();
                    String hostAddress = inetAddress.getHostAddress();
                    short networkPrefixLength = interfaceAddress.getNetworkPrefixLength();
                    if ("127.0.0.1".equals(hostAddress) || !IP_V4_FORMAT.matcher(hostAddress).matches() || networkPrefixLength < 0 || !(subnetInfo = new SubnetUtils(subnet = hostAddress + "/" + networkPrefixLength).getInfo()).isInRange(ipAddress)) continue;
                    return networkInterface;
                }
            }
        }
        catch (Exception e) {
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
        }
        catch (Exception e1) {
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
        }
        catch (Exception e1) {
            LOG.error(e1.getMessage());
        }
        return false;
    }
}

