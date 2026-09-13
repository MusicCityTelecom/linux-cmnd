package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.RoomTypeType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTNG_BasicOrSuiteRoomType", propOrder = "componentRooms")
public class HTNGBasicOrSuiteRoomType extends RoomTypeType {
   @XmlElement(name = "ComponentRooms")
   protected HTNGComponentRoomsType componentRooms;

   public HTNGComponentRoomsType getComponentRooms() {
      return this.componentRooms;
   }

   public void setComponentRooms(HTNGComponentRoomsType value) {
      this.componentRooms = value;
   }
}
