package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LedStripsViewModel {
   private String status;
   private int redValue;
   private int greenValue;
   private int blueValue;

   protected LedStripsViewModel() {
   }

   public LedStripsViewModel(final String status, final int redValue, final int greenValue, final int blueValue) {
      this.status = status;
      this.redValue = redValue;
      this.greenValue = greenValue;
      this.blueValue = blueValue;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(final String status) {
      this.status = status;
   }

   public int getRedValue() {
      return this.redValue;
   }

   public void setRedValue(final int redValue) {
      this.redValue = redValue;
   }

   public int getGreenValue() {
      return this.greenValue;
   }

   public void setGreenValue(final int greenValue) {
      this.greenValue = greenValue;
   }

   public int getBlueValue() {
      return this.blueValue;
   }

   public void setBlueValue(final int blueValue) {
      this.blueValue = blueValue;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getStatus(), this.getRedValue(), this.getGreenValue(), this.getBlueValue());
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof LedStripsViewModel)) {
         return false;
      }

      LedStripsViewModel that = (LedStripsViewModel)object;
      return new EqualsBuilder()
         .append(this.getStatus(), that.getStatus())
         .append(this.getRedValue(), that.getRedValue())
         .append(this.getGreenValue(), that.getGreenValue())
         .append(this.getBlueValue(), that.getBlueValue())
         .isEquals();
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("status", this.getStatus())
         .append("redValue", this.getRedValue())
         .append("greenValue", this.getGreenValue())
         .append("blueValue", this.getBlueValue())
         .toString();
   }
}
