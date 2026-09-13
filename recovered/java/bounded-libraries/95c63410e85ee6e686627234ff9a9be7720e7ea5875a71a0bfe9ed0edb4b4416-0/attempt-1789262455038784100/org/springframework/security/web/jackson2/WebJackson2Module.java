/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.databind.Module$SetupContext
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  org.springframework.security.jackson2.SecurityJackson2Modules
 */
package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.jackson2.DefaultCsrfTokenMixin;
import org.springframework.security.web.jackson2.PreAuthenticatedAuthenticationTokenMixin;

public class WebJackson2Module
extends SimpleModule {
    public WebJackson2Module() {
        super(WebJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    public void setupModule(Module.SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping((ObjectMapper)((ObjectMapper)context.getOwner()));
        context.setMixInAnnotations(DefaultCsrfToken.class, DefaultCsrfTokenMixin.class);
        context.setMixInAnnotations(PreAuthenticatedAuthenticationToken.class, PreAuthenticatedAuthenticationTokenMixin.class);
    }
}

