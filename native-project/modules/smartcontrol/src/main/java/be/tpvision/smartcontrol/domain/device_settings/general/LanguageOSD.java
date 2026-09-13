package be.tpvision.smartcontrol.domain.device_settings.general;

import be.tpvision.smartcontrol.domain.device_settings.MixedEnumDeviceSetting;

public enum LanguageOSD implements MixedEnumDeviceSetting {
   ENGLISH(1, "English"),
   SPANISH(2, "Español"),
   FRENCH(3, "Français"),
   ITALIAN(4, "Italiano"),
   LATVIAN(5, "Latviešu"),
   LITHUANIAN(6, "Lietuvių"),
   DUTCH(7, "Nederlands"),
   NORWEGIAN(8, "Norsk bokmål"),
   POLSKI(9, "Polski"),
   PORTUGUESE(10, "Português"),
   FINNISH(11, "Suomi"),
   SWEDISH(12, "Svenska"),
   TURKISH(13, "Türkçe"),
   RUSSIAN(14, "Pyccкий"),
   ARABIC(15, "العربية"),
   SIMPLIFIED_CHINESE(16, "中文(简体)"),
   TRADITIONAL_CHINESE(17, "中文(繁體)"),
   JAPANESE(18, "日本語"),
   CZECH(19, "Čeština"),
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

   LanguageOSD(int index, String name) {
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
