package be.tpvision.smartcontrol.rest.view_models.device;

import be.tpvision.smartcontrol.rest.view_models.miscellaneous.MiscellaneousViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeviceInfoViewModel {
   private Long id;
   private String name;
   private String sicpVersion;
   private String platformLabel;
   private String platformVersion;
   private String modelNumber;
   private String firmwareVersion;
   private String buildDate;
   private String firmwareVersionAndroid;
   private String serialCode;
   private Integer temperatureSensor;
   private MiscellaneousViewModel miscellaneous;
   private String contentTitle;

   public Long getId() {
      return this.id;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public String getSicpVersion() {
      return this.sicpVersion;
   }

   public void setSicpVersion(final String sicpVersion) {
      this.sicpVersion = sicpVersion;
   }

   public String getPlatformLabel() {
      return this.platformLabel;
   }

   public void setPlatformLabel(final String platformLabel) {
      this.platformLabel = platformLabel;
   }

   public String getPlatformVersion() {
      return this.platformVersion;
   }

   public void setPlatformVersion(final String platformVersion) {
      this.platformVersion = platformVersion;
   }

   public String getModelNumber() {
      return this.modelNumber;
   }

   public void setModelNumber(final String modelNumber) {
      this.modelNumber = modelNumber;
   }

   public String getFirmwareVersion() {
      return this.firmwareVersion;
   }

   public void setFirmwareVersion(final String firmwareVersion) {
      this.firmwareVersion = firmwareVersion;
   }

   public String getBuildDate() {
      return this.buildDate;
   }

   public void setBuildDate(final String buildDate) {
      this.buildDate = buildDate;
   }

   public String getFirmwareVersionAndroid() {
      return this.firmwareVersionAndroid;
   }

   public void setFirmwareVersionAndroid(String firmwareVersionAndroid) {
      this.firmwareVersionAndroid = firmwareVersionAndroid;
   }

   public String getSerialCode() {
      return this.serialCode;
   }

   public void setSerialCode(final String serialCode) {
      this.serialCode = serialCode;
   }

   public Integer getTemperatureSensor() {
      return this.temperatureSensor;
   }

   public void setTemperatureSensor(final Integer temperatureSensor) {
      this.temperatureSensor = temperatureSensor;
   }

   public MiscellaneousViewModel getMiscellaneous() {
      return this.miscellaneous;
   }

   public void setMiscellaneous(final MiscellaneousViewModel miscellaneous) {
      this.miscellaneous = miscellaneous;
   }

   public String getContentTitle() {
      return this.contentTitle;
   }

   public void setContentTitle(final String contentTitle) {
      this.contentTitle = contentTitle;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DeviceInfoViewModel)) {
         return false;
      }

      DeviceInfoViewModel that = (DeviceInfoViewModel)object;
      return new EqualsBuilder()
         .append(this.getId(), that.getId())
         .append(this.getName(), that.getName())
         .append(this.getSicpVersion(), that.getSicpVersion())
         .append(this.getPlatformLabel(), that.getPlatformLabel())
         .append(this.getPlatformVersion(), that.getPlatformVersion())
         .append(this.getModelNumber(), that.getModelNumber())
         .append(this.getFirmwareVersion(), that.getFirmwareVersion())
         .append(this.getBuildDate(), that.getBuildDate())
         .append(this.getFirmwareVersionAndroid(), that.getFirmwareVersionAndroid())
         .append(this.getSerialCode(), that.getSerialCode())
         .append(this.getTemperatureSensor(), that.getTemperatureSensor())
         .append(this.getMiscellaneous(), that.getMiscellaneous())
         .append(this.getContentTitle(), that.getContentTitle())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.getId(),
         this.getName(),
         this.getSicpVersion(),
         this.getPlatformLabel(),
         this.getPlatformVersion(),
         this.getModelNumber(),
         this.getFirmwareVersion(),
         this.getBuildDate(),
         this.getFirmwareVersionAndroid(),
         this.getSerialCode(),
         this.getTemperatureSensor(),
         this.getMiscellaneous(),
         this.getContentTitle()
      );
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.getId())
         .append("name", this.getName())
         .append("sicpVersion", this.getSicpVersion())
         .append("platformLabel", this.getPlatformLabel())
         .append("platformVersion", this.getPlatformVersion())
         .append("modelNumber", this.getModelNumber())
         .append("firmwareVersion", this.getFirmwareVersion())
         .append("buildDate", this.getBuildDate())
         .append("serialCode", this.getSerialCode())
         .append("temperatureSensor", this.getTemperatureSensor())
         .append("miscellaneous", this.getMiscellaneous())
         .append("contentTitle", this.getContentTitle())
         .toString();
   }
}
