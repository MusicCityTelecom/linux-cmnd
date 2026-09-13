/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.springframework.security.web.firewall;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.firewall.RequestRejectedException;

public interface RequestRejectedHandler {
    public void handle(HttpServletRequest var1, HttpServletResponse var2, RequestRejectedException var3) throws IOException, ServletException;
}

