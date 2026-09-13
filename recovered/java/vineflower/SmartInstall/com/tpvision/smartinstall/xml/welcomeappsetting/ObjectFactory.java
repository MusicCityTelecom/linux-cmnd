package com.tpvision.smartinstall.xml.welcomeappsetting;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
   public WelcomeAppSettings createWelcomeAppSettings() {
      return new WelcomeAppSettings();
   }

   public WelcomeAppSettings.SchemaVersion createWelcomeAppSettingsSchemaVersion() {
      return new WelcomeAppSettings.SchemaVersion();
   }

   public WelcomeAppSettings.Item createWelcomeAppSettingsItem() {
      return new WelcomeAppSettings.Item();
   }
}
