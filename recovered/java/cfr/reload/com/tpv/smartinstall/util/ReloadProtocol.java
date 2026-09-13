/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.coyote.http11.Http11NioProtocol
 *  org.apache.juli.logging.Log
 *  org.apache.juli.logging.LogFactory
 *  org.apache.tomcat.util.net.AbstractJsseEndpoint
 */
package com.tpv.smartinstall.util;

import java.io.File;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.AbstractJsseEndpoint;

public class ReloadProtocol
extends Http11NioProtocol {
    private static final Log LOG = LogFactory.getLog(ReloadProtocol.class);

    public ReloadProtocol() {
        RefreshSslConfigThread refresher = new RefreshSslConfigThread(this.getEndpoint(), this);
        refresher.start();
    }

    public void setKeystorePass(String s) {
        super.setKeystorePass(s);
    }

    public void setKeyPass(String s) {
        super.setKeyPass(s);
    }

    public void setTruststorePass(String p) {
        super.setTruststorePass(p);
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
            String path = System.getProperty("catalina.home");
            path = String.valueOf(path) + File.separator + "server.p12";
            LOG.error((Object)path);
            File file = new File(path);
            if (file != null) {
                lastTime = file.lastModified();
            }
            while (true) {
                try {
                    if (file != null) {
                        if (file.lastModified() > lastTime) {
                            this.abstractJsseEndpoint.reloadSslHostConfigs();
                            lastTime = file.lastModified();
                            LOG.info((Object)"Config Updated 0");
                            LOG.error((Object)"Config Updated 1");
                        }
                    } else {
                        this.abstractJsseEndpoint.reloadSslHostConfigs();
                        LOG.info((Object)"Config Updated 2");
                        LOG.error((Object)"Config Updated 3");
                    }
                }
                catch (Exception e) {
                    LOG.error((Object)"Problem while reloading");
                }
                try {
                    Thread.sleep(timeBetweenRefreshesInt);
                    continue;
                }
                catch (InterruptedException e) {
                    LOG.error((Object)"Error while sleeping");
                    continue;
                }
                break;
            }
        }
    }
}

