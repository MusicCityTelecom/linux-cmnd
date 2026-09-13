package be.tpvision.smartcontrol.repository.converters;

import be.tpvision.smartcontrol.messages.repositories.converters.enum_converter.ConstructorMessages;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public abstract class EnumConverter<T extends Enum<T>> implements AttributeConverter<T, String> {
   private final Class<T> enumClass;

   public EnumConverter(final Class<T> enumClass) {
      Assert.notNull(enumClass, ConstructorMessages.ENUM_CLASS_CAN_NOT_BE_NULL);
      this.enumClass = enumClass;
   }

   public String convertToDatabaseColumn(final T enumValue) {
      return enumValue == null ? null : String.valueOf(enumValue);
   }

   public T convertToEntityAttribute(final String enumValue) {
      try {
         return Enum.valueOf(this.enumClass, enumValue);
      } catch (IllegalArgumentException | NullPointerException e) {
         return null;
      }
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof EnumConverter)) {
         return false;
      }

      EnumConverter<?> that = (EnumConverter<?>)object;
      return Objects.equals(this.enumClass, that.enumClass);
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.enumClass);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("enumClass", this.enumClass).toString();
   }
}
