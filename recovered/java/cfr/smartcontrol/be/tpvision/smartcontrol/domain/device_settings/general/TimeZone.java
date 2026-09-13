/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.MixedEnumDeviceSetting;

public enum TimeZone implements MixedEnumDeviceSetting
{
    MIDWAY_ISLAND(1, "Midway Island"),
    HAWAII(2, "Hawaii"),
    ALASKA(3, "Alaska"),
    PACIFIC_TIME(4, "Pacific Time"),
    TIJUANA(5, "Tijuana"),
    ARIZONA(6, "Arizona"),
    CHIHUAHUA(7, "Chihuahua"),
    MOUNTAIN_TIME(8, "Mountain Time"),
    CENTRAL_AMERICA(9, "Central America"),
    CENTRAL_TIME(10, "Central Time"),
    MEXICO_CITY(11, "Mexico City"),
    SASKATCHEWAN(12, "Saskatchewan"),
    BOGOTA(13, "Bogota"),
    EASTERN_TIME(14, "Eastern Time"),
    VENEZUELA(15, "Venezuela"),
    ATLANTIC_TIME_BARBADOS(16, "Atlantic Time (Barbados)"),
    ATLANTIC_TIME_CANADA(17, "Atlantic Time (Canada)"),
    MANAUS(18, "Manaus"),
    SANTIAGO(19, "Santiago"),
    NEWFOUNDLAND(20, "Newfoundland"),
    BRASILIA(21, "Brasilia"),
    BUENOS_AIRES(22, "Buenos Aires"),
    GREENLAND(23, "Greenland"),
    MONTEVIDEO(24, "Montevideo"),
    MID_ATLANTIC(25, "Mid-Atlantic"),
    AZORES(26, "Azores"),
    CAPE_VERDE_ISLANDS(27, "Cape Verde Islands"),
    CASABLANCA(28, "Casablanca"),
    LONDON_DUBLIN(29, "London, Dublin"),
    AMSTERDAM_BERLIN(30, "Amsterdam, Berlin"),
    BELGRADE(31, "Belgrade"),
    BRUSSELS(32, "Brussels"),
    SARAJEVO(33, "Sarajevo"),
    WINDHOEK(34, "Windhoek"),
    W_AFRICA_TIME(35, "W. Africa Time"),
    AMMAN_JORDAN(36, "Amman, Jordan"),
    ATHENS_ISTANBUL(37, "Athens, Istanbul"),
    BEIRUT_LEBANON(38, "Beirut, Lebanon"),
    CAIRO(39, "Cairo"),
    HELSINKI(40, "Helsinki"),
    JERUSALEM(41, "Jerusalem"),
    HARARE(42, "Harare"),
    MINSK(43, "Minsk"),
    BAGHDAD(44, "Baghdad"),
    MOSCOW(45, "Moscow"),
    KUWAIT(46, "Kuwait"),
    NAIROBI(47, "Nairobi"),
    TEHRAN(48, "Tehran"),
    BAKU(49, "Baku"),
    TBILISI(50, "Tbilisi"),
    YEREVAN(51, "Yerevan"),
    DUBAI(52, "Dubai"),
    KABUL(53, "Kabul"),
    ISLAMABAD_KARACHI(54, "Islamabad, Karachi"),
    URAL_SK(55, "Ural'sk"),
    YEKATERINBURG(56, "Yekaterinburg"),
    KOLKATA(57, "Kolkata"),
    SRI_LANKA(58, "Sri Lanka"),
    KATHMANDU(59, "Kathmandu"),
    ASTANA(60, "Astana"),
    YANGON(61, "Yangon"),
    KRASNOYARSK(62, "Krasnoyarsk"),
    BANGKOK(63, "Bangkok"),
    JAKARTA(64, "Jakarta"),
    BEIJING(65, "Beijing"),
    HONG_KONG(66, "Hong Kong"),
    IRKUTSK(67, "Irkutsk"),
    KUALA_LUMPUR(68, "Kuala Lumpur"),
    PERTH(69, "Perth"),
    TAIPEI(70, "Taipei"),
    SEOUL(71, "Seoul"),
    TOKYO_OSAKA(72, "Tokyo, Osaka"),
    YAKUTSK(73, "Yakutsk"),
    ADELAIDE(74, "Adelaide"),
    DARWIN(75, "Darwin"),
    BRISBANE(76, "Brisbane"),
    HOBART(77, "Hobart"),
    SYDNEY_CANBERRA(78, "Sydney, Canberra"),
    VLADIVOSTOK(79, "Vladivostok"),
    GUAM(80, "Guam"),
    MAGADAN(81, "Magadan"),
    MARSHALL_ISLANDS(82, "Marshall Islands"),
    AUCKLAND(83, "Auckland"),
    FIJI(84, "Fiji"),
    TONGA(85, "Tonga");

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

    private TimeZone(int index, String name) {
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

