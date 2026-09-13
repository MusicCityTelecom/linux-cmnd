package be.tpvision.smartcontrol.rest.view_models.video;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VideoAlignmentViewModel {
   private String item;

   protected VideoAlignmentViewModel() {
   }

   public VideoAlignmentViewModel(final String item) {
      this.item = item;
   }

   public String getItem() {
      return this.item;
   }

   public void setItem(final String item) {
      this.item = item;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VideoAlignmentViewModel)) {
         return false;
      }

      VideoAlignmentViewModel that = (VideoAlignmentViewModel)object;
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
}
