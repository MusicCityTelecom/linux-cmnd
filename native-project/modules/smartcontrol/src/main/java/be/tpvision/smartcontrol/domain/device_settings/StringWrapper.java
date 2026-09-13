package be.tpvision.smartcontrol.domain.device_settings;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class StringWrapper implements DeviceSetting {
   private String value;

   protected StringWrapper() {
   }

   public StringWrapper(final String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(final String value) {
      this.value = value;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof StringWrapper)) {
         return false;
      }

      StringWrapper that = (StringWrapper)object;
      return Objects.equals(this.getValue(), that.getValue());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getValue());
   }

   @Override
   public String toString() {
      return this.getValue();
   }
}
