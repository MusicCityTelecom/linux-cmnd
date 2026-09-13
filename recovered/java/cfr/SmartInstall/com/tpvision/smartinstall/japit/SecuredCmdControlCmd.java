/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.japit.JapitCommand;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

public class SecuredCmdControlCmd
extends JapitCommand {
    public static final String TRAN_TO_SECURED = "Secured";
    public static final String TRAN_TO_NONSECURED = "NonSecured";
    public static final String STATUE_SECURED = "Secured";
    public static final String STATUE_NONSECURED = "NonSecured";
    public static final String STATUE_TRANSITING = "Transiting";
    public static final String CERT_PRESENT = "Present";
    public static final String CERT_NOTPRESENT = "NotPresent";
    public static final String CERT_ROOTCA = "RootCA";
    public static final String CERT_DEVICE = "DeviceCert";
    public static final String CERT_ALL = "All";

    public SecuredCmdControlCmd(JapitCommand.CommandType cmdType, JapitCommand.CommandSvc cmdSvc, JSONObject detail) {
        this.setCmdType(cmdType);
        this.setCmdFun(JapitCommand.CommandFun.SecuredCmdService);
        this.setCmdSvc(cmdSvc);
        this.setCmdDetail(detail);
    }

    public static JSONObject getCommandDetail(String transitCommunicationTo, String serverUrl, String caUrl, String caVersion, String caNamePassword, String secureKeySupport) {
        JSONObject jsObject = new JSONObject();
        jsObject.put("TransitCommunicationTo", transitCommunicationTo);
        jsObject.put("ServerUrl", serverUrl);
        JSONObject caJsObject = new JSONObject();
        caJsObject.put("URL", caUrl);
        caJsObject.put("CACertVersion", caVersion);
        caJsObject.put("CANameAndPassword", caNamePassword);
        if (StringUtils.isNotBlank(secureKeySupport)) {
            caJsObject.put("SecureKeySupport", "true".equalsIgnoreCase(secureKeySupport));
        }
        jsObject.put("ServerToTVCertDetails", caJsObject);
        return jsObject;
    }
}

