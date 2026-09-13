/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.TemperatureSensor;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.temperature_sensor.ToTemperatureSensorViewModelMessages;
import org.springframework.util.Assert;

public class TemperatureSensorMapper {
    private TemperatureSensorMapper() {
    }

    public static int toInteger(TemperatureSensor temperatureSensor) {
        Assert.notNull((Object)temperatureSensor, ToTemperatureSensorViewModelMessages.TEMPERATURE_SENSOR_CAN_NOT_BE_NULL);
        int temperatureOne = temperatureSensor.getTemperatureOne();
        Integer temperatureTwo = temperatureSensor.getTemperatureTwo();
        if (temperatureTwo == null) {
            return temperatureOne;
        }
        return Math.max(temperatureOne, temperatureTwo);
    }
}

