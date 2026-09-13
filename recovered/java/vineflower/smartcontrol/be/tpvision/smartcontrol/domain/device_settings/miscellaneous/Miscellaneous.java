package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class Miscellaneous implements DeviceSetting {
   @Column(nullable = true)
   private int operatingHours;

   protected Miscellaneous() {
   }

   public Miscellaneous(final int operatingHours) {
      this.operatingHours = operatingHours;
   }

   public int getOperatingHours() {
      return this.operatingHours;
   }

   public void setOperatingHours(final int operatingHours) {
      this.operatingHours = operatingHours;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Miscellaneous)) {
         return false;
      }

      Miscellaneous that = (Miscellaneous)object;
      return Objects.equals(this.getOperatingHours(), that.getOperatingHours());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getOperatingHours());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("operatingHours", this.getOperatingHours()).toString();
   }

   public enum Info implements DeviceSetting {
      OPERATING_HOURS;
   }
}
