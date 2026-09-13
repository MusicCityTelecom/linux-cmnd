package org.htng._2011b;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTNG_RoomElementType", propOrder = "componentRooms")
public class HTNGRoomElementType extends HTNGComponentRoomType {
   @XmlElement(name = "ComponentRooms")
   protected HTNGComponentRoomsType componentRooms;

   public HTNGComponentRoomsType getComponentRooms() {
      return this.componentRooms;
   }

   public void setComponentRooms(HTNGComponentRoomsType value) {
      this.componentRooms = value;
   }
}
