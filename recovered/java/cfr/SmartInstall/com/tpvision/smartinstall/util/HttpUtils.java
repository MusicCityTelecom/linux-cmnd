/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.SecuredCmdControlManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.Configs;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
    private static final int TV_SOCKET_READ_TIMEOUT = 20000;
    private static TrustManager ignoreCertificationTrustManger = new X509TrustManager(){
        private X509Certificate[] certificates;

        @Override
        public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
            }
        }

        @Override
        public void checkServerTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    private HttpUtils() {
    }

    public static String getServerUrlByRequest(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    public static String getUserManageLocalRequestAddress() {
        return "http://" + Configs.getProperty("server.name") + ":" + Configs.getProperty("server.port") + "/usermanagement";
    }

    public static boolean isSupportHttpsByDevice(Devices device) {
        if (device == null) {
            return false;
        }
        return "true".equalsIgnoreCase(device.getSecureCmdSupport());
    }

    public static int getWlsRequestPortByDevice(Devices device) {
        if ("true".equalsIgnoreCase(device.getSecureCmdSupport())) {
            return device.getWlsSecurePort();
        }
        return device.getWlsPort();
    }

    public static boolean isSupportHttpsByUniqueId(String tvUniqueId) {
        DevicesManager iptvmanager = JpaManager.getDevicesManager();
        Devices iptv2 = iptvmanager.loadByKey(tvUniqueId);
        if (iptv2 == null) {
            return false;
        }
        return HttpUtils.isSupportHttpsByDevice(iptv2);
    }

    public static boolean isSupportHttpsByIp(String ip) {
        DevicesManager iptvmanager = JpaManager.getDevicesManager();
        List<Devices> iptv2 = iptvmanager.findDevicesByTvipaddress(ip);
        if (iptv2.isEmpty()) {
            return false;
        }
        if (iptv2.size() == 1 && "true".equalsIgnoreCase(iptv2.get(0).getSecureCmdSupport())) {
            return true;
        }
        for (int i = 0; i < iptv2.size(); ++i) {
            if (!"Standby".equalsIgnoreCase(iptv2.get(i).getPowerstatus()) && !"On".equalsIgnoreCase(iptv2.get(i).getPowerstatus()) || !"true".equalsIgnoreCase(iptv2.get(i).getSecureCmdSupport())) continue;
            return true;
        }
        return false;
    }

    public static String getHttpString(boolean bHttps) {
        if (bHttps) {
            return "https://";
        }
        return "http://";
    }

    public static int getCMNDPort(boolean bHttps) {
        if (bHttps) {
            return CommonConstants.CMND_HTTPS_PORT;
        }
        return CommonConstants.CMND_HTTP_PORT;
    }

    public static String send(boolean bHttps, boolean bPost, String strUrl, String data, int timeout) throws Exception {
        String resData = "";
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HttpURLConnection connection = null;
        if (strUrl == null || strUrl.length() <= 0 || bPost && (data == null || data.length() <= 0)) {
            LOG.error("parameter error.");
            throw new Exception("URL or Post data is null");
        }
        URL url = new URL(strUrl);
        try {
            Throwable throwable;
            LOG.info("SI->TV tvip={} send={} schema={} port={}", url.getHost(), data, url.getProtocol(), url.getPort());
            if (bHttps) {
                connection = (HttpsURLConnection)url.openConnection();
                if (connection == null) {
                    LOG.error("https connection is null.");
                    throw new Exception("openConnection fail.");
                }
                TrustManager[] tm = new TrustManager[]{ignoreCertificationTrustManger};
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new SecureRandom());
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                ((HttpsURLConnection)connection).setSSLSocketFactory(ssf);
            } else {
                connection = (HttpURLConnection)url.openConnection();
                if (connection == null) {
                    LOG.error("http connection is null.");
                    throw new Exception("connection is null.");
                }
            }
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(20000);
            if (bPost) {
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");
                if (bHttps) {
                    connection.setRequestProperty("Security-Token", SecuredCmdControlManager.generateRequestTokenFromPostData(data));
                }
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                throwable = null;
                try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream());){
                    wr.writeBytes(data);
                    wr.flush();
                }
                catch (Throwable ssf) {
                    throwable = ssf;
                    throw ssf;
                }
            } else {
                connection.setRequestMethod("GET");
            }
            if (connection.getResponseCode() != 200) {
                LOG.error("Connection Failed ResponseCode {}", (Object)connection.getResponseCode());
                throw new Exception("Connection Failed ResponseCode" + connection.getResponseCode());
            }
            throwable = null;
            try (InputStream is = connection.getInputStream();
                 BufferedReader rd = new BufferedReader(new InputStreamReader(is));){
                String line;
                StringBuilder res = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    res.append(line);
                }
                resData = res.toString();
                stopWatch.stop();
                LOG.info("TV->SI tvip={} success={} schema={} port={} cost={}ms", url.getHost(), resData, url.getProtocol(), url.getPort(), stopWatch.getTime());
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
        }
        catch (Exception ex) {
            stopWatch.stop();
            LOG.error("TV->SI tvip={} failed={} schema={} port={} cost={}ms", url.getHost(), ex.getMessage(), url.getProtocol(), url.getPort(), stopWatch.getTime());
            throw ex;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return resData;
    }
}

