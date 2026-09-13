package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import java.util.Arrays;

public class TVDiscoveryCmd extends JapitCommand {
   @Override
   public String generateCommand() {
      return "{  \"Svc\": \"WebListeningServices\",  \"SvcVer\": \"1.0\",  \"Cookie\": "
         + JAPITUtils.getJapitRandomCookieValue()
         + ",  \"CmdType\": \""
         + "Request"
         + "\",  \"Fun\": \""
         + JapitCommand.CommandFun.TVDiscoveryService
         + "\"}";
   }

   public String generateUpdateTVUnqiueIdCommand(String platfromName, String targetUnqiueId) {
      String svcVer;
      String cmdType;
      if (Arrays.asList("TPN141HE_CloneData", "TPN142HE_CloneData").contains(PlatformUtils.getPlatformId(platfromName))) {
         svcVer = "1.0";
         cmdType = "Change";
      } else {
         svcVer = "4.0";
         cmdType = "change";
      }

      return "{\t\"Svc\" : \"WebListeningServices\",\t\"SvcVer\": \""
         + svcVer
         + "\",\t\"Cookie\": "
         + JAPITUtils.getJapitRandomCookieValue()
         + ",\t\"CmdType\" : \""
         + cmdType
         + "\",\t\"Fun\" : \"TVDiscoveryService\",\t\"CommandDetails\" :{\t\"WebListeningServiceParameters\": {\t\t\"TVUniqueID\" : \""
         + targetUnqiueId
         + "\"\t\t}\t}}";
   }
}
