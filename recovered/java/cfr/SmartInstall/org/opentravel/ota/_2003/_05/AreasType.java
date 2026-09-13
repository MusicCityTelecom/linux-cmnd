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
import org.opentravel.ota._2003._05.ParagraphType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AreasType", propOrder={"area"})
public class AreasType {
    @XmlElement(name="Area", required=true)
    protected List<Area> area;

    public List<Area> getArea() {
        if (this.area == null) {
            this.area = new ArrayList<Area>();
        }
        return this.area;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"areaDescription"})
    public static class Area {
        @XmlElement(name="AreaDescription")
        protected ParagraphType areaDescription;
        @XmlAttribute(name="CityCode")
        protected String cityCode;
        @XmlAttribute(name="StateProvCode")
        protected String stateProvCode;
        @XmlAttribute(name="CountryCode")
        protected String countryCode;
        @XmlAttribute(name="AreaID")
        protected String areaID;

        public ParagraphType getAreaDescription() {
            return this.areaDescription;
        }

        public void setAreaDescription(ParagraphType value) {
            this.areaDescription = value;
        }

        public String getCityCode() {
            return this.cityCode;
        }

        public void setCityCode(String value) {
            this.cityCode = value;
        }

        public String getStateProvCode() {
            return this.stateProvCode;
        }

        public void setStateProvCode(String value) {
            this.stateProvCode = value;
        }

        public String getCountryCode() {
            return this.countryCode;
        }

        public void setCountryCode(String value) {
            this.countryCode = value;
        }

        public String getAreaID() {
            return this.areaID;
        }

        public void setAreaID(String value) {
            this.areaID = value;
        }
    }
}

