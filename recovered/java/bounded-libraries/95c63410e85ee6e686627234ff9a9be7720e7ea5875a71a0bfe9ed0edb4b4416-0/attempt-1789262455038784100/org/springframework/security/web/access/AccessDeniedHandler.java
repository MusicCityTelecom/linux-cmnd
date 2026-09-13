/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.access.AccessDeniedException
 */
package org.springframework.security.web.access;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

public interface AccessDeniedHandler {
    public void handle(HttpServletRequest var1, HttpServletResponse var2, AccessDeniedException var3) throws IOException, ServletException;
}

