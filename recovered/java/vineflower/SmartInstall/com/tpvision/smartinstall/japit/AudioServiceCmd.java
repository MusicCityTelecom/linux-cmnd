package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.util.JAPITUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class AudioServiceCmd extends JapitCommand {
   private List<String> audioControlParameters = new ArrayList<>();
   public static final String AUDIO_MUTE = "AudioMute";
   public static final String VOLUME = "Volume";
   public static final String COMMAND_DETAILS = "CommandDetails";
   public static final String INDIVIDUAL_AUDIO_PARAMS = "IndividualAudioOutDestinationControlParameters";
   public static final String AUDIO_OUT_DEST = "AudioOutDestination";
   public static final String AUDIO_CONTROL_PARA = "AudioControlParameters";
   private static final String[] DESTTINATION_PRIORITY = new String[]{
      "TVSpeakers", "HDMI_ARCSpeaker", "HeadphoneWired", "HeadphoneBluetooth", "BluetoothSpeaker", "BathroomSpeakers"
   };

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
         param.put("AudioOutDestination", "All");
         param.put(key, value);
         controlParams.put(param);
         cmdDetails.put("IndividualAudioOutDestinationControlParameters", controlParams);
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
         param.put("AudioOutDestination", "All");
         controlParams.put(param);
         cmdDetails.put("AudioControlParameters", controlParams);
      } else {
         controlParams.put(key);
         cmdDetails.put("AudioControlParameters", controlParams);
      }

      return cmdDetails;
   }

   public static String getPreferredAudioValue(JSONObject cmdDetails, String key) {
      JSONArray arr = cmdDetails.getJSONArray("IndividualAudioOutDestinationControlParameters");
      String value = null;

      for (String dest : DESTTINATION_PRIORITY) {
         for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            if (dest.equals(obj.optString("AudioOutDestination")) && "Yes".equalsIgnoreCase(obj.optString("Controllability"))) {
               if (key.equalsIgnoreCase("Volume")) {
                  value = String.valueOf(obj.optInt(key, 0));
               } else {
                  value = obj.optString(key);
               }

               return value;
            }
         }
      }

      return value;
   }

   private static String decodeAudioValue(JSONObject cmdDetails, String type, String key) {
      String value = null;
      if (type.equalsIgnoreCase("TPM242HWW")) {
         value = getPreferredAudioValue(cmdDetails, key);
      } else if (key.equalsIgnoreCase("Volume")) {
         value = String.valueOf(cmdDetails.optInt("Volume"));
      } else {
         value = cmdDetails.optString(key);
      }

      return value;
   }

   public static String requestAudioMute(String tvid, String type) throws Exception {
      AudioServiceCmd cmd = new AudioServiceCmd();
      cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
      cmd.setCmdType(JapitCommand.CommandType.Request);
      JSONObject cmdDetails = buildRequestAudioCommand(type, "AudioMute");
      cmd.setCmdDetail(cmdDetails);
      cmd.send(tvid);
      return decodeAudioValue(cmd.getResponseCmdDetail(), type, "AudioMute");
   }

   public static String requestAudioVolume(String tvid, String type) throws Exception {
      AudioServiceCmd cmd = new AudioServiceCmd();
      cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
      cmd.setCmdType(JapitCommand.CommandType.Request);
      JSONObject cmdDetails = buildRequestAudioCommand(type, "Volume");
      cmd.setCmdDetail(cmdDetails);
      cmd.send(tvid);
      return decodeAudioValue(cmd.getResponseCmdDetail(), type, "Volume");
   }

   public static void changeAudioMute(String tvid, String type, String mute) throws Exception {
      AudioServiceCmd cmd = new AudioServiceCmd();
      cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
      cmd.setCmdType(JapitCommand.CommandType.Change);
      JSONObject cmdDetails = buildChangeAudioCommand(type, "AudioMute", mute);
      cmd.setCmdDetail(cmdDetails);
      cmd.send(tvid);
   }

   public static void changeAudioVolume(String tvid, String type, String volume) throws Exception {
      AudioServiceCmd cmd = new AudioServiceCmd();
      cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
      cmd.setCmdType(JapitCommand.CommandType.Change);
      JSONObject cmdDetails = buildChangeAudioCommand(type, "Volume", volume);
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
