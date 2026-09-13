/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class UIJsonHelper {
    private final JSONObject uijson;
    private final String platform;
    private String switchFlagOn;
    private String switchFlagOff;
    private String red;
    private String green;
    private String blue;
    private String fieldPath;
    private static final Map<String, Map<String, String>> platformFieldMap = new HashMap<String, Map<String, String>>();
    public Map<String, String> settingValueMap = new HashMap<String, String>();

    public UIJsonHelper(JSONObject uijson, String platform) {
        JSONObject jSONObject = this.uijson = uijson != null ? uijson : new JSONObject();
        if ("TPM242HWW".equalsIgnoreCase(platform)) {
            this.switchFlagOn = "On";
            this.switchFlagOff = "Off";
            this.platform = platform;
        } else {
            this.switchFlagOn = "true";
            this.switchFlagOff = "false";
            this.platform = "General";
        }
        this.initialSettingValue();
    }

    private void initialSettingValue() {
        if (this.uijson == null) {
            return;
        }
        for (String key : this.uijson.keySet()) {
            String value = this.uijson.optString(key, null);
            if (value == null) continue;
            this.settingValueMap.put(key, value);
        }
    }

    public boolean isSupportFunction(String fieldName) {
        return platformFieldMap.get(this.platform).containsKey(fieldName);
    }

    public void updateSettingValue(String fieldName, String value) {
        String lowerField;
        if (fieldName == null) {
            return;
        }
        switch (lowerField = fieldName.toLowerCase()) {
            case "highlightedtextcolor": {
                this.setHighlightedTextColor(value);
                return;
            }
            case "nonhighlightedtextcolor": {
                this.setNonHighlightedTextColor(value);
                return;
            }
            case "backgroundcolor": {
                this.setBackgroundColor(value);
                return;
            }
            case "mainbackgroundcolorfilter": {
                this.setMainBackgroundColorFilter(value);
                return;
            }
        }
        this.fieldPath = this.getFieldPath(fieldName);
        if (!this.settingValueMap.containsKey(this.fieldPath)) {
            return;
        }
        if (value == null) {
            value = "backgroundaudio".equals(lowerField) ? "None" : this.switchFlagOff;
        } else if ("on".equalsIgnoreCase(value)) {
            value = "backgroundaudio".equals(lowerField) ? "Custom" : this.switchFlagOn;
        }
        this.settingValueMap.put(this.fieldPath, value);
    }

    public String getHighlightedTextColor() {
        this.red = this.getColorComponent("HighlightedTextColor.Red");
        this.green = this.getColorComponent("HighlightedTextColor.Green");
        this.blue = this.getColorComponent("HighlightedTextColor.Blue");
        return this.red + this.green + this.blue;
    }

    public void setHighlightedTextColor(String colorValue) {
        this.parseRgbHexToDecimal(colorValue);
        this.fieldPath = this.getFieldPath("HighlightedTextColor.Red");
        this.settingValueMap.put(this.fieldPath, this.red);
        this.fieldPath = this.getFieldPath("HighlightedTextColor.Green");
        this.settingValueMap.put(this.fieldPath, this.green);
        this.fieldPath = this.getFieldPath("HighlightedTextColor.Blue");
        this.settingValueMap.put(this.fieldPath, this.blue);
    }

    public String getNonHighlightedTextColor() {
        this.red = this.getColorComponent("NonHighlightedTextColor.Red");
        this.green = this.getColorComponent("NonHighlightedTextColor.Green");
        this.blue = this.getColorComponent("NonHighlightedTextColor.Blue");
        return this.red + this.green + this.blue;
    }

    public void setNonHighlightedTextColor(String colorValue) {
        this.parseRgbHexToDecimal(colorValue);
        this.fieldPath = this.getFieldPath("NonHighlightedTextColor.Red");
        this.settingValueMap.put(this.fieldPath, this.red);
        this.fieldPath = this.getFieldPath("NonHighlightedTextColor.Green");
        this.settingValueMap.put(this.fieldPath, this.green);
        this.fieldPath = this.getFieldPath("NonHighlightedTextColor.Blue");
        this.settingValueMap.put(this.fieldPath, this.blue);
    }

    public String getBackgroundColor() {
        this.red = this.getColorComponent("BackgroundColor.Red");
        this.green = this.getColorComponent("BackgroundColor.Green");
        this.blue = this.getColorComponent("BackgroundColor.Blue");
        return this.red + this.green + this.blue;
    }

    public void setBackgroundColor(String colorValue) {
        this.parseRgbHexToDecimal(colorValue);
        this.fieldPath = this.getFieldPath("BackgroundColor.Red");
        this.settingValueMap.put(this.fieldPath, this.red);
        this.fieldPath = this.getFieldPath("BackgroundColor.Green");
        this.settingValueMap.put(this.fieldPath, this.green);
        this.fieldPath = this.getFieldPath("BackgroundColor.Blue");
        this.settingValueMap.put(this.fieldPath, this.blue);
    }

    public String getMainBackgroundColorFilter() {
        this.red = this.getColorComponent("MainBackgroundColorFilter.Red");
        this.green = this.getColorComponent("MainBackgroundColorFilter.Green");
        this.blue = this.getColorComponent("MainBackgroundColorFilter.Blue");
        return this.red + this.green + this.blue;
    }

    public void setMainBackgroundColorFilter(String colorValue) {
        this.parseRgbHexToDecimal(colorValue);
        this.fieldPath = this.getFieldPath("MainBackgroundColorFilter.Red");
        this.settingValueMap.put(this.fieldPath, this.red);
        this.fieldPath = this.getFieldPath("MainBackgroundColorFilter.Green");
        this.settingValueMap.put(this.fieldPath, this.green);
        this.fieldPath = this.getFieldPath("MainBackgroundColorFilter.Blue");
        this.settingValueMap.put(this.fieldPath, this.blue);
    }

    public String getColorComponent(String fieldName) {
        this.fieldPath = this.getFieldPath(fieldName);
        String value = this.getNestedValue();
        return this.toHex(value);
    }

    public String getColorComponentDec(String fieldName) {
        this.fieldPath = this.getFieldPath(fieldName);
        String value = this.getNestedValue();
        return value;
    }

    public boolean getFieldStatus(String fieldName) {
        this.fieldPath = this.getFieldPath(fieldName);
        String value = this.getNestedValue();
        if (value == null) {
            return false;
        }
        if ("BackgroundAudio".equalsIgnoreCase(fieldName)) {
            return value.equalsIgnoreCase("Custom");
        }
        return value.equalsIgnoreCase(this.switchFlagOn);
    }

    private String getFieldPath(String fieldName) {
        Map<String, String> fields = platformFieldMap.get(this.platform);
        if (fields == null) {
            return null;
        }
        return fields.get(fieldName);
    }

    private String getNestedValue() {
        if (this.uijson == null || this.fieldPath == null) {
            return null;
        }
        Object value = this.uijson.opt(this.fieldPath);
        return value != null ? value.toString() : null;
    }

    private String toHex(String decimalStr) {
        if (decimalStr == null) {
            return "00";
        }
        try {
            int value = Integer.parseInt(decimalStr);
            return String.format("%02x", Math.min(255, Math.max(0, value)));
        }
        catch (NumberFormatException e) {
            return "00";
        }
    }

    private void parseRgbHexToDecimal(String hexRgb) {
        this.blue = "0";
        this.green = "0";
        this.red = "0";
        if (hexRgb != null && hexRgb.length() == 6) {
            try {
                this.red = String.valueOf(Integer.parseInt(hexRgb.substring(0, 2), 16));
                this.green = String.valueOf(Integer.parseInt(hexRgb.substring(2, 4), 16));
                this.blue = String.valueOf(Integer.parseInt(hexRgb.substring(4, 6), 16));
            }
            catch (NumberFormatException e) {
                this.blue = "0";
                this.green = "0";
                this.red = "0";
            }
        }
    }

    static {
        HashMap<String, String> platformGeneralFields = new HashMap<String, String>();
        platformGeneralFields.put("HighlightedTextColor.Red", "UserInterfaceSetting.SidePanel.HighlightedTextColor.Red");
        platformGeneralFields.put("HighlightedTextColor.Green", "UserInterfaceSetting.SidePanel.HighlightedTextColor.Green");
        platformGeneralFields.put("HighlightedTextColor.Blue", "UserInterfaceSetting.SidePanel.HighlightedTextColor.Blue");
        platformGeneralFields.put("HighlightedTextColor.Alpha", "UserInterfaceSetting.SidePanel.HighlightedTextColor.Alpha");
        platformGeneralFields.put("NonHighlightedTextColor.Red", "UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Red");
        platformGeneralFields.put("NonHighlightedTextColor.Green", "UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Green");
        platformGeneralFields.put("NonHighlightedTextColor.Blue", "UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Blue");
        platformGeneralFields.put("NonHighlightedTextColor.Alpha", "UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Alpha");
        platformGeneralFields.put("BackgroundColor.Red", "UserInterfaceSetting.SidePanel.BackgroundColor.Red");
        platformGeneralFields.put("BackgroundColor.Green", "UserInterfaceSetting.SidePanel.BackgroundColor.Green");
        platformGeneralFields.put("BackgroundColor.Blue", "UserInterfaceSetting.SidePanel.BackgroundColor.Blue");
        platformGeneralFields.put("BackgroundColor.Alpha", "UserInterfaceSetting.SidePanel.BackgroundColor.Alpha");
        platformGeneralFields.put("MainBackgroundColorFilter.Red", "UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Red");
        platformGeneralFields.put("MainBackgroundColorFilter.Green", "UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Green");
        platformGeneralFields.put("MainBackgroundColorFilter.Blue", "UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Blue");
        platformGeneralFields.put("MainBackgroundColorFilter.Alpha", "UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Alpha");
        platformGeneralFields.put("ShowAccountIcon", "UserInterfaceSetting.ShowAccountIcon");
        platformGeneralFields.put("ShowAssistantIcon", "UserInterfaceSetting.ShowAssistantIcon");
        platformGeneralFields.put("BackgroundPanel.Enable", "UserInterfaceSetting.BackgroundPanel.Enable");
        HashMap<String, String> platformTPM242HWWFields = new HashMap<String, String>(platformGeneralFields);
        platformTPM242HWWFields.put("HighlightedTextColor.Red", "UserInterface.SidePanel.HighlightedTextColor.Red");
        platformTPM242HWWFields.put("HighlightedTextColor.Green", "UserInterface.SidePanel.HighlightedTextColor.Green");
        platformTPM242HWWFields.put("HighlightedTextColor.Blue", "UserInterface.SidePanel.HighlightedTextColor.Blue");
        platformTPM242HWWFields.put("HighlightedTextColor.Alpha", "UserInterface.SidePanel.HighlightedTextColor.Opacity");
        platformTPM242HWWFields.put("NonHighlightedTextColor.Red", "UserInterface.SidePanel.NonHighlightedTextColor.Red");
        platformTPM242HWWFields.put("NonHighlightedTextColor.Green", "UserInterface.SidePanel.NonHighlightedTextColor.Green");
        platformTPM242HWWFields.put("NonHighlightedTextColor.Blue", "UserInterface.SidePanel.NonHighlightedTextColor.Blue");
        platformTPM242HWWFields.put("NonHighlightedTextColor.Alpha", "UserInterface.SidePanel.NonHighlightedTextColor.Opacity");
        platformTPM242HWWFields.put("BackgroundColor.Red", "UserInterface.SidePanel.BackgroundColor.Red");
        platformTPM242HWWFields.put("BackgroundColor.Green", "UserInterface.SidePanel.BackgroundColor.Green");
        platformTPM242HWWFields.put("BackgroundColor.Blue", "UserInterface.SidePanel.BackgroundColor.Blue");
        platformTPM242HWWFields.put("BackgroundColor.Alpha", "UserInterface.SidePanel.BackgroundColor.Opacity");
        platformTPM242HWWFields.put("MainBackgroundColorFilter.Red", "UserInterface.BackgroundPanel.BackgroundImageFilter.Red");
        platformTPM242HWWFields.put("MainBackgroundColorFilter.Green", "UserInterface.BackgroundPanel.BackgroundImageFilter.Green");
        platformTPM242HWWFields.put("MainBackgroundColorFilter.Blue", "UserInterface.BackgroundPanel.BackgroundImageFilter.Blue");
        platformTPM242HWWFields.put("MainBackgroundColorFilter.Alpha", "UserInterface.BackgroundPanel.BackgroundImageFilter.Opacity");
        platformTPM242HWWFields.put("ShowAccountIcon", "UserInterface.ShowAccountIcon");
        platformTPM242HWWFields.put("showPremisesName", "UserInterface.showPremisesName");
        platformTPM242HWWFields.put("showWelcome", "UserInterface.showWelcome");
        platformTPM242HWWFields.put("BackgroundAudio", "UserInterface.BackgroundAudio");
        platformTPM242HWWFields.put("BackgroundPanel.Enable", "UserInterface.BackgroundPanel.Enable");
        platformTPM242HWWFields.remove("ShowAssistantIcon");
        platformFieldMap.put("TPM242HWW", platformTPM242HWWFields);
        platformFieldMap.put("General", platformGeneralFields);
    }
}

