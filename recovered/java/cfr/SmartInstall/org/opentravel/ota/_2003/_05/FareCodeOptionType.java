/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.opentravel.ota._2003._05.FreeTextType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FareCodeOptionType", propOrder={"fareRemark"})
public class FareCodeOptionType {
    @XmlElement(name="FareRemark")
    protected FareRemark fareRemark;
    @XmlAttribute(name="ListOfFareQualifierCode")
    protected List<String> listOfFareQualifierCode;
    @XmlAttribute(name="Status")
    protected String status;
    @XmlAttribute(name="FareDescription")
    protected String fareDescription;
    @XmlAttribute(name="FareCode")
    protected String fareCode;
    @XmlAttribute(name="GroupCode")
    protected String groupCode;

    public FareRemark getFareRemark() {
        return this.fareRemark;
    }

    public void setFareRemark(FareRemark value) {
        this.fareRemark = value;
    }

    public List<String> getListOfFareQualifierCode() {
        if (this.listOfFareQualifierCode == null) {
            this.listOfFareQualifierCode = new ArrayList<String>();
        }
        return this.listOfFareQualifierCode;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getFareDescription() {
        return this.fareDescription;
    }

    public void setFareDescription(String value) {
        this.fareDescription = value;
    }

    public String getFareCode() {
        return this.fareCode;
    }

    public void setFareCode(String value) {
        this.fareCode = value;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupCode(String value) {
        this.groupCode = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class FareRemark
    extends FreeTextType {
    }
}

