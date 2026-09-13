/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.Authentication
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.authentication.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.Assert;

public final class HeaderWriterLogoutHandler
implements LogoutHandler {
    private final HeaderWriter headerWriter;

    public HeaderWriterLogoutHandler(HeaderWriter headerWriter) {
        Assert.notNull((Object)headerWriter, (String)"headerWriter cannot be null");
        this.headerWriter = headerWriter;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        this.headerWriter.writeHeaders(request, response);
    }
}

