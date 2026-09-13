/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MembershipType", propOrder={"membership"})
public class MembershipType {
    @XmlElement(name="Membership", required=true)
    protected List<Membership> membership;

    public List<Membership> getMembership() {
        if (this.membership == null) {
            this.membership = new ArrayList<Membership>();
        }
        return this.membership;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Membership {
        @XmlAttribute(name="ProgramCode")
        protected String programCode;
        @XmlAttribute(name="BonusCode")
        protected String bonusCode;
        @XmlAttribute(name="AccountID")
        protected String accountID;
        @XmlAttribute(name="PointsEarned")
        protected BigInteger pointsEarned;
        @XmlAttribute(name="TravelSector")
        protected String travelSector;

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

        public BigInteger getPointsEarned() {
            return this.pointsEarned;
        }

        public void setPointsEarned(BigInteger value) {
            this.pointsEarned = value;
        }

        public String getTravelSector() {
            return this.travelSector;
        }

        public void setTravelSector(String value) {
            this.travelSector = value;
        }
    }
}

