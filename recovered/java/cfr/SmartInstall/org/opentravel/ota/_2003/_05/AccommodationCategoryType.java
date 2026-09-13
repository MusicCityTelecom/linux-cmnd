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
import org.opentravel.ota._2003._05.AccommodationType;
import org.opentravel.ota._2003._05.AncillaryService;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccommodationCategoryType", propOrder={"accommodation", "ancillaryService"})
public class AccommodationCategoryType {
    @XmlElement(name="Accommodation")
    protected List<Accommodation> accommodation;
    @XmlElement(name="AncillaryService")
    protected List<AncillaryService> ancillaryService;

    public List<Accommodation> getAccommodation() {
        if (this.accommodation == null) {
            this.accommodation = new ArrayList<Accommodation>();
        }
        return this.accommodation;
    }

    public List<AncillaryService> getAncillaryService() {
        if (this.ancillaryService == null) {
            this.ancillaryService = new ArrayList<AncillaryService>();
        }
        return this.ancillaryService;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="")
    public static class Accommodation
    extends AccommodationType {
        @XmlAttribute(name="Quantity")
        @XmlSchemaType(name="positiveInteger")
        protected BigInteger quantity;

        public BigInteger getQuantity() {
            return this.quantity;
        }

        public void setQuantity(BigInteger value) {
            this.quantity = value;
        }
    }
}

