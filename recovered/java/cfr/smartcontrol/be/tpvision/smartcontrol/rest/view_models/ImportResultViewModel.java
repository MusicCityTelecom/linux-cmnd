/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models;

import be.tpvision.smartcontrol.rest.view_models.ImportDeviceResultViewModel;
import be.tpvision.smartcontrol.rest.view_models.ImportGroupResultViewModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes(value={@JsonSubTypes.Type(value=ImportDeviceResultViewModel.class, name="device"), @JsonSubTypes.Type(value=ImportGroupResultViewModel.class, name="group")})
public interface ImportResultViewModel {
}

