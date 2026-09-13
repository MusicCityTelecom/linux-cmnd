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
package org.springframework.security.web.server.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.security.web.server.jackson2.DefaultCsrfServerTokenMixin;

public class WebServerJackson2Module
extends SimpleModule {
    private static final String NAME = WebServerJackson2Module.class.getName();
    private static final Version VERSION = new Version(1, 0, 0, null, null, null);

    public WebServerJackson2Module() {
        super(NAME, VERSION);
    }

    public void setupModule(Module.SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping((ObjectMapper)((ObjectMapper)context.getOwner()));
        context.setMixInAnnotations(DefaultCsrfToken.class, DefaultCsrfServerTokenMixin.class);
    }
}

