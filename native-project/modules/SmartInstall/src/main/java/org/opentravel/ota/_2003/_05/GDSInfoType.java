package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GDS_InfoType", propOrder = "gdsCodes")
public class GDSInfoType {
   @XmlElement(name = "GDS_Codes")
   protected GDSInfoType.GDSCodes gdsCodes;
   @XmlAttribute(name = "MasterChainCode")
   protected String masterChainCode;

   public GDSInfoType.GDSCodes getGDSCodes() {
      return this.gdsCodes;
   }

   public void setGDSCodes(GDSInfoType.GDSCodes value) {
      this.gdsCodes = value;
   }

   public String getMasterChainCode() {
      return this.masterChainCode;
   }

   public void setMasterChainCode(String value) {
      this.masterChainCode = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "gdsCode")
   public static class GDSCodes {
      @XmlElement(name = "GDS_Code")
      protected List<GDSInfoType.GDSCodes.GDSCode> gdsCode;
      @XmlAttribute(name = "LoadGDSIndicator")
      protected Boolean loadGDSIndicator;

      public List<GDSInfoType.GDSCodes.GDSCode> getGDSCode() {
         if (this.gdsCode == null) {
            this.gdsCode = new ArrayList<>();
         }

         return this.gdsCode;
      }

      public Boolean isLoadGDSIndicator() {
         return this.loadGDSIndicator;
      }

      public void setLoadGDSIndicator(Boolean value) {
         this.loadGDSIndicator = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "gdsCodeDetails")
      public static class GDSCode {
         @XmlElement(name = "GDS_CodeDetails")
         protected GDSInfoType.GDSCodes.GDSCode.GDSCodeDetails gdsCodeDetails;
         @XmlAttribute(name = "ChainCode")
         protected String chainCode;
         @XmlAttribute(name = "GDS_PropertyCode")
         protected String gdsPropertyCode;
         @XmlAttribute(name = "GDS_Name", required = true)
         protected String gdsName;
         @XmlAttribute(name = "LoadGDSIndicator")
         protected Boolean loadGDSIndicator;
         @XmlAttribute(name = "GDS_PropertyLongName")
         protected String gdsPropertyLongName;
         @XmlAttribute(name = "GDS_PropertyShortName")
         protected String gdsPropertyShortName;
         @XmlAttribute(name = "GDS_RoomTypeCode")
         protected String gdsRoomTypeCode;

         public GDSInfoType.GDSCodes.GDSCode.GDSCodeDetails getGDSCodeDetails() {
            return this.gdsCodeDetails;
         }

         public void setGDSCodeDetails(GDSInfoType.GDSCodes.GDSCode.GDSCodeDetails value) {
            this.gdsCodeDetails = value;
         }

         public String getChainCode() {
            return this.chainCode;
         }

         public void setChainCode(String value) {
            this.chainCode = value;
         }

         public String getGDSPropertyCode() {
            return this.gdsPropertyCode;
         }

         public void setGDSPropertyCode(String value) {
            this.gdsPropertyCode = value;
         }

         public String getGDSName() {
            return this.gdsName;
         }

         public void setGDSName(String value) {
            this.gdsName = value;
         }

         public Boolean isLoadGDSIndicator() {
            return this.loadGDSIndicator;
         }

         public void setLoadGDSIndicator(Boolean value) {
            this.loadGDSIndicator = value;
         }

         public String getGDSPropertyLongName() {
            return this.gdsPropertyLongName;
         }

         public void setGDSPropertyLongName(String value) {
            this.gdsPropertyLongName = value;
         }

         public String getGDSPropertyShortName() {
            return this.gdsPropertyShortName;
         }

         public void setGDSPropertyShortName(String value) {
            this.gdsPropertyShortName = value;
         }

         public String getGDSRoomTypeCode() {
            return this.gdsRoomTypeCode;
         }

         public void setGDSRoomTypeCode(String value) {
            this.gdsRoomTypeCode = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "gdsCodeDetail")
         public static class GDSCodeDetails {
            @XmlElement(name = "GDS_CodeDetail")
            protected List<GDSInfoType.GDSCodes.GDSCode.GDSCodeDetails.GDSCodeDetail> gdsCodeDetail;

            public List<GDSInfoType.GDSCodes.GDSCode.GDSCodeDetails.GDSCodeDetail> getGDSCodeDetail() {
               if (this.gdsCodeDetail == null) {
                  this.gdsCodeDetail = new ArrayList<>();
               }

               return this.gdsCodeDetail;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class GDSCodeDetail {
               @XmlAttribute(name = "PseudoCityCode")
               protected String pseudoCityCode;
               @XmlAttribute(name = "AgencyName")
               protected String agencyName;

               public String getPseudoCityCode() {
                  return this.pseudoCityCode;
               }

               public void setPseudoCityCode(String value) {
                  this.pseudoCityCode = value;
               }

               public String getAgencyName() {
                  return this.agencyName;
               }

               public void setAgencyName(String value) {
                  this.agencyName = value;
               }
            }
         }
      }
   }
}
