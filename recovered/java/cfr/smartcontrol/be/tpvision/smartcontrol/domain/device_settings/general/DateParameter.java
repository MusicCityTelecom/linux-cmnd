/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;

public class DateParameter
implements DeviceSetting {
    private int day;
    private int month;
    private int year;

    public DateParameter(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int hashCode() {
        return Objects.hash(this.day, this.month, this.year);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DateParameter other = (DateParameter)obj;
        return this.day == other.day && this.month == other.month && this.year == other.year;
    }

    public String toString() {
        return "DateParameter [day=" + this.day + ", month=" + this.month + ", year=" + this.year + "]";
    }
}

