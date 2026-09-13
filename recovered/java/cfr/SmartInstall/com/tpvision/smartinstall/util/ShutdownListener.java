/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.util.RFCommandState;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ShutdownListener
implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        RFCommandState.instance().cleanup();
        GatewayManager.getInstance().teardownGateway();
    }
}

