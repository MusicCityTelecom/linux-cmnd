package org.htng._2011b;

import com.tpvision.smartinstall.dao.core.Reservation;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeFactory;
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.HotelReservationsType;
import org.opentravel.ota._2003._05.SuccessType;
import org.opentravel.ota._2003._05.WarningType;
import org.opentravel.ota._2003._05.WarningsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(
   serviceName = "HTNG_GuestAndRoomStatusService",
   portName = "RoomStatusManagement",
   targetNamespace = "http://htng.org/2011B",
   wsdlLocation = "classpath:/HTNG/services/HTNG_GuestAndRoomStatusService.wsdl",
   endpointInterface = "org.htng._2011b.RoomStatusManagement"
)
public class RoomStatusManagementImpl implements RoomStatusManagement {
   private static final Logger LOG = LoggerFactory.getLogger(RoomStatusManagementImpl.class.getName());

   @Override
   public HTNGHotelRoomStatusSearchRS searchRooms(HTNGHotelRoomStatusSearchRQ htngHotelRoomStatusSearchRQ) {
      LOG.info("Executing operation searchRooms");
      HTNGHotelRoomStatusSearchRS _return = new HTNGHotelRoomStatusSearchRS();

      try {
         String requestData = PmsUtils.changeXML2String(htngHotelRoomStatusSearchRQ);
         LOG.info("HTNG >>>>>>>> SI {}", requestData);
         String roomId = htngHotelRoomStatusSearchRQ.getRoom().getRoomID();
         List<String> roomIds = new ArrayList<>();
         if (roomId != null && !roomId.equalsIgnoreCase("all")) {
            roomIds.add(roomId);
         } else {
            List<Reservation> reservations = JpaManager.getReservationManager().loadAll();
            roomIds = reservations.stream().map(Reservation::getRooms).distinct().collect(Collectors.toList());
         }

         HTNGHelper.fillResponseProperties(_return);
         HTNGHotelRoomStatusSearchRS.RoomInformationList roomInformationList = new HTNGHotelRoomStatusSearchRS.RoomInformationList();
         List<HTNGHotelRoomStatusSearchRS.RoomInformationList.RoomInformation> _returnRoomInformationListRoomInformation = new ArrayList<>();

         for (String aroomId : roomIds) {
            HTNGHotelRoomStatusSearchRS.RoomInformationList.RoomInformation roomInformation = new HTNGHotelRoomStatusSearchRS.RoomInformationList.RoomInformation();
            HTNGRoomElementType roomType = HTNGHelper.generateRoom(aroomId);
            roomInformation.setRoom(roomType);
            HotelReservationsType reservationsType = new HotelReservationsType();
            List<HotelReservationsType.HotelReservation> reservationList = new ArrayList<>();

            for (Reservation res : JpaManager.getReservationManager().findReservationByRooms(aroomId)) {
               reservationList.add(HTNGHelper.generateHoltelReservation(res.getReservationId()));
            }

            reservationsType.getHotelReservation().addAll(reservationList);
            roomInformation.setHotelReservations(reservationsType);
            _returnRoomInformationListRoomInformation.add(roomInformation);
         }

         roomInformationList.getRoomInformation().addAll(_returnRoomInformationListRoomInformation);
         _return.setRoomInformationList(roomInformationList);
         SuccessType returnSuccess = new SuccessType();
         _return.setSuccess(returnSuccess);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         _return.setErrors(HTNGHelper.getErrorsType(e.getMessage()));
      }

      return _return;
   }

   @Override
   public HTNGResponseBaseType updateRoomStatus(HTNGHotelRoomStatusUpdateNotifRQ htngHotelRoomStatusUpdateNotifRQ) {
      LOG.info("Executing operation updateRoomStatus");
      System.out.println(htngHotelRoomStatusUpdateNotifRQ);

      try {
         String requestData = PmsUtils.changeXML2String(htngHotelRoomStatusUpdateNotifRQ);
         LOG.info("HTNG >>>>>>>> SI {}", requestData);
         HTNGResponseBaseType _return = new HTNGResponseBaseType();
         SuccessType _returnSuccess = new SuccessType();
         _return.setSuccess(_returnSuccess);
         WarningsType _returnWarnings = new WarningsType();
         List<WarningType> _returnWarningsWarning = new ArrayList<>();
         WarningType _returnWarningsWarningVal1 = new WarningType();
         _returnWarningsWarningVal1.setValue("Value303539498");
         _returnWarningsWarningVal1.setLanguage("Language-208495117");
         _returnWarningsWarningVal1.setType("Type-112990657");
         _returnWarningsWarningVal1.setRPH("RPH-1721187406");
         _returnWarningsWarningVal1.setShortText("ShortText481180454");
         _returnWarningsWarningVal1.setCode("Code396892539");
         _returnWarningsWarningVal1.setDocURL("DocURL-448246056");
         _returnWarningsWarningVal1.setStatus("Status-1164327114");
         _returnWarningsWarningVal1.setTag("Tag-1000146547");
         _returnWarningsWarningVal1.setRecordID("RecordID-90479058");
         _returnWarningsWarning.add(_returnWarningsWarningVal1);
         _returnWarnings.getWarning().addAll(_returnWarningsWarning);
         _return.setWarnings(_returnWarnings);
         ErrorsType _returnErrors = new ErrorsType();
         List<ErrorType> _returnErrorsError = new ArrayList<>();
         ErrorType _returnErrorsErrorVal1 = new ErrorType();
         _returnErrorsErrorVal1.setValue("Value924224378");
         _returnErrorsErrorVal1.setLanguage("Language-669614484");
         _returnErrorsErrorVal1.setType("Type55636397");
         _returnErrorsErrorVal1.setNodeList("NodeList-2112596306");
         _returnErrorsErrorVal1.setShortText("ShortText-238073570");
         _returnErrorsErrorVal1.setCode("Code-1274405867");
         _returnErrorsErrorVal1.setDocURL("DocURL55981181");
         _returnErrorsErrorVal1.setStatus("Status-233979457");
         _returnErrorsErrorVal1.setTag("Tag2040701755");
         _returnErrorsErrorVal1.setRecordID("RecordID1426974182");
         _returnErrorsError.add(_returnErrorsErrorVal1);
         _returnErrors.getError().addAll(_returnErrorsError);
         _return.setErrors(_returnErrors);
         _return.setEchoToken("EchoToken1573797088");
         _return.setTimeStamp(DatatypeFactory.newInstance().newXMLGregorianCalendar("2020-03-24T09:54:33.816+08:00"));
         _return.setTarget("Target2036193598");
         _return.setTargetName("TargetName-1460903503");
         _return.setVersion(new BigDecimal("2952785344754498091.9005084143624368361"));
         _return.setTransactionIdentifier("TransactionIdentifier-573493922");
         _return.setSequenceNmbr(new BigInteger("-5308337807691351841890507908263095853"));
         _return.setTransactionStatusCode("TransactionStatusCode193501132");
         _return.setRetransmissionIndicator(false);
         _return.setCorrelationID("CorrelationID-1562070388");
         _return.setPrimaryLangID("PrimaryLangID1039734753");
         _return.setAltLangID("AltLangID2069352224");
         return _return;
      } catch (Exception ex) {
         ex.printStackTrace();
         throw new RuntimeException(ex);
      }
   }
}
