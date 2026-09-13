package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PictureInPictureSourceLimitsViewModel {
   private EnumLimitsViewModel<PictureInPictureSource.SourceType> pictureInPictureSourceSourceType;
   private EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> inputSourceSourceTypeQ2;
   private EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> inputSourceSourceTypeQ3;
   private EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> inputSourceSourceTypeQ4;

   public PictureInPictureSourceLimitsViewModel() {
      this(
         new EnumLimitsViewModel<>(PictureInPictureSource.SourceType.class),
         new EnumLimitsViewModel<>(PictureInPictureSource.InputSourceSourceType.class),
         new EnumLimitsViewModel<>(PictureInPictureSource.InputSourceSourceType.class),
         new EnumLimitsViewModel<>(PictureInPictureSource.InputSourceSourceType.class)
      );
   }

   public PictureInPictureSourceLimitsViewModel(
      final EnumLimitsViewModel<PictureInPictureSource.SourceType> pictureInPictureSourceSourceType,
      final EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> inputSourceSourceTypeQ2,
      final EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> inputSourceSourceTypeQ3,
      final EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> inputSourceSourceTypeQ4
   ) {
      this.pictureInPictureSourceSourceType = pictureInPictureSourceSourceType;
      this.inputSourceSourceTypeQ2 = inputSourceSourceTypeQ2;
      this.inputSourceSourceTypeQ3 = inputSourceSourceTypeQ3;
      this.inputSourceSourceTypeQ4 = inputSourceSourceTypeQ4;
   }

   public EnumLimitsViewModel<PictureInPictureSource.SourceType> getPictureInPictureSourceSourceType() {
      return this.pictureInPictureSourceSourceType;
   }

   public void setPictureInPictureSourceSourceType(final EnumLimitsViewModel<PictureInPictureSource.SourceType> pictureInPictureSourceSourceType) {
      this.pictureInPictureSourceSourceType = pictureInPictureSourceSourceType;
   }

   public EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> getInputSourceSourceTypeQ2() {
      return this.inputSourceSourceTypeQ2;
   }

   public void setInputSourceSourceTypeQ2(final EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> inputSourceSourceTypeQ2) {
      this.inputSourceSourceTypeQ2 = inputSourceSourceTypeQ2;
   }

   public EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> getInputSourceSourceTypeQ3() {
      return this.inputSourceSourceTypeQ3;
   }

   public void setInputSourceSourceTypeQ3(final EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> inputSourceSourceTypeQ3) {
      this.inputSourceSourceTypeQ3 = inputSourceSourceTypeQ3;
   }

   public EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> getInputSourceSourceTypeQ4() {
      return this.inputSourceSourceTypeQ4;
   }

   public void setInputSourceSourceTypeQ4(final EnumLimitsViewModel<PictureInPictureSource.InputSourceSourceType> inputSourceSourceTypeQ4) {
      this.inputSourceSourceTypeQ4 = inputSourceSourceTypeQ4;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof PictureInPictureSourceLimitsViewModel)) {
         return false;
      }

      PictureInPictureSourceLimitsViewModel that = (PictureInPictureSourceLimitsViewModel)object;
      return new EqualsBuilder()
         .append(this.getPictureInPictureSourceSourceType(), that.getPictureInPictureSourceSourceType())
         .append(this.getInputSourceSourceTypeQ2(), that.getInputSourceSourceTypeQ2())
         .append(this.getInputSourceSourceTypeQ3(), that.getInputSourceSourceTypeQ3())
         .append(this.getInputSourceSourceTypeQ4(), that.getInputSourceSourceTypeQ4())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.getPictureInPictureSourceSourceType(), this.getInputSourceSourceTypeQ2(), this.getInputSourceSourceTypeQ3(), this.getInputSourceSourceTypeQ4()
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("pictureInPictureSourceSourceType", this.getPictureInPictureSourceSourceType())
         .append("inputSourceSourceTypeQ2", this.getInputSourceSourceTypeQ2())
         .append("inputSourceSourceTypeQ3", this.getInputSourceSourceTypeQ3())
         .append("inputSourceSourceTypeQ4", this.getInputSourceSourceTypeQ4())
         .toString();
   }
}
