/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.channel.v5;

import com.tpvision.smartinstall.xml.channel.v5.App;
import com.tpvision.smartinstall.xml.channel.v5.Application;
import com.tpvision.smartinstall.xml.channel.v5.ApplicationMap;
import com.tpvision.smartinstall.xml.channel.v5.Broadcast;
import com.tpvision.smartinstall.xml.channel.v5.Channel;
import com.tpvision.smartinstall.xml.channel.v5.ChannelMap;
import com.tpvision.smartinstall.xml.channel.v5.Media;
import com.tpvision.smartinstall.xml.channel.v5.Multicast;
import com.tpvision.smartinstall.xml.channel.v5.SchemaVersion;
import com.tpvision.smartinstall.xml.channel.v5.Setup;
import com.tpvision.smartinstall.xml.channel.v5.Source;
import com.tpvision.smartinstall.xml.channel.v5.TTV1;
import com.tpvision.smartinstall.xml.channel.v5.TTV10;
import com.tpvision.smartinstall.xml.channel.v5.TTV2;
import com.tpvision.smartinstall.xml.channel.v5.TTV3;
import com.tpvision.smartinstall.xml.channel.v5.TTV4;
import com.tpvision.smartinstall.xml.channel.v5.TTV5;
import com.tpvision.smartinstall.xml.channel.v5.TTV6;
import com.tpvision.smartinstall.xml.channel.v5.TTV7;
import com.tpvision.smartinstall.xml.channel.v5.TTV8;
import com.tpvision.smartinstall.xml.channel.v5.TTV9;
import com.tpvision.smartinstall.xml.channel.v5.ThemeTV;
import com.tpvision.smartinstall.xml.channel.v5.TvContents;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public App createApp() {
        return new App();
    }

    public ChannelMap createChannelMap() {
        return new ChannelMap();
    }

    public Channel createChannel() {
        return new Channel();
    }

    public Broadcast createBroadcast() {
        return new Broadcast();
    }

    public Media createMedia() {
        return new Media();
    }

    public Multicast createMulticast() {
        return new Multicast();
    }

    public Source createSource() {
        return new Source();
    }

    public Setup createSetup() {
        return new Setup();
    }

    public ApplicationMap createApplicationMap() {
        return new ApplicationMap();
    }

    public Application createApplication() {
        return new Application();
    }

    public SchemaVersion createSchemaVersion() {
        return new SchemaVersion();
    }

    public TTV9 createTTV9() {
        return new TTV9();
    }

    public TTV8 createTTV8() {
        return new TTV8();
    }

    public TTV7 createTTV7() {
        return new TTV7();
    }

    public TTV6 createTTV6() {
        return new TTV6();
    }

    public ThemeTV createThemeTV() {
        return new ThemeTV();
    }

    public TTV1 createTTV1() {
        return new TTV1();
    }

    public TTV2 createTTV2() {
        return new TTV2();
    }

    public TTV3 createTTV3() {
        return new TTV3();
    }

    public TTV4 createTTV4() {
        return new TTV4();
    }

    public TTV5 createTTV5() {
        return new TTV5();
    }

    public TTV10 createTTV10() {
        return new TTV10();
    }

    public TvContents createTvContents() {
        return new TvContents();
    }
}

