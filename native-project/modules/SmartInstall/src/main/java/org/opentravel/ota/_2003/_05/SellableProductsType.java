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
@XmlType(name = "SellableProductsType", propOrder = "sellableProduct")
public class SellableProductsType {
   @XmlElement(name = "SellableProduct", required = true)
   protected List<SellableProductsType.SellableProduct> sellableProduct;

   public List<SellableProductsType.SellableProduct> getSellableProduct() {
      if (this.sellableProduct == null) {
         this.sellableProduct = new ArrayList<>();
      }

      return this.sellableProduct;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"destinationSystemCodes", "guestRoom", "meetingRooms", "inventoryBlock", "description", "uniqueID"})
   public static class SellableProduct {
      @XmlElement(name = "DestinationSystemCodes")
      protected SellableProductsType.SellableProduct.DestinationSystemCodes destinationSystemCodes;
      @XmlElement(name = "GuestRoom")
      protected GuestRoomType guestRoom;
      @XmlElement(name = "MeetingRooms")
      protected MeetingRoomsType meetingRooms;
      @XmlElement(name = "InventoryBlock")
      protected SellableProductsType.SellableProduct.InventoryBlock inventoryBlock;
      @XmlElement(name = "Description")
      protected ParagraphType description;
      @XmlElement(name = "UniqueID")
      protected UniqueIDType uniqueID;
      @XmlAttribute(name = "RPH")
      protected String rph;
      @XmlAttribute(name = "InvNotifType")
      protected String invNotifType;
      @XmlAttribute(name = "InvStatusType")
      protected String invStatusType;
      @XmlAttribute(name = "InvGroupingCode")
      protected String invGroupingCode;
      @XmlAttribute(name = "OrderSequence")
      protected Integer orderSequence;
      @XmlAttribute(name = "Start")
      protected String start;
      @XmlAttribute(name = "Duration")
      protected String duration;
      @XmlAttribute(name = "End")
      protected String end;
      @XmlAttribute(name = "InvCodeApplication")
      @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
      protected String invCodeApplication;
      @XmlAttribute(name = "InvCode")
      protected String invCode;
      @XmlAttribute(name = "InvType")
      protected String invType;
      @XmlAttribute(name = "InvTypeCode")
      protected String invTypeCode;
      @XmlAttribute(name = "IsRoom")
      protected Boolean isRoom;

      public SellableProductsType.SellableProduct.DestinationSystemCodes getDestinationSystemCodes() {
         return this.destinationSystemCodes;
      }

      public void setDestinationSystemCodes(SellableProductsType.SellableProduct.DestinationSystemCodes value) {
         this.destinationSystemCodes = value;
      }

      public GuestRoomType getGuestRoom() {
         return this.guestRoom;
      }

      public void setGuestRoom(GuestRoomType value) {
         this.guestRoom = value;
      }

      public MeetingRoomsType getMeetingRooms() {
         return this.meetingRooms;
      }

      public void setMeetingRooms(MeetingRoomsType value) {
         this.meetingRooms = value;
      }

      public SellableProductsType.SellableProduct.InventoryBlock getInventoryBlock() {
         return this.inventoryBlock;
      }

      public void setInventoryBlock(SellableProductsType.SellableProduct.InventoryBlock value) {
         this.inventoryBlock = value;
      }

      public ParagraphType getDescription() {
         return this.description;
      }

      public void setDescription(ParagraphType value) {
         this.description = value;
      }

      public UniqueIDType getUniqueID() {
         return this.uniqueID;
      }

      public void setUniqueID(UniqueIDType value) {
         this.uniqueID = value;
      }

      public String getRPH() {
         return this.rph;
      }

      public void setRPH(String value) {
         this.rph = value;
      }

      public String getInvNotifType() {
         return this.invNotifType;
      }

      public void setInvNotifType(String value) {
         this.invNotifType = value;
      }

      public String getInvStatusType() {
         return this.invStatusType;
      }

      public void setInvStatusType(String value) {
         this.invStatusType = value;
      }

      public String getInvGroupingCode() {
         return this.invGroupingCode;
      }

      public void setInvGroupingCode(String value) {
         this.invGroupingCode = value;
      }

      public Integer getOrderSequence() {
         return this.orderSequence;
      }

      public void setOrderSequence(Integer value) {
         this.orderSequence = value;
      }

      public String getStart() {
         return this.start;
      }

      public void setStart(String value) {
         this.start = value;
      }

      public String getDuration() {
         return this.duration;
      }

      public void setDuration(String value) {
         this.duration = value;
      }

      public String getEnd() {
         return this.end;
      }

      public void setEnd(String value) {
         this.end = value;
      }

      public String getInvCodeApplication() {
         return this.invCodeApplication;
      }

      public void setInvCodeApplication(String value) {
         this.invCodeApplication = value;
      }

      public String getInvCode() {
         return this.invCode;
      }

      public void setInvCode(String value) {
         this.invCode = value;
      }

      public String getInvType() {
         return this.invType;
      }

      public void setInvType(String value) {
         this.invType = value;
      }

      public String getInvTypeCode() {
         return this.invTypeCode;
      }

      public void setInvTypeCode(String value) {
         this.invTypeCode = value;
      }

      public Boolean isIsRoom() {
         return this.isRoom;
      }

      public void setIsRoom(Boolean value) {
         this.isRoom = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "destinationSystemCode")
      public static class DestinationSystemCodes {
         @XmlElement(name = "DestinationSystemCode", required = true)
         protected List<SellableProductsType.SellableProduct.DestinationSystemCodes.DestinationSystemCode> destinationSystemCode;

         public List<SellableProductsType.SellableProduct.DestinationSystemCodes.DestinationSystemCode> getDestinationSystemCode() {
            if (this.destinationSystemCode == null) {
               this.destinationSystemCode = new ArrayList<>();
            }

            return this.destinationSystemCode;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class DestinationSystemCode {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "ChainRateLevelCrossRef")
            protected String chainRateLevelCrossRef;
            @XmlAttribute(name = "ChainRateCodeCrossRef")
            protected String chainRateCodeCrossRef;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
            }

            public String getChainRateLevelCrossRef() {
               return this.chainRateLevelCrossRef;
            }

            public void setChainRateLevelCrossRef(String value) {
               this.chainRateLevelCrossRef = value;
            }

            public String getChainRateCodeCrossRef() {
               return this.chainRateCodeCrossRef;
            }

            public void setChainRateCodeCrossRef(String value) {
               this.chainRateCodeCrossRef = value;
            }
         }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class InventoryBlock {
         @XmlAttribute(name = "Code")
         protected String code;

         public String getCode() {
            return this.code;
         }

         public void setCode(String value) {
            this.code = value;
         }
      }
   }
}
