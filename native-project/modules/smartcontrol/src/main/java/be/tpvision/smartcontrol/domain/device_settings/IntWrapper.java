package be.tpvision.smartcontrol.domain.device_settings;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class IntWrapper implements DeviceSetting {
   private int value;

   protected IntWrapper() {
   }

   public IntWrapper(final int value) {
      this.value = value;
   }

   public int getValue() {
      return this.value;
   }

   public void setValue(final int value) {
      this.value = value;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof IntWrapper)) {
         return false;
      }

      IntWrapper that = (IntWrapper)object;
      return Objects.equals(this.getValue(), that.getValue());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getValue());
   }

   @Override
   public String toString() {
      return String.valueOf(this.getValue());
   }
}
