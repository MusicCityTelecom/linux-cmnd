package be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.messages.domain.device_settings.scheduling.scheduling_parameters.page.SetWorkingDaysMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.scheduling.scheduling_parameters.page.working_days.GetValueMessages;
import be.tpvision.smartcontrol.messages.domain.device_settings.scheduling.scheduling_parameters.page.working_days.SetValueMessages;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class Page implements DeviceSetting {
   private Integer number;
   private Page.Status status;
   private LocalTime start;
   private LocalTime end;
   private InputSource.SourceType sourceType;
   private Page.WorkingDays workingDays;
   private InputSource.Tag tag;

   protected Page() {
   }

   public Page(
      Integer number, Page.Status status, LocalTime start, LocalTime end, InputSource.SourceType sourceType, Page.WorkingDays workingDays, InputSource.Tag tag
   ) {
      this.number = number;
      this.status = status;
      this.start = start;
      this.end = end;
      this.sourceType = sourceType;
      this.workingDays = workingDays;
      this.tag = tag;
   }

   public Integer getNumber() {
      return this.number;
   }

   public void setNumber(final Integer number) {
      this.number = number;
   }

   public Page.Status getStatus() {
      return this.status;
   }

   public void setStatus(final Page.Status status) {
      this.status = status;
   }

   public LocalTime getStart() {
      return this.start;
   }

   public void setStart(final LocalTime start) {
      this.start = start;
   }

   public LocalTime getEnd() {
      return this.end;
   }

   public void setEnd(final LocalTime end) {
      this.end = end;
   }

   public InputSource.SourceType getSourceType() {
      return this.sourceType;
   }

   public void setSourceType(final InputSource.SourceType sourceType) {
      this.sourceType = sourceType;
   }

   public Page.WorkingDays getWorkingDays() {
      return this.workingDays;
   }

   public void setWorkingDays(final Page.WorkingDays workingDays) {
      Assert.notNull(workingDays, SetWorkingDaysMessages.WORKING_DAYS_CAN_NOT_BE_NULL);
      this.workingDays = workingDays;
   }

   public InputSource.Tag getTag() {
      return this.tag;
   }

   public void setTag(final InputSource.Tag tag) {
      this.tag = tag;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Page)) {
         return false;
      }

      Page that = (Page)object;
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

   public enum Status {
      DISABLED,
      ENABLED;
   }

   public enum WorkingDay {
      EVERY_WEEK,
      MONDAY,
      TUESDAY,
      WEDNESDAY,
      THURSDAY,
      FRIDAY,
      SATURDAY,
      SUNDAY;
   }

   public static class WorkingDays {
      private LinkedHashMap<Page.WorkingDay, Boolean> workingDays = new LinkedHashMap<>();

      public WorkingDays() {
         for (Page.WorkingDay workingDay : Page.WorkingDay.values()) {
            this.workingDays.put(workingDay, false);
         }
      }

      public LinkedHashMap<Page.WorkingDay, Boolean> getWorkingDays() {
         return this.workingDays;
      }

      public boolean getValue(final Page.WorkingDay workingDay) {
         Assert.notNull(workingDay, GetValueMessages.WORKING_DAY_CAN_NOT_BE_NULL);
         return this.workingDays.get(workingDay);
      }

      public void setValue(final Page.WorkingDay workingDay, final boolean value) {
         Assert.notNull(workingDay, SetValueMessages.WORKING_DAY_CAN_NOT_BE_NULL);
         this.workingDays.put(workingDay, value);
      }

      @Override
      public boolean equals(final Object object) {
         if (this == object) {
            return true;
         }

         if (!(object instanceof Page.WorkingDays)) {
            return false;
         }

         Page.WorkingDays that = (Page.WorkingDays)object;
         return new EqualsBuilder().append(this.getWorkingDays(), that.getWorkingDays()).isEquals();
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.getWorkingDays());
      }

      @Override
      public String toString() {
         return new ToStringBuilder(this).append("workingDays", this.getWorkingDays()).toString();
      }
   }
}
