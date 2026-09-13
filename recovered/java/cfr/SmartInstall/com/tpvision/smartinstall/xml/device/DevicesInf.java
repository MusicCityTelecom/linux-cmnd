/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.device;

import com.tpvision.smartinstall.xml.device.Device;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"device"})
@XmlRootElement(name="DevicesInf")
public class DevicesInf {
    @XmlElement(required=true)
    protected List<Device> device;

    public List<Device> getDevice() {
        if (null == this.device) {
            this.device = new ArrayList<Device>();
        }
        return this.device;
    }
}

