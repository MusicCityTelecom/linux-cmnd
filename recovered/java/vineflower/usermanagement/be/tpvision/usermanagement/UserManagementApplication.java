package be.tpvision.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class UserManagementApplication extends SpringBootServletInitializer {
   @Override
   protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
      return application.sources(UserManagementApplication.class);
   }

   public static void main(String[] args) {
      SpringApplication.run(UserManagementApplication.class, args);
   }
}
