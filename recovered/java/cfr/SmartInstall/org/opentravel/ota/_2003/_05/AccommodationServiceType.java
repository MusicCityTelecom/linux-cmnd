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
import org.opentravel.ota._2003._05.AncillaryService;
import org.opentravel.ota._2003._05.BookedTrainSegmentType;
import org.opentravel.ota._2003._05.RailAccommDetailType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccommodationServiceType", propOrder={"accommodationDetail", "ancillaryService"})
@XmlSeeAlso(value={BookedTrainSegmentType.Assignment.class})
public class AccommodationServiceType {
    @XmlElement(name="AccommodationDetail")
    protected AccommodationDetail accommodationDetail;
    @XmlElement(name="AncillaryService")
    protected List<AncillaryService> ancillaryService;

    public AccommodationDetail getAccommodationDetail() {
        return this.accommodationDetail;
    }

    public void setAccommodationDetail(AccommodationDetail value) {
        this.accommodationDetail = value;
    }

    public List<AncillaryService> getAncillaryService() {
        if (this.ancillaryService == null) {
            this.ancillaryService = new ArrayList<AncillaryService>();
        }
        return this.ancillaryService;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class AccommodationDetail
    extends RailAccommDetailType {
        @XmlAttribute(name="ReferenceTravelerRPH")
        protected String referenceTravelerRPH;
        @XmlAttribute(name="ReferenceInd")
        protected Boolean referenceInd;

        public String getReferenceTravelerRPH() {
            return this.referenceTravelerRPH;
        }

        public void setReferenceTravelerRPH(String value) {
            this.referenceTravelerRPH = value;
        }

        public Boolean isReferenceInd() {
            return this.referenceInd;
        }

        public void setReferenceInd(Boolean value) {
            this.referenceInd = value;
        }
    }
}

