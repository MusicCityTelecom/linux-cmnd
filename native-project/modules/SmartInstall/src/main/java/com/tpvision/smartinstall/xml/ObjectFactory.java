package com.tpvision.smartinstall.xml;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
   public Setting createSetting() {
      return new Setting();
   }

   public Platform createPlatform() {
      return new Platform();
   }

   public UnchangedFiles createUnchangedFiles() {
      return new UnchangedFiles();
   }

   public File createFile() {
      return new File();
   }

   public Crc createCrc() {
      return new Crc();
   }

   public SettingFiles createSettingFiles() {
      return new SettingFiles();
   }

   public CrcFiles createCrcFiles() {
      return new CrcFiles();
   }

   public Settings createSettings() {
      return new Settings();
   }

   public Channel createChannel() {
      return new Channel();
   }

   public Config createConfig() {
      return new Config();
   }
}
