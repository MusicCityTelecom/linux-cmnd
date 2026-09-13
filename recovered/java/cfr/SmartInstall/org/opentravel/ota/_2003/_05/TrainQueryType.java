/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.NetworkCodeType;
import org.opentravel.ota._2003._05.TrainIdentificationType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TrainQueryType", propOrder={"train", "networkCode"})
public class TrainQueryType {
    @XmlElement(name="Train")
    protected TrainIdentificationType train;
    @XmlElement(name="NetworkCode")
    protected NetworkCodeType networkCode;

    public TrainIdentificationType getTrain() {
        return this.train;
    }

    public void setTrain(TrainIdentificationType value) {
        this.train = value;
    }

    public NetworkCodeType getNetworkCode() {
        return this.networkCode;
    }

    public void setNetworkCode(NetworkCodeType value) {
        this.networkCode = value;
    }
}

