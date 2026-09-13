/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.ServiceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ServicesType", propOrder={"service"})
public class ServicesType {
    @XmlElement(name="Service", required=true)
    protected List<ServiceType> service;

    public List<ServiceType> getService() {
        if (this.service == null) {
            this.service = new ArrayList<ServiceType>();
        }
        return this.service;
    }
}

