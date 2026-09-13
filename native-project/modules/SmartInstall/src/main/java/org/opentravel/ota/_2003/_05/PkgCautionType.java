package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PkgCautionType")
public class PkgCautionType extends FormattedTextTextType {
   @XmlAttribute(name = "Type")
   protected String type;
   @XmlAttribute(name = "ID")
   protected String id;
   @XmlAttribute(name = "ListOfItineraryItemRPH")
   protected List<String> listOfItineraryItemRPH;
   @XmlAttribute(name = "ListOfExtraRPH")
   protected List<String> listOfExtraRPH;
   @XmlAttribute(name = "Start")
   protected String start;
   @XmlAttribute(name = "Duration")
   protected String duration;
   @XmlAttribute(name = "End")
   protected String end;

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getID() {
      return this.id;
   }

   public void setID(String value) {
      this.id = value;
   }

   public List<String> getListOfItineraryItemRPH() {
      if (this.listOfItineraryItemRPH == null) {
         this.listOfItineraryItemRPH = new ArrayList<>();
      }

      return this.listOfItineraryItemRPH;
   }

   public List<String> getListOfExtraRPH() {
      if (this.listOfExtraRPH == null) {
         this.listOfExtraRPH = new ArrayList<>();
      }

      return this.listOfExtraRPH;
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
