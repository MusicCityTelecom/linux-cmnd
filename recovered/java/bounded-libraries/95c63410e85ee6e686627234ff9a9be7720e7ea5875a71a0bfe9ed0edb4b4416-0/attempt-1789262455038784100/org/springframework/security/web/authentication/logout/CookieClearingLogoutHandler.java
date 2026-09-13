/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.authentication.logout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class CookieClearingLogoutHandler
implements LogoutHandler {
    private final List<Function<HttpServletRequest, Cookie>> cookiesToClear;

    public CookieClearingLogoutHandler(String ... cookiesToClear) {
        Assert.notNull((Object)cookiesToClear, (String)"List of cookies cannot be null");
        ArrayList<Function<HttpServletRequest, Cookie>> cookieList = new ArrayList<Function<HttpServletRequest, Cookie>>();
        for (String cookieName : cookiesToClear) {
            cookieList.add(request -> {
                Cookie cookie = new Cookie(cookieName, null);
                String contextPath = request.getContextPath();
                String cookiePath = StringUtils.hasText((String)contextPath) ? contextPath : "/";
                cookie.setPath(cookiePath);
                cookie.setMaxAge(0);
                cookie.setSecure(request.isSecure());
                return cookie;
            });
        }
        this.cookiesToClear = cookieList;
    }

    public CookieClearingLogoutHandler(Cookie ... cookiesToClear) {
        Assert.notNull((Object)cookiesToClear, (String)"List of cookies cannot be null");
        ArrayList<Function<HttpServletRequest, Cookie>> cookieList = new ArrayList<Function<HttpServletRequest, Cookie>>();
        for (Cookie cookie : cookiesToClear) {
            Assert.isTrue((cookie.getMaxAge() == 0 ? 1 : 0) != 0, (String)"Cookie maxAge must be 0");
            cookieList.add(request -> cookie);
        }
        this.cookiesToClear = cookieList;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        this.cookiesToClear.forEach(f -> response.addCookie((Cookie)f.apply(request)));
    }
}

