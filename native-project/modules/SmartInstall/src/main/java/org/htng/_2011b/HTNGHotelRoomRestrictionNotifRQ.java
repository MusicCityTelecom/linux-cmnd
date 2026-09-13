package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.TPAExtensionsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"systems", "room", "tpaExtensions"})
@XmlRootElement(name = "HTNG_HotelRoomRestrictionNotifRQ")
public class HTNGHotelRoomRestrictionNotifRQ extends HTNGRequestBaseType {
   @XmlElement(name = "Systems", required = true)
   protected HTNGHotelRoomRestrictionNotifRQ.Systems systems;
   @XmlElement(name = "Room")
   protected HTNGComponentRoomType room;
   @XmlElement(name = "TPA_Extensions", namespace = "http://www.opentravel.org/OTA/2003/05")
   protected TPAExtensionsType tpaExtensions;

   public HTNGHotelRoomRestrictionNotifRQ.Systems getSystems() {
      return this.systems;
   }

   public void setSystems(HTNGHotelRoomRestrictionNotifRQ.Systems value) {
      this.systems = value;
   }

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

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "system")
   public static class Systems {
      @XmlElement(name = "System", required = true)
      protected List<HTNGHotelRoomRestrictionNotifRQ.Systems.System> system;

      public List<HTNGHotelRoomRestrictionNotifRQ.Systems.System> getSystem() {
         if (this.system == null) {
            this.system = new ArrayList<>();
         }

         return this.system;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "restrictions")
      public static class System {
         @XmlElement(name = "Restrictions")
         protected HTNGHotelRoomRestrictionNotifRQ.Systems.System.Restrictions restrictions;
         @XmlAttribute(name = "Type")
         protected String type;
         @XmlAttribute(name = "Status")
         protected String status;

         public HTNGHotelRoomRestrictionNotifRQ.Systems.System.Restrictions getRestrictions() {
            return this.restrictions;
         }

         public void setRestrictions(HTNGHotelRoomRestrictionNotifRQ.Systems.System.Restrictions value) {
            this.restrictions = value;
         }

         public String getType() {
            return this.type;
         }

         public void setType(String value) {
            this.type = value;
         }

         public String getStatus() {
            return this.status;
         }

         public void setStatus(String value) {
            this.status = value;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "restriction")
         public static class Restrictions {
            @XmlElement(name = "Restriction", required = true)
            protected List<HTNGHotelRoomRestrictionNotifRQ.Systems.System.Restrictions.Restriction> restriction;

            public List<HTNGHotelRoomRestrictionNotifRQ.Systems.System.Restrictions.Restriction> getRestriction() {
               if (this.restriction == null) {
                  this.restriction = new ArrayList<>();
               }

               return this.restriction;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Restriction {
               @XmlAttribute(name = "Type")
               protected String type;
               @XmlAttribute(name = "Status")
               protected String status;
               @XmlAttribute(name = "Level")
               protected String level;

               public String getType() {
                  return this.type;
               }

               public void setType(String value) {
                  this.type = value;
               }

               public String getStatus() {
                  return this.status;
               }

               public void setStatus(String value) {
                  this.status = value;
               }

               public String getLevel() {
                  return this.level;
               }

               public void setLevel(String value) {
                  this.level = value;
               }
            }
         }
      }
   }
}
