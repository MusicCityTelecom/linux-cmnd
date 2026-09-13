/*
 * Decompiled with CFR 0.152.
 */
package org.opentravel.ota._2003._05;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opentravel.ota._2003._05.DistanceUnitNameType;
import org.opentravel.ota._2003._05.RefPointsType;
import org.opentravel.ota._2003._05.TransportationsType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RelativePositionType")
@XmlSeeAlso(value={RefPointsType.RefPoint.class})
public class RelativePositionType
extends TransportationsType {
    @XmlAttribute(name="Nearest")
    protected Boolean nearest;
    @XmlAttribute(name="IndexPointCode")
    protected String indexPointCode;
    @XmlAttribute(name="Name")
    protected String name;
    @XmlAttribute(name="PrimaryIndicator")
    protected Boolean primaryIndicator;
    @XmlAttribute(name="ToFrom")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    protected String toFrom;
    @XmlAttribute(name="ApproximateDistanceInd")
    protected Boolean approximateDistanceInd;
    @XmlAttribute(name="Direction")
    protected String direction;
    @XmlAttribute(name="Distance")
    protected String distance;
    @XmlAttribute(name="DistanceUnitName")
    protected DistanceUnitNameType distanceUnitName;
    @XmlAttribute(name="UnitOfMeasureCode")
    protected String unitOfMeasureCode;

    public Boolean isNearest() {
        return this.nearest;
    }

    public void setNearest(Boolean value) {
        this.nearest = value;
    }

    public String getIndexPointCode() {
        return this.indexPointCode;
    }

    public void setIndexPointCode(String value) {
        this.indexPointCode = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Boolean isPrimaryIndicator() {
        return this.primaryIndicator;
    }

    public void setPrimaryIndicator(Boolean value) {
        this.primaryIndicator = value;
    }

    public String getToFrom() {
        return this.toFrom;
    }

    public void setToFrom(String value) {
        this.toFrom = value;
    }

    public Boolean isApproximateDistanceInd() {
        return this.approximateDistanceInd;
    }

    public void setApproximateDistanceInd(Boolean value) {
        this.approximateDistanceInd = value;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String value) {
        this.direction = value;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String value) {
        this.distance = value;
    }

    public DistanceUnitNameType getDistanceUnitName() {
        return this.distanceUnitName;
    }

    public void setDistanceUnitName(DistanceUnitNameType value) {
        this.distanceUnitName = value;
    }

    public String getUnitOfMeasureCode() {
        return this.unitOfMeasureCode;
    }

    public void setUnitOfMeasureCode(String value) {
        this.unitOfMeasureCode = value;
    }
}

