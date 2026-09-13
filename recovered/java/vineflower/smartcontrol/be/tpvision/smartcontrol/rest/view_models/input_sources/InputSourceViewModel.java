package be.tpvision.smartcontrol.rest.view_models.input_sources;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class InputSourceViewModel {
   private String sourceType;
   private String tag;
   private String sourceLabel;

   protected InputSourceViewModel() {
   }

   public InputSourceViewModel(final String sourceType, final String sourceLabel) {
      this(sourceType, null, sourceLabel);
   }

   public InputSourceViewModel(final String sourceType, final String tag, final String sourceLabel) {
      this.sourceType = sourceType;
      this.tag = tag;
      this.sourceLabel = sourceLabel;
   }

   public String getSourceType() {
      return this.sourceType;
   }

   public void setSourceType(final String sourceType) {
      this.sourceType = sourceType;
   }

   public String getTag() {
      return this.tag;
   }

   public void setTag(String tag) {
      this.tag = tag;
   }

   public String getSourceLabel() {
      return this.sourceLabel;
   }

   public void setSourceLabel(final String sourceLabel) {
      this.sourceLabel = sourceLabel;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof InputSourceViewModel)) {
         return false;
      }

      InputSourceViewModel that = (InputSourceViewModel)object;
      return new EqualsBuilder()
         .append(this.getSourceType(), that.getSourceType())
         .append(this.getTag(), that.getTag())
         .append(this.getSourceLabel(), that.getSourceLabel())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getSourceType(), this.getTag(), this.getSourceLabel());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("sourceType", this.getSourceType())
         .append("tag", this.getTag())
         .append("sourceLabel", this.getSourceLabel())
         .toString();
   }
}
