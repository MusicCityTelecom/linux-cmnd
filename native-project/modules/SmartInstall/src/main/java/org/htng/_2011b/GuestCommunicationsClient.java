package org.htng._2011b;

import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.dom4j.DocumentHelper;
import org.opentravel.ota._2003._05.AdditionalDetailType;
import org.opentravel.ota._2003._05.AdditionalDetailsType;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.FormattedTextTextType;
import org.opentravel.ota._2003._05.POSType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.RoomAmenityPrefType;
import org.opentravel.ota._2003._05.RoomTypeType;
import org.opentravel.ota._2003._05.SourceType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public final class GuestCommunicationsClient {
   private static final Logger LOG = LoggerFactory.getLogger(GuestCommunicationsClient.class);
   private static final QName SERVICE_NAME = new QName("http://htng.org/2011B", "HTNG_GuestAndRoomStatusService");
   private String wsdl;
   private GuestCommunications port;

   public GuestCommunicationsClient(String wsdl) {
      this.wsdl = wsdl;
   }

   public GuestCommunications getPort() {
      if (this.port == null) {
         HTNGGuestAndRoomStatusService ss = new HTNGGuestAndRoomStatusService();
         this.port = ss.getGuestCommunications();
         BindingProvider bp = (BindingProvider)this.port;
         bp.getRequestContext().put("javax.xml.ws.service.endpoint.address", this.wsdl);
      }

      return this.port;
   }

   public void connect() {
      this.port = null;
      this.getPort();
   }

   public void retrieveMessages() {
      LOG.info("retrieveMessages");

      try {
         HTNGProfileMessageRQ htngProfileMessageRQ = new HTNGProfileMessageRQ();
         htngProfileMessageRQ.setRoom(HTNGHelper.generateRoom("All"));
         HTNGProfileMessageStatusType retrieveMessages = HTNGProfileMessageStatusType.NEW;
         htngProfileMessageRQ.setProfileMessageStatus(retrieveMessages);
         htngProfileMessageRQ.setEchoToken("EchoToken243790509");
         HTNGProfileMessageRS _retrieveMessages__return = this.port.retrieveMessages(htngProfileMessageRQ);
         System.out.println("retrieveMessages.result=" + _retrieveMessages__return);
         String roomid = _retrieveMessages__return.getRoom().getRoomID();

         for (HTNGProfileMessageRS.ProfileMessages.ProfileMessage message : _retrieveMessages__return.getProfileMessages().getProfileMessage()) {
            org.dom4j.Element rootElt = DocumentHelper.createDocument().addElement("messagetextresults");
            rootElt.addElement("room").setText(roomid);
            rootElt.addElement("msgid").setText(message.getMessageID());
            rootElt.addElement("status").setText(message.getStatus().name());
            Date datetime = message.getCreateDateTime().toGregorianCalendar().getTime();
            rootElt.addElement("datetime").setText(TpvDateUtils.getMessageTimeFormat().format(datetime));
            String content = null;
            if (message.getTextOrImageOrURL() != null) {
               FormattedTextTextType text = (FormattedTextTextType)message.getTextOrImageOrURL().get(0).getValue();
               content = text.getValue();
               rootElt.addElement("msgtext").setText(content);
            }

            PmsUtils.saveMessageTextResults2DB(rootElt);
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public void updateMessageStatus(String roomId, String msgId, String msgStatus) {
      HTNGProfileMessageStatusNotifRQ htngProfileMessageStatusRQ = new HTNGProfileMessageStatusNotifRQ();
      htngProfileMessageStatusRQ.setUniqueID(HTNGHelper.generateUniqueTypeID("Type1931252751", "ID-644431606"));
      htngProfileMessageStatusRQ.setPropertyInfo(HTNGHelper.generatePropertyInfo());
      htngProfileMessageStatusRQ.setEchoToken("EchoToken-1784905413");
      htngProfileMessageStatusRQ.setRoom(HTNGHelper.generateRoom(roomId));
      HTNGProfileMessageStatusNotifRQ.ProfileMessages profileMessages = new HTNGProfileMessageStatusNotifRQ.ProfileMessages();
      List<HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage> profileMessagesList = new ArrayList<>();
      HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage profileMessageVal1 = new HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage();
      profileMessageVal1.setMessageID(msgId);
      HTNGProfileMessageStatusType status = HTNGHelper.messageStatusToHTNG(msgStatus);
      profileMessageVal1.setStatus(status);
      profileMessagesList.add(profileMessageVal1);
      profileMessages.getProfileMessage().addAll(profileMessagesList);
      htngProfileMessageStatusRQ.setProfileMessages(profileMessages);
      HTNGResponseBaseType updateMessageStatusReturn = this.port.updateMessageStatus(htngProfileMessageStatusRQ);
      System.out.println("updateMessageStatus.result=" + updateMessageStatusReturn);
   }

   public static void main(String[] args) throws Exception {
      URL wsdlURL = HTNGGuestAndRoomStatusService.WSDL_LOCATION;
      if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
         File wsdlFile = new File(args[0]);

         try {
            if (wsdlFile.exists()) {
               wsdlURL = wsdlFile.toURI().toURL();
            } else {
               wsdlURL = new URL(args[0]);
            }
         } catch (MalformedURLException e) {
            e.printStackTrace();
         }
      }

      HTNGGuestAndRoomStatusService ss = new HTNGGuestAndRoomStatusService(wsdlURL, SERVICE_NAME);
      GuestCommunications port = ss.getGuestCommunications();
      System.out.println("Invoking retrieveMessages...");
      HTNGProfileMessageRQ _retrieveMessages_htngProfileMessageRQ = new HTNGProfileMessageRQ();
      POSType _retrieveMessages_htngProfileMessageRQPOS = new POSType();
      List<SourceType> _retrieveMessages_htngProfileMessageRQPOSSource = new ArrayList<>();
      SourceType _retrieveMessages_htngProfileMessageRQPOSSourceVal1 = new SourceType();
      SourceType.RequestorID _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID = new SourceType.RequestorID();
      CompanyNameType _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName = new CompanyNameType();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setValue("Value1457396405");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setDivision("Division2134170386");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setDepartment("Department974803454");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setCompanyShortName("CompanyShortName-1635923405");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setTravelSector("TravelSector-1685324882");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setCode("Code-807113159");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setCodeContext("CodeContext-1313129958");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setCompanyName(_retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName);
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setURL("URL-1129872139");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setType("Type-1603692268");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setInstance("Instance1227193571");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setIDContext("IDContext1008163950");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setID("ID-1846516509");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setMessagePassword("MessagePassword-1414676943");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setRequestorID(_retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID);
      SourceType.Position _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position = new SourceType.Position();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setLatitude("Latitude-1630238203");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setLongitude("Longitude-1064529905");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setAltitude("Altitude1804936272");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setAltitudeUnitOfMeasureCode("AltitudeUnitOfMeasureCode438039258");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setPositionAccuracy("PositionAccuracy-431300185");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setPosition(_retrieveMessages_htngProfileMessageRQPOSSourceVal1Position);
      SourceType.BookingChannel _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel = new SourceType.BookingChannel();
      CompanyNameType _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName = new CompanyNameType();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setValue("Value-1320728565");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setDivision("Division-2144086475");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setDepartment("Department130380955");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setCompanyShortName("CompanyShortName-1537381361");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setTravelSector("TravelSector-811281988");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setCode("Code521136108");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setCodeContext("CodeContext574885683");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel.setCompanyName(
         _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName
      );
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel.setType("Type1372786463");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel.setPrimary(false);
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setBookingChannel(_retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel);
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setAgentSine("AgentSine2045639480");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setPseudoCityCode("PseudoCityCode-1796896702");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setISOCountry("ISOCountry1318404603");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setISOCurrency("ISOCurrency-190147094");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setAgentDutyCode("AgentDutyCode-146482040");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setAirlineVendorID("AirlineVendorID58036800");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setAirportCode("AirportCode-1427917074");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setFirstDepartPoint("FirstDepartPoint1255475474");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setERSPUserID("ERSPUserID-1030678148");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setTerminalID("TerminalID1354726065");
      _retrieveMessages_htngProfileMessageRQPOSSource.add(_retrieveMessages_htngProfileMessageRQPOSSourceVal1);
      _retrieveMessages_htngProfileMessageRQPOS.getSource().addAll(_retrieveMessages_htngProfileMessageRQPOSSource);
      _retrieveMessages_htngProfileMessageRQ.setPOS(_retrieveMessages_htngProfileMessageRQPOS);
      UniqueIDType _retrieveMessages_htngProfileMessageRQUniqueID = new UniqueIDType();
      CompanyNameType _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName = new CompanyNameType();
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setValue("Value-553762930");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setDivision("Division955738291");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setDepartment("Department-1586693208");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setCompanyShortName("CompanyShortName163579798");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setTravelSector("TravelSector-79278307");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setCode("Code-319388587");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setCodeContext("CodeContext904326039");
      _retrieveMessages_htngProfileMessageRQUniqueID.setCompanyName(_retrieveMessages_htngProfileMessageRQUniqueIDCompanyName);
      _retrieveMessages_htngProfileMessageRQUniqueID.setURL("URL18036774");
      _retrieveMessages_htngProfileMessageRQUniqueID.setType("Type789298994");
      _retrieveMessages_htngProfileMessageRQUniqueID.setInstance("Instance-679894521");
      _retrieveMessages_htngProfileMessageRQUniqueID.setIDContext("IDContext1437368338");
      _retrieveMessages_htngProfileMessageRQUniqueID.setID("ID649125054");
      _retrieveMessages_htngProfileMessageRQ.setUniqueID(_retrieveMessages_htngProfileMessageRQUniqueID);
      HTNGProfileMessageRQ.PropertyInfo _retrieveMessages_htngProfileMessageRQPropertyInfo = new HTNGProfileMessageRQ.PropertyInfo();
      _retrieveMessages_htngProfileMessageRQPropertyInfo.setChainCode("ChainCode-1051805032");
      _retrieveMessages_htngProfileMessageRQPropertyInfo.setBrandCode("BrandCode297032050");
      _retrieveMessages_htngProfileMessageRQPropertyInfo.setHotelCode("HotelCode172015009");
      _retrieveMessages_htngProfileMessageRQPropertyInfo.setHotelCityCode("HotelCityCode1640380949");
      _retrieveMessages_htngProfileMessageRQPropertyInfo.setHotelName("HotelName-419761547");
      _retrieveMessages_htngProfileMessageRQPropertyInfo.setHotelCodeContext("HotelCodeContext-74806230");
      _retrieveMessages_htngProfileMessageRQPropertyInfo.setChainName("ChainName-52934063");
      _retrieveMessages_htngProfileMessageRQPropertyInfo.setBrandName("BrandName78261171");
      _retrieveMessages_htngProfileMessageRQPropertyInfo.setAreaID("AreaID-742489682");
      _retrieveMessages_htngProfileMessageRQ.setPropertyInfo(_retrieveMessages_htngProfileMessageRQPropertyInfo);
      HTNGComponentRoomType _retrieveMessages_htngProfileMessageRQRoom = new HTNGComponentRoomType();
      RoomTypeType _retrieveMessages_htngProfileMessageRQRoomRoomType = new RoomTypeType();
      ParagraphType _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription = new ParagraphType();
      List<JAXBElement<? extends Object>> _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescriptionTextOrImageOrURL = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.getTextOrImageOrURL()
         .addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescriptionTextOrImageOrURL);
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setName("Name-812970169");
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setParagraphNumber(new BigInteger("64037247311704539232873616015167773236"));
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setCreateDateTime(
         DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.773+08:00")
      );
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setCreatorID("CreatorID-1361881803");
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setLastModifyDateTime(
         DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.773+08:00")
      );
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setLastModifierID("LastModifierID827872615");
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setPurgeDate(
         DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.773+08:00")
      );
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setLanguage("Language-1935238507");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomDescription(_retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription);
      AdditionalDetailsType _retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetails = new AdditionalDetailsType();
      List<AdditionalDetailType> _retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetailsAdditionalDetail = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetails.getAdditionalDetail()
         .addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetailsAdditionalDetail);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setAdditionalDetails(_retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetails);
      RoomTypeType.Amenities _retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenities = new RoomTypeType.Amenities();
      List<RoomAmenityPrefType> _retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenitiesAmenity = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenities.getAmenity().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenitiesAmenity);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setAmenities(_retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenities);
      List<RoomTypeType.Occupancy> _retrieveMessages_htngProfileMessageRQRoomRoomTypeOccupancy = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomType.getOccupancy().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeOccupancy);
      TPAExtensionsType _retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensions = new TPAExtensionsType();
      List<Element> _retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensionsAny = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensions.getAny().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensionsAny);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setTPAExtensions(_retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensions);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setNumberOfUnits(new BigInteger("-85962999151565525818236294454400614598"));
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setIsRoom(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setIsConverted(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setIsAlternate(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setReqdGuaranteeType("ReqdGuaranteeType109691363");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomType("RoomType1682379124");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomTypeCode("RoomTypeCode-850605678");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomCategory("RoomCategory1789752253");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomID("RoomID-1556314332");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setFloor(-1433898627);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setInvBlockCode("InvBlockCode1949484667");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomLocationCode("RoomLocationCode-1910411632");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomViewCode("RoomViewCode-1656659178");
      List<String> _retrieveMessages_htngProfileMessageRQRoomRoomTypeBedTypeCode = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomType.getBedTypeCode().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeBedTypeCode);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setNonSmoking(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setConfiguration("Configuration1561483301");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setSizeMeasurement("SizeMeasurement392856986");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setQuantity(-102045502);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setComposite(true);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomClassificationCode("RoomClassificationCode1290116687");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomArchitectureCode("RoomArchitectureCode593645867");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomGender("RoomGender-764997362");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setSharedRoomInd(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setPromotionCode("PromotionCode938880865");
      List<String> _retrieveMessages_htngProfileMessageRQRoomRoomTypePromotionVendorCode = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomType.getPromotionVendorCode().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypePromotionVendorCode);
      _retrieveMessages_htngProfileMessageRQRoom.setRoomType(_retrieveMessages_htngProfileMessageRQRoomRoomType);
      HTNGTelephoneExtensionType _retrieveMessages_htngProfileMessageRQRoomTelephoneExtensions = new HTNGTelephoneExtensionType();
      List<String> _retrieveMessages_htngProfileMessageRQRoomTelephoneExtensionsTelephoneExtention = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomTelephoneExtensions.getTelephoneExtention()
         .addAll(_retrieveMessages_htngProfileMessageRQRoomTelephoneExtensionsTelephoneExtention);
      _retrieveMessages_htngProfileMessageRQRoom.setTelephoneExtensions(_retrieveMessages_htngProfileMessageRQRoomTelephoneExtensions);
      HTNGHousekeepingStatusType _retrieveMessages_htngProfileMessageRQRoomHKStatus = HTNGHousekeepingStatusType.VACANT_DIRTY;
      _retrieveMessages_htngProfileMessageRQRoom.setHKStatus(_retrieveMessages_htngProfileMessageRQRoomHKStatus);
      TPAExtensionsType _retrieveMessages_htngProfileMessageRQRoomTPAExtensions = new TPAExtensionsType();
      List<Element> _retrieveMessages_htngProfileMessageRQRoomTPAExtensionsAny = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomTPAExtensions.getAny().addAll(_retrieveMessages_htngProfileMessageRQRoomTPAExtensionsAny);
      _retrieveMessages_htngProfileMessageRQRoom.setTPAExtensions(_retrieveMessages_htngProfileMessageRQRoomTPAExtensions);
      _retrieveMessages_htngProfileMessageRQRoom.setRoomID("RoomID-1909058974");
      _retrieveMessages_htngProfileMessageRQ.setRoom(_retrieveMessages_htngProfileMessageRQRoom);
      TPAExtensionsType _retrieveMessages_htngProfileMessageRQTPAExtensions = new TPAExtensionsType();
      List<Element> _retrieveMessages_htngProfileMessageRQTPAExtensionsAny = new ArrayList<>();
      Element _retrieveMessages_htngProfileMessageRQTPAExtensionsAnyVal1 = null;
      _retrieveMessages_htngProfileMessageRQTPAExtensionsAny.add(_retrieveMessages_htngProfileMessageRQTPAExtensionsAnyVal1);
      _retrieveMessages_htngProfileMessageRQTPAExtensions.getAny().addAll(_retrieveMessages_htngProfileMessageRQTPAExtensionsAny);
      _retrieveMessages_htngProfileMessageRQ.setTPAExtensions(_retrieveMessages_htngProfileMessageRQTPAExtensions);
      HTNGProfileMessageStatusType _retrieveMessages_htngProfileMessageRQProfileMessageStatus = HTNGProfileMessageStatusType.DELETED;
      _retrieveMessages_htngProfileMessageRQ.setProfileMessageStatus(_retrieveMessages_htngProfileMessageRQProfileMessageStatus);
      _retrieveMessages_htngProfileMessageRQ.setEchoToken("EchoToken243790509");
      _retrieveMessages_htngProfileMessageRQ.setTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.775+08:00"));
      _retrieveMessages_htngProfileMessageRQ.setTarget("Target1854331344");
      _retrieveMessages_htngProfileMessageRQ.setTargetName("TargetName725913229");
      _retrieveMessages_htngProfileMessageRQ.setVersion(new BigDecimal("8466614471012440823.1529453360583787798"));
      _retrieveMessages_htngProfileMessageRQ.setTransactionIdentifier("TransactionIdentifier2125878297");
      _retrieveMessages_htngProfileMessageRQ.setSequenceNmbr(new BigInteger("6269208749642408187166239942873240907"));
      _retrieveMessages_htngProfileMessageRQ.setTransactionStatusCode("TransactionStatusCode34144156");
      _retrieveMessages_htngProfileMessageRQ.setRetransmissionIndicator(true);
      _retrieveMessages_htngProfileMessageRQ.setCorrelationID("CorrelationID-1674705948");
      _retrieveMessages_htngProfileMessageRQ.setPrimaryLangID("PrimaryLangID-428495910");
      _retrieveMessages_htngProfileMessageRQ.setAltLangID("AltLangID-483836716");
      HTNGProfileMessageRS _retrieveMessages__return = port.retrieveMessages(_retrieveMessages_htngProfileMessageRQ);
      System.out.println("retrieveMessages.result=" + _retrieveMessages__return);
      System.out.println("Invoking updateMessageStatus...");
      HTNGProfileMessageStatusNotifRQ _updateMessageStatus_htngProfileMessageStatusRQ = new HTNGProfileMessageStatusNotifRQ();
      _retrieveMessages_htngProfileMessageRQPOS = new POSType();
      _retrieveMessages_htngProfileMessageRQPOSSource = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1 = new SourceType();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID = new SourceType.RequestorID();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName = new CompanyNameType();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setValue("Value-1349068806");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setDivision("Division-1638066773");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setDepartment("Department-1431357598");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setCompanyShortName("CompanyShortName768866186");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setTravelSector("TravelSector201185619");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setCode("Code673425919");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName.setCodeContext("CodeContext324617814");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setCompanyName(_retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorIDCompanyName);
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setURL("URL819038768");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setType("Type-1835205563");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setInstance("Instance290504902");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setIDContext("IDContext1651886211");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setID("ID1669168893");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID.setMessagePassword("MessagePassword1168442542");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setRequestorID(_retrieveMessages_htngProfileMessageRQPOSSourceVal1RequestorID);
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position = new SourceType.Position();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setLatitude("Latitude1309132172");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setLongitude("Longitude-272677049");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setAltitude("Altitude-1713382343");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setAltitudeUnitOfMeasureCode("AltitudeUnitOfMeasureCode-822769372");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1Position.setPositionAccuracy("PositionAccuracy-1281654943");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setPosition(_retrieveMessages_htngProfileMessageRQPOSSourceVal1Position);
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel = new SourceType.BookingChannel();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName = new CompanyNameType();
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setValue("Value-1952980565");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setDivision("Division-1789366088");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setDepartment("Department24846120");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setCompanyShortName("CompanyShortName-2125620479");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setTravelSector("TravelSector1608742326");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setCode("Code-1273764701");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName.setCodeContext("CodeContext667391642");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel.setCompanyName(
         _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannelCompanyName
      );
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel.setType("Type58340149");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel.setPrimary(true);
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setBookingChannel(_retrieveMessages_htngProfileMessageRQPOSSourceVal1BookingChannel);
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setAgentSine("AgentSine112890494");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setPseudoCityCode("PseudoCityCode-567412624");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setISOCountry("ISOCountry2118712185");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setISOCurrency("ISOCurrency-316091772");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setAgentDutyCode("AgentDutyCode-867865499");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setAirlineVendorID("AirlineVendorID1695759522");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setAirportCode("AirportCode309588191");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setFirstDepartPoint("FirstDepartPoint-2059186246");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setERSPUserID("ERSPUserID-1343917672");
      _retrieveMessages_htngProfileMessageRQPOSSourceVal1.setTerminalID("TerminalID-1111495832");
      _retrieveMessages_htngProfileMessageRQPOSSource.add(_retrieveMessages_htngProfileMessageRQPOSSourceVal1);
      _retrieveMessages_htngProfileMessageRQPOS.getSource().addAll(_retrieveMessages_htngProfileMessageRQPOSSource);
      _updateMessageStatus_htngProfileMessageStatusRQ.setPOS(_retrieveMessages_htngProfileMessageRQPOS);
      _retrieveMessages_htngProfileMessageRQUniqueID = new UniqueIDType();
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName = new CompanyNameType();
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setValue("Value223196467");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setDivision("Division-812679451");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setDepartment("Department1487829050");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setCompanyShortName("CompanyShortName1074666919");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setTravelSector("TravelSector-80742306");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setCode("Code-2062152535");
      _retrieveMessages_htngProfileMessageRQUniqueIDCompanyName.setCodeContext("CodeContext511594325");
      _retrieveMessages_htngProfileMessageRQUniqueID.setCompanyName(_retrieveMessages_htngProfileMessageRQUniqueIDCompanyName);
      _retrieveMessages_htngProfileMessageRQUniqueID.setURL("URL487755944");
      _retrieveMessages_htngProfileMessageRQUniqueID.setType("Type1931252751");
      _retrieveMessages_htngProfileMessageRQUniqueID.setInstance("Instance814805728");
      _retrieveMessages_htngProfileMessageRQUniqueID.setIDContext("IDContext-2019197663");
      _retrieveMessages_htngProfileMessageRQUniqueID.setID("ID-644431606");
      _updateMessageStatus_htngProfileMessageStatusRQ.setUniqueID(_retrieveMessages_htngProfileMessageRQUniqueID);
      HTNGRequestBaseType.PropertyInfo _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo = new HTNGRequestBaseType.PropertyInfo();
      _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo.setChainCode("ChainCode1642216855");
      _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo.setBrandCode("BrandCode-619835731");
      _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo.setHotelCode("HotelCode-1889067643");
      _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo.setHotelCityCode("HotelCityCode-836419334");
      _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo.setHotelName("HotelName884994580");
      _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo.setHotelCodeContext("HotelCodeContext-270761008");
      _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo.setChainName("ChainName1656333534");
      _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo.setBrandName("BrandName952679840");
      _updateMessageStatus_htngProfileMessageStatusRQPropertyInfo.setAreaID("AreaID-1517256495");
      _updateMessageStatus_htngProfileMessageStatusRQ.setPropertyInfo(_updateMessageStatus_htngProfileMessageStatusRQPropertyInfo);
      _updateMessageStatus_htngProfileMessageStatusRQ.setEchoToken("EchoToken-1784905413");
      _updateMessageStatus_htngProfileMessageStatusRQ.setTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.777+08:00"));
      _updateMessageStatus_htngProfileMessageStatusRQ.setTarget("Target118131454");
      _updateMessageStatus_htngProfileMessageStatusRQ.setTargetName("TargetName699492316");
      _updateMessageStatus_htngProfileMessageStatusRQ.setVersion(new BigDecimal("-5243922878060152090.5360165872893408305"));
      _updateMessageStatus_htngProfileMessageStatusRQ.setTransactionIdentifier("TransactionIdentifier783415098");
      _updateMessageStatus_htngProfileMessageStatusRQ.setSequenceNmbr(new BigInteger("3582099889865957400572337578638463642"));
      _updateMessageStatus_htngProfileMessageStatusRQ.setTransactionStatusCode("TransactionStatusCode-340132956");
      _updateMessageStatus_htngProfileMessageStatusRQ.setRetransmissionIndicator(false);
      _updateMessageStatus_htngProfileMessageStatusRQ.setCorrelationID("CorrelationID1572280826");
      _updateMessageStatus_htngProfileMessageStatusRQ.setPrimaryLangID("PrimaryLangID-1779727520");
      _updateMessageStatus_htngProfileMessageStatusRQ.setAltLangID("AltLangID1236960692");
      _retrieveMessages_htngProfileMessageRQRoom = new HTNGComponentRoomType();
      _retrieveMessages_htngProfileMessageRQRoomRoomType = new RoomTypeType();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription = new ParagraphType();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescriptionTextOrImageOrURL = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.getTextOrImageOrURL()
         .addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescriptionTextOrImageOrURL);
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setName("Name-879400144");
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setParagraphNumber(new BigInteger("-65908027555515284602097276221629758290"));
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setCreateDateTime(
         DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.777+08:00")
      );
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setCreatorID("CreatorID-1033579732");
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setLastModifyDateTime(
         DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.778+08:00")
      );
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setLastModifierID("LastModifierID871005624");
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setPurgeDate(
         DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.778+08:00")
      );
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription.setLanguage("Language1002310126");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomDescription(_retrieveMessages_htngProfileMessageRQRoomRoomTypeRoomDescription);
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetails = new AdditionalDetailsType();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetailsAdditionalDetail = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetails.getAdditionalDetail()
         .addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetailsAdditionalDetail);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setAdditionalDetails(_retrieveMessages_htngProfileMessageRQRoomRoomTypeAdditionalDetails);
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenities = new RoomTypeType.Amenities();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenitiesAmenity = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenities.getAmenity().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenitiesAmenity);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setAmenities(_retrieveMessages_htngProfileMessageRQRoomRoomTypeAmenities);
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeOccupancy = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomType.getOccupancy().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeOccupancy);
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensions = new TPAExtensionsType();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensionsAny = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensions.getAny().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensionsAny);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setTPAExtensions(_retrieveMessages_htngProfileMessageRQRoomRoomTypeTPAExtensions);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setNumberOfUnits(new BigInteger("75022197953638860516592507706462469426"));
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setIsRoom(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setIsConverted(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setIsAlternate(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setReqdGuaranteeType("ReqdGuaranteeType-229511088");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomType("RoomType687732197");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomTypeCode("RoomTypeCode-948443766");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomCategory("RoomCategory2089985861");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomID("RoomID489754213");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setFloor(-302616496);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setInvBlockCode("InvBlockCode2091010639");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomLocationCode("RoomLocationCode-698413246");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomViewCode("RoomViewCode-2078071114");
      _retrieveMessages_htngProfileMessageRQRoomRoomTypeBedTypeCode = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomType.getBedTypeCode().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypeBedTypeCode);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setNonSmoking(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setConfiguration("Configuration-493869242");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setSizeMeasurement("SizeMeasurement652106146");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setQuantity(-1758942469);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setComposite(false);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomClassificationCode("RoomClassificationCode-105854490");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomArchitectureCode("RoomArchitectureCode-1616998063");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setRoomGender("RoomGender-804609907");
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setSharedRoomInd(true);
      _retrieveMessages_htngProfileMessageRQRoomRoomType.setPromotionCode("PromotionCode1492408654");
      _retrieveMessages_htngProfileMessageRQRoomRoomTypePromotionVendorCode = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomRoomType.getPromotionVendorCode().addAll(_retrieveMessages_htngProfileMessageRQRoomRoomTypePromotionVendorCode);
      _retrieveMessages_htngProfileMessageRQRoom.setRoomType(_retrieveMessages_htngProfileMessageRQRoomRoomType);
      _retrieveMessages_htngProfileMessageRQRoomTelephoneExtensions = new HTNGTelephoneExtensionType();
      _retrieveMessages_htngProfileMessageRQRoomTelephoneExtensionsTelephoneExtention = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomTelephoneExtensions.getTelephoneExtention()
         .addAll(_retrieveMessages_htngProfileMessageRQRoomTelephoneExtensionsTelephoneExtention);
      _retrieveMessages_htngProfileMessageRQRoom.setTelephoneExtensions(_retrieveMessages_htngProfileMessageRQRoomTelephoneExtensions);
      _retrieveMessages_htngProfileMessageRQRoomHKStatus = HTNGHousekeepingStatusType.PICKUP;
      _retrieveMessages_htngProfileMessageRQRoom.setHKStatus(_retrieveMessages_htngProfileMessageRQRoomHKStatus);
      _retrieveMessages_htngProfileMessageRQRoomTPAExtensions = new TPAExtensionsType();
      _retrieveMessages_htngProfileMessageRQRoomTPAExtensionsAny = new ArrayList<>();
      _retrieveMessages_htngProfileMessageRQRoomTPAExtensions.getAny().addAll(_retrieveMessages_htngProfileMessageRQRoomTPAExtensionsAny);
      _retrieveMessages_htngProfileMessageRQRoom.setTPAExtensions(_retrieveMessages_htngProfileMessageRQRoomTPAExtensions);
      _retrieveMessages_htngProfileMessageRQRoom.setRoomID("RoomID-769412807");
      _updateMessageStatus_htngProfileMessageStatusRQ.setRoom(_retrieveMessages_htngProfileMessageRQRoom);
      HTNGProfileMessageSummaryType _updateMessageStatus_htngProfileMessageStatusRQProfileMessageSummary = new HTNGProfileMessageSummaryType();
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessageSummary.setImage(-1630411226);
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessageSummary.setText(1902904624);
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessageSummary.setVideo(-463284149);
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessageSummary.setOther(-1805060778);
      _updateMessageStatus_htngProfileMessageStatusRQ.setProfileMessageSummary(_updateMessageStatus_htngProfileMessageStatusRQProfileMessageSummary);
      HTNGProfileMessageStatusNotifRQ.ProfileMessages _updateMessageStatus_htngProfileMessageStatusRQProfileMessages = new HTNGProfileMessageStatusNotifRQ.ProfileMessages();
      List<HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage> _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessage = new ArrayList<>();
      HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1 = new HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage();
      List<JAXBElement<? extends Object>> _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1TextOrImageOrURL = new ArrayList<>();
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.getTextOrImageOrURL()
         .addAll(_updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1TextOrImageOrURL);
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setName("Name1443671912");
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setParagraphNumber(
         new BigInteger("-3395467251269966598533418702656212189")
      );
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setCreateDateTime(
         DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.779+08:00")
      );
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setCreatorID("CreatorID2146666658");
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setLastModifyDateTime(
         DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.779+08:00")
      );
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setLastModifierID("LastModifierID881785239");
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setPurgeDate(
         DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.779+08:00")
      );
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setLanguage("Language-1041805529");
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setCreatedBySystemID("CreatedBySystemID-1622678532");
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setMessageID("MessageID389236385");
      HTNGProfileMessageStatusType _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1Status = HTNGProfileMessageStatusType.NEW;
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1.setStatus(
         _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1Status
      );
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessage.add(
         _updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessageVal1
      );
      _updateMessageStatus_htngProfileMessageStatusRQProfileMessages.getProfileMessage()
         .addAll(_updateMessageStatus_htngProfileMessageStatusRQProfileMessagesProfileMessage);
      _updateMessageStatus_htngProfileMessageStatusRQ.setProfileMessages(_updateMessageStatus_htngProfileMessageStatusRQProfileMessages);
      TPAExtensionsType _updateMessageStatus_htngProfileMessageStatusRQTPAExtensions = new TPAExtensionsType();
      List<Element> _updateMessageStatus_htngProfileMessageStatusRQTPAExtensionsAny = new ArrayList<>();
      Element _updateMessageStatus_htngProfileMessageStatusRQTPAExtensionsAnyVal1 = null;
      _updateMessageStatus_htngProfileMessageStatusRQTPAExtensionsAny.add(_updateMessageStatus_htngProfileMessageStatusRQTPAExtensionsAnyVal1);
      _updateMessageStatus_htngProfileMessageStatusRQTPAExtensions.getAny().addAll(_updateMessageStatus_htngProfileMessageStatusRQTPAExtensionsAny);
      _updateMessageStatus_htngProfileMessageStatusRQ.setTPAExtensions(_updateMessageStatus_htngProfileMessageStatusRQTPAExtensions);
      HTNGResponseBaseType _updateMessageStatus__return = port.updateMessageStatus(_updateMessageStatus_htngProfileMessageStatusRQ);
      System.out.println("updateMessageStatus.result=" + _updateMessageStatus__return);
      System.exit(0);
   }
}
