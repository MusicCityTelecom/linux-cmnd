package be.tpvision.smartcontrol.rest.view_models.device_limits.scheduling.scheduling_parameters;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.rest.view_models.device_limits.EnumLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device_limits.input_sources.SourceTypeLimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SchedulingParametersLimitsViewModel {
   private SchedulingParametersLimitsViewModel.PageLimitsViewModel page;

   public SchedulingParametersLimitsViewModel() {
      this(new SchedulingParametersLimitsViewModel.PageLimitsViewModel());
   }

   public SchedulingParametersLimitsViewModel(final SchedulingParametersLimitsViewModel.PageLimitsViewModel page) {
      this.page = page;
   }

   public SchedulingParametersLimitsViewModel.PageLimitsViewModel getPage() {
      return this.page;
   }

   public void setPage(final SchedulingParametersLimitsViewModel.PageLimitsViewModel page) {
      this.page = page;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof SchedulingParametersLimitsViewModel)) {
         return false;
      }

      SchedulingParametersLimitsViewModel that = (SchedulingParametersLimitsViewModel)object;
      return new EqualsBuilder().append(this.getPage(), that.getPage()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getPage());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("page", this.getPage()).toString();
   }

   public static class PageLimitsViewModel {
      private EnumLimitsViewModel<Page.Status> status;
      private EnumLimitsViewModel<InputSource.SourceType> sourceType;
      private SchedulingParametersLimitsViewModel.PageLimitsViewModel.WorkingDaysLimitsViewModel workingDays;
      private EnumLimitsViewModel<InputSource.Tag> tag;

      public PageLimitsViewModel() {
         this(
            new EnumLimitsViewModel<>(Page.Status.class),
            new SourceTypeLimitsViewModel(),
            new SchedulingParametersLimitsViewModel.PageLimitsViewModel.WorkingDaysLimitsViewModel(),
            new EnumLimitsViewModel<>(InputSource.Tag.class)
         );
      }

      public PageLimitsViewModel(
         final EnumLimitsViewModel<Page.Status> status,
         final EnumLimitsViewModel<InputSource.SourceType> sourceType,
         final SchedulingParametersLimitsViewModel.PageLimitsViewModel.WorkingDaysLimitsViewModel workingDays,
         final EnumLimitsViewModel<InputSource.Tag> tag
      ) {
         this.status = status;
         this.sourceType = sourceType;
         this.workingDays = workingDays;
         this.tag = tag;
      }

      public EnumLimitsViewModel<Page.Status> getStatus() {
         return this.status;
      }

      public void setStatus(final EnumLimitsViewModel<Page.Status> status) {
         this.status = status;
      }

      public EnumLimitsViewModel<InputSource.SourceType> getSourceType() {
         return this.sourceType;
      }

      public void setSourceType(final EnumLimitsViewModel<InputSource.SourceType> sourceType) {
         this.sourceType = sourceType;
      }

      public SchedulingParametersLimitsViewModel.PageLimitsViewModel.WorkingDaysLimitsViewModel getWorkingDays() {
         return this.workingDays;
      }

      public void setWorkingDays(final SchedulingParametersLimitsViewModel.PageLimitsViewModel.WorkingDaysLimitsViewModel workingDays) {
         this.workingDays = workingDays;
      }

      public EnumLimitsViewModel<InputSource.Tag> getTag() {
         return this.tag;
      }

      public void setTag(final EnumLimitsViewModel<InputSource.Tag> tag) {
         this.tag = tag;
      }

      @Override
      public boolean equals(final Object object) {
         if (this == object) {
            return true;
         }

         if (!(object instanceof SchedulingParametersLimitsViewModel.PageLimitsViewModel)) {
            return false;
         }

         SchedulingParametersLimitsViewModel.PageLimitsViewModel that = (SchedulingParametersLimitsViewModel.PageLimitsViewModel)object;
         return new EqualsBuilder()
            .append(this.getStatus(), that.getStatus())
            .append(this.getSourceType(), that.getSourceType())
            .append(this.getTag(), that.getTag())
            .isEquals();
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.getStatus(), this.getSourceType(), this.getTag());
      }

      @Override
      public String toString() {
         return new ToStringBuilder(this).append("status", this.getStatus()).append("sourceType", this.getSourceType()).append("tag", this.getTag()).toString();
      }

      public static class WorkingDaysLimitsViewModel {
         private EnumLimitsViewModel workingDay;

         public WorkingDaysLimitsViewModel() {
            this(new EnumLimitsViewModel<>(Page.WorkingDay.class));
         }

         public WorkingDaysLimitsViewModel(final EnumLimitsViewModel workingDay) {
            this.workingDay = workingDay;
         }

         public EnumLimitsViewModel getWorkingDay() {
            return this.workingDay;
         }

         public void setWorkingDay(final EnumLimitsViewModel workingDay) {
            this.workingDay = workingDay;
         }

         @Override
         public boolean equals(final Object object) {
            if (this == object) {
               return true;
            }

            if (!(object instanceof SchedulingParametersLimitsViewModel.PageLimitsViewModel.WorkingDaysLimitsViewModel)) {
               return false;
            }

            SchedulingParametersLimitsViewModel.PageLimitsViewModel.WorkingDaysLimitsViewModel that = (SchedulingParametersLimitsViewModel.PageLimitsViewModel.WorkingDaysLimitsViewModel)object;
            return new EqualsBuilder().append(this.getWorkingDay(), that.getWorkingDay()).isEquals();
         }

         @Override
         public int hashCode() {
            return Objects.hash(this.getWorkingDay());
         }

         @Override
         public String toString() {
            return new ToStringBuilder(this).append("workingDay", this.getWorkingDay()).toString();
         }
      }
   }
}
