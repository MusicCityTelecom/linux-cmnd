package be.tpvision.smartcontrol.rest.view_models.twa;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes(
   {
         @Type(value = MainboardOriginPositionViewModel.class, name = "mainboardOriginPosition"),
         @Type(value = StringViewModel.class, name = "string"),
         @Type(value = IntegerViewModel.class, name = "integer"),
         @Type(value = DeviceAddressWrapperViewModel.class, name = "deviceAddressWrapper")
   }
)
public interface TwaDeviceSettingViewModel {
}
