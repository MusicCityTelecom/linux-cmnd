/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import org.htng._2011b.FolioManagement;

@WebServiceClient(name="HTNG_KioskService", wsdlLocation="classpath:HTNG/services/HTNG_KioskService.wsdl", targetNamespace="http://htng.org/2011B")
public class HTNGKioskService
extends Service {
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName FolioManagement;

    public HTNGKioskService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HTNGKioskService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HTNGKioskService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public HTNGKioskService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public HTNGKioskService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public HTNGKioskService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name="FolioManagement")
    public FolioManagement getFolioManagement() {
        return super.getPort(FolioManagement, FolioManagement.class);
    }

    @WebEndpoint(name="FolioManagement")
    public FolioManagement getFolioManagement(WebServiceFeature ... features) {
        return super.getPort(FolioManagement, FolioManagement.class, features);
    }

    static {
        SERVICE = new QName("http://htng.org/2011B", "HTNG_KioskService");
        FolioManagement = new QName("http://htng.org/2011B", "FolioManagement");
        URL url = HTNGKioskService.class.getClassLoader().getResource("HTNG/services/HTNG_KioskService.wsdl");
        if (url == null) {
            Logger.getLogger(HTNGKioskService.class.getName()).log(Level.INFO, "Can not initialize the default wsdl from {0}", "classpath:HTNG/services/HTNG_KioskService.wsdl");
        }
        WSDL_LOCATION = url;
    }
}

