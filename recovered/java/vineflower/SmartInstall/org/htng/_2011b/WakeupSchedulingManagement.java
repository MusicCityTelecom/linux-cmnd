package org.htng._2011b;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://htng.org/2011B", name = "WakeupSchedulingManagement")
@XmlSeeAlso({org.opentravel.ota._2003._05.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle = ParameterStyle.BARE)
public interface WakeupSchedulingManagement {
   @WebMethod(operationName = "ScheduleWakeup", action = "http://htng.org/2011B/HTNG_GuestAndRoomStatusService#ScheduleWakeup")
   @WebResult(name = "HTNG_WakeupSchedulingNotifRS", targetNamespace = "http://htng.org/2011B", partName = "HTNG_WakeupSchedulingNotifRS")
   HTNGWakeupSchedulingNotifRS scheduleWakeup(
      @WebParam(partName = "HTNG_WakeupSchedulingNotifRQ", name = "HTNG_WakeupSchedulingNotifRQ", targetNamespace = "http://htng.org/2011B") HTNGWakeupSchedulingNotifRQ var1
   );
}
