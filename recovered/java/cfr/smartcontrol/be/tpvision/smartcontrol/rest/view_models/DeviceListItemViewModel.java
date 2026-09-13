/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models;

import be.tpvision.smartcontrol.rest.view_models.device.DetectDevicesViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.DetectGroupsViewModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes(value={@JsonSubTypes.Type(value=DetectDevicesViewModel.class, name="device"), @JsonSubTypes.Type(value=DetectGroupsViewModel.class, name="group")})
public interface DeviceListItemViewModel {
}

