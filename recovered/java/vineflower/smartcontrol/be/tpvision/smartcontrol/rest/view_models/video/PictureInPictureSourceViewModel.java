package be.tpvision.smartcontrol.rest.view_models.video;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PictureInPictureSourceViewModel {
   private String pictureInPictureSourceSourceType;
   private String inputSourceSourceTypeQ2;
   private String inputSourceSourceTypeQ3;
   private String inputSourceSourceTypeQ4;

   protected PictureInPictureSourceViewModel() {
   }

   public PictureInPictureSourceViewModel(
      final String pictureInPictureSourceSourceType,
      final String inputSourceSourceTypeQ2,
      final String inputSourceSourceTypeQ3,
      final String inputSourceSourceTypeQ4
   ) {
      this.pictureInPictureSourceSourceType = pictureInPictureSourceSourceType;
      this.inputSourceSourceTypeQ2 = inputSourceSourceTypeQ2;
      this.inputSourceSourceTypeQ3 = inputSourceSourceTypeQ3;
      this.inputSourceSourceTypeQ4 = inputSourceSourceTypeQ4;
   }

   public String getPictureInPictureSourceSourceType() {
      return this.pictureInPictureSourceSourceType;
   }

   public void setPictureInPictureSourceSourceType(final String pictureInPictureSourceSourceType) {
      this.pictureInPictureSourceSourceType = pictureInPictureSourceSourceType;
   }

   public String getInputSourceSourceTypeQ2() {
      return this.inputSourceSourceTypeQ2;
   }

   public void setInputSourceSourceTypeQ2(final String inputSourceSourceTypeQ2) {
      this.inputSourceSourceTypeQ2 = inputSourceSourceTypeQ2;
   }

   public String getInputSourceSourceTypeQ3() {
      return this.inputSourceSourceTypeQ3;
   }

   public void setInputSourceSourceTypeQ3(final String inputSourceSourceTypeQ3) {
      this.inputSourceSourceTypeQ3 = inputSourceSourceTypeQ3;
   }

   public String getInputSourceSourceTypeQ4() {
      return this.inputSourceSourceTypeQ4;
   }

   public void setInputSourceSourceTypeQ4(final String inputSourceSourceTypeQ4) {
      this.inputSourceSourceTypeQ4 = inputSourceSourceTypeQ4;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof PictureInPictureSourceViewModel)) {
         return false;
      }

      PictureInPictureSourceViewModel that = (PictureInPictureSourceViewModel)object;
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
