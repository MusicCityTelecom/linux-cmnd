package be.tpvision.smartcontrol.repository.converters.tiling;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.repository.converters.EnumConverter;
import javax.persistence.Converter;

@Converter
public class FrameCompConverter extends EnumConverter<Tiling.FrameComp> {
   public FrameCompConverter() {
      super(Tiling.FrameComp.class);
   }
}
