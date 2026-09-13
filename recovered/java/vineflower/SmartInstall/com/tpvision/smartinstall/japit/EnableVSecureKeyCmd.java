package com.tpvision.smartinstall.japit;

import org.json.JSONObject;

public class EnableVSecureKeyCmd extends JapitCommand {
   public EnableVSecureKeyCmd(JapitCommand.CommandType cmdType, JapitCommand.CommandSvc cmdSvc) {
      this.setCmdType(cmdType);
      this.setCmdFun(JapitCommand.CommandFun.EnablerService);
      this.setCmdSvc(cmdSvc);
      this.setCmdDetail(this.getCommandDetail());
   }

   private JSONObject getCommandDetail() {
      JSONObject jsonParameters = new JSONObject();
      jsonParameters.put("ContentSecurityService", "On");
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("WebListeningServicesEnablerParameters", jsonParameters);
      return jsonObject;
   }
}
