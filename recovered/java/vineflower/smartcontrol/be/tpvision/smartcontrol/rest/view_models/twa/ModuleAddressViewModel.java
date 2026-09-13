package be.tpvision.smartcontrol.rest.view_models.twa;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ModuleAddressViewModel {
   private int x;
   private int y;

   public ModuleAddressViewModel() {
   }

   public ModuleAddressViewModel(final int x, final int y) {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return this.x;
   }

   public void setX(final int x) {
      this.x = x;
   }

   public int getY() {
      return this.y;
   }

   public void setY(final int y) {
      this.y = y;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof ModuleAddressViewModel)) {
         return false;
      }

      ModuleAddressViewModel that = (ModuleAddressViewModel)object;
      return new EqualsBuilder().append(this.getX(), that.getX()).append(this.getY(), that.getY()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getX(), this.getY());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("x", this.getX()).append("y", this.getY()).toString();
   }
}
