package be.tpvision.smartcontrol.rest.view_models.device;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddOrEditDeviceViewModel {
   private Long id;
   private String modelNumber;
   private IpDestinationViewModel ipDestination;
   private String name;
   private FtpSettingsViewModel ftpSettings;
   private boolean contentRotated;

   protected AddOrEditDeviceViewModel() {
   }

   public AddOrEditDeviceViewModel(
      final Long id,
      final String modelNumber,
      final IpDestinationViewModel ipDestination,
      final String name,
      final FtpSettingsViewModel ftpSettings,
      final boolean contentRotated
   ) {
      this.id = id;
      this.modelNumber = modelNumber;
      this.ipDestination = ipDestination;
      this.name = name;
      this.ftpSettings = ftpSettings;
      this.contentRotated = contentRotated;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getModelNumber() {
      return this.modelNumber;
   }

   public void setModelNumber(String modelNumber) {
      this.modelNumber = modelNumber;
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

   public FtpSettingsViewModel getFtpSettings() {
      return this.ftpSettings;
   }

   public void setFtpSettings(final FtpSettingsViewModel ftpSettings) {
      this.ftpSettings = ftpSettings;
   }

   public boolean isContentRotated() {
      return this.contentRotated;
   }

   public void setContentRotated(final boolean contentRotated) {
      this.contentRotated = contentRotated;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof AddOrEditDeviceViewModel)) {
         return false;
      }

      AddOrEditDeviceViewModel that = (AddOrEditDeviceViewModel)object;
      return new EqualsBuilder()
         .append(this.getId(), that.getId())
         .append(this.getModelNumber(), that.getModelNumber())
         .append(this.getIpDestination(), that.getIpDestination())
         .append(this.getName(), that.getName())
         .append(this.getFtpSettings(), that.getFtpSettings())
         .append(this.isContentRotated(), that.isContentRotated())
         .isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getModelNumber(), this.getIpDestination(), this.getName(), this.getFtpSettings(), this.isContentRotated());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.getId())
         .append("modelNumber", this.getModelNumber())
         .append("ipDestination", this.getIpDestination())
         .append("name", this.getName())
         .append("ftpSettings", this.getFtpSettings())
         .append("contentRotated", this.isContentRotated())
         .toString();
   }
}
