/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.DateTimeSpanType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BlackoutDateType", propOrder={"blackoutDate"})
public class BlackoutDateType {
    @XmlElement(name="BlackoutDate", required=true)
    protected List<DateTimeSpanType> blackoutDate;

    public List<DateTimeSpanType> getBlackoutDate() {
        if (this.blackoutDate == null) {
            this.blackoutDate = new ArrayList<DateTimeSpanType>();
        }
        return this.blackoutDate;
    }
}

