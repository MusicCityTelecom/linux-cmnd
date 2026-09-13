/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.japit.JapitCommand;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetVSecureKeyCmd
extends JapitCommand {
    public GetVSecureKeyCmd(JapitCommand.CommandType cmdType, JapitCommand.CommandSvc cmdSvc) {
        this.setCmdType(cmdType);
        this.setCmdFun(JapitCommand.CommandFun.ContentSecurityService);
        this.setCmdSvc(cmdSvc);
        this.setCmdDetail(this.getCommandDetail());
    }

    private JSONObject getCommandDetail() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("VSecureKeyStatus");
        jsonArray.put("VSecureTVData");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("VSecureStatus", jsonArray);
        return jsonObject;
    }
}

