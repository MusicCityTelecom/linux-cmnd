package be.tpvision.smartcontrol.rest.view_models.device;

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

   public DeviceViewModel(
      final Long id,
      final IpDestinationViewModel ipDestinationViewModel,
      final String name,
      final String modelNumber,
      final Integer temperatureSensor,
      final String powerState,
      final InputSourceViewModel inputSource
   ) {
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

   public void setId(final Long id) {
      this.id = id;
   }

   public IpDestinationViewModel getIpDestination() {
      return this.ipDestination;
   }

   public void setIpDestination(final IpDestinationViewModel ipDestination) {
      this.ipDestination = ipDestination;
   }

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public String getModelNumber() {
      return this.modelNumber;
   }

   public void setModelNumber(final String modelNumber) {
      this.modelNumber = modelNumber;
   }

   public Integer getTemperatureSensor() {
      return this.temperatureSensor;
   }

   public void setTemperatureSensor(final Integer temperatureSensor) {
      this.temperatureSensor = temperatureSensor;
   }

   public String getPowerState() {
      return this.powerState;
   }

   public void setPowerState(final String powerState) {
      this.powerState = powerState;
   }

   public InputSourceViewModel getInputSource() {
      return this.inputSource;
   }

   public void setInputSource(final InputSourceViewModel inputSourceViewModel) {
      this.inputSource = inputSourceViewModel;
   }

   public DeviceLimitsViewModel getDeviceLimits() {
      return this.deviceLimits;
   }

   public void setDeviceLimits(final DeviceLimitsViewModel deviceLimits) {
      this.deviceLimits = deviceLimits;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DeviceViewModel)) {
         return false;
      }

      DeviceViewModel that = (DeviceViewModel)object;
      return new EqualsBuilder()
         .append(this.getId(), that.getId())
         .append(this.getIpDestination(), that.getIpDestination())
         .append(this.getName(), that.getName())
         .append(this.getModelNumber(), that.getModelNumber())
         .append(this.getTemperatureSensor(), that.getTemperatureSensor())
         .append(this.getPowerState(), that.getPowerState())
         .append(this.getInputSource(), that.getInputSource())
         .append(this.getDeviceLimits(), that.getDeviceLimits())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.getId(),
         this.getIpDestination(),
         this.getName(),
         this.getModelNumber(),
         this.getTemperatureSensor(),
         this.getPowerState(),
         this.getInputSource(),
         this.getDeviceLimits()
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.getId())
         .append("ipDestination", this.getIpDestination())
         .append("name", this.getName())
         .append("modelNumber", this.getModelNumber())
         .append("temperatureSensor", this.getTemperatureSensor())
         .append("powerState", this.getPowerState())
         .append("inputSource", this.getInputSource())
         .append("deviceLimits", this.getDeviceLimits())
         .toString();
   }
}
