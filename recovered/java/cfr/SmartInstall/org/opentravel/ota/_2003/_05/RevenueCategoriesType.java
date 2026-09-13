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
import org.opentravel.ota._2003._05.RevenueCategoryType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RevenueCategoriesType", propOrder={"revenueCategory"})
public class RevenueCategoriesType {
    @XmlElement(name="RevenueCategory", required=true)
    protected List<RevenueCategoryType> revenueCategory;

    public List<RevenueCategoryType> getRevenueCategory() {
        if (this.revenueCategory == null) {
            this.revenueCategory = new ArrayList<RevenueCategoryType>();
        }
        return this.revenueCategory;
    }
}

