/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ContactInfoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ContactInfoRootType")
public class ContactInfoRootType
extends ContactInfoType {
    @XmlAttribute(name="ContactProfileID")
    protected String contactProfileID;
    @XmlAttribute(name="ContactProfileType")
    protected String contactProfileType;
    @XmlAttribute(name="LastUpdated")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar lastUpdated;
    @XmlAttribute(name="Removal")
    protected Boolean removal;

    public String getContactProfileID() {
        return this.contactProfileID;
    }

    public void setContactProfileID(String value) {
        this.contactProfileID = value;
    }

    public String getContactProfileType() {
        return this.contactProfileType;
    }

    public void setContactProfileType(String value) {
        this.contactProfileType = value;
    }

    public XMLGregorianCalendar getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(XMLGregorianCalendar value) {
        this.lastUpdated = value;
    }

    public Boolean isRemoval() {
        return this.removal;
    }

    public void setRemoval(Boolean value) {
        this.removal = value;
    }
}

