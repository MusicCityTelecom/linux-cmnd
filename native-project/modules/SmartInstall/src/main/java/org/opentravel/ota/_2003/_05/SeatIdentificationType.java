package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeatIdentificationType")
public class SeatIdentificationType {
   @XmlAttribute(name = "CarNumber", required = true)
   @XmlSchemaType(name = "positiveInteger")
   protected BigInteger carNumber;
   @XmlAttribute(name = "SeatNumber", required = true)
   @XmlSchemaType(name = "anySimpleType")
   protected String seatNumber;
   @XmlAttribute(name = "Deck")
   protected DeckType deck;

   public BigInteger getCarNumber() {
      return this.carNumber;
   }

   public void setCarNumber(BigInteger value) {
      this.carNumber = value;
   }

   public String getSeatNumber() {
      return this.seatNumber;
   }

   public void setSeatNumber(String value) {
      this.seatNumber = value;
   }

   public DeckType getDeck() {
      return this.deck;
   }

   public void setDeck(DeckType value) {
      this.deck = value;
   }
}
