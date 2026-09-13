package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RuleInfoType", propOrder = {"resTicketingRules", "lengthOfStayRules", "chargesRules"})
@XmlSeeAlso(FareInfoType.RuleInfo.class)
public class RuleInfoType {
   @XmlElement(name = "ResTicketingRules")
   protected RuleInfoType.ResTicketingRules resTicketingRules;
   @XmlElement(name = "LengthOfStayRules")
   protected StayRestrictionsType lengthOfStayRules;
   @XmlElement(name = "ChargesRules")
   protected RuleInfoType.ChargesRules chargesRules;

   public RuleInfoType.ResTicketingRules getResTicketingRules() {
      return this.resTicketingRules;
   }

   public void setResTicketingRules(RuleInfoType.ResTicketingRules value) {
      this.resTicketingRules = value;
   }

   public StayRestrictionsType getLengthOfStayRules() {
      return this.lengthOfStayRules;
   }

   public void setLengthOfStayRules(StayRestrictionsType value) {
      this.lengthOfStayRules = value;
   }

   public RuleInfoType.ChargesRules getChargesRules() {
      return this.chargesRules;
   }

   public void setChargesRules(RuleInfoType.ChargesRules value) {
      this.chargesRules = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = {"voluntaryChanges", "voluntaryRefunds"})
   public static class ChargesRules {
      @XmlElement(name = "VoluntaryChanges")
      protected VoluntaryChangesType voluntaryChanges;
      @XmlElement(name = "VoluntaryRefunds")
      protected VoluntaryChangesType voluntaryRefunds;

      public VoluntaryChangesType getVoluntaryChanges() {
         return this.voluntaryChanges;
      }

      public void setVoluntaryChanges(VoluntaryChangesType value) {
         this.voluntaryChanges = value;
      }

      public VoluntaryChangesType getVoluntaryRefunds() {
         return this.voluntaryRefunds;
      }

      public void setVoluntaryRefunds(VoluntaryChangesType value) {
         this.voluntaryRefunds = value;
      }
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "", propOrder = "advResTicketing")
   public static class ResTicketingRules {
      @XmlElement(name = "AdvResTicketing")
      protected RuleInfoType.ResTicketingRules.AdvResTicketing advResTicketing;

      public RuleInfoType.ResTicketingRules.AdvResTicketing getAdvResTicketing() {
         return this.advResTicketing;
      }

      public void setAdvResTicketing(RuleInfoType.ResTicketingRules.AdvResTicketing value) {
         this.advResTicketing = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(name = "")
      public static class AdvResTicketing extends AdvResTicketingType {
         @XmlAttribute(name = "FirstTicketDate")
         protected String firstTicketDate;
         @XmlAttribute(name = "LastTicketDate")
         protected String lastTicketDate;

         public String getFirstTicketDate() {
            return this.firstTicketDate;
         }

         public void setFirstTicketDate(String value) {
            this.firstTicketDate = value;
         }

         public String getLastTicketDate() {
            return this.lastTicketDate;
         }

         public void setLastTicketDate(String value) {
            this.lastTicketDate = value;
         }
      }
   }
}
