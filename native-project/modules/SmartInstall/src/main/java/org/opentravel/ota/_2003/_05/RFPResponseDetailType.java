package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RFP_ResponseDetailType", propOrder = "comments")
public class RFPResponseDetailType {
   @XmlElement(name = "Comments")
   protected RFPResponseDetailType.Comments comments;
   @XmlAttribute(name = "DetailIncludedIndicator")
   protected Boolean detailIncludedIndicator;
   @XmlAttribute(name = "DeclineIndicator")
   protected Boolean declineIndicator;
   @XmlAttribute(name = "DeclineReasonCode")
   protected String declineReasonCode;
   @XmlAttribute(name = "CodeDetail")
   protected String codeDetail;
   @XmlAttribute(name = "MinimumTime")
   protected Duration minimumTime;
   @XmlAttribute(name = "MaximumTime")
   protected Duration maximumTime;
   @XmlAttribute(name = "ResponseMethod")
   protected String responseMethod;
   @XmlAttribute(name = "ResponseTimeUnit")
   protected String responseTimeUnit;

   public RFPResponseDetailType.Comments getComments() {
      return this.comments;
   }

   public void setComments(RFPResponseDetailType.Comments value) {
      this.comments = value;
   }

   public Boolean isDetailIncludedIndicator() {
      return this.detailIncludedIndicator;
   }

   public void setDetailIncludedIndicator(Boolean value) {
      this.detailIncludedIndicator = value;
   }

   public Boolean isDeclineIndicator() {
      return this.declineIndicator;
   }

   public void setDeclineIndicator(Boolean value) {
      this.declineIndicator = value;
   }

   public String getDeclineReasonCode() {
      return this.declineReasonCode;
   }

   public void setDeclineReasonCode(String value) {
      this.declineReasonCode = value;
   }

   public String getCodeDetail() {
      return this.codeDetail;
   }

   public void setCodeDetail(String value) {
      this.codeDetail = value;
   }

   public Duration getMinimumTime() {
      return this.minimumTime;
   }

   public void setMinimumTime(Duration value) {
      this.minimumTime = value;
   }

   public Duration getMaximumTime() {
      return this.maximumTime;
   }

   public void setMaximumTime(Duration value) {
      this.maximumTime = value;
   }

   public String getResponseMethod() {
      return this.responseMethod;
   }

   public void setResponseMethod(String value) {
      this.responseMethod = value;
   }

   public String getResponseTimeUnit() {
      return this.responseTimeUnit;
   }

   public void setResponseTimeUnit(String value) {
      this.responseTimeUnit = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "comment")
   public static class Comments {
      @XmlElement(name = "Comment", required = true)
      protected List<ParagraphType> comment;

      public List<ParagraphType> getComment() {
         if (this.comment == null) {
            this.comment = new ArrayList<>();
         }

         return this.comment;
      }
   }
}
