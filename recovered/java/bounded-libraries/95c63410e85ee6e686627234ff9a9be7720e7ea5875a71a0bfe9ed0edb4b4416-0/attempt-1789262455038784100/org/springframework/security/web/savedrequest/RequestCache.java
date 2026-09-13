/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.springframework.security.web.savedrequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.savedrequest.SavedRequest;

public interface RequestCache {
    public void saveRequest(HttpServletRequest var1, HttpServletResponse var2);

    public SavedRequest getRequest(HttpServletRequest var1, HttpServletResponse var2);

    public HttpServletRequest getMatchingRequest(HttpServletRequest var1, HttpServletResponse var2);

    public void removeRequest(HttpServletRequest var1, HttpServletResponse var2);
}

