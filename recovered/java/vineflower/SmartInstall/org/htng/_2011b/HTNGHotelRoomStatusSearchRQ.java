package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"room", "tpaExtensions"})
@XmlRootElement(name = "HTNG_HotelRoomStatusSearchRQ")
public class HTNGHotelRoomStatusSearchRQ extends HTNGRequestBaseType {
   @XmlElement(name = "Room")
   protected HTNGComponentRoomType room;
   @XmlElement(name = "TPA_Extensions", namespace = "http://www.opentravel.org/OTA/2003/05")
   protected TPAExtensionsType tpaExtensions;

   public HTNGComponentRoomType getRoom() {
      return this.room;
   }

   public void setRoom(HTNGComponentRoomType value) {
      this.room = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }
}
