package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceType", propOrder = {"price", "serviceDetails", "tpaExtensions"})
public class ServiceType {
   @XmlElement(name = "Price")
   protected List<AmountType> price;
   @XmlElement(name = "ServiceDetails")
   protected ResCommonDetailType serviceDetails;
   @XmlElement(name = "TPA_Extensions")
   protected TPAExtensionsType tpaExtensions;
   @XmlAttribute(name = "ServicePricingType")
   protected PricingType servicePricingType;
   @XmlAttribute(name = "ReservationStatusType")
   protected PMSResStatusType reservationStatusType;
   @XmlAttribute(name = "ServiceRPH")
   protected String serviceRPH;
   @XmlAttribute(name = "ServiceInventoryCode")
   protected String serviceInventoryCode;
   @XmlAttribute(name = "RatePlanCode")
   protected String ratePlanCode;
   @XmlAttribute(name = "InventoryBlockCode")
   protected String inventoryBlockCode;
   @XmlAttribute(name = "PriceGuaranteed")
   protected Boolean priceGuaranteed;
   @XmlAttribute(name = "Inclusive")
   protected Boolean inclusive;
   @XmlAttribute(name = "Quantity")
   protected Integer quantity;
   @XmlAttribute(name = "RequestedIndicator")
   protected Boolean requestedIndicator;
   @XmlAttribute(name = "URL")
   @XmlSchemaType(name = "anyURI")
   protected String url;
   @XmlAttribute(name = "Type", required = true)
   protected String type;
   @XmlAttribute(name = "Instance")
   protected String instance;
   @XmlAttribute(name = "ID_Context")
   protected String idContext;
   @XmlAttribute(name = "ID", required = true)
   protected String id;

   public List<AmountType> getPrice() {
      if (this.price == null) {
         this.price = new ArrayList<>();
      }

      return this.price;
   }

   public ResCommonDetailType getServiceDetails() {
      return this.serviceDetails;
   }

   public void setServiceDetails(ResCommonDetailType value) {
      this.serviceDetails = value;
   }

   public TPAExtensionsType getTPAExtensions() {
      return this.tpaExtensions;
   }

   public void setTPAExtensions(TPAExtensionsType value) {
      this.tpaExtensions = value;
   }

   public PricingType getServicePricingType() {
      return this.servicePricingType;
   }

   public void setServicePricingType(PricingType value) {
      this.servicePricingType = value;
   }

   public PMSResStatusType getReservationStatusType() {
      return this.reservationStatusType;
   }

   public void setReservationStatusType(PMSResStatusType value) {
      this.reservationStatusType = value;
   }

   public String getServiceRPH() {
      return this.serviceRPH;
   }

   public void setServiceRPH(String value) {
      this.serviceRPH = value;
   }

   public String getServiceInventoryCode() {
      return this.serviceInventoryCode;
   }

   public void setServiceInventoryCode(String value) {
      this.serviceInventoryCode = value;
   }

   public String getRatePlanCode() {
      return this.ratePlanCode;
   }

   public void setRatePlanCode(String value) {
      this.ratePlanCode = value;
   }

   public String getInventoryBlockCode() {
      return this.inventoryBlockCode;
   }

   public void setInventoryBlockCode(String value) {
      this.inventoryBlockCode = value;
   }

   public Boolean isPriceGuaranteed() {
      return this.priceGuaranteed;
   }

   public void setPriceGuaranteed(Boolean value) {
      this.priceGuaranteed = value;
   }

   public Boolean isInclusive() {
      return this.inclusive;
   }

   public void setInclusive(Boolean value) {
      this.inclusive = value;
   }

   public Integer getQuantity() {
      return this.quantity;
   }

   public void setQuantity(Integer value) {
      this.quantity = value;
   }

   public Boolean isRequestedIndicator() {
      return this.requestedIndicator;
   }

   public void setRequestedIndicator(Boolean value) {
      this.requestedIndicator = value;
   }

   public String getURL() {
      return this.url;
   }

   public void setURL(String value) {
      this.url = value;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String value) {
      this.type = value;
   }

   public String getInstance() {
      return this.instance;
   }

   public void setInstance(String value) {
      this.instance = value;
   }

   public String getIDContext() {
      return this.idContext;
   }

   public void setIDContext(String value) {
      this.idContext = value;
   }

   public String getID() {
      return this.id;
   }

   public void setID(String value) {
      this.id = value;
   }
}
