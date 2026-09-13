/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.stereotype.Controller
 *  org.springframework.web.servlet.ModelAndView
 */
package org.apereo.cas.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class AbstractDelegateController
implements ApplicationContextAware {
    protected ApplicationContext applicationContext;

    public abstract boolean canHandle(HttpServletRequest var1, HttpServletResponse var2);

    protected abstract ModelAndView handleRequestInternal(HttpServletRequest var1, HttpServletResponse var2) throws Exception;

    @Generated
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}

