/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CostingItemType", propOrder={"unitCost", "extendedCost", "commission"})
public class CostingItemType {
    @XmlElement(name="UnitCost", required=true)
    protected UnitCost unitCost;
    @XmlElement(name="ExtendedCost")
    protected ExtendedCost extendedCost;
    @XmlElement(name="Commission")
    protected Commission commission;
    @XmlAttribute(name="Description")
    protected String description;
    @XmlAttribute(name="InventoryItemRPH")
    protected String inventoryItemRPH;
    @XmlAttribute(name="PassengerRPH")
    protected String passengerRPH;
    @XmlAttribute(name="CostBasis")
    protected String costBasis;
    @XmlAttribute(name="Quantity")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger quantity;

    public UnitCost getUnitCost() {
        return this.unitCost;
    }

    public void setUnitCost(UnitCost value) {
        this.unitCost = value;
    }

    public ExtendedCost getExtendedCost() {
        return this.extendedCost;
    }

    public void setExtendedCost(ExtendedCost value) {
        this.extendedCost = value;
    }

    public Commission getCommission() {
        return this.commission;
    }

    public void setCommission(Commission value) {
        this.commission = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getInventoryItemRPH() {
        return this.inventoryItemRPH;
    }

    public void setInventoryItemRPH(String value) {
        this.inventoryItemRPH = value;
    }

    public String getPassengerRPH() {
        return this.passengerRPH;
    }

    public void setPassengerRPH(String value) {
        this.passengerRPH = value;
    }

    public String getCostBasis() {
        return this.costBasis;
    }

    public void setCostBasis(String value) {
        this.costBasis = value;
    }

    public BigInteger getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigInteger value) {
        this.quantity = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class UnitCost {
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public BigDecimal getAmount() {
            return this.amount;
        }

        public void setAmount(BigDecimal value) {
            this.amount = value;
        }

        public String getCurrencyCode() {
            return this.currencyCode;
        }

        public void setCurrencyCode(String value) {
            this.currencyCode = value;
        }

        public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
        }

        public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ExtendedCost {
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public BigDecimal getAmount() {
            return this.amount;
        }

        public void setAmount(BigDecimal value) {
            this.amount = value;
        }

        public String getCurrencyCode() {
            return this.currencyCode;
        }

        public void setCurrencyCode(String value) {
            this.currencyCode = value;
        }

        public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
        }

        public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Commission {
        @XmlAttribute(name="Description")
        protected String description;
        @XmlAttribute(name="Rate")
        protected BigDecimal rate;
        @XmlAttribute(name="CommissionType")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String commissionType;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String value) {
            this.description = value;
        }

        public BigDecimal getRate() {
            return this.rate;
        }

        public void setRate(BigDecimal value) {
            this.rate = value;
        }

        public String getCommissionType() {
            return this.commissionType;
        }

        public void setCommissionType(String value) {
            this.commissionType = value;
        }

        public BigDecimal getAmount() {
            return this.amount;
        }

        public void setAmount(BigDecimal value) {
            this.amount = value;
        }

        public String getCurrencyCode() {
            return this.currencyCode;
        }

        public void setCurrencyCode(String value) {
            this.currencyCode = value;
        }

        public BigInteger getDecimalPlaces() {
            return this.decimalPlaces;
        }

        public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
        }
    }
}

