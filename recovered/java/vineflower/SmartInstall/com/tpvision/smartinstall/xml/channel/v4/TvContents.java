package com.tpvision.smartinstall.xml.channel.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"schemaVersion", "channelMap", "applicationMap", "extensionMap"})
@XmlRootElement(name = "TvContents")
public class TvContents {
   @XmlElement(name = "SchemaVersion", required = true)
   protected SchemaVersion schemaVersion;
   @XmlElement(name = "ChannelMap", required = true)
   protected ChannelMap channelMap;
   @XmlElement(name = "ApplicationMap", required = true)
   protected ApplicationMap applicationMap;
   @XmlElement(name = "ExtensionMap", required = true)
   protected ExtensionMap extensionMap;

   public SchemaVersion getSchemaVersion() {
      return this.schemaVersion;
   }

   public void setSchemaVersion(SchemaVersion value) {
      this.schemaVersion = value;
   }

   public ChannelMap getChannelMap() {
      return this.channelMap;
   }

   public void setChannelMap(ChannelMap channel) {
      this.channelMap = channel;
   }

   public ApplicationMap getApplicationMap() {
      return this.applicationMap;
   }

   public void setApplicationMap(ApplicationMap value) {
      this.applicationMap = value;
   }

   public ExtensionMap getExtensionMap() {
      return this.extensionMap;
   }

   public void setExtensionMap(ExtensionMap value) {
      this.extensionMap = value;
   }
}
