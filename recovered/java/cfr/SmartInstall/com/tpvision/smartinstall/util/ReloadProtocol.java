/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.coyote.http11.Http11NioProtocol
 *  org.apache.tomcat.util.net.AbstractJsseEndpoint
 */
package com.tpvision.smartinstall.util;

import java.io.File;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.AbstractJsseEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReloadProtocol
extends Http11NioProtocol {
    private static final Logger LOG = LoggerFactory.getLogger(ReloadProtocol.class);

    public ReloadProtocol() {
        RefreshSslConfigThread refresher = new RefreshSslConfigThread(this.getEndpoint(), this);
        refresher.start();
    }

    class RefreshSslConfigThread
    extends Thread {
        AbstractJsseEndpoint<?, ?> abstractJsseEndpoint = null;
        Http11NioProtocol protocol = null;

        public RefreshSslConfigThread(AbstractJsseEndpoint<?, ?> abstractJsseEndpoint, Http11NioProtocol protocol) {
            this.abstractJsseEndpoint = abstractJsseEndpoint;
            this.protocol = protocol;
        }

        @Override
        public void run() {
            int timeBetweenRefreshesInt = 10000;
            long lastTime = 0L;
            String path = System.getProperty("catalina.home") + File.separator + "server.p12";
            LOG.error(path);
            File file = new File(path);
            if (file.exists()) {
                lastTime = file.lastModified();
            }
            while (true) {
                try {
                    if (file.exists()) {
                        if (file.lastModified() > lastTime) {
                            this.abstractJsseEndpoint.reloadSslHostConfigs();
                            lastTime = file.lastModified();
                            LOG.info("Config Updated reloadSslHostConfigs");
                        }
                    } else {
                        this.abstractJsseEndpoint.reloadSslHostConfigs();
                    }
                }
                catch (Exception e) {
                    LOG.error("Problem while reloading");
                }
                try {
                    Thread.sleep(timeBetweenRefreshesInt);
                    continue;
                }
                catch (InterruptedException e) {
                    LOG.error("Error while sleeping");
                    Thread.currentThread().interrupt();
                    continue;
                }
                break;
            }
        }
    }
}

