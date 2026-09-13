/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.MixedEnumDeviceSetting;

public enum LanguageOSD implements MixedEnumDeviceSetting
{
    ENGLISH(1, "English"),
    SPANISH(2, "Espa\u00f1ol"),
    FRENCH(3, "Fran\u00e7ais"),
    ITALIAN(4, "Italiano"),
    LATVIAN(5, "Latvie\u0161u"),
    LITHUANIAN(6, "Lietuvi\u0173"),
    DUTCH(7, "Nederlands"),
    NORWEGIAN(8, "Norsk bokm\u00e5l"),
    POLSKI(9, "Polski"),
    PORTUGUESE(10, "Portugu\u00eas"),
    FINNISH(11, "Suomi"),
    SWEDISH(12, "Svenska"),
    TURKISH(13, "T\u00fcrk\u00e7e"),
    RUSSIAN(14, "Pycc\u043a\u0438\u0439"),
    ARABIC(15, "\u0627\u0644\u0639\u0631\u0628\u064a\u0629"),
    SIMPLIFIED_CHINESE(16, "\u4e2d\u6587(\u7b80\u4f53)"),
    TRADITIONAL_CHINESE(17, "\u4e2d\u6587(\u7e41\u9ad4)"),
    JAPANESE(18, "\u65e5\u672c\u8a9e"),
    CZECH(19, "\u010ce\u0161tina"),
    DANISH(20, "Dansk"),
    GERMAN(21, "Deutsch"),
    ESTONIAN(22, "Eesti");

    private int index;
    private String name;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private LanguageOSD(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public String getOptionText() {
        return this.getName();
    }

    @Override
    public String getOptionValue() {
        return this.name();
    }
}

