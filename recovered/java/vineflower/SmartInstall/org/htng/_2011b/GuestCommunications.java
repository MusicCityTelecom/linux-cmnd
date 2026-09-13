package org.htng._2011b;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(targetNamespace = "http://htng.org/2011B", name = "GuestCommunications")
@XmlSeeAlso({org.opentravel.ota._2003._05.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle = ParameterStyle.BARE)
public interface GuestCommunications {
   @WebMethod(operationName = "RetrieveMessages", action = "http://htng.org/2011B/HTNG_KioskService#RetrieveMessages")
   @WebResult(name = "HTNG_ProfileMessageRS", targetNamespace = "http://htng.org/2011B", partName = "HTNG_ProfileMessageRS")
   HTNGProfileMessageRS retrieveMessages(
      @WebParam(partName = "HTNG_ProfileMessageRQ", name = "HTNG_ProfileMessageRQ", targetNamespace = "http://htng.org/2011B") HTNGProfileMessageRQ var1
   );

   @WebMethod(operationName = "UpdateMessageStatus", action = "http://htng.org/2011B/HTNG_KioskService#UpdateMessageStatus")
   @WebResult(name = "HTNG_ProfileMessageStatusNotifRS", targetNamespace = "http://htng.org/2011B", partName = "HTNG_ProfileMessageStatusRS")
   HTNGResponseBaseType updateMessageStatus(
      @WebParam(partName = "HTNG_ProfileMessageStatusRQ", name = "HTNG_ProfileMessageStatusNotifRQ", targetNamespace = "http://htng.org/2011B") HTNGProfileMessageStatusNotifRQ var1
   );
}
