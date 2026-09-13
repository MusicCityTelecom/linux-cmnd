package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BlackoutDateType", propOrder = "blackoutDate")
public class BlackoutDateType {
   @XmlElement(name = "BlackoutDate", required = true)
   protected List<DateTimeSpanType> blackoutDate;

   public List<DateTimeSpanType> getBlackoutDate() {
      if (this.blackoutDate == null) {
         this.blackoutDate = new ArrayList<>();
      }

      return this.blackoutDate;
   }
}
