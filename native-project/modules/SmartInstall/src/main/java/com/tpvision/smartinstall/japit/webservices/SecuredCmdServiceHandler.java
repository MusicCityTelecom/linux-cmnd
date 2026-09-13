package com.tpvision.smartinstall.japit.webservices;

import com.tpvision.smartinstall.japit.SecuredCmdControlManager;

public class SecuredCmdServiceHandler extends WebServiceCommandHandler {
   @Override
   public String execute() {
      return SecuredCmdControlManager.parseReceiveSecuredCmdControl(true, this.commandDetails, this.device);
   }
}
