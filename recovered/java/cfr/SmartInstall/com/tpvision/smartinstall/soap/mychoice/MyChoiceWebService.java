/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.soap.mychoice;

import com.tpvision.smartinstall.soap.mychoice.ArrayOfInteger;
import com.tpvision.smartinstall.soap.mychoice.ArrayOfString;
import com.tpvision.smartinstall.soap.mychoice.MonthlyCreditUsageList;
import java.math.BigInteger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace="http://www.my-choice.tv/soap/service", name="MyChoicePortType")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public interface MyChoiceWebService {
    @WebMethod(operationName="GetDays", action="http://www.my-choice.tv/soap/service")
    @WebResult(name="Rooms", targetNamespace="http://www.my-choice.tv/soap/service", partName="Rooms")
    public ArrayOfInteger getDays(@WebParam(partName="ApiKey", name="ApiKey") String var1);

    @WebMethod(operationName="GetRateType", action="http://www.my-choice.tv/soap/service")
    @WebResult(name="RateType", targetNamespace="http://www.my-choice.tv/soap/service", partName="RateType")
    public String getRateType(@WebParam(partName="ApiKey", name="ApiKey") String var1);

    @WebMethod(operationName="GetMonthlyCreditUsage", action="http://www.my-choice.tv/soap/service")
    @WebResult(name="MonthlyCreditUsageList", targetNamespace="http://www.my-choice.tv/soap/service", partName="MonthlyCreditUsageList")
    public MonthlyCreditUsageList getMonthlyCreditUsage(@WebParam(partName="ApiKey", name="ApiKey") String var1);

    @WebMethod(operationName="GetDurationsForRoom", action="http://www.my-choice.tv/soap/service")
    @WebResult(name="Durations", targetNamespace="http://www.my-choice.tv/soap/service", partName="Durations")
    public ArrayOfInteger getDurationsForRoom(@WebParam(partName="ApiKey", name="ApiKey") String var1, @WebParam(partName="Room", name="Room") String var2);

    @WebMethod(operationName="GetPin", action="http://www.my-choice.tv/soap/service")
    @WebResult(name="Pin", targetNamespace="http://www.my-choice.tv/soap/service", partName="Pin")
    public String getPin(@WebParam(partName="ApiKey", name="ApiKey") String var1, @WebParam(partName="Room", name="Room") String var2, @WebParam(partName="Days", name="Days") BigInteger var3, @WebParam(partName="Package", name="Package") BigInteger var4);

    @WebMethod(operationName="GetRoomsReception", action="http://www.my-choice.tv/soap/service")
    @WebResult(name="Rooms", targetNamespace="http://www.my-choice.tv/soap/service", partName="Rooms")
    public ArrayOfString getRooms(@WebParam(partName="ApiKey", name="ApiKey") String var1);

    @WebMethod(action="http://www.my-choice.tv/soap/service")
    @WebResult(name="NumPackages", targetNamespace="http://www.my-choice.tv/soap/service", partName="NumPackages")
    public BigInteger getNumPackages(@WebParam(partName="ApiKey", name="ApiKey") String var1, @WebParam(partName="Room", name="Room") String var2);
}

