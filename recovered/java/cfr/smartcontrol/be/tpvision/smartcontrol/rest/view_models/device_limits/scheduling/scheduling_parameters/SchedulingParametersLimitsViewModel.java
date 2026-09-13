/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits.scheduling.scheduling_parameters;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.rest.view_models.device_limits.EnumLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device_limits.input_sources.SourceTypeLimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SchedulingParametersLimitsViewModel {
    private PageLimitsViewModel page;

    public SchedulingParametersLimitsViewModel() {
        this(new PageLimitsViewModel());
    }

    public SchedulingParametersLimitsViewModel(PageLimitsViewModel page) {
        this.page = page;
    }

    public PageLimitsViewModel getPage() {
        return this.page;
    }

    public void setPage(PageLimitsViewModel page) {
        this.page = page;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SchedulingParametersLimitsViewModel)) {
            return false;
        }
        SchedulingParametersLimitsViewModel that = (SchedulingParametersLimitsViewModel)object;
        return new EqualsBuilder().append(this.getPage(), that.getPage()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getPage());
    }

    public String toString() {
        return new ToStringBuilder(this).append("page", this.getPage()).toString();
    }

    public static class PageLimitsViewModel {
        private EnumLimitsViewModel<Page.Status> status;
        private EnumLimitsViewModel<InputSource.SourceType> sourceType;
        private WorkingDaysLimitsViewModel workingDays;
        private EnumLimitsViewModel<InputSource.Tag> tag;

        public PageLimitsViewModel() {
            this(new EnumLimitsViewModel<Page.Status>(Page.Status.class), new SourceTypeLimitsViewModel(), new WorkingDaysLimitsViewModel(), new EnumLimitsViewModel<InputSource.Tag>(InputSource.Tag.class));
        }

        public PageLimitsViewModel(EnumLimitsViewModel<Page.Status> status, EnumLimitsViewModel<InputSource.SourceType> sourceType, WorkingDaysLimitsViewModel workingDays, EnumLimitsViewModel<InputSource.Tag> tag) {
            this.status = status;
            this.sourceType = sourceType;
            this.workingDays = workingDays;
            this.tag = tag;
        }

        public EnumLimitsViewModel<Page.Status> getStatus() {
            return this.status;
        }

        public void setStatus(EnumLimitsViewModel<Page.Status> status) {
            this.status = status;
        }

        public EnumLimitsViewModel<InputSource.SourceType> getSourceType() {
            return this.sourceType;
        }

        public void setSourceType(EnumLimitsViewModel<InputSource.SourceType> sourceType) {
            this.sourceType = sourceType;
        }

        public WorkingDaysLimitsViewModel getWorkingDays() {
            return this.workingDays;
        }

        public void setWorkingDays(WorkingDaysLimitsViewModel workingDays) {
            this.workingDays = workingDays;
        }

        public EnumLimitsViewModel<InputSource.Tag> getTag() {
            return this.tag;
        }

        public void setTag(EnumLimitsViewModel<InputSource.Tag> tag) {
            this.tag = tag;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof PageLimitsViewModel)) {
                return false;
            }
            PageLimitsViewModel that = (PageLimitsViewModel)object;
            return new EqualsBuilder().append(this.getStatus(), that.getStatus()).append(this.getSourceType(), that.getSourceType()).append(this.getTag(), that.getTag()).isEquals();
        }

        public int hashCode() {
            return Objects.hash(this.getStatus(), this.getSourceType(), this.getTag());
        }

        public String toString() {
            return new ToStringBuilder(this).append("status", this.getStatus()).append("sourceType", this.getSourceType()).append("tag", this.getTag()).toString();
        }

        public static class WorkingDaysLimitsViewModel {
            private EnumLimitsViewModel workingDay;

            public WorkingDaysLimitsViewModel() {
                this(new EnumLimitsViewModel<Page.WorkingDay>(Page.WorkingDay.class));
            }

            public WorkingDaysLimitsViewModel(EnumLimitsViewModel workingDay) {
                this.workingDay = workingDay;
            }

            public EnumLimitsViewModel getWorkingDay() {
                return this.workingDay;
            }

            public void setWorkingDay(EnumLimitsViewModel workingDay) {
                this.workingDay = workingDay;
            }

            public boolean equals(Object object) {
                if (this == object) {
                    return true;
                }
                if (!(object instanceof WorkingDaysLimitsViewModel)) {
                    return false;
                }
                WorkingDaysLimitsViewModel that = (WorkingDaysLimitsViewModel)object;
                return new EqualsBuilder().append(this.getWorkingDay(), that.getWorkingDay()).isEquals();
            }

            public int hashCode() {
                return Objects.hash(this.getWorkingDay());
            }

            public String toString() {
                return new ToStringBuilder(this).append("workingDay", this.getWorkingDay()).toString();
            }
        }
    }
}

