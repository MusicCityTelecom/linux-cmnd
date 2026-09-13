package org.htng._2011b;

import com.tpvision.smartinstall.dao.core.GuestInfo;
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
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import org.dom4j.DocumentHelper;
import org.opentravel.ota._2003._05.CompanyNameType;
import org.opentravel.ota._2003._05.CostingItemType;
import org.opentravel.ota._2003._05.POSType;
import org.opentravel.ota._2003._05.PkgInvoiceDetail;
import org.opentravel.ota._2003._05.SourceType;
import org.opentravel.ota._2003._05.TPAExtensionsType;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public final class FolioManagementClient {
   private static final Logger LOG = LoggerFactory.getLogger(FolioManagementClient.class);
   private static final QName SERVICE_NAME = new QName("http://htng.org/2011B", "HTNG_KioskService");
   private String wsdl;
   private FolioManagement port;

   public FolioManagement getPort() {
      if (this.port == null) {
         HTNGKioskService ss = new HTNGKioskService();
         this.port = ss.getFolioManagement();
         BindingProvider bp = (BindingProvider)this.port;
         bp.getRequestContext().put("javax.xml.ws.service.endpoint.address", this.wsdl);
      }

      return this.port;
   }

   public void connect() {
      this.port = null;
      this.getPort();
   }

   public FolioManagementClient(String wsdl) {
      try {
         this.wsdl = wsdl;
         new URL(wsdl);
      } catch (MalformedURLException e) {
         LOG.error("invalid wsdlurl:{}", wsdl);
      }
   }

   public void requestBill(String roomId) {
      HTNGHotelFolioRQ htngHotelFolioRQ = new HTNGHotelFolioRQ();
      htngHotelFolioRQ.setUniqueID(HTNGHelper.generateUniqueTypeID("1", roomId));
      htngHotelFolioRQ.setEchoToken("EchoToken-1006521602");

      try {
         HTNGHotelFolioRS retrieveFolio = this.port.retrieveFolio(htngHotelFolioRQ);

         for (HTNGHotelFolioRS.Folios.Folio folio : retrieveFolio.getFolios().getFolio()) {
            PkgInvoiceDetail pkgInvoiceDetail = folio.getRevenueSummary();
            PmsUtils.deletePreBillItems(roomId);

            for (CostingItemType item : pkgInvoiceDetail.getCostingItems().getCostingItem()) {
               org.dom4j.Element rootElt = DocumentHelper.createDocument().addElement("roombillresults");
               rootElt.addElement("room").setText(roomId);
               rootElt.addElement("description").setText(item.getDescription());
               rootElt.addElement("charge").setText(String.valueOf(item.getExtendedCost().getAmount()));
               rootElt.addElement("datetime").setText(TpvDateUtils.getBillItemDate(new Date()));
               PmsUtils.saveBillItem2DB(rootElt);
            }

            org.dom4j.Element rootElt = DocumentHelper.createDocument().addElement("roombillresults");
            rootElt.addElement("room").setText(roomId);
            rootElt.addElement("balance").setText(String.valueOf(pkgInvoiceDetail.getGrossAmount().getAmount()));
            GuestInfo guest = PmsUtils.saveBillBalance2DB(rootElt);
            PmsUtils.sendBill2TV(guest);
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public static void main(String[] args) throws Exception {
      URL wsdlURL = HTNGKioskService.WSDL_LOCATION;
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

      HTNGKioskService ss = new HTNGKioskService(wsdlURL, SERVICE_NAME);
      FolioManagement port = ss.getFolioManagement();
      System.out.println("Invoking retrieveFolio...");
      HTNGHotelFolioRQ _retrieveFolio_htngHotelFolioRQ = new HTNGHotelFolioRQ();
      POSType _retrieveFolio_htngHotelFolioRQPOS = new POSType();
      List<SourceType> _retrieveFolio_htngHotelFolioRQPOSSource = new ArrayList<>();
      SourceType _retrieveFolio_htngHotelFolioRQPOSSourceVal1 = new SourceType();
      SourceType.RequestorID _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorID = new SourceType.RequestorID();
      CompanyNameType _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorIDCompanyName = new CompanyNameType();
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorIDCompanyName.setValue("Value-1623752242");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorIDCompanyName.setDivision("Division-1021695910");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorIDCompanyName.setDepartment("Department1217012187");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorIDCompanyName.setCompanyShortName("CompanyShortName-496036201");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorIDCompanyName.setTravelSector("TravelSector-1447296854");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorIDCompanyName.setCode("Code364121903");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorIDCompanyName.setCodeContext("CodeContext77432645");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorID.setCompanyName(_retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorIDCompanyName);
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorID.setURL("URL1963752152");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorID.setType("Type-1564795501");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorID.setInstance("Instance-69629960");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorID.setIDContext("IDContext-882715063");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorID.setID("ID-1927350710");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorID.setMessagePassword("MessagePassword1137403207");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setRequestorID(_retrieveFolio_htngHotelFolioRQPOSSourceVal1RequestorID);
      SourceType.Position _retrieveFolio_htngHotelFolioRQPOSSourceVal1Position = new SourceType.Position();
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1Position.setLatitude("Latitude1471866675");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1Position.setLongitude("Longitude2133505279");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1Position.setAltitude("Altitude-835080618");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1Position.setAltitudeUnitOfMeasureCode("AltitudeUnitOfMeasureCode-290757665");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1Position.setPositionAccuracy("PositionAccuracy1015553321");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setPosition(_retrieveFolio_htngHotelFolioRQPOSSourceVal1Position);
      SourceType.BookingChannel _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannel = new SourceType.BookingChannel();
      CompanyNameType _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannelCompanyName = new CompanyNameType();
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannelCompanyName.setValue("Value708780244");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannelCompanyName.setDivision("Division1955886494");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannelCompanyName.setDepartment("Department-1927293353");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannelCompanyName.setCompanyShortName("CompanyShortName-29859045");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannelCompanyName.setTravelSector("TravelSector-529209935");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannelCompanyName.setCode("Code506846802");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannelCompanyName.setCodeContext("CodeContext-1044058034");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannel.setCompanyName(_retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannelCompanyName);
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannel.setType("Type1208158139");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannel.setPrimary(true);
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setBookingChannel(_retrieveFolio_htngHotelFolioRQPOSSourceVal1BookingChannel);
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setAgentSine("AgentSine-168059455");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setPseudoCityCode("PseudoCityCode-393758199");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setISOCountry("ISOCountry2117340893");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setISOCurrency("ISOCurrency-896678628");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setAgentDutyCode("AgentDutyCode-1785916110");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setAirlineVendorID("AirlineVendorID759745213");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setAirportCode("AirportCode1132370030");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setFirstDepartPoint("FirstDepartPoint-2105733213");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setERSPUserID("ERSPUserID397218805");
      _retrieveFolio_htngHotelFolioRQPOSSourceVal1.setTerminalID("TerminalID259646221");
      _retrieveFolio_htngHotelFolioRQPOSSource.add(_retrieveFolio_htngHotelFolioRQPOSSourceVal1);
      _retrieveFolio_htngHotelFolioRQPOS.getSource().addAll(_retrieveFolio_htngHotelFolioRQPOSSource);
      _retrieveFolio_htngHotelFolioRQ.setPOS(_retrieveFolio_htngHotelFolioRQPOS);
      UniqueIDType _retrieveFolio_htngHotelFolioRQUniqueID = new UniqueIDType();
      CompanyNameType _retrieveFolio_htngHotelFolioRQUniqueIDCompanyName = new CompanyNameType();
      _retrieveFolio_htngHotelFolioRQUniqueIDCompanyName.setValue("Value93995684");
      _retrieveFolio_htngHotelFolioRQUniqueIDCompanyName.setDivision("Division-2141673176");
      _retrieveFolio_htngHotelFolioRQUniqueIDCompanyName.setDepartment("Department1310533300");
      _retrieveFolio_htngHotelFolioRQUniqueIDCompanyName.setCompanyShortName("CompanyShortName-2036585519");
      _retrieveFolio_htngHotelFolioRQUniqueIDCompanyName.setTravelSector("TravelSector-937952393");
      _retrieveFolio_htngHotelFolioRQUniqueIDCompanyName.setCode("Code-630439981");
      _retrieveFolio_htngHotelFolioRQUniqueIDCompanyName.setCodeContext("CodeContext1586890766");
      _retrieveFolio_htngHotelFolioRQUniqueID.setCompanyName(_retrieveFolio_htngHotelFolioRQUniqueIDCompanyName);
      _retrieveFolio_htngHotelFolioRQUniqueID.setURL("URL765271320");
      _retrieveFolio_htngHotelFolioRQUniqueID.setType("Type-2110917297");
      _retrieveFolio_htngHotelFolioRQUniqueID.setInstance("Instance731918942");
      _retrieveFolio_htngHotelFolioRQUniqueID.setIDContext("IDContext-2038561178");
      _retrieveFolio_htngHotelFolioRQUniqueID.setID("ID778850672");
      _retrieveFolio_htngHotelFolioRQ.setUniqueID(_retrieveFolio_htngHotelFolioRQUniqueID);
      TPAExtensionsType _retrieveFolio_htngHotelFolioRQTPAExtensions = new TPAExtensionsType();
      List<Element> _retrieveFolio_htngHotelFolioRQTPAExtensionsAny = new ArrayList<>();
      Element _retrieveFolio_htngHotelFolioRQTPAExtensionsAnyVal1 = null;
      _retrieveFolio_htngHotelFolioRQTPAExtensionsAny.add(_retrieveFolio_htngHotelFolioRQTPAExtensionsAnyVal1);
      _retrieveFolio_htngHotelFolioRQTPAExtensions.getAny().addAll(_retrieveFolio_htngHotelFolioRQTPAExtensionsAny);
      _retrieveFolio_htngHotelFolioRQ.setTPAExtensions(_retrieveFolio_htngHotelFolioRQTPAExtensions);
      _retrieveFolio_htngHotelFolioRQ.setEchoToken("EchoToken-1006521602");
      _retrieveFolio_htngHotelFolioRQ.setTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-26T13:55:26.182+08:00"));
      _retrieveFolio_htngHotelFolioRQ.setTarget("Target397270776");
      _retrieveFolio_htngHotelFolioRQ.setTargetName("TargetName-700484873");
      _retrieveFolio_htngHotelFolioRQ.setVersion(new BigDecimal("7783567485809352431.508762086628552235"));
      _retrieveFolio_htngHotelFolioRQ.setTransactionIdentifier("TransactionIdentifier-1278713019");
      _retrieveFolio_htngHotelFolioRQ.setSequenceNmbr(new BigInteger("61848162642838081302454080458174270253"));
      _retrieveFolio_htngHotelFolioRQ.setTransactionStatusCode("TransactionStatusCode-348755123");
      _retrieveFolio_htngHotelFolioRQ.setRetransmissionIndicator(false);
      _retrieveFolio_htngHotelFolioRQ.setCorrelationID("CorrelationID-1841376068");
      _retrieveFolio_htngHotelFolioRQ.setPrimaryLangID("PrimaryLangID-169646272");
      _retrieveFolio_htngHotelFolioRQ.setAltLangID("AltLangID-741251859");
      HTNGHotelFolioRS _retrieveFolio__return = port.retrieveFolio(_retrieveFolio_htngHotelFolioRQ);
      System.out.println("retrieveFolio.result=" + _retrieveFolio__return);
      System.exit(0);
   }
}
