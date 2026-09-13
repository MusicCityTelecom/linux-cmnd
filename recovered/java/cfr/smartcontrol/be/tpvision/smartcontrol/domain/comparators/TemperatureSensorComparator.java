/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import java.util.Comparator;

public class TemperatureSensorComparator
implements Comparator<TemperatureSensor> {
    @Override
    public int compare(TemperatureSensor temperatureSensor1, TemperatureSensor temperatureSensor2) {
        int temperatureOne = temperatureSensor1.getTemperatureOne();
        Integer temperatureTwo = temperatureSensor1.getTemperatureTwo();
        int firstTemperature = temperatureTwo != null ? Math.max(temperatureOne, temperatureTwo) : temperatureOne;
        temperatureOne = temperatureSensor2.getTemperatureOne();
        temperatureTwo = temperatureSensor2.getTemperatureTwo();
        int secondTemperature = temperatureTwo != null ? Math.max(temperatureOne, temperatureTwo) : temperatureOne;
        return Integer.compare(firstTemperature, secondTemperature);
    }
}

