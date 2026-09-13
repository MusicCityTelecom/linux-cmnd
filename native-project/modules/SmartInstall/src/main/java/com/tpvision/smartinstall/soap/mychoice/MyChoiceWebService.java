package com.tpvision.smartinstall.soap.mychoice;

import java.math.BigInteger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(targetNamespace = "http://www.my-choice.tv/soap/service", name = "MyChoicePortType")
@SOAPBinding(style = Style.RPC)
public interface MyChoiceWebService {
   @WebMethod(operationName = "GetDays", action = "http://www.my-choice.tv/soap/service")
   @WebResult(name = "Rooms", targetNamespace = "http://www.my-choice.tv/soap/service", partName = "Rooms")
   ArrayOfInteger getDays(@WebParam(partName = "ApiKey", name = "ApiKey") String var1);

   @WebMethod(operationName = "GetRateType", action = "http://www.my-choice.tv/soap/service")
   @WebResult(name = "RateType", targetNamespace = "http://www.my-choice.tv/soap/service", partName = "RateType")
   String getRateType(@WebParam(partName = "ApiKey", name = "ApiKey") String var1);

   @WebMethod(operationName = "GetMonthlyCreditUsage", action = "http://www.my-choice.tv/soap/service")
   @WebResult(name = "MonthlyCreditUsageList", targetNamespace = "http://www.my-choice.tv/soap/service", partName = "MonthlyCreditUsageList")
   MonthlyCreditUsageList getMonthlyCreditUsage(@WebParam(partName = "ApiKey", name = "ApiKey") String var1);

   @WebMethod(operationName = "GetDurationsForRoom", action = "http://www.my-choice.tv/soap/service")
   @WebResult(name = "Durations", targetNamespace = "http://www.my-choice.tv/soap/service", partName = "Durations")
   ArrayOfInteger getDurationsForRoom(@WebParam(partName = "ApiKey", name = "ApiKey") String var1, @WebParam(partName = "Room", name = "Room") String var2);

   @WebMethod(operationName = "GetPin", action = "http://www.my-choice.tv/soap/service")
   @WebResult(name = "Pin", targetNamespace = "http://www.my-choice.tv/soap/service", partName = "Pin")
   String getPin(
      @WebParam(partName = "ApiKey", name = "ApiKey") String var1,
      @WebParam(partName = "Room", name = "Room") String var2,
      @WebParam(partName = "Days", name = "Days") BigInteger var3,
      @WebParam(partName = "Package", name = "Package") BigInteger var4
   );

   @WebMethod(operationName = "GetRoomsReception", action = "http://www.my-choice.tv/soap/service")
   @WebResult(name = "Rooms", targetNamespace = "http://www.my-choice.tv/soap/service", partName = "Rooms")
   ArrayOfString getRooms(@WebParam(partName = "ApiKey", name = "ApiKey") String var1);

   @WebMethod(action = "http://www.my-choice.tv/soap/service")
   @WebResult(name = "NumPackages", targetNamespace = "http://www.my-choice.tv/soap/service", partName = "NumPackages")
   BigInteger getNumPackages(@WebParam(partName = "ApiKey", name = "ApiKey") String var1, @WebParam(partName = "Room", name = "Room") String var2);
}
