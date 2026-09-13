package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MiscellaneousViewModel {
   private int operatingHours;

   protected MiscellaneousViewModel() {
   }

   public MiscellaneousViewModel(final int operatingHours) {
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

      if (!(object instanceof MiscellaneousViewModel)) {
         return false;
      }

      MiscellaneousViewModel that = (MiscellaneousViewModel)object;
      return new EqualsBuilder().append(this.getOperatingHours(), that.getOperatingHours()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getOperatingHours());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("operatingHours", this.getOperatingHours()).toString();
   }
}
