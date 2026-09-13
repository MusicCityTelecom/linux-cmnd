/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.htng._2011b.HTNGHotelRoomStatusSearchRQ;
import org.htng._2011b.HTNGHotelRoomStatusSearchRS;
import org.htng._2011b.HTNGHotelRoomStatusUpdateNotifRQ;
import org.htng._2011b.HTNGResponseBaseType;
import org.htng._2011b.ObjectFactory;

@WebService(targetNamespace="http://htng.org/2011B", name="RoomStatusManagement")
@XmlSeeAlso(value={org.opentravel.ota._2003._05.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface RoomStatusManagement {
    @WebMethod(operationName="SearchRooms", action="http://htng.org/2011B/HTNG_GuestAndRoomStatusService#SearchRooms")
    @WebResult(name="HTNG_HotelRoomStatusSearchRS", targetNamespace="http://htng.org/2011B", partName="HTNG_HotelRoomStatusSearchRS")
    public HTNGHotelRoomStatusSearchRS searchRooms(@WebParam(partName="HTNG_HotelRoomStatusSearchRQ", name="HTNG_HotelRoomStatusSearchRQ", targetNamespace="http://htng.org/2011B") HTNGHotelRoomStatusSearchRQ var1);

    @WebMethod(operationName="UpdateRoomStatus", action="http://htng.org/2011B/HTNG_GuestAndRoomStatusService#UpdateRoomStatus")
    @WebResult(name="HTNG_HotelRoomStatusUpdateNotifRS", targetNamespace="http://htng.org/2011B", partName="HTNG_HotelRoomStatusUpdateNotifRS")
    public HTNGResponseBaseType updateRoomStatus(@WebParam(partName="HTNG_HotelRoomStatusUpdateNotifRQ", name="HTNG_HotelRoomStatusUpdateNotifRQ", targetNamespace="http://htng.org/2011B") HTNGHotelRoomStatusUpdateNotifRQ var1);
}

