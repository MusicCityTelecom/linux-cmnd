package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactsType", propOrder = "name")
public class ContactsType {
   @XmlElement(name = "Name", required = true)
   protected List<ContactsType.Name> name;

   public List<ContactsType.Name> getName() {
      if (this.name == null) {
         this.name = new ArrayList<>();
      }

      return this.name;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "jobTitle")
   public static class Name extends PersonNameType {
      @XmlElement(name = "JobTitle")
      protected List<ContactsType.Name.JobTitle> jobTitle;
      @XmlAttribute(name = "SrvcCode")
      protected String srvcCode;
      @XmlAttribute(name = "Location")
      protected String location;
      @XmlAttribute(name = "CorporatePosition")
      protected String corporatePosition;
      @XmlAttribute(name = "OKToPublish")
      protected Boolean okToPublish;
      @XmlAttribute(name = "NameOrdered")
      protected String nameOrdered;
      @XmlAttribute(name = "CodeDetail")
      protected String codeDetail;
      @XmlAttribute(name = "Removal")
      protected Boolean removal;
      @XmlAttribute(name = "Gender")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String gender;
      @XmlAttribute(name = "ID")
      protected String id;

      public List<ContactsType.Name.JobTitle> getJobTitle() {
         if (this.jobTitle == null) {
            this.jobTitle = new ArrayList<>();
         }

         return this.jobTitle;
      }

      public String getSrvcCode() {
         return this.srvcCode;
      }

      public void setSrvcCode(String value) {
         this.srvcCode = value;
      }

      public String getLocation() {
         return this.location;
      }

      public void setLocation(String value) {
         this.location = value;
      }

      public String getCorporatePosition() {
         return this.corporatePosition;
      }

      public void setCorporatePosition(String value) {
         this.corporatePosition = value;
      }

      public Boolean isOKToPublish() {
         return this.okToPublish;
      }

      public void setOKToPublish(Boolean value) {
         this.okToPublish = value;
      }

      public String getNameOrdered() {
         return this.nameOrdered;
      }

      public void setNameOrdered(String value) {
         this.nameOrdered = value;
      }

      public String getCodeDetail() {
         return this.codeDetail;
      }

      public void setCodeDetail(String value) {
         this.codeDetail = value;
      }

      public Boolean isRemoval() {
         return this.removal;
      }

      public void setRemoval(Boolean value) {
         this.removal = value;
      }

      public String getGender() {
         return this.gender;
      }

      public void setGender(String value) {
         this.gender = value;
      }

      public String getID() {
         return this.id;
      }

      public void setID(String value) {
         this.id = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "value")
      public static class JobTitle {
         @XmlValue
         protected String value;
         @XmlAttribute(name = "Type")
         protected String type;

         public String getValue() {
            return this.value;
         }

         public void setValue(String value) {
            this.value = value;
         }

         public String getType() {
            return this.type;
         }

         public void setType(String value) {
            this.type = value;
         }
      }
   }
}
