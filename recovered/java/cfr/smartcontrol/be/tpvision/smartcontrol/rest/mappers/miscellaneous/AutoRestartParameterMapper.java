/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.general.AutoRestartParameter;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.AutoRestartParameterViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.time.LocalTime;
import org.apache.commons.lang3.StringUtils;

public class AutoRestartParameterMapper {
    private AutoRestartParameterMapper() {
    }

    public static AutoRestartParameterViewModel toAutoRestartParameterViewModel(AutoRestartParameter autoRestartParameter) {
        String restartTime = null;
        if (autoRestartParameter.getHour() != 24 && autoRestartParameter.getMinute() != 60) {
            LocalTime time = LocalTime.of(autoRestartParameter.getHour(), autoRestartParameter.getMinute());
            restartTime = String.valueOf(time);
        }
        return new AutoRestartParameterViewModel(autoRestartParameter.getStatus().name(), restartTime);
    }

    public static AutoRestartParameter toAutoRestartParameter(AutoRestartParameterViewModel autoRestartParameterViewModel) {
        String statusString = autoRestartParameterViewModel.getStatus();
        AutoRestartParameter.Status status = ValueUtilities.getEnumValue(AutoRestartParameter.Status.class, statusString);
        String restartTime = autoRestartParameterViewModel.getRestartTime();
        int hour = 24;
        int minute = 60;
        if (StringUtils.isNotEmpty(restartTime)) {
            String[] times = restartTime.split(":");
            hour = Integer.valueOf(times[0]);
            minute = Integer.valueOf(times[1]);
        }
        return new AutoRestartParameter(status, hour, minute);
    }
}

