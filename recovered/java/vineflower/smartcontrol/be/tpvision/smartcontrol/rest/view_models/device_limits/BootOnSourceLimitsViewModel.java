package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.general.BootOnSource;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BootOnSourceLimitsViewModel {
   private EnumLimitsViewModel<BootOnSource.VideoSourceType> videoSourceType;
   private EnumLimitsViewModel<BootOnSource.Tag> tag;

   public BootOnSourceLimitsViewModel() {
      this(new EnumLimitsViewModel<>(BootOnSource.VideoSourceType.class), new EnumLimitsViewModel<>(BootOnSource.Tag.class));
   }

   public BootOnSourceLimitsViewModel(final EnumLimitsViewModel<BootOnSource.VideoSourceType> videoSourceType, final EnumLimitsViewModel<BootOnSource.Tag> tag) {
      this.videoSourceType = videoSourceType;
      this.tag = tag;
   }

   public EnumLimitsViewModel<BootOnSource.VideoSourceType> getVideoSourceType() {
      return this.videoSourceType;
   }

   public void setVideoSourceType(EnumLimitsViewModel<BootOnSource.VideoSourceType> videoSourceType) {
      this.videoSourceType = videoSourceType;
   }

   public EnumLimitsViewModel<BootOnSource.Tag> getTag() {
      return this.tag;
   }

   public void setTag(EnumLimitsViewModel<BootOnSource.Tag> tag) {
      this.tag = tag;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof BootOnSourceLimitsViewModel)) {
         return false;
      }

      BootOnSourceLimitsViewModel that = (BootOnSourceLimitsViewModel)object;
      return new EqualsBuilder().append(this.getVideoSourceType(), that.getVideoSourceType()).append(this.getTag(), that.getTag()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getVideoSourceType(), this.getTag());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("videoSourceType", this.getVideoSourceType()).append("tag", this.getTag()).toString();
   }
}
