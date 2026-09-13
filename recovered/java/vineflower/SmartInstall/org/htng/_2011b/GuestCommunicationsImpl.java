package org.htng._2011b;

import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.util.Date;
import javax.jws.WebService;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.opentravel.ota._2003._05.FormattedTextTextType;
import org.opentravel.ota._2003._05.SuccessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(
   serviceName = "HTNG_GuestAndRoomStatusService",
   portName = "GuestCommunications",
   targetNamespace = "http://htng.org/2011B",
   wsdlLocation = "classpath:HTNG/services/HTNG_GuestAndRoomStatusService.wsdl",
   endpointInterface = "org.htng._2011b.GuestCommunications"
)
public class GuestCommunicationsImpl extends BaseHtngService implements GuestCommunications {
   private static final Logger LOG = LoggerFactory.getLogger(GuestCommunicationsImpl.class.getName());

   @Override
   public HTNGProfileMessageRS retrieveMessages(HTNGProfileMessageRQ htngProfileMessageRQ) {
      LOG.info("Executing operation retrieveMessages");
      System.out.println(htngProfileMessageRQ);
      HTNGProfileMessageRS _return = new HTNGProfileMessageRS();

      try {
         HTNGHelper.checkRoomId(htngProfileMessageRQ.getRoom());
         String roomId = htngProfileMessageRQ.getRoom().getRoomID();
         _return.setRoom(HTNGHelper.generateRoom(roomId));
         HTNGProfileMessageRS.ProfileMessages profileMessages = new HTNGProfileMessageRS.ProfileMessages();

         for (HTNGProfileMessageRS.ProfileMessages.ProfileMessage msg : HTNGHelper.getMessages(roomId)) {
            profileMessages.getProfileMessage().add(msg);
         }

         _return.setProfileMessages(profileMessages);
         _return.setSuccess(new SuccessType());
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         _return.setErrors(HTNGHelper.getErrorsType(e.getMessage()));
      }

      return _return;
   }

   @Override
   public HTNGResponseBaseType updateMessageStatus(HTNGProfileMessageStatusNotifRQ htngProfileMessageStatusRQ) {
      LOG.info("Executing operation updateMessageStatus,{}", htngProfileMessageStatusRQ);
      System.out.println(htngProfileMessageStatusRQ);

      try {
         HTNGHelper.checkHTNGEnabled();
         HTNGHelper.setConnected(true);
         String roomid = "0";
         if (htngProfileMessageStatusRQ.getRoom() != null) {
            roomid = htngProfileMessageStatusRQ.getRoom().getRoomID();
         }

         for (HTNGProfileMessageStatusNotifRQ.ProfileMessages.ProfileMessage message : htngProfileMessageStatusRQ.getProfileMessages().getProfileMessage()) {
            Element rootElt = DocumentHelper.createDocument().addElement("messagetextresults");
            rootElt.addElement("room").setText(roomid);
            rootElt.addElement("msgid").setText(message.getMessageID());
            HTNGProfileMessageStatusType statusType = message.getStatus();
            rootElt.addElement("status").setText(HTNGHelper.convertStatusToCMND(statusType));
            if (message.getStatus() == HTNGProfileMessageStatusType.NEW) {
               Date datetime = message.getCreateDateTime().toGregorianCalendar().getTime();
               rootElt.addElement("datetime").setText(TpvDateUtils.getMessageTimeFormat().format(datetime));
               String content = null;
               if (message.getTextOrImageOrURL() != null) {
                  FormattedTextTextType text = (FormattedTextTextType)message.getTextOrImageOrURL().get(0).getValue();
                  content = text.getValue();
                  rootElt.addElement("msgtext").setText(content);
               }

               LOG.info("new message received:id={},content={}", message.getMessageID(), content);
            } else {
               LOG.info("update message:id={},status={}", message.getMessageID(), message.getStatus().name());
            }

            PmsUtils.saveMessageTextResults2DB(rootElt);
         }

         return this.successResponse();
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return this.failureResponse(e.getMessage());
      }
   }
}
