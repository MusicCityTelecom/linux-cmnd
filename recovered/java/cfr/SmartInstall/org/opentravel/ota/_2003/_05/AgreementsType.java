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
import org.opentravel.ota._2003._05.AllianceConsortiumType;
import org.opentravel.ota._2003._05.CertificationType;
import org.opentravel.ota._2003._05.CommissionInfoType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.TransferActionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AgreementsType", propOrder={"certification", "allianceConsortium", "commissionInfo", "profileSecurity", "contractInformation", "tpaExtensions"})
public class AgreementsType {
    @XmlElement(name="Certification")
    protected List<CertificationType> certification;
    @XmlElement(name="AllianceConsortium")
    protected List<AllianceConsortiumType> allianceConsortium;
    @XmlElement(name="CommissionInfo")
    protected List<CommissionInfoType> commissionInfo;
    @XmlElement(name="ProfileSecurity")
    protected List<ProfileSecurity> profileSecurity;
    @XmlElement(name="ContractInformation")
    protected ParagraphType contractInformation;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;

    public List<CertificationType> getCertification() {
        if (this.certification == null) {
            this.certification = new ArrayList<CertificationType>();
        }
        return this.certification;
    }

    public List<AllianceConsortiumType> getAllianceConsortium() {
        if (this.allianceConsortium == null) {
            this.allianceConsortium = new ArrayList<AllianceConsortiumType>();
        }
        return this.allianceConsortium;
    }

    public List<CommissionInfoType> getCommissionInfo() {
        if (this.commissionInfo == null) {
            this.commissionInfo = new ArrayList<CommissionInfoType>();
        }
        return this.commissionInfo;
    }

    public List<ProfileSecurity> getProfileSecurity() {
        if (this.profileSecurity == null) {
            this.profileSecurity = new ArrayList<ProfileSecurity>();
        }
        return this.profileSecurity;
    }

    public ParagraphType getContractInformation() {
        return this.contractInformation;
    }

    public void setContractInformation(ParagraphType value) {
        this.contractInformation = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ProfileSecurity {
        @XmlAttribute(name="AccessingOrganizationType")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String accessingOrganizationType;
        @XmlAttribute(name="AccessingOrganizationID")
        protected String accessingOrganizationID;
        @XmlAttribute(name="AccessType")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String accessType;
        @XmlAttribute(name="TransferAction")
        protected TransferActionType transferAction;

        public String getAccessingOrganizationType() {
            return this.accessingOrganizationType;
        }

        public void setAccessingOrganizationType(String value) {
            this.accessingOrganizationType = value;
        }

        public String getAccessingOrganizationID() {
            return this.accessingOrganizationID;
        }

        public void setAccessingOrganizationID(String value) {
            this.accessingOrganizationID = value;
        }

        public String getAccessType() {
            return this.accessType;
        }

        public void setAccessType(String value) {
            this.accessType = value;
        }

        public TransferActionType getTransferAction() {
            return this.transferAction;
        }

        public void setTransferAction(TransferActionType value) {
            this.transferAction = value;
        }
    }
}

