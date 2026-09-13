package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PictureInPictureLimitsViewModel {
   private EnumLimitsViewModel<PictureInPicture.Status> status;
   private EnumLimitsViewModel<PictureInPicture.WindowPosition> windowPosition;

   public PictureInPictureLimitsViewModel() {
      this(new EnumLimitsViewModel<>(PictureInPicture.Status.class), new EnumLimitsViewModel<>(PictureInPicture.WindowPosition.class));
   }

   public PictureInPictureLimitsViewModel(
      final EnumLimitsViewModel<PictureInPicture.Status> status, final EnumLimitsViewModel<PictureInPicture.WindowPosition> windowPosition
   ) {
      this.status = status;
      this.windowPosition = windowPosition;
   }

   public EnumLimitsViewModel<PictureInPicture.Status> getStatus() {
      return this.status;
   }

   public void setStatus(EnumLimitsViewModel<PictureInPicture.Status> status) {
      this.status = status;
   }

   public EnumLimitsViewModel<PictureInPicture.WindowPosition> getWindowPosition() {
      return this.windowPosition;
   }

   public void setWindowPosition(final EnumLimitsViewModel<PictureInPicture.WindowPosition> windowPosition) {
      this.windowPosition = windowPosition;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof PictureInPictureLimitsViewModel)) {
         return false;
      }

      PictureInPictureLimitsViewModel that = (PictureInPictureLimitsViewModel)object;
      return new EqualsBuilder().append(this.status, that.status).append(this.windowPosition, that.windowPosition).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.status, this.windowPosition);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("status", this.status).append("windowPosition", this.windowPosition).toString();
   }
}
