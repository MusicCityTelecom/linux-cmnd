package be.tpvision.smartcontrol;

import java.security.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SmartControlApplication extends SpringBootServletInitializer {
   @Override
   protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
      return application.sources(SmartControlApplication.class);
   }

   public static void main(String[] args) {
      SpringApplication.run(SmartControlApplication.class, args);
   }

   @RequestMapping("/user")
   public Principal user(final Principal user) {
      return user;
   }
}
