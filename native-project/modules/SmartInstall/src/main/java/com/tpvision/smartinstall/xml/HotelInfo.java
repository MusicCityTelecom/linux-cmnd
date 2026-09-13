package com.tpvision.smartinstall.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"imageSpec", "hotelInfoImageSpec"})
@XmlRootElement(name = "hotelInfo")
public class HotelInfo {
   protected ImageSpec imageSpec;
   protected HotelInfoImageSpec hotelInfoImageSpec;
   @XmlAttribute(name = "folderName", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String folderName;

   public ImageSpec getImageSpec() {
      return this.imageSpec;
   }

   public void setImageSpec(ImageSpec value) {
      this.imageSpec = value;
   }

   public HotelInfoImageSpec getHotelInfoImageSpec() {
      return this.hotelInfoImageSpec;
   }

   public void setHotelInfoImageSpec(HotelInfoImageSpec value) {
      this.hotelInfoImageSpec = value;
   }

   public String getFolderName() {
      return this.folderName;
   }

   public void setFolderName(String value) {
      this.folderName = value;
   }
}
