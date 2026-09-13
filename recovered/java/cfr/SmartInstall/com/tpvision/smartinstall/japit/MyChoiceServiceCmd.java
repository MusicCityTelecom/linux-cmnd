/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.japit.JapitCommand;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyChoiceServiceCmd
extends JapitCommand {
    private String tvUniqueId;
    private JSONArray myChoiceParameters;

    public MyChoiceServiceCmd(String tvUniqueId, JapitCommand.CommandType commandType, JSONArray myChoiceParameters) {
        this.setCmdType(commandType);
        this.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        this.setCmdFun(JapitCommand.CommandFun.MyChoiceService);
        this.setSvcVer("4.0");
        this.tvUniqueId = tvUniqueId;
        this.myChoiceParameters = myChoiceParameters;
    }

    @Override
    public JSONObject getCommandDetails() {
        if (this.getCmdType() == JapitCommand.CommandType.Request) {
            return null;
        }
        JSONObject commandDetails = new JSONObject();
        JSONObject webListeningServicesEnablerParameters = new JSONObject();
        webListeningServicesEnablerParameters.put("TVUniqueID", this.tvUniqueId);
        commandDetails.put("WebListeningServiceParameters", webListeningServicesEnablerParameters);
        if (this.myChoiceParameters != null) {
            commandDetails.put("MyChoiceParameters", this.myChoiceParameters);
        }
        return commandDetails;
    }

    public String getTvUniqueId() {
        return this.tvUniqueId;
    }

    public void setTvUniqueId(String tvUniqueId) {
        this.tvUniqueId = tvUniqueId;
    }

    public JSONArray getMyChoiceParameters() {
        return this.myChoiceParameters;
    }

    public void setMyChoiceParameters(JSONArray myChoiceParameters) {
        this.myChoiceParameters = myChoiceParameters;
    }
}

