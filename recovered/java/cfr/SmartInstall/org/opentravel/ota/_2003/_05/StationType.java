/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.LocationType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StationType")
public class StationType
extends LocationType {
    @XmlAttribute(name="IsStaffedInd")
    protected Boolean isStaffedInd;
    @XmlAttribute(name="TicketPrinterInd")
    protected Boolean ticketPrinterInd;
    @XmlAttribute(name="SST_MachineInd")
    protected Boolean sstMachineInd;
    @XmlAttribute(name="TimeZoneOffset")
    protected String timeZoneOffset;

    public Boolean isIsStaffedInd() {
        return this.isStaffedInd;
    }

    public void setIsStaffedInd(Boolean value) {
        this.isStaffedInd = value;
    }

    public Boolean isTicketPrinterInd() {
        return this.ticketPrinterInd;
    }

    public void setTicketPrinterInd(Boolean value) {
        this.ticketPrinterInd = value;
    }

    public Boolean isSSTMachineInd() {
        return this.sstMachineInd;
    }

    public void setSSTMachineInd(Boolean value) {
        this.sstMachineInd = value;
    }

    public String getTimeZoneOffset() {
        return this.timeZoneOffset;
    }

    public void setTimeZoneOffset(String value) {
        this.timeZoneOffset = value;
    }
}

