/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device;

import be.tpvision.smartcontrol.rest.view_models.device.IpDestinationViewModel;
import be.tpvision.smartcontrol.rest.view_models.device_limits.DeviceLimitsViewModel;
import be.tpvision.smartcontrol.rest.view_models.input_sources.InputSourceViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeviceViewModel {
    private Long id;
    private IpDestinationViewModel ipDestination;
    private String name;
    private String modelNumber;
    private Integer temperatureSensor;
    private String powerState;
    private InputSourceViewModel inputSource;
    private DeviceLimitsViewModel deviceLimits;

    protected DeviceViewModel() {
    }

    public DeviceViewModel(Long id, IpDestinationViewModel ipDestinationViewModel, String name, String modelNumber, Integer temperatureSensor, String powerState, InputSourceViewModel inputSource) {
        this.id = id;
        this.ipDestination = ipDestinationViewModel;
        this.name = name;
        this.modelNumber = modelNumber;
        this.temperatureSensor = temperatureSensor;
        this.powerState = powerState;
        this.inputSource = inputSource;
        this.deviceLimits = new DeviceLimitsViewModel();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IpDestinationViewModel getIpDestination() {
        return this.ipDestination;
    }

    public void setIpDestination(IpDestinationViewModel ipDestination) {
        this.ipDestination = ipDestination;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelNumber() {
        return this.modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public Integer getTemperatureSensor() {
        return this.temperatureSensor;
    }

    public void setTemperatureSensor(Integer temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    public String getPowerState() {
        return this.powerState;
    }

    public void setPowerState(String powerState) {
        this.powerState = powerState;
    }

    public InputSourceViewModel getInputSource() {
        return this.inputSource;
    }

    public void setInputSource(InputSourceViewModel inputSourceViewModel) {
        this.inputSource = inputSourceViewModel;
    }

    public DeviceLimitsViewModel getDeviceLimits() {
        return this.deviceLimits;
    }

    public void setDeviceLimits(DeviceLimitsViewModel deviceLimits) {
        this.deviceLimits = deviceLimits;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DeviceViewModel)) {
            return false;
        }
        DeviceViewModel that = (DeviceViewModel)object;
        return new EqualsBuilder().append(this.getId(), that.getId()).append(this.getIpDestination(), that.getIpDestination()).append(this.getName(), that.getName()).append(this.getModelNumber(), that.getModelNumber()).append(this.getTemperatureSensor(), that.getTemperatureSensor()).append(this.getPowerState(), that.getPowerState()).append(this.getInputSource(), that.getInputSource()).append(this.getDeviceLimits(), that.getDeviceLimits()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getId(), this.getIpDestination(), this.getName(), this.getModelNumber(), this.getTemperatureSensor(), this.getPowerState(), this.getInputSource(), this.getDeviceLimits());
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", this.getId()).append("ipDestination", this.getIpDestination()).append("name", this.getName()).append("modelNumber", this.getModelNumber()).append("temperatureSensor", this.getTemperatureSensor()).append("powerState", this.getPowerState()).append("inputSource", this.getInputSource()).append("deviceLimits", this.getDeviceLimits()).toString();
    }
}

