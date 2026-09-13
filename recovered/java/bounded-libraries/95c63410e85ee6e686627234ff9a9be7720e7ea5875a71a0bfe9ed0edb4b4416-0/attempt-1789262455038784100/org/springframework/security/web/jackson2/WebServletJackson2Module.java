/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.databind.Module$SetupContext
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  javax.servlet.http.Cookie
 *  org.springframework.security.jackson2.SecurityJackson2Modules
 */
package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.servlet.http.Cookie;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.jackson2.CookieMixin;
import org.springframework.security.web.jackson2.DefaultSavedRequestMixin;
import org.springframework.security.web.jackson2.SavedCookieMixin;
import org.springframework.security.web.jackson2.WebAuthenticationDetailsMixin;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.SavedCookie;

public class WebServletJackson2Module
extends SimpleModule {
    public WebServletJackson2Module() {
        super(WebServletJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    public void setupModule(Module.SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping((ObjectMapper)((ObjectMapper)context.getOwner()));
        context.setMixInAnnotations(Cookie.class, CookieMixin.class);
        context.setMixInAnnotations(SavedCookie.class, SavedCookieMixin.class);
        context.setMixInAnnotations(DefaultSavedRequest.class, DefaultSavedRequestMixin.class);
        context.setMixInAnnotations(WebAuthenticationDetails.class, WebAuthenticationDetailsMixin.class);
    }
}

