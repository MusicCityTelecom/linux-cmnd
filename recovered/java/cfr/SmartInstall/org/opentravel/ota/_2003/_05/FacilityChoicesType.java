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
import org.opentravel.ota._2003._05.MealPlanType;
import org.opentravel.ota._2003._05.PkgRoomInventoryType;
import org.opentravel.ota._2003._05.RoomPriceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FacilityChoicesType", propOrder={"availableRooms", "availableMealPlans", "roomPrices"})
public class FacilityChoicesType {
    @XmlElement(name="AvailableRooms")
    protected AvailableRooms availableRooms;
    @XmlElement(name="AvailableMealPlans")
    protected AvailableMealPlans availableMealPlans;
    @XmlElement(name="RoomPrices")
    protected List<RoomPrices> roomPrices;
    @XmlAttribute(name="MinOccupancy")
    protected Integer minOccupancy;
    @XmlAttribute(name="MaxOccupancy")
    protected Integer maxOccupancy;

    public AvailableRooms getAvailableRooms() {
        return this.availableRooms;
    }

    public void setAvailableRooms(AvailableRooms value) {
        this.availableRooms = value;
    }

    public AvailableMealPlans getAvailableMealPlans() {
        return this.availableMealPlans;
    }

    public void setAvailableMealPlans(AvailableMealPlans value) {
        this.availableMealPlans = value;
    }

    public List<RoomPrices> getRoomPrices() {
        if (this.roomPrices == null) {
            this.roomPrices = new ArrayList<RoomPrices>();
        }
        return this.roomPrices;
    }

    public Integer getMinOccupancy() {
        return this.minOccupancy;
    }

    public void setMinOccupancy(Integer value) {
        this.minOccupancy = value;
    }

    public Integer getMaxOccupancy() {
        return this.maxOccupancy;
    }

    public void setMaxOccupancy(Integer value) {
        this.maxOccupancy = value;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"roomPrice"})
    public static class RoomPrices {
        @XmlElement(name="RoomPrice", required=true)
        protected List<RoomPriceType> roomPrice;
        @XmlAttribute(name="MealPlan")
        protected String mealPlan;

        public List<RoomPriceType> getRoomPrice() {
            if (this.roomPrice == null) {
                this.roomPrice = new ArrayList<RoomPriceType>();
            }
            return this.roomPrice;
        }

        public String getMealPlan() {
            return this.mealPlan;
        }

        public void setMealPlan(String value) {
            this.mealPlan = value;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"room"})
    public static class AvailableRooms {
        @XmlElement(name="Room", required=true)
        protected List<PkgRoomInventoryType> room;

        public List<PkgRoomInventoryType> getRoom() {
            if (this.room == null) {
                this.room = new ArrayList<PkgRoomInventoryType>();
            }
            return this.room;
        }
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"mealPlan"})
    public static class AvailableMealPlans {
        @XmlElement(name="MealPlan", required=true)
        protected List<MealPlanType> mealPlan;

        public List<MealPlanType> getMealPlan() {
            if (this.mealPlan == null) {
                this.mealPlan = new ArrayList<MealPlanType>();
            }
            return this.mealPlan;
        }
    }
}

