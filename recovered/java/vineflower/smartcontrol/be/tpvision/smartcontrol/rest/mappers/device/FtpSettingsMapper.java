package be.tpvision.smartcontrol.rest.mappers.device;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.messages.mappers.device.ftp_settings.ToFtpSettingsMessages;
import be.tpvision.smartcontrol.messages.mappers.device.ftp_settings.ToFtpSettingsViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.device.FtpSettingsViewModel;
import org.springframework.util.Assert;

public class FtpSettingsMapper {
   private FtpSettingsMapper() {
   }

   public static FtpSettingsViewModel toFtpSettingsViewModel(final FtpSettings ftpSettings) {
      Assert.notNull(ftpSettings, ToFtpSettingsViewModelMessages.FTP_SETTINGS_CAN_NOT_BE_NULL);
      boolean useDefault = ftpSettings.isUseDefault();
      Integer port = ftpSettings.getPort();
      String username = ftpSettings.getUsername();
      String password = null;
      return new FtpSettingsViewModel(useDefault, port, username, password);
   }

   public static FtpSettings toFtpSettings(final FtpSettingsViewModel ftpSettingsViewModel) {
      Assert.notNull(ftpSettingsViewModel, ToFtpSettingsMessages.FTP_SETTINGS_VIEW_MODEL_CAN_NOT_BE_NULL);
      boolean useDefault = ftpSettingsViewModel.isUseDefault();
      Integer port = ftpSettingsViewModel.getPort();
      String username = ftpSettingsViewModel.getUsername();
      String password = ftpSettingsViewModel.getPassword();
      return new FtpSettings(useDefault, port, username, password);
   }
}
