package org.htng._2011b;

import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.pms.PmsUtils;
import java.io.IOException;
import javax.jws.WebService;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;
import org.opentravel.ota._2003._05.HotelReservationsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(
   serviceName = "HTNG_GuestAndRoomStatusService",
   portName = "StayNotification",
   targetNamespace = "http://htng.org/2011B",
   wsdlLocation = "classpath:/HTNG/services/HTNG_GuestAndRoomStatusService.wsdl",
   endpointInterface = "org.htng._2011b.StayNotification"
)
public class StayNotificationImpl extends BaseHtngService implements StayNotification {
   private static final Logger LOG = LoggerFactory.getLogger(StayNotificationImpl.class.getName());

   @Override
   public HTNGResponseBaseType checkedIn(HTNGHotelCheckInNotifRQ htngHotelCheckInNotifRQ) {
      try {
         String requestData = PmsUtils.changeXML2String(htngHotelCheckInNotifRQ);
         LOG.info("HTNG >>>>>>>> SI {}", requestData);
         HTNGHelper.checkHTNGEnabled();
         HTNGHelper.setConnected(true);
         HTNGHelper.checkRoomId(htngHotelCheckInNotifRQ.getRoom());
         String roomId = htngHotelCheckInNotifRQ.getRoom().getRoomID();
         LOG.info("Executing HTNG checkedIn for {}", roomId);
         HTNGHelper.checkTVExists(roomId);
         HotelReservationsType.HotelReservation res = HTNGHelper.extractFirstReservation(htngHotelCheckInNotifRQ.getHotelReservations());
         Element rootElt = HTNGHelper.getElementFromReservation(res, roomId);
         GuestInfo guest = PmsUtils.saveCheckinResults2DB(rootElt);
         PmsUtils.processCheckin(guest.getGuestId());
         return this.successResponse();
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return this.failureResponse(e.getMessage());
      }
   }

   @Override
   public HTNGResponseBaseType checkedOut(HTNGHotelCheckOutNotifRQ htngHotelCheckOutNotifRQ) {
      try {
         String requestData = PmsUtils.changeXML2String(htngHotelCheckOutNotifRQ);
         LOG.info("HTNG >>>>>>>> SI {}", requestData);
         HTNGHelper.checkHTNGEnabled();
         HTNGHelper.setConnected(true);
         HTNGHelper.checkRoomId(htngHotelCheckOutNotifRQ.getRoom());
         String roomid = htngHotelCheckOutNotifRQ.getRoom().getRoomID();
         LOG.info("Executing HTNG checkedOut roomid:{}", roomid);
         GuestInfo guest = null;
         if (htngHotelCheckOutNotifRQ.getAffectedGuests() != null) {
            String guestId = htngHotelCheckOutNotifRQ.getAffectedGuests().getUniqueID().getID();
            Element rootElt = DocumentHelper.createDocument().addElement("checkoutresults");
            rootElt.addAttribute("resno", "");
            rootElt.addElement("room").setText(roomid);
            rootElt.addElement("guestid").setText(guestId);
            guest = PmsUtils.saveCheckoutResults2DB(rootElt);
         } else {
            guest = PmsUtils.getGuestInfoByRoomId(roomid);
         }

         if (guest == null) {
            throw new IOException("guest for room " + roomid + " not found");
         }

         PmsUtils.processCheckout(guest.getRoomid());
         return this.successResponse();
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return this.failureResponse(e.getMessage());
      }
   }

   @Override
   public HTNGResponseBaseType roomMoved(HTNGHotelRoomMoveNotifRQ htngHotelRoomMoveNotifRQ) {
      try {
         String requestData = PmsUtils.changeXML2String(htngHotelRoomMoveNotifRQ);
         LOG.info("HTNG >>>>>>>> SI {}", requestData);
         HTNGHelper.checkHTNGEnabled();
         HTNGHelper.setConnected(true);
         String srcRoomId = HTNGHelper.getSourceRoomId(htngHotelRoomMoveNotifRQ);
         String dstRoomId = HTNGHelper.getDestinationRoomId(htngHotelRoomMoveNotifRQ);
         LOG.info("room moved from {} to {}", srcRoomId, dstRoomId);
         if (!PmsUtils.isGuestCheckin(srcRoomId)) {
            HotelReservationsType.HotelReservation res = HTNGHelper.extractFirstReservation(
               htngHotelRoomMoveNotifRQ.getDestinationRoomInformation().getHotelReservations()
            );
            Element rootElt = HTNGHelper.getElementFromReservation(res, dstRoomId);
            GuestInfo guest = PmsUtils.saveCheckinResults2DB(rootElt);
            PmsUtils.checkin2TV(guest, true);
         } else {
            PmsUtils.changeGuestRoom(srcRoomId, dstRoomId);
         }

         return this.successResponse();
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return this.failureResponse(e.getMessage());
      }
   }

   @Override
   public HTNGResponseBaseType stayUpdated(HTNGHotelStayUpdateNotifRQ htngHotelStayUpdateNotifRQ) {
      try {
         String requestData = PmsUtils.changeXML2String(htngHotelStayUpdateNotifRQ);
         LOG.info("HTNG >>>>>>>> SI {}", requestData);
         HTNGHelper.checkHTNGEnabled();
         HTNGHelper.setConnected(true);
         HTNGHelper.checkRoomId(htngHotelStayUpdateNotifRQ.getRoom());
         String roomId = htngHotelStayUpdateNotifRQ.getRoom().getRoomID();
         HTNGHelper.checkTVExists(roomId);
         HotelReservationsType.HotelReservation res = HTNGHelper.extractFirstReservation(htngHotelStayUpdateNotifRQ.getHotelReservations());
         JSONObject updateContentObj = HTNGHelper.getUpdateContent(res);
         if (!updateContentObj.isEmpty()) {
            LOG.info("update content: {}", updateContentObj.toString());
            JSONObject timeSpan = updateContentObj.optJSONObject("TimeSpan");
            if (timeSpan != null) {
               PmsUtils.updateGuestInfo(roomId, PmsUtils.GuestInfoFieldType.GuestDepartureDate, timeSpan.optString("End"));
            }

            String language = updateContentObj.optString("Language");
            if (!"".equalsIgnoreCase(language)) {
               PmsUtils.updateGuestInfo(roomId, PmsUtils.GuestInfoFieldType.GuestLanguage, language);
            }

            String guestName = updateContentObj.optString("GuestName");
            if (!"".equalsIgnoreCase(guestName)) {
               PmsUtils.updateGuestInfo(roomId, PmsUtils.GuestInfoFieldType.GuestName, guestName);
            }
         }

         return this.successResponse();
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return this.failureResponse(e.getMessage());
      }
   }
}
