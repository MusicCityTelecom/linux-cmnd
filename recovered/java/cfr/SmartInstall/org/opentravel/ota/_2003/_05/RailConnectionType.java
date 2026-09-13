/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.LocationType;
import org.opentravel.ota._2003._05.PreferLevelType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RailConnectionType", propOrder={"connectionLocation"})
public class RailConnectionType {
    @XmlElement(name="ConnectionLocation", required=true)
    protected List<ConnectionLocation> connectionLocation;

    public List<ConnectionLocation> getConnectionLocation() {
        if (this.connectionLocation == null) {
            this.connectionLocation = new ArrayList<ConnectionLocation>();
        }
        return this.connectionLocation;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class ConnectionLocation
    extends LocationType {
        @XmlAttribute(name="MultiCityStationInd")
        protected Boolean multiCityStationInd;
        @XmlAttribute(name="MinChangeTime")
        @XmlSchemaType(name="nonNegativeInteger")
        protected BigInteger minChangeTime;
        @XmlAttribute(name="ConnectionInfo")
        protected String connectionInfo;
        @XmlAttribute(name="ConnectType")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String connectType;
        @XmlAttribute(name="PreferLevel")
        protected PreferLevelType preferLevel;

        public Boolean isMultiCityStationInd() {
            return this.multiCityStationInd;
        }

        public void setMultiCityStationInd(Boolean value) {
            this.multiCityStationInd = value;
        }

        public BigInteger getMinChangeTime() {
            return this.minChangeTime;
        }

        public void setMinChangeTime(BigInteger value) {
            this.minChangeTime = value;
        }

        public String getConnectionInfo() {
            return this.connectionInfo;
        }

        public void setConnectionInfo(String value) {
            this.connectionInfo = value;
        }

        public String getConnectType() {
            return this.connectType;
        }

        public void setConnectType(String value) {
            this.connectType = value;
        }

        public PreferLevelType getPreferLevel() {
            return this.preferLevel;
        }

        public void setPreferLevel(PreferLevelType value) {
            this.preferLevel = value;
        }
    }
}

