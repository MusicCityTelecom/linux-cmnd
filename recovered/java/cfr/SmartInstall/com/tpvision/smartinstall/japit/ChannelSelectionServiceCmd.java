/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.japit.JapitCommand;
import org.json.JSONObject;

public class ChannelSelectionServiceCmd
extends JapitCommand {
    public ChannelSelectionServiceCmd() {
        this.setCmdFun(JapitCommand.CommandFun.ChannelSelectionServices);
    }

    public static String requestCurrentChannel(String tvid) throws Exception {
        ChannelSelectionServiceCmd cmd = new ChannelSelectionServiceCmd();
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.setCmdType(JapitCommand.CommandType.Request);
        cmd.send(tvid);
        JSONObject channelTuningDetails = cmd.getResponseCmdDetail().optJSONObject("ChannelTuningDetails");
        if (channelTuningDetails != null) {
            return channelTuningDetails.optString("ChannelNumber");
        }
        return null;
    }

    public static void changeCurrentChannel(String tvid, int channelNumber) throws Exception {
        ChannelSelectionServiceCmd cmd = new ChannelSelectionServiceCmd();
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.setCmdType(JapitCommand.CommandType.Change);
        JSONObject channelNumberObject = new JSONObject();
        channelNumberObject.put("ChannelNumber", channelNumber);
        cmd.setCmdDetail("ChannelTuningDetails", channelNumberObject);
        cmd.send(tvid);
    }
}

