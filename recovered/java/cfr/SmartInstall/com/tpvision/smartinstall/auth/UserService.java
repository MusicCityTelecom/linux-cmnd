/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.auth;

import com.tpvision.smartinstall.util.Configs;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service(value="userService")
public class UserService {
    public Map<String, Object> getUserByUsername(String username) {
        HashMap<String, Object> userMap = null;
        if (username.equals("admin") || username.equals("user")) {
            userMap = new HashMap<String, Object>();
            userMap.put("username", "admin");
            userMap.put("password", Configs.getProperty("security.pwd"));
            userMap.put("role", username.equals("admin") ? "admin" : "user");
            return userMap;
        }
        return null;
    }
}

