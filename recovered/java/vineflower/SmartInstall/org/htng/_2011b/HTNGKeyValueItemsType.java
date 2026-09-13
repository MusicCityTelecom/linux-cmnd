package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HTNG_KeyValueItemsType", propOrder = "keyValueItem")
public class HTNGKeyValueItemsType {
   @XmlElement(name = "KeyValueItem", required = true)
   protected List<HTNGKeyValueItemsType.KeyValueItem> keyValueItem;

   public List<HTNGKeyValueItemsType.KeyValueItem> getKeyValueItem() {
      if (this.keyValueItem == null) {
         this.keyValueItem = new ArrayList<>();
      }

      return this.keyValueItem;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(name = "")
   public static class KeyValueItem {
      @XmlAttribute(name = "Key", required = true)
      protected String key;
      @XmlAttribute(name = "Operator")
      protected HTNGComparisonOperatorType operator;
      @XmlAttribute(name = "Value", required = true)
      protected String value;

      public String getKey() {
         return this.key;
      }

      public void setKey(String value) {
         this.key = value;
      }

      public HTNGComparisonOperatorType getOperator() {
         return this.operator == null ? HTNGComparisonOperatorType.EQUALS : this.operator;
      }

      public void setOperator(HTNGComparisonOperatorType value) {
         this.operator = value;
      }

      public String getValue() {
         return this.value;
      }

      public void setValue(String value) {
         this.value = value;
      }
   }
}
