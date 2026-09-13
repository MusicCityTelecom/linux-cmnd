/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.japit.JapitCommand;
import org.json.JSONObject;

public class EnablerServiceCmd
extends JapitCommand {
    private boolean isWebListeningServices = true;
    private Boolean applicationControlService;
    private Boolean audioService;
    private Boolean channelSelectionService;
    private Boolean powerService;
    private Boolean sourceService;
    private Boolean mychoiceService;
    private Boolean professionalSettingsService;

    public EnablerServiceCmd(JapitCommand.CommandType cmdType, JapitCommand.CommandSvc cmdSvc) {
        this.setCmdType(cmdType);
        this.setCmdSvc(cmdSvc);
        this.setCmdFun(JapitCommand.CommandFun.EnablerService);
    }

    public static void changeEnabler(String tvid, boolean enable) throws Exception {
        EnablerServiceCmd cmd = new EnablerServiceCmd(JapitCommand.CommandType.Change, JapitCommand.CommandSvc.WebListeningServices);
        if (enable) {
            cmd.enableAll();
        } else {
            cmd.disableAll();
        }
        cmd.setCmdDetail(cmd.getCommandDetails());
        cmd.send(tvid);
    }

    @Override
    public JSONObject getCommandDetails() {
        JSONObject enablerParameters = new JSONObject();
        if (this.applicationControlService != null) {
            enablerParameters.put("ApplicationControlService", this.applicationControlService != false ? "On" : "Off");
        }
        if (this.audioService != null) {
            enablerParameters.put("AudioService", this.audioService != false ? "On" : "Off");
        }
        if (this.channelSelectionService != null) {
            enablerParameters.put("ChannelSelectionService", this.channelSelectionService != false ? "On" : "Off");
        }
        if (this.powerService != null) {
            enablerParameters.put("PowerService", this.powerService != false ? "On" : "Off");
        }
        if (this.sourceService != null) {
            enablerParameters.put("SourceService", this.sourceService != false ? "On" : "Off");
        }
        if (this.mychoiceService != null) {
            enablerParameters.put("MyChoiceService", this.mychoiceService != false ? "On" : "Off");
        }
        if (this.professionalSettingsService != null) {
            enablerParameters.put("ProfessionalSettingsService", this.professionalSettingsService != false ? "On" : "Off");
        }
        JSONObject commandDetails = new JSONObject();
        if (this.isWebListeningServices) {
            commandDetails.put("WebListeningServicesEnablerParameters", enablerParameters);
        } else {
            commandDetails.put("WebServicesEnablerParameters", enablerParameters);
        }
        return commandDetails;
    }

    public void enableAll() {
        this.applicationControlService = true;
        this.audioService = true;
        this.channelSelectionService = true;
        this.powerService = true;
        this.sourceService = true;
        this.mychoiceService = true;
        this.professionalSettingsService = true;
    }

    public void disableAll() {
        this.applicationControlService = false;
        this.audioService = false;
        this.channelSelectionService = false;
        this.powerService = false;
        this.sourceService = false;
        this.mychoiceService = false;
        this.professionalSettingsService = false;
    }

    public void setMychoiceService(Boolean mychoiceService) {
        this.mychoiceService = mychoiceService;
    }

    public void setWebListeningServices(boolean isWebListeningServices) {
        this.isWebListeningServices = isWebListeningServices;
    }

    public void setApplicationControlService(boolean applicationControlService) {
        this.applicationControlService = applicationControlService;
    }

    public void setProfessionalSettingsService(Boolean professionalSettingsService) {
        this.professionalSettingsService = professionalSettingsService;
    }
}

