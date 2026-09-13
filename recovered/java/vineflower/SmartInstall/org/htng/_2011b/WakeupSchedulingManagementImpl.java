package org.htng._2011b;

import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.WakeupInfoManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.ActionType;
import org.opentravel.ota._2003._05.SuccessType;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(
   serviceName = "HTNG_GuestAndRoomStatusService",
   portName = "WakeupSchedulingManagement",
   targetNamespace = "http://htng.org/2011B",
   wsdlLocation = "classpath:/HTNG/services/HTNG_GuestAndRoomStatusService.wsdl",
   endpointInterface = "org.htng._2011b.WakeupSchedulingManagement"
)
public class WakeupSchedulingManagementImpl implements WakeupSchedulingManagement {
   private static final Logger LOG = LoggerFactory.getLogger(WakeupSchedulingManagementImpl.class.getName());

   @Override
   public HTNGWakeupSchedulingNotifRS scheduleWakeup(HTNGWakeupSchedulingNotifRQ htngWakeupSchedulingNotifRQ) {
      LOG.info("Executing operation scheduleWakeup");
      HTNGWakeupSchedulingNotifRS _return = new HTNGWakeupSchedulingNotifRS();

      try {
         String requestData = PmsUtils.changeXML2String(htngWakeupSchedulingNotifRQ);
         LOG.info("HTNG >>>>>>>> SI {}", requestData);
         HTNGWakeupSchedulingNotifRQ.RoomInformationList roomInfoList = htngWakeupSchedulingNotifRQ.getRoomInformationList();
         if (roomInfoList == null) {
            _return.setErrors(HTNGHelper.getErrorsType("lost element:RoomInformationList"));
         } else {
            HTNGComponentRoomType room = roomInfoList.getRoom();
            UniqueIDType uniqueId = roomInfoList.getWakeupID();
            HTNGWakeupSchedulingNotifRQ.RoomInformationList.WakeupInfo wakeupInfo = roomInfoList.getWakeupInfo();
            if (room == null) {
               _return.setErrors(HTNGHelper.getErrorsType("lost element:Room"));
            } else if (uniqueId == null) {
               _return.setErrors(HTNGHelper.getErrorsType("lost element:WakeupID"));
            } else if (wakeupInfo == null) {
               _return.setErrors(HTNGHelper.getErrorsType("lost element:WakeupInfo"));
            } else {
               String roomId = room.getRoomID();
               _return.setWakeupID(uniqueId);
               ActionType actionType = wakeupInfo.getAction();
               int totalDays = wakeupInfo.getTotalDays();
               XMLGregorianCalendar wakeupTimeCalendar = wakeupInfo.getWakeupTime();
               String wakeupId = uniqueId.getID();
               String wakeupTime = TpvDateUtils.formatXMLGregorianCalendar(wakeupTimeCalendar);
               LOG.info(
                  "Executing HTNG wakeup for RoomId:{},WakeupId:{},ActionType:{},TotalDays:{},WakeupTime: {}",
                  roomId,
                  wakeupId,
                  actionType,
                  totalDays,
                  wakeupTime
               );
               HTNGHelper.checkTVExists(roomId);
               WakeupInfoManager wakeupInfoManager = JpaManager.getWakeupInfoManager();
               switch (actionType) {
                  case ADD_UPDATE:
                  case REPLACE:
                     if (!wakeupInfoManager.findWakeupInfoByRoomIdAndWakeupId(roomId, wakeupId).isEmpty()) {
                        wakeupInfoManager.deleteWakeupByRoomIdAndWakeupId(roomId, wakeupId);
                     }

                     for (int i = 0; i < totalDays; i++) {
                        wakeupInfoManager.insertWakeup(roomId, wakeupId, wakeupTime);
                        wakeupTime = TpvDateUtils.getNextDate(wakeupTime);
                     }
                     break;
                  case ADD:
                     for (int i = 0; i < totalDays; i++) {
                        wakeupInfoManager.insertWakeup(roomId, wakeupId, wakeupTime);
                        wakeupTime = TpvDateUtils.getNextDate(wakeupTime);
                     }
                     break;
                  case CANCEL:
                  case DELETE:
                     wakeupInfoManager.deleteWakeupByRoomIdAndWakeupId(roomId, wakeupId);
               }

               SuccessType returnSuccess = new SuccessType();
               _return.setSuccess(returnSuccess);
            }
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         _return.setErrors(HTNGHelper.getErrorsType(e.getMessage()));
      }

      return _return;
   }
}
