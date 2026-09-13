/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.soap;

import com.tpvision.smartinstall.soap.mychoice.MyChoiceWebService;
import com.tpvision.smartinstall.util.Utils;
import org.springframework.web.context.WebApplicationContext;

public class RemoteWebServiceManager {
    private RemoteWebServiceManager() {
    }

    public static MyChoiceWebService getMyChoiceWebService() {
        return (MyChoiceWebService)RemoteWebServiceManager.getObjectFromSpringContext("myChoiceClient");
    }

    private static Object getObjectFromSpringContext(String name) {
        WebApplicationContext webApplicationContext = (WebApplicationContext)Utils.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        return webApplicationContext.getBean(name);
    }
}

