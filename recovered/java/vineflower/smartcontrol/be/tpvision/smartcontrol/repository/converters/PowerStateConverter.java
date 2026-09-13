package be.tpvision.smartcontrol.repository.converters;

import be.tpvision.smartcontrol.domain.device_settings.general.PowerState;
import javax.persistence.Converter;

@Converter
public class PowerStateConverter extends EnumConverter<PowerState> {
   public PowerStateConverter() {
      super(PowerState.class);
   }
}
