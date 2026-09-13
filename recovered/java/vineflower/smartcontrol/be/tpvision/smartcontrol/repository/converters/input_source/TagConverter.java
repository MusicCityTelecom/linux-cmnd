package be.tpvision.smartcontrol.repository.converters.input_source;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.repository.converters.EnumConverter;
import javax.persistence.Converter;

@Converter
public class TagConverter extends EnumConverter<InputSource.Tag> {
   public TagConverter() {
      super(InputSource.Tag.class);
   }
}
