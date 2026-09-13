package be.tpvision.smartcontrol.domain.device_settings.video;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.picture_in_picture.SetStatusMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.video.picture_in_picture.SetWindowPositionMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class PictureInPicture implements DeviceSetting {
   private PictureInPicture.Status status;
   private PictureInPicture.WindowPosition windowPosition;

   protected PictureInPicture() {
   }

   public PictureInPicture(final PictureInPicture.Status status, final PictureInPicture.WindowPosition windowPosition) {
      this.setStatus(status);
      this.setWindowPosition(windowPosition);
   }

   public PictureInPicture.Status getStatus() {
      return this.status;
   }

   public void setStatus(final PictureInPicture.Status status) {
      Assert.notNull(status, SetStatusMessages.STATUS_CAN_NOT_BE_NULL);
      this.status = status;
   }

   public PictureInPicture.WindowPosition getWindowPosition() {
      return this.windowPosition;
   }

   public void setWindowPosition(final PictureInPicture.WindowPosition windowPosition) {
      Assert.notNull(windowPosition, SetWindowPositionMessages.WINDOW_POSITION_CAN_NOT_BE_NULL);
      this.windowPosition = windowPosition;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof PictureInPicture)) {
         return false;
      }

      PictureInPicture that = (PictureInPicture)object;
      return new EqualsBuilder().append(this.getStatus(), that.getStatus()).append(this.getWindowPosition(), that.getWindowPosition()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getStatus(), this.getWindowPosition());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("status", this.getStatus()).append("windowPosition", this.getWindowPosition()).toString();
   }

   public enum Status {
      OFF,
      ON,
      POP,
      QUICK_SWAP,
      PBP_2WIN,
      PBP_3WIN,
      PBP_4WIN,
      PBP_3WIN_1,
      PBP_3WIN_2,
      PBP_4WIN_1,
      SICP;
   }

   public enum WindowPosition {
      BOTTOM_LEFT,
      TOP_LEFT,
      TOP_RIGHT,
      BOTTOM_RIGHT,
      CENTER;
   }
}
