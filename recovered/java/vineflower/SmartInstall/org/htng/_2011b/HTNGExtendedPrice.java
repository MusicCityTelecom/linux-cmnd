package org.htng._2011b;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.TotalType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTNG_ExtendedPrice")
public class HTNGExtendedPrice extends TotalType {
   @XmlAttribute(name = "Quantity")
   @XmlSchemaType(name = "nonNegativeInteger")
   protected BigInteger quantity;

   public BigInteger getQuantity() {
      return this.quantity;
   }

   public void setQuantity(BigInteger value) {
      this.quantity = value;
   }
}
