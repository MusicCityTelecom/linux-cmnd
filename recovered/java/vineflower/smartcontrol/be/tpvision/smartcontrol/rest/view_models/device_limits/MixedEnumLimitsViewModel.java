package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.MixedEnumDeviceSetting;
import be.tpvision.smartcontrol.messages.view_models.device_limits.enum_limits.SetEnumClassMessages;
import be.tpvision.smartcontrol.messages.view_models.device_limits.enum_limits.SetValuesMessages;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class MixedEnumLimitsViewModel<T extends Enum<? extends MixedEnumDeviceSetting>> {
   private Class<T> enumClass;
   private List<Map<String, String>> values;

   protected MixedEnumLimitsViewModel() {
   }

   public MixedEnumLimitsViewModel(final Class<T> enumClass) {
      this(enumClass, extractMixPariList(enumClass));
   }

   private static <T extends Enum<? extends MixedEnumDeviceSetting>> List<Map<String, String>> extractMixPariList(final Class<T> enumClass) {
      List<Map<String, String>> result = new ArrayList<>();

      for (T t : (Enum[])enumClass.getEnumConstants()) {
         MixedEnumDeviceSetting mix = (MixedEnumDeviceSetting)t;
         Map<String, String> timeZoneMap = new HashMap<>();
         timeZoneMap.put("value", mix.getOptionValue());
         timeZoneMap.put("label", mix.getOptionText());
         result.add(timeZoneMap);
      }

      return result;
   }

   public MixedEnumLimitsViewModel(final Class<T> enumClass, final List<Map<String, String>> values) {
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

   public List<Map<String, String>> getValues() {
      return this.values;
   }

   public void setValues(final List<Map<String, String>> values) {
      Assert.notNull(values, SetValuesMessages.VALUES_CAN_NOT_BE_NULL);
      this.values = values;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof MixedEnumLimitsViewModel)) {
         return false;
      }

      MixedEnumLimitsViewModel<?> that = (MixedEnumLimitsViewModel<?>)object;
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
