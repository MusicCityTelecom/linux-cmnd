package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.messages.view_models.device_limits.enum_limits.SetEnumClassMessages;
import be.tpvision.smartcontrol.messages.view_models.device_limits.enum_limits.SetValuesMessages;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class EnumLimitsViewModel<T extends Enum> {
   private Class<T> enumClass;
   private List<String> values;

   protected EnumLimitsViewModel() {
   }

   public EnumLimitsViewModel(final Class<T> enumClass) {
      this(enumClass, ValueUtilities.enumToStringList(enumClass));
   }

   public EnumLimitsViewModel(final Class<T> enumClass, final List<String> values) {
      this.setEnumClass(enumClass);
      this.setValues(values);
   }

   public Class<T> getEnumClass() {
      return this.enumClass;
   }

   protected void setEnumClass(final Class<T> enumClass) {
      Assert.notNull(enumClass, SetEnumClassMessages.ENUM_CLASS_CAN_NOT_BE_NULL);
      this.enumClass = enumClass;
   }

   public List<String> getValues() {
      return this.values;
   }

   public void setValues(final List<String> values) {
      Assert.notNull(values, SetValuesMessages.VALUES_CAN_NOT_BE_NULL);
      this.values = values;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof EnumLimitsViewModel)) {
         return false;
      }

      EnumLimitsViewModel<?> that = (EnumLimitsViewModel<?>)object;
      return new EqualsBuilder().append(this.getEnumClass(), that.getEnumClass()).append(this.getValues(), that.getValues()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getEnumClass(), this.getValues());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("enumClass", this.getEnumClass()).append("values", this.getValues()).toString();
   }
}
