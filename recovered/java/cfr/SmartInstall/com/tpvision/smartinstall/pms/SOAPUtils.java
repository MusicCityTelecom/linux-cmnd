/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.pms;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SOAPUtils {
    private static final Logger LOG = LoggerFactory.getLogger(SOAPUtils.class);
    private static final String SOAP_BODY = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">    <soap:Body>%s    </soap:Body></soap:Envelope>";
    private String site;
    private String key;
    private String tmsUrl;

    public SOAPUtils(String site, String key, String url) {
        this.site = site;
        this.key = key;
        this.tmsUrl = url;
    }

    private static String escape(String src) {
        return src.replace("<", "&lt;").replace(">", "&gt;");
    }

    private static String getRequestMessage(SOAPAction soapAction, String msg) {
        String msgTpl = "<%s xmlns=\"http://tigergenericinterface.org/\"><XMLString>%s</XMLString></%s>";
        String requestMsg = String.format(Locale.ENGLISH, msgTpl, soapAction.name(), SOAPUtils.escape(msg), soapAction.name());
        return String.format(Locale.ENGLISH, SOAP_BODY, requestMsg);
    }

    public static String getResponseMessage(String msg) {
        String responseMsg = "<SendMessageToExternalInterfaceResponse xmlns=\"http://tigergenericinterface.org/\"><SendMessageToExternalInterfaceResult>" + msg + "</SendMessageToExternalInterfaceResult></SendMessageToExternalInterfaceResponse>";
        return String.format(Locale.ENGLISH, SOAP_BODY, responseMsg);
    }

    public String requestBillMsg(String roomId) {
        String msgTpl = "<?xml version=\"1.0\" encoding=\"utf-8\"?><requestbill resno=\"1000TD\" ><site>%s</site><room>%s</room><wsuserkey>%s</wsuserkey></requestbill>";
        String msg = String.format(Locale.ENGLISH, msgTpl, this.site, roomId, this.key);
        return SOAPUtils.getRequestMessage(SOAPAction.requestBill, msg);
    }

    public String requestRefreshMsg(String roomId) {
        String msgTpl = "<?xml version=\"1.0\" encoding=\"utf-8\"?><requestrefresh resno=\"1000TD\" ><site>%s</site><room>%s</room><wsuserkey>%s</wsuserkey></requestrefresh>";
        String msg = String.format(Locale.ENGLISH, msgTpl, this.site, roomId, this.key);
        return SOAPUtils.getRequestMessage(SOAPAction.requestRefresh, msg);
    }

    public String requestCheckoutMsg(String roomId, String balance) {
        String msgTpl = "<?xml version=\"1.0\" encoding=\"utf-8\"?><requestcheckout resno=\"1000TD\"><site>%s</site><room>%s</room><balance>%s</balance><wsuserkey>%s</wsuserkey></requestcheckout>";
        String msg = String.format(Locale.ENGLISH, msgTpl, this.site, roomId, balance, this.key);
        return SOAPUtils.getRequestMessage(SOAPAction.requestCheckout, msg);
    }

    public String requestDeleteMessage(String roomId, String msgId) {
        String msgTpl = "<?xml version=\"1.0\" encoding=\"utf-8\"?><deletemessagetext resno=\"1000TD\"><site>%s</site><room>%s</room><msgid>%s</msgid><wsuserkey>%s</wsuserkey></deletemessagetext>";
        String msg = String.format(Locale.ENGLISH, msgTpl, this.site, roomId, msgId, this.key);
        return SOAPUtils.getRequestMessage(SOAPAction.deleteMessageText, msg);
    }

    public String responseWakeupStatus(String roomId, String resno, String date, String time, String status) {
        String msgTpl = "<?xml version=\"1.0\" encoding=\"utf-8\"?><wakeupstatusresults resno=\"%s\"><site>%s</site><room>%s</room><wakeupdate>%s</wakeupdate><wakeuptime>%s</wakeuptime><status>%s</status><wsuserkey>%s</wsuserkey></wakeupstatusresults>";
        String msg = String.format(Locale.ENGLISH, msgTpl, resno, this.site, roomId, date, time, status, this.key);
        return SOAPUtils.getRequestMessage(SOAPAction.wakeupStatus, msg);
    }

    public String sendCommand(String data, String soapAction) throws IOException {
        URL url = new URL(this.tmsUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        if (data != null) {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));
            connection.setRequestProperty("SOAPAction", "http://tigergenericinterface.org/" + soapAction);
            connection.setRequestProperty("Expect", "100-continue");
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream());){
                wr.writeBytes(data);
                wr.flush();
            }
        }
        int responseCode = connection.getResponseCode();
        LOG.info("Response Code:{} ", (Object)responseCode);
        if (responseCode != 200) {
            throw new IOException("Http return code: " + responseCode);
        }
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));){
            String line;
            StringBuilder res = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                res.append(line);
            }
            String string = res.toString();
            return string;
        }
    }

    public static enum SOAPAction {
        requestRefresh,
        requestBill,
        requestCheckout,
        deleteMessageText,
        wakeupStatus;

    }
}

