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
import org.opentravel.ota._2003._05.VendorMessageType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VendorMessagesType", propOrder={"vendorMessage"})
public class VendorMessagesType {
    @XmlElement(name="VendorMessage", required=true)
    protected List<VendorMessageType> vendorMessage;

    public List<VendorMessageType> getVendorMessage() {
        if (this.vendorMessage == null) {
            this.vendorMessage = new ArrayList<VendorMessageType>();
        }
        return this.vendorMessage;
    }
}

