package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleEquipmentType", propOrder = "description")
@XmlSeeAlso(VehicleRentalTransactionType.PricedEquips.PricedEquip.Equipment.class)
public class VehicleEquipmentType {
   @XmlElement(name = "Description")
   protected String description;
   @XmlAttribute(name = "Restriction")
   protected EquipmentRestrictionType restriction;
   @XmlAttribute(name = "EquipType", required = true)
   protected String equipType;
   @XmlAttribute(name = "Quantity")
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger quantity;

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      this.description = value;
   }

   public EquipmentRestrictionType getRestriction() {
      return this.restriction;
   }

   public void setRestriction(EquipmentRestrictionType value) {
      this.restriction = value;
   }

   public String getEquipType() {
      return this.equipType;
   }

   public void setEquipType(String value) {
      this.equipType = value;
   }

   public BigInteger getQuantity() {
      return this.quantity;
   }

   public void setQuantity(BigInteger value) {
      this.quantity = value;
   }
}
