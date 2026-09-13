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
import org.opentravel.ota._2003._05.AirTravelerType;
import org.opentravel.ota._2003._05.FormattedTextTextType;
import org.opentravel.ota._2003._05.SpecialReqDetailsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TravelerInfoType", propOrder={"airTraveler", "specialReqDetails"})
public class TravelerInfoType {
    @XmlElement(name="AirTraveler")
    protected List<AirTraveler> airTraveler;
    @XmlElement(name="SpecialReqDetails")
    protected List<SpecialReqDetailsType> specialReqDetails;

    public List<AirTraveler> getAirTraveler() {
        if (this.airTraveler == null) {
            this.airTraveler = new ArrayList<AirTraveler>();
        }
        return this.airTraveler;
    }

    public List<SpecialReqDetailsType> getSpecialReqDetails() {
        if (this.specialReqDetails == null) {
            this.specialReqDetails = new ArrayList<SpecialReqDetailsType>();
        }
        return this.specialReqDetails;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"comment"})
    public static class AirTraveler
    extends AirTravelerType {
        @XmlElement(name="Comment")
        protected List<Comment> comment;

        public List<Comment> getComment() {
            if (this.comment == null) {
                this.comment = new ArrayList<Comment>();
            }
            return this.comment;
        }

        @XmlAccessorType(value=XmlAccessType.FIELD)
        @XmlType(name="")
        public static class Comment
        extends FormattedTextTextType {
            @XmlAttribute(name="Name")
            protected String name;

            public String getName() {
                return this.name;
            }

            public void setName(String value) {
                this.name = value;
            }
        }
    }
}

