/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.scheduling.scheduling_parameters;

import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.messages.mappers.scheduling.scheduling_parameters.working_days.ToWorkingDayViewModelListMessages;
import be.tpvision.smartcontrol.messages.mappers.scheduling.scheduling_parameters.working_days.ToWorkingDaysMessages;
import be.tpvision.smartcontrol.rest.view_models.scheduling.WorkingDayViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.util.Assert;

public class WorkingDayMapper {
    private WorkingDayMapper() {
    }

    public static List<WorkingDayViewModel> toWorkingDayViewModelList(Page.WorkingDays workingDays) {
        Assert.notNull((Object)workingDays, ToWorkingDayViewModelListMessages.WORKING_DAYS_CAN_NOT_BE_NULL);
        LinkedHashMap<Page.WorkingDay, Boolean> domainWorkingDays = workingDays.getWorkingDays();
        Assert.state(domainWorkingDays != null, ToWorkingDayViewModelListMessages.DOMAIN_WORKING_DAYS_CAN_NOT_BE_NULL);
        ArrayList<WorkingDayViewModel> viewModelWorkingDays = new ArrayList<WorkingDayViewModel>();
        domainWorkingDays.entrySet().stream().forEach(entry -> {
            Assert.state(entry != null, ToWorkingDayViewModelListMessages.ENTRY_CAN_NOT_BE_NULL);
            Page.WorkingDay domainWorkingDay = (Page.WorkingDay)((Object)((Object)entry.getKey()));
            String viewModelWorkingDay = String.valueOf((Object)domainWorkingDay);
            Boolean value = (Boolean)entry.getValue();
            Assert.state(value != null, ToWorkingDayViewModelListMessages.VALUE_CAN_NOT_BE_NULL);
            WorkingDayViewModel workingDayViewModel = new WorkingDayViewModel(viewModelWorkingDay, value);
            viewModelWorkingDays.add(workingDayViewModel);
        });
        return viewModelWorkingDays;
    }

    public static Page.WorkingDays toWorkingDays(List<WorkingDayViewModel> workingDayViewModelList) {
        Assert.notNull(workingDayViewModelList, ToWorkingDaysMessages.WORKING_DAY_VIEW_MODEL_LIST_CAN_NOT_BE_NULL_MESSAGE);
        Page.WorkingDays workingDays = new Page.WorkingDays();
        workingDayViewModelList.stream().forEach(workingDayViewModel -> {
            Assert.state(workingDayViewModel != null, ToWorkingDaysMessages.WORKING_DAY_VIEW_MODEL_CAN_NOT_BE_NULL_MESSAGE);
            String viewModelWorkingDay = workingDayViewModel.getWorkingDay();
            Assert.state(viewModelWorkingDay != null, ToWorkingDaysMessages.VIEW_MODEL_WORKING_DAY_CAN_NOT_BE_NULL_MESSAGE);
            Page.WorkingDay domainWorkingDay = ValueUtilities.getEnumValue(Page.WorkingDay.class, viewModelWorkingDay);
            boolean value = workingDayViewModel.isValue();
            workingDays.setValue(domainWorkingDay, value);
        });
        return workingDays;
    }
}

