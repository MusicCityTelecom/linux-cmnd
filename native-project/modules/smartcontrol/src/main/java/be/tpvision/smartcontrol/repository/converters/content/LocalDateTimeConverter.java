package be.tpvision.smartcontrol.repository.converters.content;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter
@Component
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
   public Timestamp convertToDatabaseColumn(final LocalDateTime localDateTime) {
      return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
   }

   public LocalDateTime convertToEntityAttribute(final Timestamp timestamp) {
      return timestamp == null ? null : timestamp.toLocalDateTime();
   }
}
