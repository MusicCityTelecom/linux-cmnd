/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.SeatAvailabilityType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CabinAvailType", propOrder={"seat", "baggageAllowance"})
public class CabinAvailType {
    @XmlElement(name="Seat", required=true)
    protected List<SeatAvailabilityType> seat;
    @XmlElement(name="BaggageAllowance")
    protected BaggageAllowance baggageAllowance;
    @XmlAttribute(name="Meal")
    protected String meal;
    @XmlAttribute(name="Code")
    protected String code;
    @XmlAttribute(name="Name")
    protected String name;

    public List<SeatAvailabilityType> getSeat() {
        if (this.seat == null) {
            this.seat = new ArrayList<SeatAvailabilityType>();
        }
        return this.seat;
    }

    public BaggageAllowance getBaggageAllowance() {
        return this.baggageAllowance;
    }

    public void setBaggageAllowance(BaggageAllowance value) {
        this.baggageAllowance = value;
    }

    public String getMeal() {
        return this.meal;
    }

    public void setMeal(String value) {
        this.meal = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class BaggageAllowance {
        @XmlAttribute(name="UnitOfMeasureQuantity")
        protected BigDecimal unitOfMeasureQuantity;
        @XmlAttribute(name="UnitOfMeasure")
        protected String unitOfMeasure;
        @XmlAttribute(name="UnitOfMeasureCode")
        protected String unitOfMeasureCode;

        public BigDecimal getUnitOfMeasureQuantity() {
            return this.unitOfMeasureQuantity;
        }

        public void setUnitOfMeasureQuantity(BigDecimal value) {
            this.unitOfMeasureQuantity = value;
        }

        public String getUnitOfMeasure() {
            return this.unitOfMeasure;
        }

        public void setUnitOfMeasure(String value) {
            this.unitOfMeasure = value;
        }

        public String getUnitOfMeasureCode() {
            return this.unitOfMeasureCode;
        }

        public void setUnitOfMeasureCode(String value) {
            this.unitOfMeasureCode = value;
        }
    }
}

