/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.japit.JapitCommand;
import com.tpvision.smartinstall.util.JAPITUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class AudioServiceCmd
extends JapitCommand {
    private List<String> audioControlParameters = new ArrayList<String>();
    public static final String AUDIO_MUTE = "AudioMute";
    public static final String VOLUME = "Volume";
    public static final String COMMAND_DETAILS = "CommandDetails";
    public static final String INDIVIDUAL_AUDIO_PARAMS = "IndividualAudioOutDestinationControlParameters";
    public static final String AUDIO_OUT_DEST = "AudioOutDestination";
    public static final String AUDIO_CONTROL_PARA = "AudioControlParameters";
    private static final String[] DESTTINATION_PRIORITY = new String[]{"TVSpeakers", "HDMI_ARCSpeaker", "HeadphoneWired", "HeadphoneBluetooth", "BluetoothSpeaker", "BathroomSpeakers"};

    public AudioServiceCmd() {
        this.setCmdFun(JapitCommand.CommandFun.AudioService);
        this.setCookie(JAPITUtils.getJapitRandomCookieValue());
        this.setSvcVer("4.0");
    }

    private static JSONObject buildChangeAudioCommand(String type, String key, Object value) {
        JSONObject cmdDetails = new JSONObject();
        if (type.equalsIgnoreCase("TPM242HWW")) {
            JSONArray controlParams = new JSONArray();
            JSONObject param = new JSONObject();
            param.put(AUDIO_OUT_DEST, "All");
            param.put(key, value);
            controlParams.put(param);
            cmdDetails.put(INDIVIDUAL_AUDIO_PARAMS, controlParams);
        } else {
            cmdDetails.put(key, value);
        }
        return cmdDetails;
    }

    private static JSONObject buildRequestAudioCommand(String type, String key) {
        JSONObject cmdDetails = new JSONObject();
        JSONArray controlParams = new JSONArray();
        if (type.equalsIgnoreCase("TPM242HWW")) {
            JSONObject param = new JSONObject();
            param.put(AUDIO_OUT_DEST, "All");
            controlParams.put(param);
            cmdDetails.put(AUDIO_CONTROL_PARA, controlParams);
        } else {
            controlParams.put(key);
            cmdDetails.put(AUDIO_CONTROL_PARA, controlParams);
        }
        return cmdDetails;
    }

    public static String getPreferredAudioValue(JSONObject cmdDetails, String key) {
        JSONArray arr = cmdDetails.getJSONArray(INDIVIDUAL_AUDIO_PARAMS);
        String value = null;
        for (String dest : DESTTINATION_PRIORITY) {
            for (int i = 0; i < arr.length(); ++i) {
                JSONObject obj = arr.getJSONObject(i);
                if (!dest.equals(obj.optString(AUDIO_OUT_DEST)) || !"Yes".equalsIgnoreCase(obj.optString("Controllability"))) continue;
                value = key.equalsIgnoreCase(VOLUME) ? String.valueOf(obj.optInt(key, 0)) : obj.optString(key);
                return value;
            }
        }
        return value;
    }

    private static String decodeAudioValue(JSONObject cmdDetails, String type, String key) {
        String value = null;
        value = type.equalsIgnoreCase("TPM242HWW") ? AudioServiceCmd.getPreferredAudioValue(cmdDetails, key) : (key.equalsIgnoreCase(VOLUME) ? String.valueOf(cmdDetails.optInt(VOLUME)) : cmdDetails.optString(key));
        return value;
    }

    public static String requestAudioMute(String tvid, String type) throws Exception {
        AudioServiceCmd cmd = new AudioServiceCmd();
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.setCmdType(JapitCommand.CommandType.Request);
        JSONObject cmdDetails = AudioServiceCmd.buildRequestAudioCommand(type, AUDIO_MUTE);
        cmd.setCmdDetail(cmdDetails);
        cmd.send(tvid);
        return AudioServiceCmd.decodeAudioValue(cmd.getResponseCmdDetail(), type, AUDIO_MUTE);
    }

    public static String requestAudioVolume(String tvid, String type) throws Exception {
        AudioServiceCmd cmd = new AudioServiceCmd();
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.setCmdType(JapitCommand.CommandType.Request);
        JSONObject cmdDetails = AudioServiceCmd.buildRequestAudioCommand(type, VOLUME);
        cmd.setCmdDetail(cmdDetails);
        cmd.send(tvid);
        return AudioServiceCmd.decodeAudioValue(cmd.getResponseCmdDetail(), type, VOLUME);
    }

    public static void changeAudioMute(String tvid, String type, String mute) throws Exception {
        AudioServiceCmd cmd = new AudioServiceCmd();
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.setCmdType(JapitCommand.CommandType.Change);
        JSONObject cmdDetails = AudioServiceCmd.buildChangeAudioCommand(type, AUDIO_MUTE, mute);
        cmd.setCmdDetail(cmdDetails);
        cmd.send(tvid);
    }

    public static void changeAudioVolume(String tvid, String type, String volume) throws Exception {
        AudioServiceCmd cmd = new AudioServiceCmd();
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.setCmdType(JapitCommand.CommandType.Change);
        JSONObject cmdDetails = AudioServiceCmd.buildChangeAudioCommand(type, VOLUME, volume);
        cmd.setCmdDetail(cmdDetails);
        cmd.send(tvid);
    }

    public List<String> getAudioControlParameters() {
        return this.audioControlParameters;
    }

    public void setAudioControlParameters(List<String> audioControlParameters) {
        this.audioControlParameters = audioControlParameters;
    }
}

