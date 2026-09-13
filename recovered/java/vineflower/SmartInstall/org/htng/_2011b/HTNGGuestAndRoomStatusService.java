package org.htng._2011b;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(
   name = "HTNG_GuestAndRoomStatusService",
   wsdlLocation = "classpath:HTNG/services/HTNG_GuestAndRoomStatusService.wsdl",
   targetNamespace = "http://htng.org/2011B"
)
public class HTNGGuestAndRoomStatusService extends Service {
   public static final URL WSDL_LOCATION;
   public static final QName SERVICE = new QName("http://htng.org/2011B", "HTNG_GuestAndRoomStatusService");
   public static final QName RoomStatusManagement = new QName("http://htng.org/2011B", "RoomStatusManagement");
   public static final QName StayNotification = new QName("http://htng.org/2011B", "StayNotification");
   public static final QName WakeupSchedulingManagement = new QName("http://htng.org/2011B", "WakeupSchedulingManagement");
   public static final QName GuestCommunications = new QName("http://htng.org/2011B", "GuestCommunications");

   public HTNGGuestAndRoomStatusService(URL wsdlLocation) {
      super(wsdlLocation, SERVICE);
   }

   public HTNGGuestAndRoomStatusService(URL wsdlLocation, QName serviceName) {
      super(wsdlLocation, serviceName);
   }

   public HTNGGuestAndRoomStatusService() {
      super(WSDL_LOCATION, SERVICE);
   }

   public HTNGGuestAndRoomStatusService(WebServiceFeature... features) {
      super(WSDL_LOCATION, SERVICE, features);
   }

   public HTNGGuestAndRoomStatusService(URL wsdlLocation, WebServiceFeature... features) {
      super(wsdlLocation, SERVICE, features);
   }

   public HTNGGuestAndRoomStatusService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
      super(wsdlLocation, serviceName, features);
   }

   @WebEndpoint(name = "RoomStatusManagement")
   public RoomStatusManagement getRoomStatusManagement() {
      return super.getPort(RoomStatusManagement, RoomStatusManagement.class);
   }

   @WebEndpoint(name = "RoomStatusManagement")
   public RoomStatusManagement getRoomStatusManagement(WebServiceFeature... features) {
      return super.getPort(RoomStatusManagement, RoomStatusManagement.class, features);
   }

   @WebEndpoint(name = "StayNotification")
   public StayNotification getStayNotification() {
      return super.getPort(StayNotification, StayNotification.class);
   }

   @WebEndpoint(name = "StayNotification")
   public StayNotification getStayNotification(WebServiceFeature... features) {
      return super.getPort(StayNotification, StayNotification.class, features);
   }

   @WebEndpoint(name = "WakeupSchedulingManagement")
   public WakeupSchedulingManagement getWakeupSchedulingManagement() {
      return super.getPort(WakeupSchedulingManagement, WakeupSchedulingManagement.class);
   }

   @WebEndpoint(name = "WakeupSchedulingManagement")
   public WakeupSchedulingManagement getWakeupSchedulingManagement(WebServiceFeature... features) {
      return super.getPort(WakeupSchedulingManagement, WakeupSchedulingManagement.class, features);
   }

   @WebEndpoint(name = "GuestCommunications")
   public GuestCommunications getGuestCommunications() {
      return super.getPort(GuestCommunications, GuestCommunications.class);
   }

   @WebEndpoint(name = "GuestCommunications")
   public GuestCommunications getGuestCommunications(WebServiceFeature... features) {
      return super.getPort(GuestCommunications, GuestCommunications.class, features);
   }

   static {
      URL url = HTNGGuestAndRoomStatusService.class.getClassLoader().getResource("HTNG/services/HTNG_GuestAndRoomStatusService.wsdl");
      if (url == null) {
         Logger.getLogger(HTNGGuestAndRoomStatusService.class.getName())
            .log(Level.INFO, "Can not initialize the default wsdl from {0}", "classpath:HTNG/services/HTNG_GuestAndRoomStatusService.wsdl");
      }

      WSDL_LOCATION = url;
   }
}
