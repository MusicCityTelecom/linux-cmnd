/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import org.opentravel.ota._2003._05.AllianceConsortiumType;
import org.opentravel.ota._2003._05.CompanyNamePrefType;
import org.opentravel.ota._2003._05.ContactInfoType;
import org.opentravel.ota._2003._05.DirectBillType;
import org.opentravel.ota._2003._05.FlightSegmentType;
import org.opentravel.ota._2003._05.OperatingAirlineType;
import org.opentravel.ota._2003._05.TravelArrangerType;
import org.opentravel.ota._2003._05.VehicleAvailCoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CompanyNameType", propOrder={"value"})
@XmlSeeAlso(value={OperatingAirlineType.class, FlightSegmentType.MarketingAirline.class, VehicleAvailCoreType.Vendor.class, CompanyNamePrefType.class, AllianceConsortiumType.AllianceMember.class, TravelArrangerType.class, DirectBillType.CompanyName.class, ContactInfoType.CompanyName.class})
public class CompanyNameType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="Division")
    protected String division;
    @XmlAttribute(name="Department")
    protected String department;
    @XmlAttribute(name="CompanyShortName")
    protected String companyShortName;
    @XmlAttribute(name="TravelSector")
    protected String travelSector;
    @XmlAttribute(name="Code")
    protected String code;
    @XmlAttribute(name="CodeContext")
    protected String codeContext;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDivision() {
        return this.division;
    }

    public void setDivision(String value) {
        this.division = value;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String value) {
        this.department = value;
    }

    public String getCompanyShortName() {
        return this.companyShortName;
    }

    public void setCompanyShortName(String value) {
        this.companyShortName = value;
    }

    public String getTravelSector() {
        return this.travelSector;
    }

    public void setTravelSector(String value) {
        this.travelSector = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getCodeContext() {
        return this.codeContext;
    }

    public void setCodeContext(String value) {
        this.codeContext = value;
    }
}

