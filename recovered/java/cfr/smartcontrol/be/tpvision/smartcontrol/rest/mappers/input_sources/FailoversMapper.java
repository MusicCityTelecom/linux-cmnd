/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.input_sources;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failover;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.messages.mappers.input_sources.failovers.ToFailoversMessages;
import be.tpvision.smartcontrol.messages.mappers.input_sources.failovers.ToStringListMessages;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class FailoversMapper {
    private FailoversMapper() {
    }

    public static List<String> toStringList(Failovers failovers) {
        Assert.notNull((Object)failovers, ToStringListMessages.FAILOVERS_CAN_NOT_BE_NULL);
        ArrayList<Failover> failoverList = failovers.getFailoverList();
        Assert.state(failoverList != null, ToStringListMessages.FAILOVER_LIST_CAN_NOT_BE_NULL);
        boolean isAnyFailoverNull = failoverList.stream().anyMatch(Objects::isNull);
        Assert.state(!isAnyFailoverNull, ToStringListMessages.FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES);
        return failoverList.stream().map(String::valueOf).collect(Collectors.toList());
    }

    public static Failovers toFailovers(List<String> failoverStringList) {
        Assert.notNull(failoverStringList, ToFailoversMessages.FAILOVER_STRING_LIST_CAN_NOT_BE_NULL);
        boolean isAnyFailoverStringNull = failoverStringList.stream().anyMatch(Objects::isNull);
        Assert.state(!isAnyFailoverStringNull, ToFailoversMessages.FAILOVER_STRING_LIST_CAN_NOT_CONTAIN_NULL_VALUES);
        ArrayList failoverList = failoverStringList.stream().map(failoverString -> ValueUtilities.getEnumValue(Failover.class, failoverString)).collect(Collectors.toCollection(ArrayList::new));
        boolean isAnyFailoverNull = failoverList.stream().anyMatch(Objects::isNull);
        Assert.state(!isAnyFailoverNull, ToFailoversMessages.FAILOVER_LIST_CAN_NOT_CONTAIN_NULL_VALUES);
        return new Failovers(failoverList);
    }
}

