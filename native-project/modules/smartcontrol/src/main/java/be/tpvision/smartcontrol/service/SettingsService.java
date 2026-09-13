package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Settings;
import be.tpvision.smartcontrol.io.ip.NetworkAdapter;
import java.util.List;

public interface SettingsService {
   Settings getSettings();

   void setSettings(Settings settings);

   void setSettings(Settings settings, boolean encodeDefaultFtpSettingsPassword);

   List<NetworkAdapter> getNetworkAdapters();

   void autoUpdateServerIp();
}
