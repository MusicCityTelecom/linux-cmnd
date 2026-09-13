/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class TemperatureSensor
implements DeviceSetting {
    private int temperatureOne;
    private Integer temperatureTwo;

    protected TemperatureSensor() {
    }

    public TemperatureSensor(int temperatureOne) {
        this(temperatureOne, null);
    }

    public TemperatureSensor(int temperatureOne, Integer temperatureTwo) {
        this.temperatureOne = temperatureOne;
        this.temperatureTwo = temperatureTwo;
    }

    public int getTemperatureOne() {
        return this.temperatureOne;
    }

    public void setTemperatureOne(int temperatureOne) {
        this.temperatureOne = temperatureOne;
    }

    public Integer getTemperatureTwo() {
        return this.temperatureTwo;
    }

    public void setTemperatureTwo(Integer temperatureTwo) {
        this.temperatureTwo = temperatureTwo;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TemperatureSensor)) {
            return false;
        }
        TemperatureSensor that = (TemperatureSensor)object;
        return new EqualsBuilder().append(this.getTemperatureOne(), that.getTemperatureOne()).append(this.getTemperatureTwo(), that.getTemperatureTwo()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getTemperatureOne(), this.getTemperatureTwo());
    }

    public String toString() {
        return new ToStringBuilder(this).append("temperatureOne", this.getTemperatureOne()).append("temperatureTwo", this.getTemperatureTwo()).toString();
    }
}

