package com.tpvision.smartinstall.xml.welcomeappsetting;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"schemaVersion", "item"})
@XmlRootElement(name = "WelcomeAppSettings")
public class WelcomeAppSettings {
   @XmlElement(name = "SchemaVersion", required = true)
   protected WelcomeAppSettings.SchemaVersion schemaVersion;
   protected List<WelcomeAppSettings.Item> item;

   public WelcomeAppSettings.SchemaVersion getSchemaVersion() {
      return this.schemaVersion;
   }

   public void setSchemaVersion(WelcomeAppSettings.SchemaVersion value) {
      this.schemaVersion = value;
   }

   public List<WelcomeAppSettings.Item> getItem() {
      if (null == this.item) {
         this.item = new ArrayList<>();
      }

      return this.item;
   }

   public WelcomeAppSettings.Item getItemByName(String itemName) {
      for (WelcomeAppSettings.Item aitem : this.getItem()) {
         if (aitem.getName().equalsIgnoreCase(itemName)) {
            return aitem;
         }
      }

      WelcomeAppSettings.Item newItem = new WelcomeAppSettings.Item();
      newItem.name = itemName;
      newItem.value = "";
      newItem.cloneIn = "Yes";
      this.getItem().add(newItem);
      return newItem;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"name", "value", "cloneIn"})
   public static class Item {
      @XmlElement(name = "Name", required = true)
      protected String name;
      @XmlElement(name = "Value", required = true)
      protected String value;
      @XmlElement(name = "CloneIn", required = true)
      protected String cloneIn;

      public String getName() {
         return this.name;
      }

      public void setName(String value) {
         this.name = value;
      }

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public String getCloneIn() {
         return this.cloneIn;
      }

      public void setCloneIn(String value) {
         this.cloneIn = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "value")
   public static class SchemaVersion {
      @XmlValue
      protected String value;
      @XmlAttribute(name = "MajorVerNo")
      protected Byte majorVerNo;
      @XmlAttribute(name = "MinorVerNo")
      protected Byte minorVerNo;

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }

      public Byte getMajorVerNo() {
         return this.majorVerNo;
      }

      public void setMajorVerNo(Byte value) {
         this.majorVerNo = value;
      }

      public Byte getMinorVerNo() {
         return this.minorVerNo;
      }

      public void setMinorVerNo(Byte value) {
         this.minorVerNo = value;
      }
   }
}
