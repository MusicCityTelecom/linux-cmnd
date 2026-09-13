package be.tpvision.smartcontrol.rest.view_models.twa;

import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IntegerViewModel implements TwaDeviceSettingViewModel {
   private Integer value;

   public IntegerViewModel() {
   }

   public IntegerViewModel(final Integer value) {
      this.value = value;
   }

   public Integer getValue() {
      return this.value;
   }

   public void setValue(final Integer value) {
      this.value = value;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof IntegerViewModel)) {
         return false;
      }

      IntegerViewModel that = (IntegerViewModel)object;
      return Objects.equals(this.getValue(), that.getValue());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getValue());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("value", this.getValue()).toString();
   }
}
