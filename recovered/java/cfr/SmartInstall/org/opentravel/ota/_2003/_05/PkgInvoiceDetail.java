/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.CostingItemType;
import org.opentravel.ota._2003._05.TaxesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PkgInvoiceDetail", propOrder={"costingItems", "grossAmount", "depositAmount", "agentCommission", "netAmount", "taxItems", "balanceDueAmount", "amountReceived"})
public class PkgInvoiceDetail {
    @XmlElement(name="CostingItems")
    protected CostingItems costingItems;
    @XmlElement(name="GrossAmount")
    protected GrossAmount grossAmount;
    @XmlElement(name="DepositAmount")
    protected DepositAmount depositAmount;
    @XmlElement(name="AgentCommission")
    protected List<AgentCommission> agentCommission;
    @XmlElement(name="NetAmount")
    protected NetAmount netAmount;
    @XmlElement(name="TaxItems")
    protected TaxesType taxItems;
    @XmlElement(name="BalanceDueAmount")
    protected BalanceDueAmount balanceDueAmount;
    @XmlElement(name="AmountReceived")
    protected AmountReceived amountReceived;

    public CostingItems getCostingItems() {
        return this.costingItems;
    }

    public void setCostingItems(CostingItems value) {
        this.costingItems = value;
    }

    public GrossAmount getGrossAmount() {
        return this.grossAmount;
    }

    public void setGrossAmount(GrossAmount value) {
        this.grossAmount = value;
    }

    public DepositAmount getDepositAmount() {
        return this.depositAmount;
    }

    public void setDepositAmount(DepositAmount value) {
        this.depositAmount = value;
    }

    public List<AgentCommission> getAgentCommission() {
        if (this.agentCommission == null) {
            this.agentCommission = new ArrayList<AgentCommission>();
        }
        return this.agentCommission;
    }

    public NetAmount getNetAmount() {
        return this.netAmount;
    }

    public void setNetAmount(NetAmount value) {
        this.netAmount = value;
    }

    public TaxesType getTaxItems() {
        return this.taxItems;
    }

    public void setTaxItems(TaxesType value) {
        this.taxItems = value;
    }

    public BalanceDueAmount getBalanceDueAmount() {
        return this.balanceDueAmount;
    }

    public void setBalanceDueAmount(BalanceDueAmount value) {
        this.balanceDueAmount = value;
    }

    public AmountReceived getAmountReceived() {
        return this.amountReceived;
    }

    public void setAmountReceived(AmountReceived value) {
        this.amountReceived = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class NetAmount {
        @XmlAttribute(name="PaymentDueDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar paymentDueDate;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public XMLGregorianCalendar getPaymentDueDate() {
            return this.paymentDueDate;
        }

        public void setPaymentDueDate(XMLGregorianCalendar value) {
            this.paymentDueDate = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class GrossAmount {
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
    public static class DepositAmount {
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
    @XmlType(name="", propOrder={"costingItem"})
    public static class CostingItems {
        @XmlElement(name="CostingItem", required=true)
        protected List<CostingItemType> costingItem;

        public List<CostingItemType> getCostingItem() {
            if (this.costingItem == null) {
                this.costingItem = new ArrayList<CostingItemType>();
            }
            return this.costingItem;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class BalanceDueAmount {
        @XmlAttribute(name="PaymentDueDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar paymentDueDate;
        @XmlAttribute(name="Amount")
        protected BigDecimal amount;
        @XmlAttribute(name="CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name="DecimalPlaces")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger decimalPlaces;

        public XMLGregorianCalendar getPaymentDueDate() {
            return this.paymentDueDate;
        }

        public void setPaymentDueDate(XMLGregorianCalendar value) {
            this.paymentDueDate = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class AmountReceived {
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
    public static class AgentCommission {
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

