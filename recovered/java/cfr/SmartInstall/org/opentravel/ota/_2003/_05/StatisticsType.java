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
import org.opentravel.ota._2003._05.StatisticType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StatisticsType", propOrder={"statistic"})
public class StatisticsType {
    @XmlElement(name="Statistic", required=true)
    protected List<StatisticType> statistic;

    public List<StatisticType> getStatistic() {
        if (this.statistic == null) {
            this.statistic = new ArrayList<StatisticType>();
        }
        return this.statistic;
    }
}

