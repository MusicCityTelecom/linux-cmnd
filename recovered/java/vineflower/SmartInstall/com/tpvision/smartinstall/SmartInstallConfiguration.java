package com.tpvision.smartinstall;

import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.xml.Config;
import com.tpvision.smartinstall.xml.Platform;
import com.tpvision.smartinstall.xml.Setting;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartInstallConfiguration {
   private static final Logger LOG = LoggerFactory.getLogger(SmartInstallConfiguration.class);
   private static SmartInstallConfiguration instance = new SmartInstallConfiguration();
   private Config config;
   private Set<Setting> settingInfo;

   private SmartInstallConfiguration() {
      try {
         File file = new File(CommonConstants.CONFIG_FILE_LOCATION);
         JAXBContext jc = JAXBContext.newInstance("com.tpvision.smartinstall.xml");
         Unmarshaller u = jc.createUnmarshaller();
         this.config = (Config)u.unmarshal(file);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public Config getConfig() {
      return this.config;
   }

   public static SmartInstallConfiguration instance() {
      return instance;
   }

   public Set<Setting> getAllSettingInfo() {
      if (null == this.settingInfo) {
         Set<Setting> ret = new HashSet<>();

         for (Platform platform : this.config.getPlatform()) {
            for (Setting setting : platform.getSettings().getSetting()) {
               ret.add(setting);
            }
         }

         this.settingInfo = ret;
      }

      return this.settingInfo;
   }

   public Platform getPlatform(String platformId) {
      if (this.config == null) {
         return null;
      }

      for (Platform p : this.config.getPlatform()) {
         if (p.getId().equalsIgnoreCase(platformId)) {
            return p;
         }
      }

      return null;
   }
}
