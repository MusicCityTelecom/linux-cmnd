package com.tpv.smartinstall.util;

import java.io.File;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.AbstractJsseEndpoint;

public class ReloadProtocol extends Http11NioProtocol {
   private static final Log LOG = LogFactory.getLog(ReloadProtocol.class);

   public ReloadProtocol() {
      ReloadProtocol.RefreshSslConfigThread refresher = new ReloadProtocol.RefreshSslConfigThread(this.getEndpoint(), this);
      refresher.start();
   }

   @Override
   public void setKeystorePass(String s) {
      super.setKeystorePass(s);
   }

   @Override
   public void setKeyPass(String s) {
      super.setKeyPass(s);
   }

   @Override
   public void setTruststorePass(String p) {
      super.setTruststorePass(p);
   }

   class RefreshSslConfigThread extends Thread {
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
         path = path + File.separator + "server.p12";
         ReloadProtocol.LOG.error(path);
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
                     ReloadProtocol.LOG.info("Config Updated 0");
                     ReloadProtocol.LOG.error("Config Updated 1");
                  }
               } else {
                  this.abstractJsseEndpoint.reloadSslHostConfigs();
                  ReloadProtocol.LOG.info("Config Updated 2");
                  ReloadProtocol.LOG.error("Config Updated 3");
               }
            } catch (Exception e) {
               ReloadProtocol.LOG.error("Problem while reloading");
            }

            try {
               Thread.sleep(timeBetweenRefreshesInt);
            } catch (InterruptedException e) {
               ReloadProtocol.LOG.error("Error while sleeping");
            }
         }
      }
   }
}
