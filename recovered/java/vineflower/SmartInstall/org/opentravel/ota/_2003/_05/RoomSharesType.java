package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomSharesType", propOrder = "roomShare")
public class RoomSharesType {
   @XmlElement(name = "RoomShare", required = true)
   protected List<RoomSharesType.RoomShare> roomShare;

   public List<RoomSharesType.RoomShare> getRoomShare() {
      if (this.roomShare == null) {
         this.roomShare = new ArrayList<>();
      }

      return this.roomShare;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "guestRPHs")
   public static class RoomShare {
      @XmlElement(name = "GuestRPHs")
      protected RoomSharesType.RoomShare.GuestRPHs guestRPHs;

      public RoomSharesType.RoomShare.GuestRPHs getGuestRPHs() {
         return this.guestRPHs;
      }

      public void setGuestRPHs(RoomSharesType.RoomShare.GuestRPHs value) {
         this.guestRPHs = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "", propOrder = "guestRPH")
      public static class GuestRPHs {
         @XmlElement(name = "GuestRPH", required = true)
         protected List<RoomSharesType.RoomShare.GuestRPHs.GuestRPH> guestRPH;

         public List<RoomSharesType.RoomShare.GuestRPHs.GuestRPH> getGuestRPH() {
            if (this.guestRPH == null) {
               this.guestRPH = new ArrayList<>();
            }

            return this.guestRPH;
         }

         @XmlAccessorType(XmlAccessType.FIELD)
         @XmlType(name = "", propOrder = "value")
         public static class GuestRPH {
            @XmlValue
            protected String value;
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;

            public String getValue() {
               return this.value;
            }

            public void setValue(String value) {
               this.value = value;
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
         }
      }
   }
}
