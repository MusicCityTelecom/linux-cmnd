package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeetingRoomCodeType", propOrder = {"charge", "multimediaDescriptions"})
public class MeetingRoomCodeType {
   @XmlElement(name = "Charge")
   protected FeeType charge;
   @XmlElement(name = "MultimediaDescriptions")
   protected MultimediaDescriptionsType multimediaDescriptions;
   @XmlAttribute(name = "Code")
   protected String code;
   @XmlAttribute(name = "ExistsCode")
   protected String existsCode;
   @XmlAttribute(name = "DiscountsAvailableCode")
   protected String discountsAvailableCode;
   @XmlAttribute(name = "ID")
   protected String id;
   @XmlAttribute(name = "CodeDetail")
   protected String codeDetail;
   @XmlAttribute(name = "Removal")
   protected Boolean removal;
   @XmlAttribute(name = "Quantity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger quantity;

   public FeeType getCharge() {
      return this.charge;
   }

   public void setCharge(FeeType value) {
      this.charge = value;
   }

   public MultimediaDescriptionsType getMultimediaDescriptions() {
      return this.multimediaDescriptions;
   }

   public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
      this.multimediaDescriptions = value;
   }

   public String getCode() {
      return this.code;
   }

   public void setCode(String value) {
      this.code = value;
   }

   public String getExistsCode() {
      return this.existsCode;
   }

   public void setExistsCode(String value) {
      this.existsCode = value;
   }

   public String getDiscountsAvailableCode() {
      return this.discountsAvailableCode;
   }

   public void setDiscountsAvailableCode(String value) {
      this.discountsAvailableCode = value;
   }

   public String getID() {
      return this.id;
   }

   public void setID(String value) {
      this.id = value;
   }

   public String getCodeDetail() {
      return this.codeDetail;
   }

   public void setCodeDetail(String value) {
      this.codeDetail = value;
   }

   public Boolean isRemoval() {
      return this.removal;
   }

   public void setRemoval(Boolean value) {
      this.removal = value;
   }

   public BigInteger getQuantity() {
      return this.quantity;
   }

   public void setQuantity(BigInteger value) {
      this.quantity = value;
   }
}
