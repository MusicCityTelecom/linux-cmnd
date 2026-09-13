/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml;

import com.tpvision.smartinstall.xml.Channel;
import com.tpvision.smartinstall.xml.Config;
import com.tpvision.smartinstall.xml.Crc;
import com.tpvision.smartinstall.xml.CrcFiles;
import com.tpvision.smartinstall.xml.File;
import com.tpvision.smartinstall.xml.Platform;
import com.tpvision.smartinstall.xml.Setting;
import com.tpvision.smartinstall.xml.SettingFiles;
import com.tpvision.smartinstall.xml.Settings;
import com.tpvision.smartinstall.xml.UnchangedFiles;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public Setting createSetting() {
        return new Setting();
    }

    public Platform createPlatform() {
        return new Platform();
    }

    public UnchangedFiles createUnchangedFiles() {
        return new UnchangedFiles();
    }

    public File createFile() {
        return new File();
    }

    public Crc createCrc() {
        return new Crc();
    }

    public SettingFiles createSettingFiles() {
        return new SettingFiles();
    }

    public CrcFiles createCrcFiles() {
        return new CrcFiles();
    }

    public Settings createSettings() {
        return new Settings();
    }

    public Channel createChannel() {
        return new Channel();
    }

    public Config createConfig() {
        return new Config();
    }
}

