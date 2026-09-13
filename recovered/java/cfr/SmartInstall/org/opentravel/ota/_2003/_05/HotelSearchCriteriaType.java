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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.AvailRequestSegmentsType;
import org.opentravel.ota._2003._05.HotelSearchCriterionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="HotelSearchCriteriaType", propOrder={"criterion"})
@XmlSeeAlso(value={AvailRequestSegmentsType.AvailRequestSegment.HotelSearchCriteria.class})
public class HotelSearchCriteriaType {
    @XmlElement(name="Criterion", required=true)
    protected List<Criterion> criterion;
    @XmlAttribute(name="AvailableOnlyIndicator")
    protected Boolean availableOnlyIndicator;
    @XmlAttribute(name="BestOnlyIndicator")
    protected Boolean bestOnlyIndicator;

    public List<Criterion> getCriterion() {
        if (this.criterion == null) {
            this.criterion = new ArrayList<Criterion>();
        }
        return this.criterion;
    }

    public Boolean isAvailableOnlyIndicator() {
        return this.availableOnlyIndicator;
    }

    public void setAvailableOnlyIndicator(Boolean value) {
        this.availableOnlyIndicator = value;
    }

    public Boolean isBestOnlyIndicator() {
        return this.bestOnlyIndicator;
    }

    public void setBestOnlyIndicator(Boolean value) {
        this.bestOnlyIndicator = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Criterion
    extends HotelSearchCriterionType {
        @XmlAttribute(name="MoreDataEchoToken")
        protected String moreDataEchoToken;
        @XmlAttribute(name="InfoSource")
        protected String infoSource;
        @XmlAttribute(name="AlternateAvailability")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String alternateAvailability;
        @XmlAttribute(name="AddressSearchScope")
        @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
        protected String addressSearchScope;

        public String getMoreDataEchoToken() {
            return this.moreDataEchoToken;
        }

        public void setMoreDataEchoToken(String value) {
            this.moreDataEchoToken = value;
        }

        public String getInfoSource() {
            return this.infoSource;
        }

        public void setInfoSource(String value) {
            this.infoSource = value;
        }

        public String getAlternateAvailability() {
            return this.alternateAvailability;
        }

        public void setAlternateAvailability(String value) {
            this.alternateAvailability = value;
        }

        public String getAddressSearchScope() {
            return this.addressSearchScope;
        }

        public void setAddressSearchScope(String value) {
            this.addressSearchScope = value;
        }
    }
}

