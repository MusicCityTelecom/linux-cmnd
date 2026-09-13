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
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CustomerCountsType", propOrder={"customerCount"})
public class CustomerCountsType {
    @XmlElement(name="CustomerCount", required=true)
    protected List<CustomerCount> customerCount;

    public List<CustomerCount> getCustomerCount() {
        if (this.customerCount == null) {
            this.customerCount = new ArrayList<CustomerCount>();
        }
        return this.customerCount;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CustomerCount {
        @XmlAttribute(name="Age")
        protected Integer age;
        @XmlAttribute(name="Code")
        protected String code;
        @XmlAttribute(name="CodeContext")
        protected String codeContext;
        @XmlAttribute(name="URI")
        @XmlSchemaType(name="anyURI")
        protected String uri;
        @XmlAttribute(name="Quantity")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger quantity;
        @XmlAttribute(name="BirthDate")
        @XmlSchemaType(name="date")
        protected XMLGregorianCalendar birthDate;

        public Integer getAge() {
            return this.age;
        }

        public void setAge(Integer value) {
            this.age = value;
        }

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

        public String getURI() {
            return this.uri;
        }

        public void setURI(String value) {
            this.uri = value;
        }

        public BigInteger getQuantity() {
            return this.quantity;
        }

        public void setQuantity(BigInteger value) {
            this.quantity = value;
        }

        public XMLGregorianCalendar getBirthDate() {
            return this.birthDate;
        }

        public void setBirthDate(XMLGregorianCalendar value) {
            this.birthDate = value;
        }
    }
}

