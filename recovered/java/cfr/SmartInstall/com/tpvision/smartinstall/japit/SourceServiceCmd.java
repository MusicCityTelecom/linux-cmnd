/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.japit.JapitCommand;
import org.json.JSONObject;

public class SourceServiceCmd
extends JapitCommand {
    public SourceServiceCmd() {
        this.setCmdFun(JapitCommand.CommandFun.SourceService);
    }

    public static String requestSource(String tvid) throws Exception {
        SourceServiceCmd cmd = new SourceServiceCmd();
        cmd.setCmdType(JapitCommand.CommandType.Request);
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.send(tvid);
        JSONObject sourceServiceParameters = cmd.getResponseCmdDetail().optJSONObject("SourceServiceParameters");
        if (sourceServiceParameters != null) {
            return sourceServiceParameters.optString("TunedSource");
        }
        return null;
    }

    public static void changeSource(String tvid, String source) throws Exception {
        SourceServiceCmd cmd = new SourceServiceCmd();
        cmd.setCmdType(JapitCommand.CommandType.Change);
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        JSONObject sourceServiceParameters = new JSONObject();
        sourceServiceParameters.put("TuneToSource", source);
        cmd.setCmdDetail("SourceServiceParameters", sourceServiceParameters);
        cmd.send(tvid);
    }
}

