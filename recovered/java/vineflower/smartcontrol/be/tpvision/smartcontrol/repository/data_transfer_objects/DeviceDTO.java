package be.tpvision.smartcontrol.repository.data_transfer_objects;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeviceDTO {
   private Long id;
   private IpDestination address;
   private StringWrapper serialCode;
   private FtpSettings ftpSettings;

   public Long getId() {
      return this.id;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public IpDestination getAddress() {
      return this.address;
   }

   public void setAddress(final IpDestination address) {
      this.address = address;
   }

   public StringWrapper getSerialCode() {
      return this.serialCode;
   }

   public void setSerialCode(final StringWrapper serialCode) {
      this.serialCode = serialCode;
   }

   public FtpSettings getFtpSettings() {
      return this.ftpSettings;
   }

   public void setFtpSettings(final FtpSettings ftpSettings) {
      this.ftpSettings = ftpSettings;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof DeviceDTO)) {
         return false;
      }

      DeviceDTO that = (DeviceDTO)object;
      return Objects.equals(this.getAddress(), that.getAddress());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getAddress());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this)
         .append("id", this.getId())
         .append("address", this.getAddress())
         .append("serialCode", this.getSerialCode())
         .append("ftpSettings", this.getFtpSettings())
         .toString();
   }
}
