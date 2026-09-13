package be.tpvision.smartcontrol.rest.view_models.scheduling;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WorkingDayViewModel {
   private String workingDay;
   private boolean value;

   protected WorkingDayViewModel() {
   }

   public WorkingDayViewModel(final String workingDay, final boolean value) {
      this.workingDay = workingDay;
      this.value = value;
   }

   public String getWorkingDay() {
      return this.workingDay;
   }

   public void setWorkingDay(String workingDay) {
      this.workingDay = workingDay;
   }

   public boolean isValue() {
      return this.value;
   }

   public void setValue(boolean value) {
      this.value = value;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof WorkingDayViewModel)) {
         return false;
      }

      WorkingDayViewModel that = (WorkingDayViewModel)object;
      return new EqualsBuilder().append(this.getWorkingDay(), that.getWorkingDay()).append(this.isValue(), that.isValue()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getWorkingDay(), this.isValue());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("workingDay", this.getWorkingDay()).append("value", this.isValue()).toString();
   }
}
