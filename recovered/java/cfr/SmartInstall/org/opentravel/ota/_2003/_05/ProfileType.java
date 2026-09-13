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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.AccessesType;
import org.opentravel.ota._2003._05.AffiliationsType;
import org.opentravel.ota._2003._05.AgreementsType;
import org.opentravel.ota._2003._05.CompanyInfoType;
import org.opentravel.ota._2003._05.CustomerType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.PreferencesType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.TransferActionType;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.opentravel.ota._2003._05.YesNoType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ProfileType", propOrder={"accesses", "customer", "userID", "prefCollections", "companyInfo", "affiliations", "agreements", "comments", "tpaExtensions"})
public class ProfileType {
    @XmlElement(name="Accesses")
    protected AccessesType accesses;
    @XmlElement(name="Customer")
    protected CustomerType customer;
    @XmlElement(name="UserID")
    protected List<UserID> userID;
    @XmlElement(name="PrefCollections")
    protected PreferencesType prefCollections;
    @XmlElement(name="CompanyInfo")
    protected CompanyInfoType companyInfo;
    @XmlElement(name="Affiliations")
    protected AffiliationsType affiliations;
    @XmlElement(name="Agreements")
    protected AgreementsType agreements;
    @XmlElement(name="Comments")
    protected Comments comments;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="RPH")
    protected String rph;
    @XmlAttribute(name="StatusCode")
    protected List<String> statusCode;
    @XmlAttribute(name="CreateDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar createDateTime;
    @XmlAttribute(name="CreatorID")
    protected String creatorID;
    @XmlAttribute(name="LastModifyDateTime")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar lastModifyDateTime;
    @XmlAttribute(name="LastModifierID")
    protected String lastModifierID;
    @XmlAttribute(name="PurgeDate")
    @XmlSchemaType(name="date")
    protected XMLGregorianCalendar purgeDate;
    @XmlAttribute(name="ProfileType")
    protected String profileType;
    @XmlAttribute(name="ShareAllSynchInd")
    protected YesNoType shareAllSynchInd;
    @XmlAttribute(name="ShareAllMarketInd")
    protected YesNoType shareAllMarketInd;
    @XmlAttribute(name="ShareAllOptOutInd")
    protected YesNoType shareAllOptOutInd;

    public AccessesType getAccesses() {
        return this.accesses;
    }

    public void setAccesses(AccessesType value) {
        this.accesses = value;
    }

    public CustomerType getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerType value) {
        this.customer = value;
    }

    public List<UserID> getUserID() {
        if (this.userID == null) {
            this.userID = new ArrayList<UserID>();
        }
        return this.userID;
    }

    public PreferencesType getPrefCollections() {
        return this.prefCollections;
    }

    public void setPrefCollections(PreferencesType value) {
        this.prefCollections = value;
    }

    public CompanyInfoType getCompanyInfo() {
        return this.companyInfo;
    }

    public void setCompanyInfo(CompanyInfoType value) {
        this.companyInfo = value;
    }

    public AffiliationsType getAffiliations() {
        return this.affiliations;
    }

    public void setAffiliations(AffiliationsType value) {
        this.affiliations = value;
    }

    public AgreementsType getAgreements() {
        return this.agreements;
    }

    public void setAgreements(AgreementsType value) {
        this.agreements = value;
    }

    public Comments getComments() {
        return this.comments;
    }

    public void setComments(Comments value) {
        this.comments = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public String getRPH() {
        return this.rph;
    }

    public void setRPH(String value) {
        this.rph = value;
    }

    public List<String> getStatusCode() {
        if (this.statusCode == null) {
            this.statusCode = new ArrayList<String>();
        }
        return this.statusCode;
    }

    public XMLGregorianCalendar getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(XMLGregorianCalendar value) {
        this.createDateTime = value;
    }

    public String getCreatorID() {
        return this.creatorID;
    }

    public void setCreatorID(String value) {
        this.creatorID = value;
    }

    public XMLGregorianCalendar getLastModifyDateTime() {
        return this.lastModifyDateTime;
    }

    public void setLastModifyDateTime(XMLGregorianCalendar value) {
        this.lastModifyDateTime = value;
    }

    public String getLastModifierID() {
        return this.lastModifierID;
    }

    public void setLastModifierID(String value) {
        this.lastModifierID = value;
    }

    public XMLGregorianCalendar getPurgeDate() {
        return this.purgeDate;
    }

    public void setPurgeDate(XMLGregorianCalendar value) {
        this.purgeDate = value;
    }

    public String getProfileType() {
        return this.profileType;
    }

    public void setProfileType(String value) {
        this.profileType = value;
    }

    public YesNoType getShareAllSynchInd() {
        return this.shareAllSynchInd;
    }

    public void setShareAllSynchInd(YesNoType value) {
        this.shareAllSynchInd = value;
    }

    public YesNoType getShareAllMarketInd() {
        return this.shareAllMarketInd;
    }

    public void setShareAllMarketInd(YesNoType value) {
        this.shareAllMarketInd = value;
    }

    public YesNoType getShareAllOptOutInd() {
        return this.shareAllOptOutInd;
    }

    public void setShareAllOptOutInd(YesNoType value) {
        this.shareAllOptOutInd = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class UserID
    extends UniqueIDType {
        @XmlAttribute(name="PinNumber")
        protected String pinNumber;

        public String getPinNumber() {
            return this.pinNumber;
        }

        public void setPinNumber(String value) {
            this.pinNumber = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"comment"})
    public static class Comments {
        @XmlElement(name="Comment", required=true)
        protected List<Comment> comment;

        public List<Comment> getComment() {
            if (this.comment == null) {
                this.comment = new ArrayList<Comment>();
            }
            return this.comment;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="", propOrder={"authorizedViewer"})
        public static class Comment
        extends ParagraphType {
            @XmlElement(name="AuthorizedViewer")
            protected List<AuthorizedViewer> authorizedViewer;
            @XmlAttribute(name="CommentOriginatorCode")
            protected String commentOriginatorCode;
            @XmlAttribute(name="GuestViewable")
            protected Boolean guestViewable;
            @XmlAttribute(name="Category")
            protected String category;
            @XmlAttribute(name="AirlineVendorPrefRPH")
            protected String airlineVendorPrefRPH;
            @XmlAttribute(name="TransferAction")
            protected TransferActionType transferAction;
            @XmlAttribute(name="ActionDate")
            @XmlSchemaType(name="date")
            protected XMLGregorianCalendar actionDate;

            public List<AuthorizedViewer> getAuthorizedViewer() {
                if (this.authorizedViewer == null) {
                    this.authorizedViewer = new ArrayList<AuthorizedViewer>();
                }
                return this.authorizedViewer;
            }

            public String getCommentOriginatorCode() {
                return this.commentOriginatorCode;
            }

            public void setCommentOriginatorCode(String value) {
                this.commentOriginatorCode = value;
            }

            public Boolean isGuestViewable() {
                return this.guestViewable;
            }

            public void setGuestViewable(Boolean value) {
                this.guestViewable = value;
            }

            public String getCategory() {
                return this.category;
            }

            public void setCategory(String value) {
                this.category = value;
            }

            public String getAirlineVendorPrefRPH() {
                return this.airlineVendorPrefRPH;
            }

            public void setAirlineVendorPrefRPH(String value) {
                this.airlineVendorPrefRPH = value;
            }

            public TransferActionType getTransferAction() {
                return this.transferAction;
            }

            public void setTransferAction(TransferActionType value) {
                this.transferAction = value;
            }

            public XMLGregorianCalendar getActionDate() {
                return this.actionDate;
            }

            public void setActionDate(XMLGregorianCalendar value) {
                this.actionDate = value;
            }

            @XmlAccessorType(value=XmlAccessType.FIELD)
            @XmlType(name="")
            public static class AuthorizedViewer {
                @XmlAttribute(name="ViewerCode")
                protected String viewerCode;

                public String getViewerCode() {
                    return this.viewerCode;
                }

                public void setViewerCode(String value) {
                    this.viewerCode = value;
                }
            }
        }
    }
}

