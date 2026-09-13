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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.AddressPrefType;
import org.opentravel.ota._2003._05.EmployeeInfoType;
import org.opentravel.ota._2003._05.InsurancePrefType;
import org.opentravel.ota._2003._05.InterestPrefType;
import org.opentravel.ota._2003._05.LoyaltyPrefType;
import org.opentravel.ota._2003._05.MealPrefType;
import org.opentravel.ota._2003._05.MediaEntertainPrefType;
import org.opentravel.ota._2003._05.NamePrefType;
import org.opentravel.ota._2003._05.PaymentFormPrefType;
import org.opentravel.ota._2003._05.PetInfoPrefType;
import org.opentravel.ota._2003._05.PhonePrefType;
import org.opentravel.ota._2003._05.PreferLevelType;
import org.opentravel.ota._2003._05.RelatedTravelerPrefType;
import org.opentravel.ota._2003._05.SeatingPrefType;
import org.opentravel.ota._2003._05.SpecRequestPrefType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.TicketDistribPrefType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CommonPrefType", propOrder={"namePref", "phonePref", "addressPref", "paymentFormPref", "interestPref", "insurancePref", "seatingPref", "ticketDistribPref", "mediaEntertainPref", "petInfoPref", "mealPref", "loyaltyPref", "specRequestPref", "relatedTravelerPref", "contactPref", "employeeLevelInfo", "tpaExtensions"})
public class CommonPrefType {
    @XmlElement(name="NamePref")
    protected List<NamePrefType> namePref;
    @XmlElement(name="PhonePref")
    protected List<PhonePrefType> phonePref;
    @XmlElement(name="AddressPref")
    protected List<AddressPrefType> addressPref;
    @XmlElement(name="PaymentFormPref")
    protected List<PaymentFormPrefType> paymentFormPref;
    @XmlElement(name="InterestPref")
    protected List<InterestPrefType> interestPref;
    @XmlElement(name="InsurancePref")
    protected List<InsurancePrefType> insurancePref;
    @XmlElement(name="SeatingPref")
    protected List<SeatingPrefType> seatingPref;
    @XmlElement(name="TicketDistribPref")
    protected List<TicketDistribPrefType> ticketDistribPref;
    @XmlElement(name="MediaEntertainPref")
    protected List<MediaEntertainPrefType> mediaEntertainPref;
    @XmlElement(name="PetInfoPref")
    protected List<PetInfoPrefType> petInfoPref;
    @XmlElement(name="MealPref")
    protected List<MealPrefType> mealPref;
    @XmlElement(name="LoyaltyPref")
    protected List<LoyaltyPrefType> loyaltyPref;
    @XmlElement(name="SpecRequestPref")
    protected List<SpecRequestPrefType> specRequestPref;
    @XmlElement(name="RelatedTravelerPref")
    protected List<RelatedTravelerPrefType> relatedTravelerPref;
    @XmlElement(name="ContactPref")
    protected List<ContactPref> contactPref;
    @XmlElement(name="EmployeeLevelInfo")
    protected EmployeeInfoType employeeLevelInfo;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name="PrimaryLangID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String primaryLangID;
    @XmlAttribute(name="ShareSynchInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareSynchInd;
    @XmlAttribute(name="ShareMarketInd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String shareMarketInd;
    @XmlAttribute(name="SmokingAllowed")
    protected Boolean smokingAllowed;
    @XmlAttribute(name="AltLangID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="language")
    protected String altLangID;

    public List<NamePrefType> getNamePref() {
        if (this.namePref == null) {
            this.namePref = new ArrayList<NamePrefType>();
        }
        return this.namePref;
    }

    public List<PhonePrefType> getPhonePref() {
        if (this.phonePref == null) {
            this.phonePref = new ArrayList<PhonePrefType>();
        }
        return this.phonePref;
    }

    public List<AddressPrefType> getAddressPref() {
        if (this.addressPref == null) {
            this.addressPref = new ArrayList<AddressPrefType>();
        }
        return this.addressPref;
    }

    public List<PaymentFormPrefType> getPaymentFormPref() {
        if (this.paymentFormPref == null) {
            this.paymentFormPref = new ArrayList<PaymentFormPrefType>();
        }
        return this.paymentFormPref;
    }

    public List<InterestPrefType> getInterestPref() {
        if (this.interestPref == null) {
            this.interestPref = new ArrayList<InterestPrefType>();
        }
        return this.interestPref;
    }

    public List<InsurancePrefType> getInsurancePref() {
        if (this.insurancePref == null) {
            this.insurancePref = new ArrayList<InsurancePrefType>();
        }
        return this.insurancePref;
    }

    public List<SeatingPrefType> getSeatingPref() {
        if (this.seatingPref == null) {
            this.seatingPref = new ArrayList<SeatingPrefType>();
        }
        return this.seatingPref;
    }

    public List<TicketDistribPrefType> getTicketDistribPref() {
        if (this.ticketDistribPref == null) {
            this.ticketDistribPref = new ArrayList<TicketDistribPrefType>();
        }
        return this.ticketDistribPref;
    }

    public List<MediaEntertainPrefType> getMediaEntertainPref() {
        if (this.mediaEntertainPref == null) {
            this.mediaEntertainPref = new ArrayList<MediaEntertainPrefType>();
        }
        return this.mediaEntertainPref;
    }

    public List<PetInfoPrefType> getPetInfoPref() {
        if (this.petInfoPref == null) {
            this.petInfoPref = new ArrayList<PetInfoPrefType>();
        }
        return this.petInfoPref;
    }

    public List<MealPrefType> getMealPref() {
        if (this.mealPref == null) {
            this.mealPref = new ArrayList<MealPrefType>();
        }
        return this.mealPref;
    }

    public List<LoyaltyPrefType> getLoyaltyPref() {
        if (this.loyaltyPref == null) {
            this.loyaltyPref = new ArrayList<LoyaltyPrefType>();
        }
        return this.loyaltyPref;
    }

    public List<SpecRequestPrefType> getSpecRequestPref() {
        if (this.specRequestPref == null) {
            this.specRequestPref = new ArrayList<SpecRequestPrefType>();
        }
        return this.specRequestPref;
    }

    public List<RelatedTravelerPrefType> getRelatedTravelerPref() {
        if (this.relatedTravelerPref == null) {
            this.relatedTravelerPref = new ArrayList<RelatedTravelerPrefType>();
        }
        return this.relatedTravelerPref;
    }

    public List<ContactPref> getContactPref() {
        if (this.contactPref == null) {
            this.contactPref = new ArrayList<ContactPref>();
        }
        return this.contactPref;
    }

    public EmployeeInfoType getEmployeeLevelInfo() {
        return this.employeeLevelInfo;
    }

    public void setEmployeeLevelInfo(EmployeeInfoType value) {
        this.employeeLevelInfo = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    public String getPrimaryLangID() {
        return this.primaryLangID;
    }

    public void setPrimaryLangID(String value) {
        this.primaryLangID = value;
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

    public Boolean isSmokingAllowed() {
        return this.smokingAllowed;
    }

    public void setSmokingAllowed(Boolean value) {
        this.smokingAllowed = value;
    }

    public String getAltLangID() {
        return this.altLangID;
    }

    public void setAltLangID(String value) {
        this.altLangID = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ContactPref {
        @XmlAttribute(name="PreferLevel")
        protected PreferLevelType preferLevel;
        @XmlAttribute(name="ContactMethodCode")
        protected String contactMethodCode;

        public PreferLevelType getPreferLevel() {
            return this.preferLevel;
        }

        public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
        }

        public String getContactMethodCode() {
            return this.contactMethodCode;
        }

        public void setContactMethodCode(String value) {
            this.contactMethodCode = value;
        }
    }
}

