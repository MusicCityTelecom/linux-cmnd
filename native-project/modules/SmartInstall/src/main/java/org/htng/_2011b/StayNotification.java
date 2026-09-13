package org.htng._2011b;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://htng.org/2011B", name = "StayNotification")
@XmlSeeAlso({org.opentravel.ota._2003._05.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle = ParameterStyle.BARE)
public interface StayNotification {
   @WebMethod(operationName = "CheckedIn", action = "http://htng.org/2011B/HTNG_GuestAndRoomStatusService#CheckedIn")
   @WebResult(name = "HTNG_HotelCheckInNotifRS", targetNamespace = "http://htng.org/2011B", partName = "HTNG_HotelCheckInNotifRS")
   HTNGResponseBaseType checkedIn(
      @WebParam(partName = "HTNG_HotelCheckInNotifRQ", name = "HTNG_HotelCheckInNotifRQ", targetNamespace = "http://htng.org/2011B") HTNGHotelCheckInNotifRQ var1
   );

   @WebMethod(operationName = "CheckedOut", action = "http://htng.org/2011B/HTNG_GuestAndRoomStatusService#CheckedOut")
   @WebResult(name = "HTNG_HotelCheckOutNotifRS", targetNamespace = "http://htng.org/2011B", partName = "HTNG_HotelCheckOutNotifRS")
   HTNGResponseBaseType checkedOut(
      @WebParam(partName = "HTNG_HotelCheckOutNotifRQ", name = "HTNG_HotelCheckOutNotifRQ", targetNamespace = "http://htng.org/2011B") HTNGHotelCheckOutNotifRQ var1
   );

   @WebMethod(operationName = "RoomMoved", action = "http://htng.org/2011B/HTNG_GuestAndRoomStatusService#RoomMoved")
   @WebResult(name = "HTNG_HotelRoomMoveNotifRS", targetNamespace = "http://htng.org/2011B", partName = "HTNG_HotelRoomMoveNotifRS")
   HTNGResponseBaseType roomMoved(
      @WebParam(partName = "HTNG_HotelRoomMoveNotifRQ", name = "HTNG_HotelRoomMoveNotifRQ", targetNamespace = "http://htng.org/2011B") HTNGHotelRoomMoveNotifRQ var1
   );

   @WebMethod(operationName = "StayUpdated", action = "http://htng.org/2011B/HTNG_GuestAndRoomStatusService#StayUpdated")
   @WebResult(name = "HTNG_HotelStayUpdateNotifRS", targetNamespace = "http://htng.org/2011B", partName = "HTNG_HotelStayUpdateNotifRS")
   HTNGResponseBaseType stayUpdated(
      @WebParam(partName = "HTNG_HotelStayUpdateNotifRQ", name = "HTNG_HotelStayUpdateNotifRQ", targetNamespace = "http://htng.org/2011B") HTNGHotelStayUpdateNotifRQ var1
   );
}
