package be.tpvision.smartcontrol.protocol.todelete;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TemperatureSensor.class)
public abstract class TemperatureSensor_ {
   public static volatile SingularAttribute<TemperatureSensor, Integer> temperature;
   public static final String TEMPERATURE = "temperature";
}
