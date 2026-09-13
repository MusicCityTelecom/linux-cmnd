package com.tpvision.smartinstall.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"unchangedFiles", "settingFiles", "crcFiles", "settings", "channel"})
@XmlRootElement(name = "platform")
public class Platform {
   @XmlElement(required = true)
   protected UnchangedFiles unchangedFiles;
   @XmlElement(required = true)
   protected SettingFiles settingFiles;
   @XmlElement(required = true)
   protected CrcFiles crcFiles;
   @XmlElement(required = true)
   protected Settings settings;
   @XmlElement(required = true)
   protected Channel channel;
   @XmlAttribute(name = "cloneRootFolderName", required = true)
   protected String cloneRootFolderName;
   @XmlAttribute(name = "id", required = true)
   protected String id;
   @XmlAttribute(name = "swver")
   protected String swver;

   public UnchangedFiles getUnchangedFiles() {
      return this.unchangedFiles;
   }

   public void setUnchangedFiles(UnchangedFiles value) {
      this.unchangedFiles = value;
   }

   public SettingFiles getSettingFiles() {
      return this.settingFiles;
   }

   public void setSettingFiles(SettingFiles value) {
      this.settingFiles = value;
   }

   public CrcFiles getCrcFiles() {
      return this.crcFiles;
   }

   public void setCrcFiles(CrcFiles value) {
      this.crcFiles = value;
   }

   public Settings getSettings() {
      return this.settings;
   }

   public void setSettings(Settings value) {
      this.settings = value;
   }

   public Channel getChannel() {
      return this.channel;
   }

   public void setChannel(Channel value) {
      this.channel = value;
   }

   public String getCloneRootFolderName() {
      return this.cloneRootFolderName;
   }

   public void setCloneRootFolderName(String value) {
      this.cloneRootFolderName = value;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String value) {
      this.id = value;
   }

   public String getSwver() {
      return this.swver;
   }

   public void setSwver(String value) {
      this.swver = value;
   }
}
