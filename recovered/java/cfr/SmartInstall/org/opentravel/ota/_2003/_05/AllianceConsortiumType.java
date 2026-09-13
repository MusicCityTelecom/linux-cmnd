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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.CompanyNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AllianceConsortiumType", propOrder={"allianceMember"})
public class AllianceConsortiumType {
    @XmlElement(name="AllianceMember")
    protected List<AllianceMember> allianceMember;
    @XmlAttribute(name="ID")
    protected String id;
    @XmlAttribute(name="EffectiveDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar effectiveDate;
    @XmlAttribute(name="ExpireDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar expireDate;
    @XmlAttribute(name="ExpireDateExclusiveIndicator")
    protected Boolean expireDateExclusiveIndicator;

    public List<AllianceMember> getAllianceMember() {
        if (this.allianceMember == null) {
            this.allianceMember = new ArrayList<AllianceMember>();
        }
        return this.allianceMember;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public XMLGregorianCalendar getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    public XMLGregorianCalendar getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(XMLGregorianCalendar value) {
        this.expireDate = value;
    }

    public Boolean isExpireDateExclusiveIndicator() {
        return this.expireDateExclusiveIndicator;
    }

    public void setExpireDateExclusiveIndicator(Boolean value) {
        this.expireDateExclusiveIndicator = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class AllianceMember
    extends CompanyNameType {
        @XmlAttribute(name="MemberCode")
        protected String memberCode;

        public String getMemberCode() {
            return this.memberCode;
        }

        public void setMemberCode(String value) {
            this.memberCode = value;
        }
    }
}

