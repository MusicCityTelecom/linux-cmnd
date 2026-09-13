package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.FtpSettings;

public interface SettingsServiceJdbc {
   String getServerIp();

   FtpSettings getDefaultFtpSettings();
}
