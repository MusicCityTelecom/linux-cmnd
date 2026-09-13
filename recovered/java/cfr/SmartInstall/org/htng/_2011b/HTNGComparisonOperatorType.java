/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="HTNG_ComparisonOperatorType")
@XmlEnum
public enum HTNGComparisonOperatorType {
    EQUALS("Equals"),
    DOES_NOT_EQUAL("Does Not Equal"),
    CONTAINS("Contains"),
    DOES_NOT_CONTAIN("Does Not Contain"),
    BEGINS_WITH("Begins With"),
    DOES_NOT_BEGIN_WITH("Does Not Begin With"),
    ENDS_WITH("Ends With"),
    DOES_NOT_END_WITH("Does Not End With"),
    CONTAINS_DATA("Contains Data"),
    DOES_NOT_CONTAIN_DATA("Does Not Contain Data"),
    IS_GREATER_THAN("Is Greater Than"),
    IS_GREATER_THAN_OR_EQUAL_TO("Is Greater Than or Equal To"),
    IS_LESS_THAN("Is Less Than"),
    IS_LESS_THAN_OR_EQUAL_TO("Is Less Than or Equal To");

    private final String value;

    private HTNGComparisonOperatorType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static HTNGComparisonOperatorType fromValue(String v) {
        for (HTNGComparisonOperatorType c : HTNGComparisonOperatorType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

