package be.tpvision.smartcontrol.rest.view_models.hardware;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class HardwareViewModel {
   private String hardwareKey;
   private String contentId;

   public String getHardwareKey() {
      return this.hardwareKey;
   }

   public void setHardwareKey(final String hardwareKey) {
      this.hardwareKey = hardwareKey;
   }

   public String getContentId() {
      return this.contentId;
   }

   public void setContentId(final String contentId) {
      this.contentId = contentId;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof HardwareViewModel)) {
         return false;
      }

      HardwareViewModel that = (HardwareViewModel)object;
      return new EqualsBuilder().append(this.getHardwareKey(), that.getHardwareKey()).append(this.getContentId(), that.getContentId()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getHardwareKey(), this.getContentId());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("hardwareKey", this.getHardwareKey()).append("contentId", this.getContentId()).toString();
   }
}
