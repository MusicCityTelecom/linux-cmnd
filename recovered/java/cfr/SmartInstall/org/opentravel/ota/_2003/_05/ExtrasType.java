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
import org.opentravel.ota._2003._05.CustomerCountsType;
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.ExtrasCoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ExtrasType", propOrder={"date", "passengerCounts", "additionalInfo"})
public class ExtrasType
extends ExtrasCoreType {
    @XmlElement(name="Date")
    protected DateTimeSpanType date;
    @XmlElement(name="PassengerCounts")
    protected CustomerCountsType passengerCounts;
    @XmlElement(name="AdditionalInfo")
    protected String additionalInfo;
    @XmlAttribute(name="ListOfPassengerRPH")
    protected List<String> listOfPassengerRPH;

    public DateTimeSpanType getDate() {
        return this.date;
    }

    public void setDate(DateTimeSpanType value) {
        this.date = value;
    }

    public CustomerCountsType getPassengerCounts() {
        return this.passengerCounts;
    }

    public void setPassengerCounts(CustomerCountsType value) {
        this.passengerCounts = value;
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }

    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

    public List<String> getListOfPassengerRPH() {
        if (this.listOfPassengerRPH == null) {
            this.listOfPassengerRPH = new ArrayList<String>();
        }
        return this.listOfPassengerRPH;
    }
}

