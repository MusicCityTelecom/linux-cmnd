/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.soap.mychoice;

import com.tpvision.smartinstall.soap.mychoice.MonthlyCreditUsageRecord;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MonthlyCreditUsageList", propOrder={"monthlyCreditUsageRecord"})
public class MonthlyCreditUsageList {
    @XmlElement(name="MonthlyCreditUsageRecord", nillable=true)
    protected List<MonthlyCreditUsageRecord> monthlyCreditUsageRecord;

    public List<MonthlyCreditUsageRecord> getMonthlyCreditUsageRecord() {
        if (this.monthlyCreditUsageRecord == null) {
            this.monthlyCreditUsageRecord = new ArrayList<MonthlyCreditUsageRecord>();
        }
        return this.monthlyCreditUsageRecord;
    }
}

