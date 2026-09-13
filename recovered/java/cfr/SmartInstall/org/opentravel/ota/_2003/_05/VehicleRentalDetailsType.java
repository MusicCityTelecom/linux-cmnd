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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.FormattedTextTextType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehicleRentalDetailsType", propOrder={"fuelLevelDetails", "odometerReading", "conditionReport"})
public class VehicleRentalDetailsType {
    @XmlElement(name="FuelLevelDetails")
    protected FuelLevelDetails fuelLevelDetails;
    @XmlElement(name="OdometerReading")
    protected OdometerReading odometerReading;
    @XmlElement(name="ConditionReport")
    protected List<ConditionReport> conditionReport;
    @XmlAttribute(name="ParkingLocation")
    protected String parkingLocation;

    public FuelLevelDetails getFuelLevelDetails() {
        return this.fuelLevelDetails;
    }

    public void setFuelLevelDetails(FuelLevelDetails value) {
        this.fuelLevelDetails = value;
    }

    public OdometerReading getOdometerReading() {
        return this.odometerReading;
    }

    public void setOdometerReading(OdometerReading value) {
        this.odometerReading = value;
    }

    public List<ConditionReport> getConditionReport() {
        if (this.conditionReport == null) {
            this.conditionReport = new ArrayList<ConditionReport>();
        }
        return this.conditionReport;
    }

    public String getParkingLocation() {
        return this.parkingLocation;
    }

    public void setParkingLocation(String value) {
        this.parkingLocation = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class OdometerReading {
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class FuelLevelDetails {
        @XmlAttribute(name="FuelLevelValue")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String fuelLevelValue;
        @XmlAttribute(name="UnitOfMeasureQuantity")
        protected BigDecimal unitOfMeasureQuantity;
        @XmlAttribute(name="UnitOfMeasure")
        protected String unitOfMeasure;
        @XmlAttribute(name="UnitOfMeasureCode")
        protected String unitOfMeasureCode;

        public String getFuelLevelValue() {
            return this.fuelLevelValue;
        }

        public void setFuelLevelValue(String value) {
            this.fuelLevelValue = value;
        }

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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ConditionReport
    extends FormattedTextTextType {
        @XmlAttribute(name="Condition")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String condition;

        public String getCondition() {
            return this.condition;
        }

        public void setCondition(String value) {
            this.condition = value;
        }
    }
}

