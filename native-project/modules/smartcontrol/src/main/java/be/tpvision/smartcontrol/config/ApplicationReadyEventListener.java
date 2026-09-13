package be.tpvision.smartcontrol.config;

import be.tpvision.smartcontrol.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ApplicationReadyEventListener {
   private final SettingsService settingsService;

   @Autowired
   public ApplicationReadyEventListener(SettingsService settingsService) {
      Assert.notNull(settingsService, "Settings service can not be null.");
      this.settingsService = settingsService;
   }

   @EventListener
   public void handleApplicationReadyEvent(ApplicationReadyEvent applicationReadyEvent) {
      this.settingsService.autoUpdateServerIp();
   }
}
