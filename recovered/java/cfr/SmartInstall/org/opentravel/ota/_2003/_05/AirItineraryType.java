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
import org.opentravel.ota._2003._05.AirTripType;
import org.opentravel.ota._2003._05.OriginDestinationOptionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AirItineraryType", propOrder={"originDestinationOptions"})
public class AirItineraryType {
    @XmlElement(name="OriginDestinationOptions", required=true)
    protected OriginDestinationOptions originDestinationOptions;
    @XmlAttribute(name="DirectionInd")
    protected AirTripType directionInd;

    public OriginDestinationOptions getOriginDestinationOptions() {
        return this.originDestinationOptions;
    }

    public void setOriginDestinationOptions(OriginDestinationOptions value) {
        this.originDestinationOptions = value;
    }

    public AirTripType getDirectionInd() {
        return this.directionInd;
    }

    public void setDirectionInd(AirTripType value) {
        this.directionInd = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"originDestinationOption"})
    public static class OriginDestinationOptions {
        @XmlElement(name="OriginDestinationOption", required=true)
        protected List<OriginDestinationOption> originDestinationOption;

        public List<OriginDestinationOption> getOriginDestinationOption() {
            if (this.originDestinationOption == null) {
                this.originDestinationOption = new ArrayList<OriginDestinationOption>();
            }
            return this.originDestinationOption;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class OriginDestinationOption
        extends OriginDestinationOptionType {
            @XmlAttribute(name="RefNumber")
            protected Integer refNumber;

            public Integer getRefNumber() {
                return this.refNumber;
            }

            public void setRefNumber(Integer value) {
                this.refNumber = value;
            }
        }
    }
}

