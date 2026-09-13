/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.DonationType;
import org.opentravel.ota._2003._05.HotelResRequestType;
import org.opentravel.ota._2003._05.RebateType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"donationInformation", "rebatePrograms"})
@XmlRootElement(name="OTA_HotelResRQ")
public class OTAHotelResRQ
extends HotelResRequestType {
    @XmlElement(name="DonationInformation")
    protected DonationType donationInformation;
    @XmlElement(name="RebatePrograms")
    protected RebatePrograms rebatePrograms;

    public DonationType getDonationInformation() {
        return this.donationInformation;
    }

    public void setDonationInformation(DonationType value) {
        this.donationInformation = value;
    }

    public RebatePrograms getRebatePrograms() {
        return this.rebatePrograms;
    }

    public void setRebatePrograms(RebatePrograms value) {
        this.rebatePrograms = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"rebateProgram"})
    public static class RebatePrograms {
        @XmlElement(name="RebateProgram")
        protected List<RebateType> rebateProgram;

        public List<RebateType> getRebateProgram() {
            if (this.rebateProgram == null) {
                this.rebateProgram = new ArrayList<RebateType>();
            }
            return this.rebateProgram;
        }
    }
}

