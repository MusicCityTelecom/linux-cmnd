package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.messages.controllers.version.GetVersionMessages;
import be.tpvision.smartcontrol.rest.ResponseWrapper;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/version")
public class VersionController {
   @GetMapping
   public ResponseWrapper<String> getVersion() {
      Class<? extends VersionController> theClass = (Class<? extends VersionController>)this.getClass();
      Assert.state(theClass != null, GetVersionMessages.THE_CLASS_CAN_NOT_BE_NULL);
      Package thePackage = theClass.getPackage();
      Assert.state(thePackage != null, GetVersionMessages.THE_PACKAGE_CAN_NOT_BE_NULL);
      String implementationVersion = thePackage.getImplementationVersion();
      if (implementationVersion == null) {
         implementationVersion = "(version will be shown here when packaged)";
      }

      return new ResponseWrapper<>(implementationVersion);
   }
}
