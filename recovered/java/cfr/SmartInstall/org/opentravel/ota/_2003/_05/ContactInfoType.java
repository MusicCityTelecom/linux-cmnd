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
import org.opentravel.ota._2003._05.AddressesType;
import org.opentravel.ota._2003._05.AreaInfoType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.ContactInfoRootType;
import org.opentravel.ota._2003._05.ContactsType;
import org.opentravel.ota._2003._05.EmailsType;
import org.opentravel.ota._2003._05.HotelInfoType;
import org.opentravel.ota._2003._05.PhonesType;
import org.opentravel.ota._2003._05.URLsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ContactInfoType", propOrder={"names", "addresses", "phones", "emails", "urLs", "companyName"})
@XmlSeeAlso(value={AreaInfoType.Attractions.Attraction.Contact.class, ContactInfoRootType.class, HotelInfoType.OwnershipManagementInfos.OwnershipManagementInfo.class})
public class ContactInfoType {
    @XmlElement(name="Names")
    protected ContactsType names;
    @XmlElement(name="Addresses")
    protected AddressesType addresses;
    @XmlElement(name="Phones")
    protected PhonesType phones;
    @XmlElement(name="Emails")
    protected EmailsType emails;
    @XmlElement(name="URLs")
    protected URLsType urLs;
    @XmlElement(name="CompanyName")
    protected CompanyName companyName;
    @XmlAttribute(name="Location")
    protected String location;

    public ContactsType getNames() {
        return this.names;
    }

    public void setNames(ContactsType value) {
        this.names = value;
    }

    public AddressesType getAddresses() {
        return this.addresses;
    }

    public void setAddresses(AddressesType value) {
        this.addresses = value;
    }

    public PhonesType getPhones() {
        return this.phones;
    }

    public void setPhones(PhonesType value) {
        this.phones = value;
    }

    public EmailsType getEmails() {
        return this.emails;
    }

    public void setEmails(EmailsType value) {
        this.emails = value;
    }

    public URLsType getURLs() {
        return this.urLs;
    }

    public void setURLs(URLsType value) {
        this.urLs = value;
    }

    public CompanyName getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(CompanyName value) {
        this.companyName = value;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String value) {
        this.location = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CompanyName
    extends CompanyNameType {
        @XmlAttribute(name="ID")
        protected String id;

        public String getID() {
            return this.id;
        }

        public void setID(String value) {
            this.id = value;
        }
    }
}

