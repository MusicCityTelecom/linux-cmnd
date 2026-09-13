package com.tpvision.smartinstall.auth;

import com.tpvision.smartinstall.util.Configs;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {
   public Map<String, Object> getUserByUsername(String username) {
      Map<String, Object> userMap = null;
      if (!username.equals("admin") && !username.equals("user")) {
         return null;
      }

      userMap = new HashMap<>();
      userMap.put("username", "admin");
      userMap.put("password", Configs.getProperty("security.pwd"));
      userMap.put("role", username.equals("admin") ? "admin" : "user");
      return userMap;
   }
}
