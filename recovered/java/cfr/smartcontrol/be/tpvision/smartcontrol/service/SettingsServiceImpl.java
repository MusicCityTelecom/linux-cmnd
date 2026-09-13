/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.Settings;
import be.tpvision.smartcontrol.io.ip.NetworkAdapter;
import be.tpvision.smartcontrol.messages.services.settings.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.settings.SetSettingsMessages;
import be.tpvision.smartcontrol.repository.SettingsRepository;
import be.tpvision.smartcontrol.service.SettingsService;
import be.tpvision.smartcontrol.util.SecurityUtilities;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class SettingsServiceImpl
implements SettingsService {
    private static final Logger logger = LoggerFactory.getLogger(SettingsServiceImpl.class);
    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsServiceImpl(SettingsRepository settingsRepository) {
        Assert.notNull((Object)settingsRepository, ConstructorMessages.SETTINGS_REPOSITORY_CAN_NOT_BE_NULL);
        this.settingsRepository = settingsRepository;
    }

    @Override
    public Settings getSettings() {
        return this.settingsRepository.getSettings();
    }

    @Override
    @Transactional
    public void setSettings(Settings settings) {
        Assert.notNull((Object)settings, SetSettingsMessages.SETTINGS_CAN_NOT_BE_NULL);
        this.setSettings(settings, false);
    }

    @Override
    @Transactional
    public void setSettings(Settings settings, boolean encodeDefaultFtpSettingsPassword) {
        Assert.notNull((Object)settings, SetSettingsMessages.SETTINGS_CAN_NOT_BE_NULL);
        if (encodeDefaultFtpSettingsPassword) {
            FtpSettings defaultFtpSettings = settings.getDefaultFtpSettings();
            Assert.state(defaultFtpSettings != null, SetSettingsMessages.DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL);
            String password = defaultFtpSettings.getPassword();
            Assert.state(password != null, SetSettingsMessages.PASSWORD_CAN_NOT_BE_NULL);
            String encodedPassword = SecurityUtilities.encrypt(password);
            Assert.state(encodedPassword != null, SetSettingsMessages.ENCODED_PASSWORD_CAN_NOT_BE_NULL);
            defaultFtpSettings.setPassword(encodedPassword);
        }
        this.settingsRepository.merge(settings);
    }

    @Override
    public List<NetworkAdapter> getNetworkAdapters() {
        ArrayList<NetworkAdapter> networkAdapterList = new ArrayList<NetworkAdapter>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) continue;
                List<NetworkAdapter> networkInterfaceNetworkAdapters = this.getNetworkAdapters(networkInterface);
                networkAdapterList.addAll(networkInterfaceNetworkAdapters);
            }
        }
        catch (SocketException e) {
            String message = e.getMessage();
            logger.error(message, e);
        }
        return networkAdapterList;
    }

    private List<NetworkAdapter> getNetworkAdapters(NetworkInterface networkInterface) {
        InterfaceAddress interfaceAddress;
        InetAddress address;
        Assert.notNull((Object)networkInterface, "Network interface can not be null.");
        ArrayList<NetworkAdapter> networkAdapterList = new ArrayList<NetworkAdapter>();
        List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
        Iterator<InterfaceAddress> iterator = interfaceAddresses.iterator();
        while (iterator.hasNext() && !((address = (interfaceAddress = iterator.next()).getAddress()) instanceof Inet6Address)) {
            String displayName = networkInterface.getDisplayName();
            String hostAddress = address.getHostAddress();
            NetworkAdapter networkAdapter = new NetworkAdapter(displayName, hostAddress);
            networkAdapterList.add(networkAdapter);
        }
        return networkAdapterList;
    }

    @Override
    @Transactional
    public void autoUpdateServerIp() {
        Settings settings = this.settingsRepository.getSettings();
        String serverIp = settings.getServerIp();
        if (!serverIp.equals("0.0.0.0")) {
            return;
        }
        List<NetworkAdapter> networkAdapters = this.getNetworkAdapters();
        if (networkAdapters.isEmpty()) {
            return;
        }
        NetworkAdapter firstNetworkAdapter = networkAdapters.get(0);
        String ipv4Address = firstNetworkAdapter.getIpv4Address();
        settings.setServerIp(ipv4Address);
        this.settingsRepository.merge(settings);
    }
}

