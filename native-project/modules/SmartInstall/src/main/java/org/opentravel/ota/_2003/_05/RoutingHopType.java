package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoutingHopType", propOrder = "routingHop")
public class RoutingHopType {
   @XmlElement(name = "RoutingHop", required = true)
   protected List<RoutingHopType.RoutingHop> routingHop;

   public List<RoutingHopType.RoutingHop> getRoutingHop() {
      if (this.routingHop == null) {
         this.routingHop = new ArrayList<>();
      }

      return this.routingHop;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class RoutingHop {
      @XmlAttribute(name = "SystemCode")
      protected String systemCode;
      @XmlAttribute(name = "LocalRefID")
      protected String localRefID;
      @XmlAttribute(name = "TimeStamp")
      @XmlSchemaType(name = "dateTime")
      protected XMLGregorianCalendar timeStamp;
      @XmlAttribute(name = "Comment")
      protected String comment;
      @XmlAttribute(name = "SequenceNmbr")
      protected BigInteger sequenceNmbr;
      @XmlAttribute(name = "Data")
      protected String data;

      public String getSystemCode() {
         return this.systemCode;
      }

      public void setSystemCode(String value) {
         this.systemCode = value;
      }

      public String getLocalRefID() {
         return this.localRefID;
      }

      public void setLocalRefID(String value) {
         this.localRefID = value;
      }

      public XMLGregorianCalendar getTimeStamp() {
         return this.timeStamp;
      }

      public void setTimeStamp(XMLGregorianCalendar value) {
         this.timeStamp = value;
      }

      public String getComment() {
         return this.comment;
      }

      public void setComment(String value) {
         this.comment = value;
      }

      public BigInteger getSequenceNmbr() {
         return this.sequenceNmbr;
      }

      public void setSequenceNmbr(BigInteger value) {
         this.sequenceNmbr = value;
      }

      public String getData() {
         return this.data;
      }

      public void setData(String value) {
         this.data = value;
      }
   }
}
