package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomTypeLiteType", propOrder = {"roomDescription", "amenity"})
public class RoomTypeLiteType {
   @XmlElement(name = "RoomDescription")
   protected ParagraphType roomDescription;
   @XmlElement(name = "Amenity")
   protected List<RoomAmenityPrefType> amenity;
   @XmlAttribute(name = "RoomTypeCode")
   protected String roomTypeCode;
   @XmlAttribute(name = "NumberOfUnits")
   protected BigInteger numberOfUnits;

   public ParagraphType getRoomDescription() {
      return this.roomDescription;
   }

   public void setRoomDescription(ParagraphType value) {
      this.roomDescription = value;
   }

   public List<RoomAmenityPrefType> getAmenity() {
      if (this.amenity == null) {
         this.amenity = new ArrayList<>();
      }

      return this.amenity;
   }

   public String getRoomTypeCode() {
      return this.roomTypeCode;
   }

   public void setRoomTypeCode(String value) {
      this.roomTypeCode = value;
   }

   public BigInteger getNumberOfUnits() {
      return this.numberOfUnits;
   }

   public void setNumberOfUnits(BigInteger value) {
      this.numberOfUnits = value;
   }
}
