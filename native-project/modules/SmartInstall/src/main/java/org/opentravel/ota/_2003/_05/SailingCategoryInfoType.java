package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SailingCategoryInfoType", propOrder = "selectedCategory")
public class SailingCategoryInfoType extends SailingInfoType {
   @XmlElement(name = "SelectedCategory")
   protected List<SailingCategoryInfoType.SelectedCategory> selectedCategory;

   public List<SailingCategoryInfoType.SelectedCategory> getSelectedCategory() {
      if (this.selectedCategory == null) {
         this.selectedCategory = new ArrayList<>();
      }

      return this.selectedCategory;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"cabinAttributes", "selectedCabin"})
   public static class SelectedCategory {
      @XmlElement(name = "CabinAttributes")
      protected SailingCategoryInfoType.SelectedCategory.CabinAttributes cabinAttributes;
      @XmlElement(name = "SelectedCabin")
      protected List<SailingCategoryInfoType.SelectedCategory.SelectedCabin> selectedCabin;
      @XmlAttribute(name = "WaitlistIndicator")
      protected Boolean waitlistIndicator;
      @XmlAttribute(name = "BerthedCategoryCode")
      protected String berthedCategoryCode;
      @XmlAttribute(name = "PricedCategoryCode")
      protected String pricedCategoryCode;
      @XmlAttribute(name = "DeckNumber")
      protected String deckNumber;
      @XmlAttribute(name = "DeckName")
      protected String deckName;
      @XmlAttribute(name = "FareCode")
      protected String fareCode;
      @XmlAttribute(name = "GroupCode")
      protected String groupCode;

      public SailingCategoryInfoType.SelectedCategory.CabinAttributes getCabinAttributes() {
         return this.cabinAttributes;
      }

      public void setCabinAttributes(SailingCategoryInfoType.SelectedCategory.CabinAttributes value) {
         this.cabinAttributes = value;
      }

      public List<SailingCategoryInfoType.SelectedCategory.SelectedCabin> getSelectedCabin() {
         if (this.selectedCabin == null) {
            this.selectedCabin = new ArrayList<>();
         }

         return this.selectedCabin;
      }

      public Boolean isWaitlistIndicator() {
         return this.waitlistIndicator;
      }

      public void setWaitlistIndicator(Boolean value) {
         this.waitlistIndicator = value;
      }

      public String getBerthedCategoryCode() {
         return this.berthedCategoryCode;
      }

      public void setBerthedCategoryCode(String value) {
         this.berthedCategoryCode = value;
      }

      public String getPricedCategoryCode() {
         return this.pricedCategoryCode;
      }

      public void setPricedCategoryCode(String value) {
         this.pricedCategoryCode = value;
      }

      public String getDeckNumber() {
         return this.deckNumber;
      }

      public void setDeckNumber(String value) {
         this.deckNumber = value;
      }

      public String getDeckName() {
         return this.deckName;
      }

      public void setDeckName(String value) {
         this.deckName = value;
      }

      public String getFareCode() {
         return this.fareCode;
      }

      public void setFareCode(String value) {
         this.fareCode = value;
      }

      public String getGroupCode() {
         return this.groupCode;
      }

      public void setGroupCode(String value) {
         this.groupCode = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "cabinAttribute")
      public static class CabinAttributes {
         @XmlElement(name = "CabinAttribute", required = true)
         protected List<SailingCategoryInfoType.SelectedCategory.CabinAttributes.CabinAttribute> cabinAttribute;

         public List<SailingCategoryInfoType.SelectedCategory.CabinAttributes.CabinAttribute> getCabinAttribute() {
            if (this.cabinAttribute == null) {
               this.cabinAttribute = new ArrayList<>();
            }

            return this.cabinAttribute;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "")
         public static class CabinAttribute {
            @XmlAttribute(name = "CabinAttributeCode")
            protected String cabinAttributeCode;

            public String getCabinAttributeCode() {
               return this.cabinAttributeCode;
            }

            public void setCabinAttributeCode(String value) {
               this.cabinAttributeCode = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "cabinAttributes")
      public static class SelectedCabin extends CabinOptionType {
         @XmlElement(name = "CabinAttributes")
         protected SailingCategoryInfoType.SelectedCategory.SelectedCabin.CabinAttributes cabinAttributes;

         public SailingCategoryInfoType.SelectedCategory.SelectedCabin.CabinAttributes getCabinAttributes() {
            return this.cabinAttributes;
         }

         public void setCabinAttributes(SailingCategoryInfoType.SelectedCategory.SelectedCabin.CabinAttributes value) {
            this.cabinAttributes = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "cabinAttribute")
         public static class CabinAttributes {
            @XmlElement(name = "CabinAttribute", required = true)
            protected List<SailingCategoryInfoType.SelectedCategory.SelectedCabin.CabinAttributes.CabinAttribute> cabinAttribute;

            public List<SailingCategoryInfoType.SelectedCategory.SelectedCabin.CabinAttributes.CabinAttribute> getCabinAttribute() {
               if (this.cabinAttribute == null) {
                  this.cabinAttribute = new ArrayList<>();
               }

               return this.cabinAttribute;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class CabinAttribute {
               @XmlAttribute(name = "CabinAttributeCode")
               protected String cabinAttributeCode;

               public String getCabinAttributeCode() {
                  return this.cabinAttributeCode;
               }

               public void setCabinAttributeCode(String value) {
                  this.cabinAttributeCode = value;
               }
            }
         }
      }
   }
}
