/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers;

import be.tpvision.smartcontrol.io.ip.NetworkAdapter;
import be.tpvision.smartcontrol.messages.mappers.settings.ToNetworkAdapterViewModelListMessages;
import be.tpvision.smartcontrol.messages.mappers.settings.ToNetworkAdapterViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.NetworkAdapterViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class NetworkAdapterMapper {
    private NetworkAdapterMapper() {
    }

    public static NetworkAdapterViewModel toNetworkAdapterViewModel(NetworkAdapter networkAdapter) {
        Assert.notNull((Object)networkAdapter, ToNetworkAdapterViewModelMessages.NETWORK_ADAPTER_CAN_NOT_BE_NULL);
        String displayName = networkAdapter.getDisplayName();
        String ipv4Address = networkAdapter.getIpv4Address();
        return new NetworkAdapterViewModel(displayName, ipv4Address);
    }

    public static List<NetworkAdapterViewModel> toNetworkAdapterViewModelList(Collection<NetworkAdapter> networkAdapterCollection) {
        Assert.notNull(networkAdapterCollection, ToNetworkAdapterViewModelListMessages.NETWORK_ADAPTER_COLLECTION_CAN_NOT_BE_NULL);
        return networkAdapterCollection.stream().filter(Objects::nonNull).map(NetworkAdapterMapper::toNetworkAdapterViewModel).collect(Collectors.toCollection(ArrayList::new));
    }
}

