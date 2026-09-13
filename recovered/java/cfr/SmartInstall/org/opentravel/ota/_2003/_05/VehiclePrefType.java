/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.opentravel.ota._2003._05.PreferLevelType;
import org.opentravel.ota._2003._05.VehicleAvailRQCoreType;
import org.opentravel.ota._2003._05.VehicleCoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="VehiclePrefType", propOrder={"vehMakeModel"})
@XmlSeeAlso(value={VehicleAvailRQCoreType.VehPrefs.VehPref.class})
public class VehiclePrefType
extends VehicleCoreType {
    @XmlElement(name="VehMakeModel")
    protected VehMakeModel vehMakeModel;
    @XmlAttribute(name="TypePref")
    protected PreferLevelType typePref;
    @XmlAttribute(name="ClassPref")
    protected PreferLevelType classPref;
    @XmlAttribute(name="AirConditionPref")
    protected PreferLevelType airConditionPref;
    @XmlAttribute(name="TransmissionPref")
    protected PreferLevelType transmissionPref;
    @XmlAttribute(name="VendorCarType")
    protected String vendorCarType;
    @XmlAttribute(name="VehicleQty")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger vehicleQty;
    @XmlAttribute(name="Code")
    protected String code;
    @XmlAttribute(name="CodeContext")
    protected String codeContext;

    public VehMakeModel getVehMakeModel() {
        return this.vehMakeModel;
    }

    public void setVehMakeModel(VehMakeModel value) {
        this.vehMakeModel = value;
    }

    public PreferLevelType getTypePref() {
        return this.typePref;
    }

    public void setTypePref(PreferLevelType value) {
        this.typePref = value;
    }

    public PreferLevelType getClassPref() {
        return this.classPref;
    }

    public void setClassPref(PreferLevelType value) {
        this.classPref = value;
    }

    public PreferLevelType getAirConditionPref() {
        return this.airConditionPref;
    }

    public void setAirConditionPref(PreferLevelType value) {
        this.airConditionPref = value;
    }

    public PreferLevelType getTransmissionPref() {
        return this.transmissionPref;
    }

    public void setTransmissionPref(PreferLevelType value) {
        this.transmissionPref = value;
    }

    public String getVendorCarType() {
        return this.vendorCarType;
    }

    public void setVendorCarType(String value) {
        this.vendorCarType = value;
    }

    public BigInteger getVehicleQty() {
        return this.vehicleQty;
    }

    public void setVehicleQty(BigInteger value) {
        this.vehicleQty = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class VehMakeModel {
        @XmlAttribute(name="ModelYear")
        @XmlSchemaType(name="gYear")
        protected XMLGregorianCalendar modelYear;
        @XmlAttribute(name="Name", required=true)
        protected String name;
        @XmlAttribute(name="Code")
        protected String code;

        public XMLGregorianCalendar getModelYear() {
            return this.modelYear;
        }

        public void setModelYear(XMLGregorianCalendar value) {
            this.modelYear = value;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String value) {
            this.name = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }
    }
}

