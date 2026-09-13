/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.controller;

import be.tpvision.usermanagement.domain.User;
import be.tpvision.usermanagement.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import util.MessageUtilities;

@RestController
@RequestMapping(value={"/api"})
public class ApiController {
    private final UserService userService;

    @Autowired
    public ApiController(UserService userService) {
        Assert.notNull((Object)userService, MessageUtilities.USER_SERVICE_NOT_NULL_MESSAGE);
        this.userService = userService;
    }

    private void assertUserServiceStateNotNull() {
        Assert.state(this.userService != null, MessageUtilities.USER_SERVICE_NOT_NULL_MESSAGE);
    }

    @RequestMapping(value={"/user/updatePassword"}, method={RequestMethod.POST})
    public Map<String, Object> updateUserPassword(String username, String password) {
        this.assertUserServiceStateNotNull();
        User user = this.userService.getUser(username);
        Objects.requireNonNull(password, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
        user.setPassword(password);
        this.userService.updateUser(user, true);
        return this.getSuccessResult();
    }

    @RequestMapping(value={"/user/checkPassword"}, method={RequestMethod.POST})
    public Map<String, Object> checkUserPassword(String username, String password) {
        this.assertUserServiceStateNotNull();
        User user = this.userService.getUser(username);
        Objects.requireNonNull(password, MessageUtilities.USER_PASSWORD_NOT_NULL_MESSAGE);
        String encodedPassword = DigestUtils.md5Hex(password);
        if (user != null && StringUtils.equalsIgnoreCase(encodedPassword, user.getPassword())) {
            return this.getSuccessResult();
        }
        return this.getFailureResult(100, "invalid password");
    }

    private Map<String, Object> getSuccessResult() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("msg", "OK");
        return result;
    }

    private Map<String, Object> getFailureResult(int code, String msg) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }
}

