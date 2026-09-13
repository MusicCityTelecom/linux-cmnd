package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookedTrainSegmentType", propOrder = {"classCode", "comment", "assignment"})
public class BookedTrainSegmentType extends TrainSegmentType {
   @XmlElement(name = "ClassCode")
   protected ClassCodeType classCode;
   @XmlElement(name = "Comment")
   protected List<FreeTextType> comment;
   @XmlElement(name = "Assignment")
   protected List<BookedTrainSegmentType.Assignment> assignment;
   @XmlAttribute(name = "BookStatus", required = true)
   protected String bookStatus;
   @XmlAttribute(name = "TicketStatus")
   protected String ticketStatus;

   public ClassCodeType getClassCode() {
      return this.classCode;
   }

   public void setClassCode(ClassCodeType value) {
      this.classCode = value;
   }

   public List<FreeTextType> getComment() {
      if (this.comment == null) {
         this.comment = new ArrayList<>();
      }

      return this.comment;
   }

   public List<BookedTrainSegmentType.Assignment> getAssignment() {
      if (this.assignment == null) {
         this.assignment = new ArrayList<>();
      }

      return this.assignment;
   }

   public String getBookStatus() {
      return this.bookStatus;
   }

   public void setBookStatus(String value) {
      this.bookStatus = value;
   }

   public String getTicketStatus() {
      return this.ticketStatus;
   }

   public void setTicketStatus(String value) {
      this.ticketStatus = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class Assignment extends AccommodationServiceType {
      @XmlAttribute(name = "TravelerRPH")
      protected String travelerRPH;

      public String getTravelerRPH() {
         return this.travelerRPH;
      }

      public void setTravelerRPH(String value) {
         this.travelerRPH = value;
      }
   }
}
