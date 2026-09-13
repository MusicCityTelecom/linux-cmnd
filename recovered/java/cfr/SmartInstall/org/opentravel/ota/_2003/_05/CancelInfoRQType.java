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
import org.opentravel.ota._2003._05.PersonNameType;
import org.opentravel.ota._2003._05.TransactionActionType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CancelInfoRQType", propOrder={"uniqueID", "personName"})
public class CancelInfoRQType {
    @XmlElement(name="UniqueID", required=true)
    protected List<UniqueIDType> uniqueID;
    @XmlElement(name="PersonName")
    protected PersonNameType personName;
    @XmlAttribute(name="CancelType", required=true)
    protected TransactionActionType cancelType;

    public List<UniqueIDType> getUniqueID() {
        if (this.uniqueID == null) {
            this.uniqueID = new ArrayList<UniqueIDType>();
        }
        return this.uniqueID;
    }

    public PersonNameType getPersonName() {
        return this.personName;
    }

    public void setPersonName(PersonNameType value) {
        this.personName = value;
    }

    public TransactionActionType getCancelType() {
        return this.cancelType;
    }

    public void setCancelType(TransactionActionType value) {
        this.cancelType = value;
    }
}

