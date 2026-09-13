/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.AddressInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AddressesType", propOrder={"address"})
public class AddressesType {
    @XmlElement(name="Address", required=true)
    protected List<Address> address;

    public List<Address> getAddress() {
        if (this.address == null) {
            this.address = new ArrayList<Address>();
        }
        return this.address;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Address
    extends AddressInfoType {
        @XmlAttribute(name="Removal")
        protected Boolean removal;
        @XmlAttribute(name="ID")
        protected String id;

        public Boolean isRemoval() {
            return this.removal;
        }

        public void setRemoval(Boolean value) {
            this.removal = value;
        }

        public String getID() {
            return this.id;
        }

        public void setID(String value) {
            this.id = value;
        }
    }
}

