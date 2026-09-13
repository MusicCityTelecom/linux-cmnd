package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.miscellaneous.video_alignment.ConstructorMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class VideoAlignment implements DeviceSetting {
   private VideoAlignment.Item item;

   public VideoAlignment(final VideoAlignment.Item item) {
      Assert.notNull(item, ConstructorMessages.ITEM_CAN_NOT_BE_NULL);
      this.item = item;
   }

   public VideoAlignment.Item getItem() {
      return this.item;
   }

   public void setItem(final VideoAlignment.Item item) {
      this.item = item;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VideoAlignment)) {
         return false;
      }

      VideoAlignment that = (VideoAlignment)object;
      return new EqualsBuilder().append(this.getItem(), that.getItem()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getItem());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("item", this.getItem()).toString();
   }

   public enum Item {
      AUTO_ADJUST;
   }
}
