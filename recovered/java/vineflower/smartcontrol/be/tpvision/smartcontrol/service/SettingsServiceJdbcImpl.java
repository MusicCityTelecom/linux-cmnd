package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.messages.services.jdbc.settings.ConstructorMessages;
import be.tpvision.smartcontrol.repository.SettingsRepositoryJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SettingsServiceJdbcImpl implements SettingsServiceJdbc {
   private final SettingsRepositoryJdbc settingsRepositoryJdbc;

   @Autowired
   public SettingsServiceJdbcImpl(final SettingsRepositoryJdbc settingsRepositoryJdbc) {
      Assert.notNull(settingsRepositoryJdbc, ConstructorMessages.SETTINGS_REPOSITORY_JDBC_CAN_NOT_BE_NULL);
      this.settingsRepositoryJdbc = settingsRepositoryJdbc;
   }

   @Override
   public String getServerIp() {
      return this.settingsRepositoryJdbc.getServerIp();
   }

   @Override
   public FtpSettings getDefaultFtpSettings() {
      return this.settingsRepositoryJdbc.getDefaultFtpSettings();
   }
}
