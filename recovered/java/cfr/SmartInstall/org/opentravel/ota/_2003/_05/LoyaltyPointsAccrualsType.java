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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="LoyaltyPointsAccrualsType", propOrder={"selectedLoyalty"})
public class LoyaltyPointsAccrualsType {
    @XmlElement(name="SelectedLoyalty", required=true)
    protected List<SelectedLoyalty> selectedLoyalty;

    public List<SelectedLoyalty> getSelectedLoyalty() {
        if (this.selectedLoyalty == null) {
            this.selectedLoyalty = new ArrayList<SelectedLoyalty>();
        }
        return this.selectedLoyalty;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class SelectedLoyalty {
        @XmlAttribute(name="ReservationActionType")
        protected String reservationActionType;
        @XmlAttribute(name="SelectedLoyaltyRPH")
        protected String selectedLoyaltyRPH;
        @XmlAttribute(name="ProgramCode")
        protected String programCode;
        @XmlAttribute(name="BonusCode")
        protected String bonusCode;
        @XmlAttribute(name="AccountID")
        protected String accountID;
        @XmlAttribute(name="PointsEarned")
        protected String pointsEarned;

        public String getReservationActionType() {
            return this.reservationActionType;
        }

        public void setReservationActionType(String value) {
            this.reservationActionType = value;
        }

        public String getSelectedLoyaltyRPH() {
            return this.selectedLoyaltyRPH;
        }

        public void setSelectedLoyaltyRPH(String value) {
            this.selectedLoyaltyRPH = value;
        }

        public String getProgramCode() {
            return this.programCode;
        }

        public void setProgramCode(String value) {
            this.programCode = value;
        }

        public String getBonusCode() {
            return this.bonusCode;
        }

        public void setBonusCode(String value) {
            this.bonusCode = value;
        }

        public String getAccountID() {
            return this.accountID;
        }

        public void setAccountID(String value) {
            this.accountID = value;
        }

        public String getPointsEarned() {
            return this.pointsEarned;
        }

        public void setPointsEarned(String value) {
            this.pointsEarned = value;
        }
    }
}

