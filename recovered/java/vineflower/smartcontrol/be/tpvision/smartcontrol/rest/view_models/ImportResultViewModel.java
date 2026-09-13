package be.tpvision.smartcontrol.rest.view_models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "@class")
@JsonSubTypes({@Type(value = ImportDeviceResultViewModel.class, name = "device"), @Type(value = ImportGroupResultViewModel.class, name = "group")})
public interface ImportResultViewModel {
}
