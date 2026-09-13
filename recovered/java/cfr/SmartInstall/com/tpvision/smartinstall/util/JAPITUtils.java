/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.IReceiver;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.HttpUtils;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.TpvNamedThreadFactory;
import com.tpvision.smartinstall.util.TpvRunableTask;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAPITUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JAPITUtils.class);
    public static final String request = "Request";
    public static final String RESPONSE = "Response";
    public static final String CHANGE = "Change";
    public static final String WEB_SERVICE = "WebServices";
    public static final String WEB_LISTENING_SERVICE = "WebListeningServices";
    public static final String SvcVer_2 = "3.0";
    public static final String SvcVer_1 = "1.0";
    public static final String JAPIT_ERROR_EMPTY_RESPONSE = "japit return error,not a valid json string";
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 300, 50000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000), new TpvNamedThreadFactory("japit-pool"));

    public static boolean isJapitListening(Devices devices) {
        return JAPITUtils.getValidJapitPort(devices) != -1;
    }

    public static int getValidJapitPort(Devices devices) {
        ArrayList<Integer> possiblePortList = new ArrayList<Integer>();
        if (devices.isSecureHttpJapit()) {
            possiblePortList.addAll(CommonConstants.TV_WIXP_HTTPS_PORTS);
            possiblePortList.addAll(CommonConstants.TV_WIXP_HTTP_PORTS);
        } else {
            possiblePortList.addAll(CommonConstants.TV_WIXP_HTTP_PORTS);
            possiblePortList.addAll(CommonConstants.TV_WIXP_HTTPS_PORTS);
        }
        if (devices.getWlsPort() != 0 && !possiblePortList.contains(devices.getWlsPort())) {
            possiblePortList.add(devices.getWlsPort());
        }
        if (devices.getWlsSecurePort() != 0 && !possiblePortList.contains(devices.getWlsSecurePort())) {
            possiblePortList.add(devices.getWlsSecurePort());
        }
        return NetworkUtils.findFirstListenPortOnTargetIp(devices.getTvipaddress(), possiblePortList);
    }

    public static String getServerRequestPath(Devices tv) {
        if (HttpUtils.isSupportHttpsByUniqueId(tv.getTvuniqueid())) {
            return String.format(Locale.ENGLISH, "https://%s:%d/SmartInstall/", NetworkUtils.getServerIpFromSameRoute(tv.getTvipaddress()), CommonConstants.CMND_HTTPS_PORT);
        }
        return String.format(Locale.ENGLISH, "http://%s:%d/SmartInstall/", NetworkUtils.getServerIpFromSameRoute(tv.getTvipaddress()), CommonConstants.CMND_HTTP_PORT);
    }

    public static String sendJapitCommandToTV(String tvIp, int port, boolean bHttps, String data, int timeout) throws Exception {
        String strUrl = HttpUtils.getHttpString(bHttps) + tvIp + ":" + port + "/WIXP";
        List<Devices> deviceList = JpaManager.getDevicesManager().findDevicesByTvipaddressAndPowerStatus(tvIp, "ON");
        if (!deviceList.isEmpty() && "Transiting".equalsIgnoreCase(deviceList.get(0).getSecureCmdSupport())) {
            throw new Exception("unable to send request to the TV(" + deviceList.get(0).getTvuniqueid() + ")which is still transition status.");
        }
        return HttpUtils.send(bHttps, true, strUrl, data, timeout);
    }

    public static void sendAsyncCommand(Devices device, String data) {
        JAPITUtils.sendAsyncCommandWithDelay(device, data, 0L, null);
    }

    public static void sendAsyncCommand(Devices device, String data, IReceiver receiver) {
        JAPITUtils.sendAsyncCommandWithDelay(device, data, 0L, receiver);
    }

    public static void sendAsyncCommandWithDelay(Devices device, String data, long delayTime, IReceiver receiver) {
        executor.submit(new SendCommandTask(device, data, receiver, delayTime));
    }

    public static String sendJapitCommand(Devices device, String data) throws Exception {
        return JAPITUtils.sendJapitCommand(device, data, 2000);
    }

    public static String sendJapitCommand(Devices device, String data, int timeout) throws Exception {
        if ("Transiting".equalsIgnoreCase(device.getSecureCmdSupport())) {
            throw new Exception("unable to send request to the TV(" + device.getTvuniqueid() + ")which is still transition status.");
        }
        try {
            return JAPITUtils.sendJapitCommandToTV(device.getTvipaddress(), HttpUtils.getWlsRequestPortByDevice(device), HttpUtils.isSupportHttpsByDevice(device), data, timeout);
        }
        catch (ConnectException | SocketTimeoutException ex) {
            if (ex.getMessage().contains("Connection refused") || ex.getMessage().contains("Connect timed out")) {
                LOG.warn("connect failure, give one more retry on valid port", ex);
                int validPort = JAPITUtils.getValidJapitPort(device);
                if (validPort != -1) {
                    boolean isSecureMode = device.getWlsSecurePort() == validPort || CommonConstants.TV_WIXP_HTTPS_PORTS.contains(validPort);
                    String result = JAPITUtils.sendJapitCommandToTV(device.getTvipaddress(), validPort, isSecureMode, data, timeout);
                    device.setSecureCmdSupport(String.valueOf(isSecureMode));
                    JpaManager.getDevicesManager().save(device);
                    return result;
                }
            }
            throw ex;
        }
    }

    public static int getJapitRandomCookieValue() {
        return RandomUtils.nextInt(0, 99999);
    }

    private static class SendCommandTask
    extends TpvRunableTask {
        private Devices device;
        private String command = "";
        private IReceiver receiver;
        private long delayTime;

        public SendCommandTask(Devices device, String command, IReceiver receiver, long delayTime) {
            this.device = device;
            this.command = command;
            this.receiver = receiver;
            this.delayTime = delayTime;
        }

        @Override
        public void execute() {
            block7: {
                try {
                    if (this.delayTime > 0L) {
                        Thread.sleep(this.delayTime);
                        this.device = JpaManager.getDevicesManager().loadByKey(this.device.getId());
                    }
                    String resData = JAPITUtils.sendJapitCommand(this.device, this.command, 30000);
                    if (this.receiver != null) {
                        if (!TpvStringUtils.isJSONString(resData)) {
                            throw new RuntimeException(JAPITUtils.JAPIT_ERROR_EMPTY_RESPONSE);
                        }
                        JSONObject jsonObject = new JSONObject(resData);
                        JSONObject commandDetails = jsonObject.optJSONObject("CommandDetails");
                        if (commandDetails == null) {
                            throw new RuntimeException("japit return result error, not contains command details data");
                        }
                        this.receiver.receiveResponse(true, commandDetails);
                    }
                }
                catch (Exception ex) {
                    LOG.warn(ex.getMessage(), ex);
                    if (this.receiver != null) {
                        JSONObject errorJSON = new JSONObject();
                        errorJSON.put("errMsg", ex.getMessage());
                        this.receiver.receiveResponse(false, errorJSON);
                    }
                    if (!(ex instanceof SocketTimeoutException) || !this.command.contains("PowerService")) break block7;
                    this.updateDeviceStatus();
                }
            }
        }

        private void updateDeviceStatus() {
            DevicesManager devicesManager = JpaManager.getDevicesManager();
            this.device.setPowerstatus("offline");
            devicesManager.save(this.device);
        }
    }

    public static enum WebServiceType {
        WebServices,
        WebListeningServices;

    }
}

