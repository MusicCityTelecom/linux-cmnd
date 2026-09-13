/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.Settings;
import be.tpvision.smartcontrol.io.ip.NetworkAdapter;
import be.tpvision.smartcontrol.messages.controllers.settings.ConstructorMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.GetDefaultFtpSettingsMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.GetDefaultFtpSettingsPortMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.GetDefaultFtpSettingsUsernameMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.GetDetectDevicesTimeoutInMillisecondsMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.GetNetworkAdaptersMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.GetServerIpMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.GetSettingsMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.SetDefaultFtpSettingsPasswordMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.SetDefaultFtpSettingsPortMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.SetDefaultFtpSettingsUsernameMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.SetDetectDevicesTimeoutInMillisecondsMessages;
import be.tpvision.smartcontrol.messages.controllers.settings.SetServerIpMessages;
import be.tpvision.smartcontrol.rest.ResponseWrapper;
import be.tpvision.smartcontrol.rest.mappers.NetworkAdapterMapper;
import be.tpvision.smartcontrol.rest.mappers.SettingsMapper;
import be.tpvision.smartcontrol.rest.view_models.DefaultFtpSettingsPasswordViewModel;
import be.tpvision.smartcontrol.rest.view_models.NetworkAdapterViewModel;
import be.tpvision.smartcontrol.rest.view_models.SettingsViewModel;
import be.tpvision.smartcontrol.service.SettingsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/settings"})
public class SettingsController {
    private final SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        Assert.notNull((Object)settingsService, ConstructorMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        this.settingsService = settingsService;
    }

    @GetMapping
    public SettingsViewModel getSettings() {
        Assert.state(this.settingsService != null, GetSettingsMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        Settings settings = this.settingsService.getSettings();
        Assert.state(settings != null, GetSettingsMessages.SETTINGS_CAN_NOT_BE_NULL);
        return SettingsMapper.toSettingsViewModel(settings);
    }

    @GetMapping(value={"/serverIp"})
    public ResponseWrapper<String> getServerIp() {
        Assert.state(this.settingsService != null, GetServerIpMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        Settings settings = this.settingsService.getSettings();
        Assert.state(settings != null, GetServerIpMessages.SETTINGS_CAN_NOT_BE_NULL);
        String serverIp = settings.getServerIp();
        Assert.state(serverIp != null, GetServerIpMessages.SERVER_IP_CAN_NOT_BE_NULL);
        Assert.state(!serverIp.isEmpty(), GetServerIpMessages.SERVER_IP_CAN_NOT_BE_EMPTY);
        return new ResponseWrapper<String>(serverIp);
    }

    @PutMapping(value={"/serverIp/{serverIp}"})
    public void setServerIp(@PathVariable(value="serverIp") String serverIp) {
        Assert.state(this.settingsService != null, SetServerIpMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)serverIp, SetServerIpMessages.SERVER_IP_CAN_NOT_BE_NULL);
        Assert.isTrue(!serverIp.isEmpty(), SetServerIpMessages.SERVER_IP_CAN_NOT_BE_EMPTY);
        Settings settings = this.settingsService.getSettings();
        Assert.state(settings != null, SetServerIpMessages.SETTINGS_CAN_NOT_BE_NULL);
        settings.setServerIp(serverIp);
        this.settingsService.setSettings(settings);
    }

    private FtpSettings getDefaultFtpSettings() {
        Assert.state(this.settingsService != null, GetDefaultFtpSettingsMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        Settings settings = this.settingsService.getSettings();
        Assert.state(settings != null, GetDefaultFtpSettingsMessages.SETTINGS_CAN_NOT_BE_NULL);
        FtpSettings defaultFtpSettings = settings.getDefaultFtpSettings();
        Assert.state(defaultFtpSettings != null, GetDefaultFtpSettingsMessages.DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL);
        return defaultFtpSettings;
    }

    @GetMapping(value={"/defaultFtpSettings/port"})
    public ResponseWrapper<Integer> getDefaultFtpSettingsPort() {
        FtpSettings defaultFtpSettings = this.getDefaultFtpSettings();
        Assert.state(defaultFtpSettings != null, GetDefaultFtpSettingsPortMessages.DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL);
        Integer port = defaultFtpSettings.getPort();
        Assert.state(port != null, GetDefaultFtpSettingsPortMessages.PORT_CAN_NOT_BE_NULL);
        return new ResponseWrapper<Integer>(port);
    }

    @PutMapping(value={"/defaultFtpSettings/port/{port}"})
    public void setDefaultFtpSettingsPort(@PathVariable(value="port") int port) {
        Assert.state(this.settingsService != null, SetDefaultFtpSettingsPortMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        Settings settings = this.settingsService.getSettings();
        Assert.state(settings != null, SetDefaultFtpSettingsPortMessages.SETTINGS_CAN_NOT_BE_NULL);
        FtpSettings defaultFtpSettings = settings.getDefaultFtpSettings();
        Assert.state(defaultFtpSettings != null, SetDefaultFtpSettingsPortMessages.DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL);
        defaultFtpSettings.setPort(port);
        this.settingsService.setSettings(settings);
    }

    @GetMapping(value={"/defaultFtpSettings/username"})
    public ResponseWrapper<String> getDefaultFtpSettingsUsername() {
        Assert.state(this.settingsService != null, GetDefaultFtpSettingsUsernameMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        FtpSettings defaultFtpSettings = this.getDefaultFtpSettings();
        Assert.state(defaultFtpSettings != null, GetDefaultFtpSettingsUsernameMessages.DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL);
        String username = defaultFtpSettings.getUsername();
        Assert.state(username != null, GetDefaultFtpSettingsUsernameMessages.USERNAME_CAN_NOT_BE_NULL);
        return new ResponseWrapper<String>(username);
    }

    @PutMapping(value={"/defaultFtpSettings/username/{username}"})
    public void setDefaultFtpSettingsUsername(@PathVariable(value="username") String username) {
        Assert.state(this.settingsService != null, SetDefaultFtpSettingsUsernameMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)username, SetDefaultFtpSettingsUsernameMessages.USERNAME_CAN_NOT_BE_NULL);
        Settings settings = this.settingsService.getSettings();
        Assert.state(settings != null, SetDefaultFtpSettingsUsernameMessages.SETTINGS_CAN_NOT_BE_NULL);
        FtpSettings defaultFtpSettings = settings.getDefaultFtpSettings();
        Assert.state(defaultFtpSettings != null, SetDefaultFtpSettingsUsernameMessages.DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL);
        defaultFtpSettings.setUsername(username);
        this.settingsService.setSettings(settings);
    }

    @PutMapping(value={"/defaultFtpSettings/password"})
    public void setDefaultFtpSettingsPassword(@RequestBody DefaultFtpSettingsPasswordViewModel defaultFtpSettingsPasswordViewModel) {
        Assert.state(this.settingsService != null, SetDefaultFtpSettingsPasswordMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)defaultFtpSettingsPasswordViewModel, SetDefaultFtpSettingsPasswordMessages.DEFAULT_FTP_SETTINGS_PASSWORD_VIEW_MODEL);
        String password = defaultFtpSettingsPasswordViewModel.getPassword();
        Assert.state(password != null, SetDefaultFtpSettingsPasswordMessages.PASSWORD_CAN_NOT_BE_NULL);
        Settings settings = this.settingsService.getSettings();
        Assert.state(settings != null, SetDefaultFtpSettingsPasswordMessages.SETTINGS_CAN_NOT_BE_NULL);
        FtpSettings defaultFtpSettings = settings.getDefaultFtpSettings();
        Assert.state(defaultFtpSettings != null, SetDefaultFtpSettingsPasswordMessages.DEFAULT_FTP_SETTINGS_CAN_NOT_BE_NULL);
        defaultFtpSettings.setPassword(password);
        this.settingsService.setSettings(settings, true);
    }

    @GetMapping(value={"/networkAdapters"})
    public List<NetworkAdapterViewModel> getNetworkAdapters() {
        Assert.state(this.settingsService != null, GetNetworkAdaptersMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        List<NetworkAdapter> networkAdapters = this.settingsService.getNetworkAdapters();
        Assert.state(networkAdapters != null, GetNetworkAdaptersMessages.NETWORK_ADAPTERS_CAN_NOT_BE_NULL);
        return NetworkAdapterMapper.toNetworkAdapterViewModelList(networkAdapters);
    }

    @GetMapping(value={"/detectDevicesTimeoutInMilliseconds"})
    public ResponseWrapper<Integer> getDetectDevicesTimeoutInMilliseconds() {
        Assert.state(this.settingsService != null, GetDetectDevicesTimeoutInMillisecondsMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        Settings settings = this.settingsService.getSettings();
        Assert.state(settings != null, GetDetectDevicesTimeoutInMillisecondsMessages.SETTINGS_CAN_NOT_BE_NULL);
        int detectDevicesTimeoutInMilliseconds = settings.getDetectDevicesTimeoutInMilliseconds();
        return new ResponseWrapper<Integer>(detectDevicesTimeoutInMilliseconds);
    }

    @PutMapping(value={"/detectDevicesTimeoutInMilliseconds/{detectDevicesTimeoutInMilliseconds}"})
    public void setDetectDevicesTimeoutInMilliseconds(@PathVariable(value="detectDevicesTimeoutInMilliseconds") int detectDevicesTimeoutInMilliseconds) {
        Assert.state(this.settingsService != null, SetDetectDevicesTimeoutInMillisecondsMessages.SETTINGS_SERVICE_CAN_NOT_BE_NULL);
        Settings settings = this.settingsService.getSettings();
        Assert.state(settings != null, SetDetectDevicesTimeoutInMillisecondsMessages.SETTINGS_CAN_NOT_BE_NULL);
        settings.setDetectDevicesTimeoutInMilliseconds(detectDevicesTimeoutInMilliseconds);
        this.settingsService.setSettings(settings);
    }
}

