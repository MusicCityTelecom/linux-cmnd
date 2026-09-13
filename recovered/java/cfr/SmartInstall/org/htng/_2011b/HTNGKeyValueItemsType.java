/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.htng._2011b.HTNGComparisonOperatorType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_KeyValueItemsType", propOrder={"keyValueItem"})
public class HTNGKeyValueItemsType {
    @XmlElement(name="KeyValueItem", required=true)
    protected List<KeyValueItem> keyValueItem;

    public List<KeyValueItem> getKeyValueItem() {
        if (this.keyValueItem == null) {
            this.keyValueItem = new ArrayList<KeyValueItem>();
        }
        return this.keyValueItem;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class KeyValueItem {
        @XmlAttribute(name="Key", required=true)
        protected String key;
        @XmlAttribute(name="Operator")
        protected HTNGComparisonOperatorType operator;
        @XmlAttribute(name="Value", required=true)
        protected String value;

        public String getKey() {
            return this.key;
        }

        public void setKey(String value) {
            this.key = value;
        }

        public HTNGComparisonOperatorType getOperator() {
            if (this.operator == null) {
                return HTNGComparisonOperatorType.EQUALS;
            }
            return this.operator;
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

