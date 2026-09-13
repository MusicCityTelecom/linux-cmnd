/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.PreferLevelType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailAmenityType", propOrder={"railAmenity"})
public class RailAmenityType {
    @XmlElement(name="RailAmenity", required=true)
    protected List<RailAmenity> railAmenity;

    public List<RailAmenity> getRailAmenity() {
        if (this.railAmenity == null) {
            this.railAmenity = new ArrayList<RailAmenity>();
        }
        return this.railAmenity;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class RailAmenity {
        @XmlAttribute(name="Code", required=true)
        protected String code;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;
        @XmlAttribute(name="Quantity")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger quantity;
        @XmlAttribute(name="PreferLevel")
        protected PreferLevelType preferLevel;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getCodeContext() {
            return this.codeContext;
        }

        public void setCodeContext(String value) {
            this.codeContext = value;
        }

        public BigInteger getQuantity() {
            return this.quantity;
        }

        public void setQuantity(BigInteger value) {
            this.quantity = value;
        }

        public PreferLevelType getPreferLevel() {
            return this.preferLevel;
        }

        public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
        }
    }
}

