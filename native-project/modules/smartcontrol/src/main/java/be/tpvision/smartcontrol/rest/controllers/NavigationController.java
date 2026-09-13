package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.messages.controllers.navigation.ConstructorMessages;
import be.tpvision.smartcontrol.rest.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/navigation")
public class NavigationController {
   private final String navigationBarUrl;

   @Autowired
   public NavigationController(@Value("${smartcontrol.navigation.bar.url}") final String navigationBarUrl) {
      Assert.notNull(navigationBarUrl, ConstructorMessages.NAVIGATION_BAR_URL_CAN_NOT_BE_NULL);
      this.navigationBarUrl = navigationBarUrl;
   }

   @GetMapping
   public ResponseWrapper<String> getNavigationBarUrl() {
      return new ResponseWrapper<>(this.navigationBarUrl);
   }
}
