package org.htng._2011b;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
   private static final QName _HTNGEventNotificationHeader_QNAME = new QName("http://htng.org/2011B", "HTNG_EventNotificationHeader");
   private static final QName _HTNGHotelRoomMoveNotifRS_QNAME = new QName("http://htng.org/2011B", "HTNG_HotelRoomMoveNotifRS");
   private static final QName _HTNGHotelCheckOutNotifRS_QNAME = new QName("http://htng.org/2011B", "HTNG_HotelCheckOutNotifRS");
   private static final QName _HTNGHotelRoomStatusUpdateNotifRS_QNAME = new QName("http://htng.org/2011B", "HTNG_HotelRoomStatusUpdateNotifRS");
   private static final QName _HTNGHotelStayUpdateNotifRS_QNAME = new QName("http://htng.org/2011B", "HTNG_HotelStayUpdateNotifRS");
   private static final QName _HTNGHotelRoomRestrictionNotifRS_QNAME = new QName("http://htng.org/2011B", "HTNG_HotelRoomRestrictionNotifRS");
   private static final QName _HTNGProfileMessageStatusNotifRS_QNAME = new QName("http://htng.org/2011B", "HTNG_ProfileMessageStatusNotifRS");
   private static final QName _HTNGHotelCheckInNotifRS_QNAME = new QName("http://htng.org/2011B", "HTNG_HotelCheckInNotifRS");

   public HTNGRequestBaseType createHTNGRequestBaseType() {
      return new HTNGRequestBaseType();
   }

   public HTNGHotelRoomTypeUpsellRS createHTNGHotelRoomTypeUpsellRS() {
      return new HTNGHotelRoomTypeUpsellRS();
   }

   public HTNGProfileMessageRQ createHTNGProfileMessageRQ() {
      return new HTNGProfileMessageRQ();
   }

   public HTNGWakeupSchedulingNotifRQ createHTNGWakeupSchedulingNotifRQ() {
      return new HTNGWakeupSchedulingNotifRQ();
   }

   public HTNGProfileMessageStatusNotifRQ createHTNGProfileMessageStatusNotifRQ() {
      return new HTNGProfileMessageStatusNotifRQ();
   }

   public HTNGProfileMessageRS createHTNGProfileMessageRS() {
      return new HTNGProfileMessageRS();
   }

   public HTNGHotelRoomStatusSearchRS createHTNGHotelRoomStatusSearchRS() {
      return new HTNGHotelRoomStatusSearchRS();
   }

   public HTNGSubscriptionsAvailableRS createHTNGSubscriptionsAvailableRS() {
      return new HTNGSubscriptionsAvailableRS();
   }

   public HTNGStatisticsRQ createHTNGStatisticsRQ() {
      return new HTNGStatisticsRQ();
   }

   public HTNGHotelCommissionPaymentRQ createHTNGHotelCommissionPaymentRQ() {
      return new HTNGHotelCommissionPaymentRQ();
   }

   public HTNGSubscriptionRQ createHTNGSubscriptionRQ() {
      return new HTNGSubscriptionRQ();
   }

   public HTNGPaymentCardProcessingRS createHTNGPaymentCardProcessingRS() {
      return new HTNGPaymentCardProcessingRS();
   }

   public HTNGHotelFolioRS createHTNGHotelFolioRS() {
      return new HTNGHotelFolioRS();
   }

   public HTNGHotelRoomRestrictionNotifRQ createHTNGHotelRoomRestrictionNotifRQ() {
      return new HTNGHotelRoomRestrictionNotifRQ();
   }

   public HTNGStatisticsRS createHTNGStatisticsRS() {
      return new HTNGStatisticsRS();
   }

   public HTNGHotelRoomMoveNotifRQ createHTNGHotelRoomMoveNotifRQ() {
      return new HTNGHotelRoomMoveNotifRQ();
   }

   public HTNGSubscriptionStatusRS createHTNGSubscriptionStatusRS() {
      return new HTNGSubscriptionStatusRS();
   }

   public HTNGKeyValueItemsType createHTNGKeyValueItemsType() {
      return new HTNGKeyValueItemsType();
   }

   public HTNGSubscriptionStatusRS.Subscriptions createHTNGSubscriptionStatusRSSubscriptions() {
      return new HTNGSubscriptionStatusRS.Subscriptions();
   }

   public HTNGStatisticsRS.Queries createHTNGStatisticsRSQueries() {
      return new HTNGStatisticsRS.Queries();
   }

   public HTNGStatisticsRS.Queries.Query createHTNGStatisticsRSQueriesQuery() {
      return new HTNGStatisticsRS.Queries.Query();
   }

   public HTNGHotelRoomRestrictionNotifRQ.Systems createHTNGHotelRoomRestrictionNotifRQSystems() {
      return new HTNGHotelRoomRestrictionNotifRQ.Systems();
   }

   public HTNGHotelRoomRestrictionNotifRQ.Systems.System createHTNGHotelRoomRestrictionNotifRQSystemsSystem() {
      return new HTNGHotelRoomRestrictionNotifRQ.Systems.System();
   }

   public HTNGHotelRoomRestrictionNotifRQ.Systems.System.Restrictions createHTNGHotelRoomRestrictionNotifRQSystemsSystemRestrictions() {
      return new HTNGHotelRoomRestrictionNotifRQ.Systems.System.Restrictions();
   }

   public HTNGHotelFolioRS.Folios createHTNGHotelFolioRSFolios() {
      return new HTNGHotelFolioRS.Folios();
   }

   public HTNGPaymentCardProcessingRS.Authorization createHTNGPaymentCardProcessingRSAuthorization() {
      return new HTNGPaymentCardProcessingRS.Authorization();
   }

   public HTNGAuthorizationType createHTNGAuthorizationType() {
      return new HTNGAuthorizationType();
   }

   public HTNGHotelCommissionPaymentRQ.CommissionRecipients createHTNGHotelCommissionPaymentRQCommissionRecipients() {
      return new HTNGHotelCommissionPaymentRQ.CommissionRecipients();
   }

   public HTNGHotelCommissionPaymentRQ.CommissionRecipients.CommissionRecipient createHTNGHotelCommissionPaymentRQCommissionRecipientsCommissionRecipient() {
      return new HTNGHotelCommissionPaymentRQ.CommissionRecipients.CommissionRecipient();
   }

   public HTNGHotelCommissionPaymentRQ.CommissionRecipients.CommissionRecipient.CommissionableReservations createHTNGHotelCommissionPaymentRQCommissionRecipientsCommissionRecipientCommissionableReservations() {
      return new HTNGHotelCommissionPaymentRQ.CommissionRecipients.CommissionRecipient.CommissionableReservations();
   }

   public HTNGStatisticsRQ.Queries createHTNGStatisticsRQQueries() {
      return new HTNGStatisticsRQ.Queries();
   }

   public HTNGStatisticsRQ.Queries.Query createHTNGStatisticsRQQueriesQuery() {
      return new HTNGStatisticsRQ.Queries.Query();
   }

   public HTNGSubscriptionsAvailableRS.AvailableEventTypes createHTNGSubscriptionsAvailableRSAvailableEventTypes() {
      return new HTNGSubscriptionsAvailableRS.AvailableEventTypes();
   }

   public HTNGHotelRoomStatusSearchRS.RoomInformationList createHTNGHotelRoomStatusSearchRSRoomInformationList() {
      return new HTNGHotelRoomStatusSearchRS.RoomInformationList();
   }

   public HTNGProfileMessageRS.ProfileMessages createHTNGProfileMessageRSProfileMessages() {
      return new HTNGProfileMessageRS.ProfileMessages();
   }

   public HTNGProfileMessageStatusNotifRQ.ProfileMessages createHTNGProfileMessageStatusNotifRQProfileMessages() {
      return new HTNGProfileMessageStatusNotifRQ.ProfileMessages();
   }

   public HTNGWakeupSchedulingNotifRQ.RoomInformationList createHTNGWakeupSchedulingNotifRQRoomInformationList() {
      return new HTNGWakeupSchedulingNotifRQ.RoomInformationList();
   }

   public HTNGHotelKeyEncodeType createHTNGHotelKeyEncodeType() {
      return new HTNGHotelKeyEncodeType();
   }

   public HTNGEventNotificationHeaderType createHTNGEventNotificationHeaderType() {
      return new HTNGEventNotificationHeaderType();
   }

   public HTNGHotelCheckOutNotifRQ createHTNGHotelCheckOutNotifRQ() {
      return new HTNGHotelCheckOutNotifRQ();
   }

   public HTNGRequestBaseType.PropertyInfo createHTNGRequestBaseTypePropertyInfo() {
      return new HTNGRequestBaseType.PropertyInfo();
   }

   public HTNGCollectionOfUniqueIDs createHTNGCollectionOfUniqueIDs() {
      return new HTNGCollectionOfUniqueIDs();
   }

   public HTNGRoomElementType createHTNGRoomElementType() {
      return new HTNGRoomElementType();
   }

   public HTNGHotelRoomTypeUpsellRS.GuestRoomTypes createHTNGHotelRoomTypeUpsellRSGuestRoomTypes() {
      return new HTNGHotelRoomTypeUpsellRS.GuestRoomTypes();
   }

   public HTNGHotelKeyEncodeRS createHTNGHotelKeyEncodeRS() {
      return new HTNGHotelKeyEncodeRS();
   }

   public HTNGProfileMessageRQ.PropertyInfo createHTNGProfileMessageRQPropertyInfo() {
      return new HTNGProfileMessageRQ.PropertyInfo();
   }

   public HTNGComponentRoomType createHTNGComponentRoomType() {
      return new HTNGComponentRoomType();
   }

   public HTNGResponseBaseType createHTNGResponseBaseType() {
      return new HTNGResponseBaseType();
   }

   public HTNGHotelRoomTypeUpsellRQ createHTNGHotelRoomTypeUpsellRQ() {
      return new HTNGHotelRoomTypeUpsellRQ();
   }

   public HTNGHotelKeyEncodeRQ createHTNGHotelKeyEncodeRQ() {
      return new HTNGHotelKeyEncodeRQ();
   }

   public HTNGHotelRoomStatusUpdateNotifRQ createHTNGHotelRoomStatusUpdateNotifRQ() {
      return new HTNGHotelRoomStatusUpdateNotifRQ();
   }

   public HTNGHotelCheckInNotifRQ createHTNGHotelCheckInNotifRQ() {
      return new HTNGHotelCheckInNotifRQ();
   }

   public HTNGHotelCommissionPaymentRS createHTNGHotelCommissionPaymentRS() {
      return new HTNGHotelCommissionPaymentRS();
   }

   public HTNGProfileMessageSummaryType createHTNGProfileMessageSummaryType() {
      return new HTNGProfileMessageSummaryType();
   }

   public HTNGHotelFolioRQ createHTNGHotelFolioRQ() {
      return new HTNGHotelFolioRQ();
   }

   public HTNGSubscriptionsAvailableRQ createHTNGSubscriptionsAvailableRQ() {
      return new HTNGSubscriptionsAvailableRQ();
   }

   public HTNGSubscriptionStatusRQ createHTNGSubscriptionStatusRQ() {
      return new HTNGSubscriptionStatusRQ();
   }

   public HTNGPaymentCardProcessingRQ createHTNGPaymentCardProcessingRQ() {
      return new HTNGPaymentCardProcessingRQ();
   }

   public HTNGSubscriptionRQ.EventType createHTNGSubscriptionRQEventType() {
      return new HTNGSubscriptionRQ.EventType();
   }

   public HTNGHotelRoomStatusSearchRQ createHTNGHotelRoomStatusSearchRQ() {
      return new HTNGHotelRoomStatusSearchRQ();
   }

   public HTNGHotelStayUpdateNotifRQ createHTNGHotelStayUpdateNotifRQ() {
      return new HTNGHotelStayUpdateNotifRQ();
   }

   public HTNGSubscriptionRS createHTNGSubscriptionRS() {
      return new HTNGSubscriptionRS();
   }

   public HTNGHotelRoomMoveNotifRQ.SourceRoomInformation createHTNGHotelRoomMoveNotifRQSourceRoomInformation() {
      return new HTNGHotelRoomMoveNotifRQ.SourceRoomInformation();
   }

   public HTNGHotelRoomMoveNotifRQ.DestinationRoomInformation createHTNGHotelRoomMoveNotifRQDestinationRoomInformation() {
      return new HTNGHotelRoomMoveNotifRQ.DestinationRoomInformation();
   }

   public HTNGWakeupSchedulingNotifRS createHTNGWakeupSchedulingNotifRS() {
      return new HTNGWakeupSchedulingNotifRS();
   }

   public HTNGBasicOrSuiteRoomType createHTNGBasicOrSuiteRoomType() {
      return new HTNGBasicOrSuiteRoomType();
   }

   public HTNGComponentRoomsType createHTNGComponentRoomsType() {
      return new HTNGComponentRoomsType();
   }

   public HTNGEventFiltersType createHTNGEventFiltersType() {
      return new HTNGEventFiltersType();
   }

   public HTNGExtendedPrice createHTNGExtendedPrice() {
      return new HTNGExtendedPrice();
   }

   public HTNGMagneticStripeType createHTNGMagneticStripeType() {
      return new HTNGMagneticStripeType();
   }

   public HTNGPaymentCardType createHTNGPaymentCardType() {
      return new HTNGPaymentCardType();
   }

   public HTNGRevenueDetailsType createHTNGRevenueDetailsType() {
      return new HTNGRevenueDetailsType();
   }

   public HTNGRevenueDetailType createHTNGRevenueDetailType() {
      return new HTNGRevenueDetailType();
   }

   public HTNGSmartCardDataType createHTNGSmartCardDataType() {
      return new HTNGSmartCardDataType();
   }

   public HTNGTelephoneExtensionType createHTNGTelephoneExtensionType() {
      return new HTNGTelephoneExtensionType();
   }

   public HTNGKeyValueItemsType.KeyValueItem createHTNGKeyValueItemsTypeKeyValueItem() {
      return new HTNGKeyValueItemsType.KeyValueItem();
   }

   public HTNGSubscriptionStatusRS.Subscriptions.Subscription createHTNGSubscriptionStatusRSSubscriptionsSubscription() {
      return new HTNGSubscriptionStatusRS.Subscriptions.Subscription();
   }

   public HTNGStatisticsRS.Queries.Query.ResponseParameters createHTNGStatisticsRSQueriesQueryResponseParameters() {
      return new HTNGStatisticsRS.Queries.Query.ResponseParameters();
   }

   public HTNGHotelRoomRestrictionNotifRQ.Systems.System.Restrictions.Restriction createHTNGHotelRoomRestrictionNotifRQSystemsSystemRestrictionsRestriction() {
      return new HTNGHotelRoomRestrictionNotifRQ.Systems.System.Restrictions.Restriction();
   }

   public HTNGHotelFolioRS.Folios.Folio createHTNGHotelFolioRSFoliosFolio() {
      return new HTNGHotelFolioRS.Folios.Folio();
   }

   public HTNGPaymentCardProcessingRS.Authorization.AuthorizationResult createHTNGPaymentCardProcessingRSAuthorizationAuthorizationResult() {
      return new HTNGPaymentCardProcessingRS.Authorization.AuthorizationResult();
   }

   public HTNGAuthorizationType.CreditCardAuthorization createHTNGAuthorizationTypeCreditCardAuthorization() {
      return new HTNGAuthorizationType.CreditCardAuthorization();
   }

   public HTNGAuthorizationType.BookingReferenceID createHTNGAuthorizationTypeBookingReferenceID() {
      return new HTNGAuthorizationType.BookingReferenceID();
   }

   public HTNGHotelCommissionPaymentRQ.CommissionRecipients.CommissionRecipient.CommissionableReservations.CommissionableReservation createHTNGHotelCommissionPaymentRQCommissionRecipientsCommissionRecipientCommissionableReservationsCommissionableReservation() {
      return new HTNGHotelCommissionPaymentRQ.CommissionRecipients.CommissionRecipient.CommissionableReservations.CommissionableReservation();
   }

   public HTNGStatisticsRQ.Queries.Query.ResponseParameters createHTNGStatisticsRQQueriesQueryResponseParameters() {
      return new HTNGStatisticsRQ.Queries.Query.ResponseParameters();
   }

   public HTNGSubscriptionsAvailableRS.AvailableEventTypes.AvailableEventType createHTNGSubscriptionsAvailableRSAvailableEventTypesAvailableEventType() {
      return new HTNGSubscriptionsAvailableRS.AvailableEventTypes.AvailableEventType();
   }

   public HTNGHotelRoomStatusSearchRS.RoomInformationList.RoomInformation createHTNGHotelRoomStatusSearchRSRoomInformationListRoomInformation() {
      return new HTNGHotelRoomStatusSearchRS.RoomInformationList.RoomInformation();
   }

   public HTNGProfileMessageRS.ProfileMessages.ProfileMessage createHTNGProfileMessageRSProfileMessagesProfileMessage() {
      return new HTNGProfileMessageRS.ProfileMessages.ProfileMessage();
   }

   public HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage createHTNGProfileMessageStatusNotifRQProfileMessagesProfileMessage() {
      return new HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage();
   }

   public HTNGWakeupSchedulingNotifRQ.RoomInformationList.WakeupInfo createHTNGWakeupSchedulingNotifRQRoomInformationListWakeupInfo() {
      return new HTNGWakeupSchedulingNotifRQ.RoomInformationList.WakeupInfo();
   }

   public HTNGHotelKeyEncodeType.AccessAreas createHTNGHotelKeyEncodeTypeAccessAreas() {
      return new HTNGHotelKeyEncodeType.AccessAreas();
   }

   public HTNGEventNotificationHeaderType.SubscriptionProducerMessages createHTNGEventNotificationHeaderTypeSubscriptionProducerMessages() {
      return new HTNGEventNotificationHeaderType.SubscriptionProducerMessages();
   }

   @XmlElementDecl(namespace = "http://htng.org/2011B", name = "HTNG_EventNotificationHeader")
   public JAXBElement<HTNGEventNotificationHeaderType> createHTNGEventNotificationHeader(HTNGEventNotificationHeaderType value) {
      return new JAXBElement<>(_HTNGEventNotificationHeader_QNAME, HTNGEventNotificationHeaderType.class, null, value);
   }

   @XmlElementDecl(namespace = "http://htng.org/2011B", name = "HTNG_HotelRoomMoveNotifRS")
   public JAXBElement<HTNGResponseBaseType> createHTNGHotelRoomMoveNotifRS(HTNGResponseBaseType value) {
      return new JAXBElement<>(_HTNGHotelRoomMoveNotifRS_QNAME, HTNGResponseBaseType.class, null, value);
   }

   @XmlElementDecl(namespace = "http://htng.org/2011B", name = "HTNG_HotelCheckOutNotifRS")
   public JAXBElement<HTNGResponseBaseType> createHTNGHotelCheckOutNotifRS(HTNGResponseBaseType value) {
      return new JAXBElement<>(_HTNGHotelCheckOutNotifRS_QNAME, HTNGResponseBaseType.class, null, value);
   }

   @XmlElementDecl(namespace = "http://htng.org/2011B", name = "HTNG_HotelRoomStatusUpdateNotifRS")
   public JAXBElement<HTNGResponseBaseType> createHTNGHotelRoomStatusUpdateNotifRS(HTNGResponseBaseType value) {
      return new JAXBElement<>(_HTNGHotelRoomStatusUpdateNotifRS_QNAME, HTNGResponseBaseType.class, null, value);
   }

   @XmlElementDecl(namespace = "http://htng.org/2011B", name = "HTNG_HotelStayUpdateNotifRS")
   public JAXBElement<HTNGResponseBaseType> createHTNGHotelStayUpdateNotifRS(HTNGResponseBaseType value) {
      return new JAXBElement<>(_HTNGHotelStayUpdateNotifRS_QNAME, HTNGResponseBaseType.class, null, value);
   }

   @XmlElementDecl(namespace = "http://htng.org/2011B", name = "HTNG_HotelRoomRestrictionNotifRS")
   public JAXBElement<HTNGResponseBaseType> createHTNGHotelRoomRestrictionNotifRS(HTNGResponseBaseType value) {
      return new JAXBElement<>(_HTNGHotelRoomRestrictionNotifRS_QNAME, HTNGResponseBaseType.class, null, value);
   }

   @XmlElementDecl(namespace = "http://htng.org/2011B", name = "HTNG_ProfileMessageStatusNotifRS")
   public JAXBElement<HTNGResponseBaseType> createHTNGProfileMessageStatusNotifRS(HTNGResponseBaseType value) {
      return new JAXBElement<>(_HTNGProfileMessageStatusNotifRS_QNAME, HTNGResponseBaseType.class, null, value);
   }

   @XmlElementDecl(namespace = "http://htng.org/2011B", name = "HTNG_HotelCheckInNotifRS")
   public JAXBElement<HTNGResponseBaseType> createHTNGHotelCheckInNotifRS(HTNGResponseBaseType value) {
      return new JAXBElement<>(_HTNGHotelCheckInNotifRS_QNAME, HTNGResponseBaseType.class, null, value);
   }
}
