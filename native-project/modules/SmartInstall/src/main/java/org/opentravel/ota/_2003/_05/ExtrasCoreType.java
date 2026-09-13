package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtrasCoreType", propOrder = "subCategory")
@XmlSeeAlso({ExtrasInfoType.class, ExtrasType.class})
public class ExtrasCoreType {
   @XmlElement(name = "SubCategory")
   protected List<ExtrasCoreType.SubCategory> subCategory;
   @XmlAttribute(name = "RPH")
   protected String rph;
   @XmlAttribute(name = "Code")
   protected String code;
   @XmlAttribute(name = "Type")
   protected String type;
   @XmlAttribute(name = "Quantity")
   protected Integer quantity;
   @XmlAttribute(name = "GroupCode")
   protected String groupCode;
   @XmlAttribute(name = "Name")
   protected String name;
   @XmlAttribute(name = "ListOfInventoryItemRPH")
   protected List<String> listOfInventoryItemRPH;
   @XmlAttribute(name = "ListofRoomRPH")
   protected List<String> listofRoomRPH;

   public List<ExtrasCoreType.SubCategory> getSubCategory() {
      if (this.subCategory == null) {
         this.subCategory = new ArrayList<>();
      }

      return this.subCategory;
   }

   public String getRPH() {
      return this.rph;
   }

   public void setRPH(String value) {
      this.rph = value;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String value) {
      this.code = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public Integer getQuantity() {
      return this.quantity;
   }

   public void setQuantity(Integer value) {
      this.quantity = value;
   }

   public String getGroupCode() {
      return this.groupCode;
   }

   public void setGroupCode(String value) {
      this.groupCode = value;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public List<String> getListOfInventoryItemRPH() {
      if (this.listOfInventoryItemRPH == null) {
         this.listOfInventoryItemRPH = new ArrayList<>();
      }

      return this.listOfInventoryItemRPH;
   }

   public List<String> getListofRoomRPH() {
      if (this.listofRoomRPH == null) {
         this.listofRoomRPH = new ArrayList<>();
      }

      return this.listofRoomRPH;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class SubCategory {
      @XmlAttribute(name = "Code")
      protected String code;
      @XmlAttribute(name = "Name")
      protected String name;

      public String getCode() {
         return this.code;
      }

      public void setCode(String value) {
         this.code = value;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String value) {
         this.name = value;
      }
   }
}
