package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TemperatureSensor.class)
public abstract class TemperatureSensor_ {
   public static volatile SingularAttribute<TemperatureSensor, Integer> temperatureOne;
   public static volatile SingularAttribute<TemperatureSensor, Integer> temperatureTwo;
   public static final String TEMPERATURE_ONE = "temperatureOne";
   public static final String TEMPERATURE_TWO = "temperatureTwo";
}
