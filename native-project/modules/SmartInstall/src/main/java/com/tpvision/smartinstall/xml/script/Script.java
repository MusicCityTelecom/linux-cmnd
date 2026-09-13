package com.tpvision.smartinstall.xml.script;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Script")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"schemaVersion", "activities"})
public class Script {
   @XmlElement(name = "SchemaVersion", required = false)
   private SchemaVersion schemaVersion;
   @XmlElement(name = "Activity", required = false)
   private List<Activity> activities;

   public SchemaVersion getSchemaVersion() {
      return this.schemaVersion;
   }

   public void setSchemaVersion(SchemaVersion schemaVersion) {
      this.schemaVersion = schemaVersion;
   }

   public List<Activity> getActivity() {
      return this.activities;
   }

   public void setActivity(List<Activity> activities) {
      this.activities = activities;
   }
}
