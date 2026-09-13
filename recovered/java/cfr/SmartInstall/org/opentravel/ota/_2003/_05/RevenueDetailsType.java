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
import org.opentravel.ota._2003._05.RevenueDetailType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RevenueDetailsType", propOrder={"revenueDetail"})
public class RevenueDetailsType {
    @XmlElement(name="RevenueDetail", required=true)
    protected List<RevenueDetailType> revenueDetail;

    public List<RevenueDetailType> getRevenueDetail() {
        if (this.revenueDetail == null) {
            this.revenueDetail = new ArrayList<RevenueDetailType>();
        }
        return this.revenueDetail;
    }
}

