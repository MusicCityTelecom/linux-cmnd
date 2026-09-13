package be.tpvision.smartcontrol.domain.twa;

import be.tpvision.smartcontrol.messages.domain.twa.module_address.GetXMessages;
import be.tpvision.smartcontrol.messages.domain.twa.module_address.GetYMessages;
import be.tpvision.smartcontrol.messages.domain.twa.module_address.SetXMessages;
import be.tpvision.smartcontrol.messages.domain.twa.module_address.SetYMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class ModuleAddress {
   private int x;
   private int y;

   public ModuleAddress(final int x, final int y) {
      this.setX(x);
      this.setY(y);
   }

   public int getX() {
      Assert.state(this.x >= 0, GetXMessages.X_HAS_TO_BE_A_POSITIVE_NUMBER);
      return this.x;
   }

   public void setX(final int x) {
      Assert.isTrue(this.x >= 0, SetXMessages.X_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.x = x;
   }

   public int getY() {
      Assert.state(this.y >= 0, GetYMessages.Y_HAS_TO_BE_A_POSITIVE_NUMBER);
      return this.y;
   }

   public void setY(final int y) {
      Assert.isTrue(this.y >= 0, SetYMessages.Y_HAS_TO_BE_A_POSITIVE_NUMBER);
      this.y = y;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof ModuleAddress)) {
         return false;
      }

      ModuleAddress that = (ModuleAddress)object;
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
