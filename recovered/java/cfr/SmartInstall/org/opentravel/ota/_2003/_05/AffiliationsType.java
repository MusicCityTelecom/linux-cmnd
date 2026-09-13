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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.EmployerType;
import org.opentravel.ota._2003._05.InsuranceType;
import org.opentravel.ota._2003._05.OrganizationType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.TravelArrangerType;
import org.opentravel.ota._2003._05.TravelClubType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AffiliationsType", propOrder={"organization", "employer", "travelArranger", "travelClub", "insurance", "tpaExtensions"})
public class AffiliationsType {
    @XmlElement(name="Organization")
    protected List<OrganizationType> organization;
    @XmlElement(name="Employer")
    protected List<EmployerType> employer;
    @XmlElement(name="TravelArranger")
    protected List<TravelArrangerType> travelArranger;
    @XmlElement(name="TravelClub")
    protected List<TravelClubType> travelClub;
    @XmlElement(name="Insurance")
    protected List<InsuranceType> insurance;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;

    public List<OrganizationType> getOrganization() {
        if (this.organization == null) {
            this.organization = new ArrayList<OrganizationType>();
        }
        return this.organization;
    }

    public List<EmployerType> getEmployer() {
        if (this.employer == null) {
            this.employer = new ArrayList<EmployerType>();
        }
        return this.employer;
    }

    public List<TravelArrangerType> getTravelArranger() {
        if (this.travelArranger == null) {
            this.travelArranger = new ArrayList<TravelArrangerType>();
        }
        return this.travelArranger;
    }

    public List<TravelClubType> getTravelClub() {
        if (this.travelClub == null) {
            this.travelClub = new ArrayList<TravelClubType>();
        }
        return this.travelClub;
    }

    public List<InsuranceType> getInsurance() {
        if (this.insurance == null) {
            this.insurance = new ArrayList<InsuranceType>();
        }
        return this.insurance;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public String getShareSynchInd() {
        return this.shareSynchInd;
    }

    public void setShareSynchInd(String value) {
        this.shareSynchInd = value;
    }

    public String getShareMarketInd() {
        return this.shareMarketInd;
    }

    public void setShareMarketInd(String value) {
        this.shareMarketInd = value;
    }
}

