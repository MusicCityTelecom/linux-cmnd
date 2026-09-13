/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.servlet.ModelAndView
 */
package org.springframework.boot.autoconfigure.web.servlet.error;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

@FunctionalInterface
public interface ErrorViewResolver {
    public ModelAndView resolveErrorView(HttpServletRequest var1, HttpStatus var2, Map<String, Object> var3);
}

