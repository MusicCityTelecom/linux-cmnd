package be.tpvision.smartcontrol.rest.mappers;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.Settings;
import be.tpvision.smartcontrol.messages.mappers.settings.ToSettingsViewModelMessages;
import be.tpvision.smartcontrol.rest.mappers.device.FtpSettingsMapper;
import be.tpvision.smartcontrol.rest.view_models.SettingsViewModel;
import be.tpvision.smartcontrol.rest.view_models.device.FtpSettingsViewModel;
import org.springframework.util.Assert;

public class SettingsMapper {
   private SettingsMapper() {
   }

   public static SettingsViewModel toSettingsViewModel(final Settings settings) {
      Assert.notNull(settings, ToSettingsViewModelMessages.SETTINGS_CAN_NOT_BE_NULL);
      SettingsViewModel settingsViewModel = new SettingsViewModel();
      String serverIp = settings.getServerIp();
      Assert.state(serverIp != null, ToSettingsViewModelMessages.SERVER_IP_CAN_NOT_BE_NULL);
      Assert.state(!serverIp.isEmpty(), ToSettingsViewModelMessages.SERVER_IP_CAN_NOT_BE_EMPTY);
      settingsViewModel.setServerIp(serverIp);
      FtpSettings defaultFtpSettings = settings.getDefaultFtpSettings();
      Assert.state(defaultFtpSettings != null, ToSettingsViewModelMessages.DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL);
      FtpSettingsViewModel defaultFtpSettingsViewModel = FtpSettingsMapper.toFtpSettingsViewModel(defaultFtpSettings);
      Assert.state(defaultFtpSettingsViewModel != null, ToSettingsViewModelMessages.DEFAULT_FTP_SETTINGS_VIEW_MODEL_CAN_NOT_BE_NULL);
      settingsViewModel.setDefaultFtpSettings(defaultFtpSettingsViewModel);
      return settingsViewModel;
   }
}
