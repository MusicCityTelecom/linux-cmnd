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
import org.opentravel.ota._2003._05.ContactPersonType;
import org.opentravel.ota._2003._05.OwnInsuranceChoiceType;
import org.opentravel.ota._2003._05.POSType;
import org.opentravel.ota._2003._05.PackageResponseType;
import org.opentravel.ota._2003._05.PkgInvoiceDetail;
import org.opentravel.ota._2003._05.PkgPassengerListItem;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.TicketingInfoRSType;
import org.opentravel.ota._2003._05.TransactionType;
import org.opentravel.ota._2003._05.UniqueIDType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PkgReservation", propOrder={"pos", "uniqueID", "_package", "contactDetail", "passengerListItems", "ownInsuranceChoices", "ticketingInfo", "invoiceDetail", "transactions", "tpaExtensions"})
public class PkgReservation {
    @XmlElement(name="POS")
    protected POSType pos;
    @XmlElement(name="UniqueID", required=true)
    protected UniqueIDType uniqueID;
    @XmlElement(name="Package", required=true)
    protected PackageResponseType _package;
    @XmlElement(name="ContactDetail", required=true)
    protected ContactPersonType contactDetail;
    @XmlElement(name="PassengerListItems", required=true)
    protected PassengerListItems passengerListItems;
    @XmlElement(name="OwnInsuranceChoices")
    protected OwnInsuranceChoices ownInsuranceChoices;
    @XmlElement(name="TicketingInfo")
    protected TicketingInfo ticketingInfo;
    @XmlElement(name="InvoiceDetail", required=true)
    protected PkgInvoiceDetail invoiceDetail;
    @XmlElement(name="Transactions")
    protected Transactions transactions;
    @XmlElement(name="TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

    public POSType getPOS() {
        return this.pos;
    }

    public void setPOS(POSType value) {
        this.pos = value;
    }

    public UniqueIDType getUniqueID() {
        return this.uniqueID;
    }

    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    public PackageResponseType getPackage() {
        return this._package;
    }

    public void setPackage(PackageResponseType value) {
        this._package = value;
    }

    public ContactPersonType getContactDetail() {
        return this.contactDetail;
    }

    public void setContactDetail(ContactPersonType value) {
        this.contactDetail = value;
    }

    public PassengerListItems getPassengerListItems() {
        return this.passengerListItems;
    }

    public void setPassengerListItems(PassengerListItems value) {
        this.passengerListItems = value;
    }

    public OwnInsuranceChoices getOwnInsuranceChoices() {
        return this.ownInsuranceChoices;
    }

    public void setOwnInsuranceChoices(OwnInsuranceChoices value) {
        this.ownInsuranceChoices = value;
    }

    public TicketingInfo getTicketingInfo() {
        return this.ticketingInfo;
    }

    public void setTicketingInfo(TicketingInfo value) {
        this.ticketingInfo = value;
    }

    public PkgInvoiceDetail getInvoiceDetail() {
        return this.invoiceDetail;
    }

    public void setInvoiceDetail(PkgInvoiceDetail value) {
        this.invoiceDetail = value;
    }

    public Transactions getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Transactions value) {
        this.transactions = value;
    }

    public TPAExtensionsType getTPAExtensions() {
        return this.tpaExtensions;
    }

    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"transaction"})
    public static class Transactions {
        @XmlElement(name="Transaction", required=true)
        protected List<TransactionType> transaction;

        public List<TransactionType> getTransaction() {
            if (this.transaction == null) {
                this.transaction = new ArrayList<TransactionType>();
            }
            return this.transaction;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class TicketingInfo
    extends TicketingInfoRSType {
        @XmlAttribute(name="TOD_Ind")
        protected Boolean todInd;

        public Boolean isTODInd() {
            return this.todInd;
        }

        public void setTODInd(Boolean value) {
            this.todInd = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"passengerListItem"})
    public static class PassengerListItems {
        @XmlElement(name="PassengerListItem", required=true)
        protected List<PkgPassengerListItem> passengerListItem;

        public List<PkgPassengerListItem> getPassengerListItem() {
            if (this.passengerListItem == null) {
                this.passengerListItem = new ArrayList<PkgPassengerListItem>();
            }
            return this.passengerListItem;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"ownInsuranceChoice"})
    public static class OwnInsuranceChoices {
        @XmlElement(name="OwnInsuranceChoice", required=true)
        protected List<OwnInsuranceChoiceType> ownInsuranceChoice;

        public List<OwnInsuranceChoiceType> getOwnInsuranceChoice() {
            if (this.ownInsuranceChoice == null) {
                this.ownInsuranceChoice = new ArrayList<OwnInsuranceChoiceType>();
            }
            return this.ownInsuranceChoice;
        }
    }
}

