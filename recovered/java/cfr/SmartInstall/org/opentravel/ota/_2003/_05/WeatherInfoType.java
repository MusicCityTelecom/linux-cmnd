/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="WeatherInfoType", propOrder={"precipitation", "temperature"})
public class WeatherInfoType {
    @XmlElement(name="Precipitation")
    protected List<Precipitation> precipitation;
    @XmlElement(name="Temperature")
    protected List<Temperature> temperature;
    @XmlAttribute(name="Period")
    protected String period;

    public List<Precipitation> getPrecipitation() {
        if (this.precipitation == null) {
            this.precipitation = new ArrayList<Precipitation>();
        }
        return this.precipitation;
    }

    public List<Temperature> getTemperature() {
        if (this.temperature == null) {
            this.temperature = new ArrayList<Temperature>();
        }
        return this.temperature;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String value) {
        this.period = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Temperature {
        @XmlAttribute(name="AverageHighTemp")
        protected BigInteger averageHighTemp;
        @XmlAttribute(name="AverageLowTemp")
        protected BigInteger averageLowTemp;
        @XmlAttribute(name="TempUnit")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String tempUnit;

        public BigInteger getAverageHighTemp() {
            return this.averageHighTemp;
        }

        public void setAverageHighTemp(BigInteger value) {
            this.averageHighTemp = value;
        }

        public BigInteger getAverageLowTemp() {
            return this.averageLowTemp;
        }

        public void setAverageLowTemp(BigInteger value) {
            this.averageLowTemp = value;
        }

        public String getTempUnit() {
            return this.tempUnit;
        }

        public void setTempUnit(String value) {
            this.tempUnit = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Precipitation {
        @XmlAttribute(name="AveragePrecipitation")
        protected BigDecimal averagePrecipitation;
        @XmlAttribute(name="UnitOfMeasure")
        protected String unitOfMeasure;

        public BigDecimal getAveragePrecipitation() {
            return this.averagePrecipitation;
        }

        public void setAveragePrecipitation(BigDecimal value) {
            this.averagePrecipitation = value;
        }

        public String getUnitOfMeasure() {
            return this.unitOfMeasure;
        }

        public void setUnitOfMeasure(String value) {
            this.unitOfMeasure = value;
        }
    }
}

