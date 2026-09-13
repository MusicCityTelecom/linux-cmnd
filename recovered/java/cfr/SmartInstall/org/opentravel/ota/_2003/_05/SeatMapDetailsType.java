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
import org.opentravel.ota._2003._05.CabinClassType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SeatMapDetailsType", propOrder={"cabinClass"})
public class SeatMapDetailsType {
    @XmlElement(name="CabinClass", required=true)
    protected List<CabinClass> cabinClass;
    @XmlAttribute(name="TravelerRefNumberRPHs")
    protected List<String> travelerRefNumberRPHs;

    public List<CabinClass> getCabinClass() {
        if (this.cabinClass == null) {
            this.cabinClass = new ArrayList<CabinClass>();
        }
        return this.cabinClass;
    }

    public List<String> getTravelerRefNumberRPHs() {
        if (this.travelerRefNumberRPHs == null) {
            this.travelerRefNumberRPHs = new ArrayList<String>();
        }
        return this.travelerRefNumberRPHs;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class CabinClass
    extends CabinClassType {
        @XmlAttribute(name="StartingRow")
        protected Integer startingRow;
        @XmlAttribute(name="EndingRow")
        protected Integer endingRow;

        public Integer getStartingRow() {
            return this.startingRow;
        }

        public void setStartingRow(Integer value) {
            this.startingRow = value;
        }

        public Integer getEndingRow() {
            return this.endingRow;
        }

        public void setEndingRow(Integer value) {
            this.endingRow = value;
        }
    }
}

