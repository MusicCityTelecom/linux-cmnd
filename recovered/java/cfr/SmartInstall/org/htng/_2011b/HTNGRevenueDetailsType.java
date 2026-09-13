/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.htng._2011b.HTNGRevenueDetailType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HTNG_RevenueDetailsType", propOrder={"revenueDetail"})
public class HTNGRevenueDetailsType {
    @XmlElement(name="RevenueDetail", required=true)
    protected List<HTNGRevenueDetailType> revenueDetail;

    public List<HTNGRevenueDetailType> getRevenueDetail() {
        if (this.revenueDetail == null) {
            this.revenueDetail = new ArrayList<HTNGRevenueDetailType>();
        }
        return this.revenueDetail;
    }
}

