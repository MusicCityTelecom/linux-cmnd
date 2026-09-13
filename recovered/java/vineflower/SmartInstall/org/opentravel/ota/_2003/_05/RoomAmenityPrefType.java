package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomAmenityPrefType", propOrder = "value")
@XmlSeeAlso(PropertyValueMatchType.Amenities.Amenity.class)
public class RoomAmenityPrefType {
   @XmlValue
   protected String value;
   @XmlAttribute(name = "RoomAmenity")
   protected String roomAmenity;
   @XmlAttribute(name = "ExistsCode")
   protected String existsCode;
   @XmlAttribute(name = "QualityLevel")
   protected String qualityLevel;
   @XmlAttribute(name = "RoomGender")
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   protected String roomGender;
   @XmlAttribute(name = "SharedRoomInd")
   protected Boolean sharedRoomInd;
   @XmlAttribute(name = "Quantity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger quantity;
   @XmlAttribute(name = "PreferLevel")
   protected PreferLevelType preferLevel;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getRoomAmenity() {
      return this.roomAmenity;
   }

   public void setRoomAmenity(String value) {
      this.roomAmenity = value;
   }

   public String getExistsCode() {
      return this.existsCode;
   }

   public void setExistsCode(String value) {
      this.existsCode = value;
   }

   public String getQualityLevel() {
      return this.qualityLevel;
   }

   public void setQualityLevel(String value) {
      this.qualityLevel = value;
   }

   public String getRoomGender() {
      return this.roomGender;
   }

   public void setRoomGender(String value) {
      this.roomGender = value;
   }

   public Boolean isSharedRoomInd() {
      return this.sharedRoomInd;
   }

   public void setSharedRoomInd(Boolean value) {
      this.sharedRoomInd = value;
   }

   public BigInteger getQuantity() {
      return this.quantity;
   }

   public void setQuantity(BigInteger value) {
      this.quantity = value;
   }

   public PreferLevelType getPreferLevel() {
      return this.preferLevel;
   }

   public void setPreferLevel(PreferLevelType value) {
      this.preferLevel = value;
   }
}
