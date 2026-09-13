package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TransactionActionType")
@XmlEnum
public enum TransactionActionType {
   @XmlEnumValue("Book")
   BOOK("Book"),
   @XmlEnumValue("Quote")
   QUOTE("Quote"),
   @XmlEnumValue("Hold")
   HOLD("Hold"),
   @XmlEnumValue("Initiate")
   INITIATE("Initiate"),
   @XmlEnumValue("Ignore")
   IGNORE("Ignore"),
   @XmlEnumValue("Modify")
   MODIFY("Modify"),
   @XmlEnumValue("Commit")
   COMMIT("Commit"),
   @XmlEnumValue("Cancel")
   CANCEL("Cancel"),
   @XmlEnumValue("CommitOverrideEdits")
   COMMIT_OVERRIDE_EDITS("CommitOverrideEdits"),
   @XmlEnumValue("VerifyPrice")
   VERIFY_PRICE("VerifyPrice"),
   @XmlEnumValue("Ticket")
   TICKET("Ticket");

   private final String value;

   TransactionActionType(String v) {
      this.value = v;
   }

   public String value() {
      return this.value;
   }

   public static TransactionActionType fromValue(String v) {
      for (TransactionActionType c : values()) {
         if (c.value.equals(v)) {
            return c;
         }
      }

      throw new IllegalArgumentException(v);
   }
}
