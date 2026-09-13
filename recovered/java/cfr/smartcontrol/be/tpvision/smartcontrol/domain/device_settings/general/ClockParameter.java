/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;

public class ClockParameter
implements DeviceSetting {
    private int hour;
    private int minute;

    public ClockParameter(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int hashCode() {
        return Objects.hash(this.hour, this.minute);
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
        ClockParameter other = (ClockParameter)obj;
        return this.hour == other.hour && this.minute == other.minute;
    }

    public String toString() {
        return "Clock [hour=" + this.hour + ", minute=" + this.minute + "]";
    }
}

