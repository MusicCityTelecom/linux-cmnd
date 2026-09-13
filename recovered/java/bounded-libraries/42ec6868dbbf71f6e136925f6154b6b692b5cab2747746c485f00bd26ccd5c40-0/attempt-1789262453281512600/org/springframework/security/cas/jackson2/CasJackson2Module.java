/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.Version
 *  com.fasterxml.jackson.databind.Module$SetupContext
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  org.jasig.cas.client.authentication.AttributePrincipalImpl
 *  org.jasig.cas.client.validation.AssertionImpl
 *  org.springframework.security.jackson2.SecurityJackson2Modules
 */
package org.springframework.security.cas.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.jasig.cas.client.validation.AssertionImpl;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.jackson2.AssertionImplMixin;
import org.springframework.security.cas.jackson2.AttributePrincipalImplMixin;
import org.springframework.security.cas.jackson2.CasAuthenticationTokenMixin;
import org.springframework.security.jackson2.SecurityJackson2Modules;

public class CasJackson2Module
extends SimpleModule {
    public CasJackson2Module() {
        super(CasJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    public void setupModule(Module.SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping((ObjectMapper)((ObjectMapper)context.getOwner()));
        context.setMixInAnnotations(AssertionImpl.class, AssertionImplMixin.class);
        context.setMixInAnnotations(AttributePrincipalImpl.class, AttributePrincipalImplMixin.class);
        context.setMixInAnnotations(CasAuthenticationToken.class, CasAuthenticationTokenMixin.class);
    }
}

