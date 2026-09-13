package be.tpvision.smartcontrol.repository.converters.input_source;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.repository.converters.EnumConverter;
import javax.persistence.Converter;

@Converter
public class SourceTypeConverter extends EnumConverter<InputSource.SourceType> {
   public SourceTypeConverter() {
      super(InputSource.SourceType.class);
   }
}
