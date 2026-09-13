/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models;

import be.tpvision.smartcontrol.rest.view_models.device.ImportExportDeviceViewModel;
import be.tpvision.smartcontrol.rest.view_models.group.ImportExportGroupViewModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes(value={@JsonSubTypes.Type(value=ImportExportDeviceViewModel.class, name="device"), @JsonSubTypes.Type(value=ImportExportGroupViewModel.class, name="group")})
public interface ImportExportItemListViewModel {
}

