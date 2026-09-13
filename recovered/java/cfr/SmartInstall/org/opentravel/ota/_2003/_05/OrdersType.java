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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OrdersType", propOrder={"order"})
public class OrdersType {
    @XmlElement(name="Order")
    protected List<Order> order;
    @XmlAttribute(name="OrderType")
    protected String orderType;
    @XmlAttribute(name="DiscountCode")
    protected String discountCode;
    @XmlAttribute(name="VendorPurchaseOrderID")
    protected String vendorPurchaseOrderID;
    @XmlAttribute(name="OrderID")
    protected String orderID;

    public List<Order> getOrder() {
        if (this.order == null) {
            this.order = new ArrayList<Order>();
        }
        return this.order;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String value) {
        this.orderType = value;
    }

    public String getDiscountCode() {
        return this.discountCode;
    }

    public void setDiscountCode(String value) {
        this.discountCode = value;
    }

    public String getVendorPurchaseOrderID() {
        return this.vendorPurchaseOrderID;
    }

    public void setVendorPurchaseOrderID(String value) {
        this.vendorPurchaseOrderID = value;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public void setOrderID(String value) {
        this.orderID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"products"})
    public static class Order {
        @XmlElement(name="Products")
        protected Products products;
        @XmlAttribute(name="OrderType")
        protected String orderType;
        @XmlAttribute(name="OrderID")
        protected String orderID;
        @XmlAttribute(name="ListOfRecipientRPH")
        protected List<String> listOfRecipientRPH;

        public Products getProducts() {
            return this.products;
        }

        public void setProducts(Products value) {
            this.products = value;
        }

        public String getOrderType() {
            return this.orderType;
        }

        public void setOrderType(String value) {
            this.orderType = value;
        }

        public String getOrderID() {
            return this.orderID;
        }

        public void setOrderID(String value) {
            this.orderID = value;
        }

        public List<String> getListOfRecipientRPH() {
            if (this.listOfRecipientRPH == null) {
                this.listOfRecipientRPH = new ArrayList<String>();
            }
            return this.listOfRecipientRPH;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"product"})
        public static class Products {
            @XmlElement(name="Product")
            protected List<Product> product;

            public List<Product> getProduct() {
                if (this.product == null) {
                    this.product = new ArrayList<Product>();
                }
                return this.product;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class Product {
                @XmlAttribute(name="ProductIssueDate")
                protected String productIssueDate;
                @XmlAttribute(name="ProductID")
                protected String productID;
                @XmlAttribute(name="ProductType")
                protected String productType;
                @XmlAttribute(name="ProductQuantity")
                @XmlSchemaType(name="nonNegativeInteger")
                protected BigInteger productQuantity;
                @XmlAttribute(name="ProductSerialNumber")
                protected String productSerialNumber;
                @XmlAttribute(name="DiscountCode")
                protected String discountCode;
                @XmlAttribute(name="Status")
                @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
                protected String status;
                @XmlAttribute(name="ListOfRecipientRPH")
                protected List<String> listOfRecipientRPH;
                @XmlAttribute(name="Amount")
                protected BigDecimal amount;
                @XmlAttribute(name="CurrencyCode")
                protected String currencyCode;
                @XmlAttribute(name="DecimalPlaces")
                @XmlSchemaType(name="nonNegativeInteger")
                protected BigInteger decimalPlaces;

                public String getProductIssueDate() {
                    return this.productIssueDate;
                }

                public void setProductIssueDate(String value) {
                    this.productIssueDate = value;
                }

                public String getProductID() {
                    return this.productID;
                }

                public void setProductID(String value) {
                    this.productID = value;
                }

                public String getProductType() {
                    return this.productType;
                }

                public void setProductType(String value) {
                    this.productType = value;
                }

                public BigInteger getProductQuantity() {
                    return this.productQuantity;
                }

                public void setProductQuantity(BigInteger value) {
                    this.productQuantity = value;
                }

                public String getProductSerialNumber() {
                    return this.productSerialNumber;
                }

                public void setProductSerialNumber(String value) {
                    this.productSerialNumber = value;
                }

                public String getDiscountCode() {
                    return this.discountCode;
                }

                public void setDiscountCode(String value) {
                    this.discountCode = value;
                }

                public String getStatus() {
                    return this.status;
                }

                public void setStatus(String value) {
                    this.status = value;
                }

                public List<String> getListOfRecipientRPH() {
                    if (this.listOfRecipientRPH == null) {
                        this.listOfRecipientRPH = new ArrayList<String>();
                    }
                    return this.listOfRecipientRPH;
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
    }
}

