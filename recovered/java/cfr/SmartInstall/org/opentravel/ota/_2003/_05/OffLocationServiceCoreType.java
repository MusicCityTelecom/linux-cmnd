/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AddressType;
import org.opentravel.ota._2003._05.OffLocationServiceIDType;
import org.opentravel.ota._2003._05.OffLocationServiceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OffLocationServiceCoreType", propOrder={"address"})
@XmlSeeAlso(value={OffLocationServiceType.class})
public class OffLocationServiceCoreType {
    @XmlElement(name="Address")
    protected Address address;
    @XmlAttribute(name="Type", required=true)
    protected OffLocationServiceIDType type;

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address value) {
        this.address = value;
    }

    public OffLocationServiceIDType getType() {
        return this.type;
    }

    public void setType(OffLocationServiceIDType value) {
        this.type = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Address
    extends AddressType {
        @XmlAttribute(name="SiteID")
        protected String siteID;
        @XmlAttribute(name="SiteName")
        protected String siteName;

        public String getSiteID() {
            return this.siteID;
        }

        public void setSiteID(String value) {
            this.siteID = value;
        }

        public String getSiteName() {
            return this.siteName;
        }

        public void setSiteName(String value) {
            this.siteName = value;
        }
    }
}

