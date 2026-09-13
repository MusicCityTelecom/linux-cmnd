package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.FtpSettings;

public interface SettingsRepositoryJdbc {
   String getServerIp();

   FtpSettings getDefaultFtpSettings();
}
