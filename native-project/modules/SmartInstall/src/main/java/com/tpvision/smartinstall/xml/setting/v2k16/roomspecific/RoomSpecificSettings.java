package com.tpvision.smartinstall.xml.setting.v2k16.roomspecific;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"schemaVersion", "tv"})
@XmlRootElement(name = "RoomSpecificSettings")
public class RoomSpecificSettings {
   @XmlElement(name = "SchemaVersion", required = true)
   protected SchemaVersion schemaVersion;
   @XmlElement(name = "TV", required = true)
   protected TV tv;

   public SchemaVersion getSchemaVersion() {
      return this.schemaVersion;
   }

   public void setSchemaVersion(SchemaVersion value) {
      this.schemaVersion = value;
   }

   public TV getTV() {
      return this.tv;
   }

   public void setTV(TV value) {
      this.tv = value;
   }
}
