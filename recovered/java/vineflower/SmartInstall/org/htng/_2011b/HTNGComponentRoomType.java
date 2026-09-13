package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.RoomTypeType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTNG_ComponentRoomType", propOrder = {"roomType", "telephoneExtensions", "hkStatus", "tpaExtensions"})
@XmlSeeAlso(HTNGRoomElementType.class)
public class HTNGComponentRoomType {
   @XmlElement(name = "RoomType", required = true)
   protected RoomTypeType roomType;
   @XmlElement(name = "TelephoneExtensions", required = true)
   protected HTNGTelephoneExtensionType telephoneExtensions;
   @XmlElement(name = "HKStatus", required = true)
   @XmlSchemaType(name = "string")
   protected HTNGHousekeepingStatusType hkStatus;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "RoomID", required = true)
   protected String roomID;

   public RoomTypeType getRoomType() {
      return this.roomType;
   }

   public void setRoomType(RoomTypeType value) {
      this.roomType = value;
   }

   public HTNGTelephoneExtensionType getTelephoneExtensions() {
      return this.telephoneExtensions;
   }

   public void setTelephoneExtensions(HTNGTelephoneExtensionType value) {
      this.telephoneExtensions = value;
   }

   public HTNGHousekeepingStatusType getHKStatus() {
      return this.hkStatus;
   }

   public void setHKStatus(HTNGHousekeepingStatusType value) {
      this.hkStatus = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public String getRoomID() {
      return this.roomID;
   }

   public void setRoomID(String value) {
      this.roomID = value;
   }
}
