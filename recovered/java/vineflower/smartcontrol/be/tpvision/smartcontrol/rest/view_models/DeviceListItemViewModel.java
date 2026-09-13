package be.tpvision.smartcontrol.rest.view_models;

import be.tpvision.smartcontrol.rest.view_models.device.DetectDevicesViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.DetectGroupsViewModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({@Type(value = DetectDevicesViewModel.class, name = "device"), @Type(value = DetectGroupsViewModel.class, name = "group")})
public interface DeviceListItemViewModel {
}
