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
import org.opentravel.ota._2003._05.OperationScheduleType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OperationSchedulesType", propOrder={"operationSchedule"})
public class OperationSchedulesType {
    @XmlElement(name="OperationSchedule", required=true)
    protected List<OperationScheduleType> operationSchedule;
    @XmlAttribute(name="Start")
    protected String start;
    @XmlAttribute(name="Duration")
    protected String duration;
    @XmlAttribute(name="End")
    protected String end;

    public List<OperationScheduleType> getOperationSchedule() {
        if (this.operationSchedule == null) {
            this.operationSchedule = new ArrayList<OperationScheduleType>();
        }
        return this.operationSchedule;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String value) {
        this.start = value;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String value) {
        this.end = value;
    }
}

