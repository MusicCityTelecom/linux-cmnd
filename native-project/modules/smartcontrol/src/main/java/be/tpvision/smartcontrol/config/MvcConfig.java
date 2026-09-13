package be.tpvision.smartcontrol.config;

import be.tpvision.smartcontrol.messages.config.mvc_config.AddViewControllersMessages;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
   @Override
   public void addViewControllers(final ViewControllerRegistry viewControllerRegistry) {
      Assert.notNull(viewControllerRegistry, AddViewControllersMessages.VIEW_CONTROLLER_REGISTRY_CAN_NOT_BE_NULL);
      viewControllerRegistry.addViewController("/devices").setViewName("/index.html");
      viewControllerRegistry.addViewController("/ledDevices").setViewName("/index.html");
   }
}
