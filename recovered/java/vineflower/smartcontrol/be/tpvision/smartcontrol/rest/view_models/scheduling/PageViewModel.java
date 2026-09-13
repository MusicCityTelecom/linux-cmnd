package be.tpvision.smartcontrol.rest.view_models.scheduling;

import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PageViewModel {
   private Integer number;
   private String status;
   private String start;
   private String end;
   private String sourceType;
   private List<WorkingDayViewModel> workingDays;
   private String tag;

   public Integer getNumber() {
      return this.number;
   }

   public void setNumber(final Integer number) {
      this.number = number;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(final String status) {
      this.status = status;
   }

   public String getStart() {
      return this.start;
   }

   public void setStart(final String start) {
      this.start = start;
   }

   public String getEnd() {
      return this.end;
   }

   public void setEnd(final String end) {
      this.end = end;
   }

   public String getSourceType() {
      return this.sourceType;
   }

   public void setSourceType(final String sourceType) {
      this.sourceType = sourceType;
   }

   public List<WorkingDayViewModel> getWorkingDays() {
      return this.workingDays;
   }

   public void setWorkingDays(final List<WorkingDayViewModel> workingDays) {
      this.workingDays = workingDays;
   }

   public String getTag() {
      return this.tag;
   }

   public void setTag(final String tag) {
      this.tag = tag;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof PageViewModel)) {
         return false;
      }

      PageViewModel that = (PageViewModel)object;
      return new EqualsBuilder()
         .append(this.getNumber(), that.getNumber())
         .append(this.getStatus(), that.getStatus())
         .append(this.getStart(), that.getStart())
         .append(this.getEnd(), that.getEnd())
         .append(this.getSourceType(), that.getSourceType())
         .append(this.getWorkingDays(), that.getWorkingDays())
         .append(this.getTag(), that.getTag())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getNumber(), this.getStatus(), this.getStart(), this.getEnd(), this.getSourceType(), this.getWorkingDays(), this.getTag());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("number", this.getNumber())
         .append("status", this.getStatus())
         .append("start", this.getStart())
         .append("end", this.getEnd())
         .append("sourceType", this.getSourceType())
         .append("workingDays", this.getWorkingDays())
         .append("tag", this.getTag())
         .toString();
   }
}
