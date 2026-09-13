/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.twa;

import be.tpvision.smartcontrol.rest.view_models.twa.DeviceAddressWrapperViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.IntegerViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.MainboardOriginPositionViewModel;
import be.tpvision.smartcontrol.rest.view_models.twa.StringViewModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes(value={@JsonSubTypes.Type(value=MainboardOriginPositionViewModel.class, name="mainboardOriginPosition"), @JsonSubTypes.Type(value=StringViewModel.class, name="string"), @JsonSubTypes.Type(value=IntegerViewModel.class, name="integer"), @JsonSubTypes.Type(value=DeviceAddressWrapperViewModel.class, name="deviceAddressWrapper")})
public interface TwaDeviceSettingViewModel {
}

