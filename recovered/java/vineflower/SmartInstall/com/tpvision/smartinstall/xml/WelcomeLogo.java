package com.tpvision.smartinstall.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"imageSpec", "welcomeLogoImageSpec"})
@XmlRootElement(name = "welcomeLogo")
public class WelcomeLogo {
   protected ImageSpec imageSpec;
   protected WelcomeLogoImageSpec welcomeLogoImageSpec;
   @XmlAttribute(name = "folderName", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String folderName;

   public ImageSpec getImageSpec() {
      return this.imageSpec;
   }

   public void setImageSpec(ImageSpec value) {
      this.imageSpec = value;
   }

   public WelcomeLogoImageSpec getWelcomeLogoImageSpec() {
      return this.welcomeLogoImageSpec;
   }

   public void setWelcomeLogoImageSpec(WelcomeLogoImageSpec value) {
      this.welcomeLogoImageSpec = value;
   }

   public String getFolderName() {
      return this.folderName;
   }

   public void setFolderName(String value) {
      this.folderName = value;
   }
}
