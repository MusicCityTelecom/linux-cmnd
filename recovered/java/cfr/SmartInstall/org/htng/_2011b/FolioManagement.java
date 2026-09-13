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
import org.htng._2011b.HTNGHotelFolioRQ;
import org.htng._2011b.HTNGHotelFolioRS;
import org.htng._2011b.ObjectFactory;

@WebService(targetNamespace="http://htng.org/2011B", name="FolioManagement")
@XmlSeeAlso(value={org.opentravel.ota._2003._05.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface FolioManagement {
    @WebMethod(operationName="RetrieveFolio", action="http://htng.org/2011B/HTNG_KioskService#RetrieveFolio")
    @WebResult(name="HTNG_HotelFolioRS", targetNamespace="http://htng.org/2011B", partName="HTNG_HotelFolioRS")
    public HTNGHotelFolioRS retrieveFolio(@WebParam(partName="HTNG_HotelFolioRQ", name="HTNG_HotelFolioRQ", targetNamespace="http://htng.org/2011B") HTNGHotelFolioRQ var1);
}

