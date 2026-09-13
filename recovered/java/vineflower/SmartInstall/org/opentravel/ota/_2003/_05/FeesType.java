package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeesType", propOrder = "fee")
@XmlSeeAlso(GuestRoomType.RoomLevelFees.class)
public class FeesType {
   @XmlElement(name = "Fee", required = true)
   protected List<FeeType> fee;

   public List<FeeType> getFee() {
      if (this.fee == null) {
         this.fee = new ArrayList<>();
      }

      return this.fee;
   }
}
