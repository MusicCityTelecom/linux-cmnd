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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.FreeTextType;
import org.opentravel.ota._2003._05.PersonNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccessesType", propOrder={"access"})
public class AccessesType {
    @XmlElement(name="Access", required=true)
    protected List<Access> access;
    @XmlAttribute(name="CreateDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar createDateTime;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;

    public List<Access> getAccess() {
        if (this.access == null) {
            this.access = new ArrayList<Access>();
        }
        return this.access;
    }

    public XMLGregorianCalendar getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(XMLGregorianCalendar value) {
        this.createDateTime = value;
    }

    public String getShareSynchInd() {
        return this.shareSynchInd;
    }

    public void setShareSynchInd(String value) {
        this.shareSynchInd = value;
    }

    public String getShareMarketInd() {
        return this.shareMarketInd;
    }

    public void setShareMarketInd(String value) {
        this.shareMarketInd = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"accessPerson", "accessComment"})
    public static class Access {
        @XmlElement(name="AccessPerson")
        protected PersonNameType accessPerson;
        @XmlElement(name="AccessComment")
        protected FreeTextType accessComment;
        @XmlAttribute(name="ActionType")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String actionType;
        @XmlAttribute(name="ActionDateTime")
        @XmlSchemaType(name="dateTime")
        protected XMLGregorianCalendar actionDateTime;
        @XmlAttribute(name="ID", required=true)
        protected String id;

        public PersonNameType getAccessPerson() {
            return this.accessPerson;
        }

        public void setAccessPerson(PersonNameType value) {
            this.accessPerson = value;
        }

        public FreeTextType getAccessComment() {
            return this.accessComment;
        }

        public void setAccessComment(FreeTextType value) {
            this.accessComment = value;
        }

        public String getActionType() {
            return this.actionType;
        }

        public void setActionType(String value) {
            this.actionType = value;
        }

        public XMLGregorianCalendar getActionDateTime() {
            return this.actionDateTime;
        }

        public void setActionDateTime(XMLGregorianCalendar value) {
            this.actionDateTime = value;
        }

        public String getID() {
            return this.id;
        }

        public void setID(String value) {
            this.id = value;
        }
    }
}

